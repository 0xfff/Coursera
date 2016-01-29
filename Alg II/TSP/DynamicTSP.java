// Albert Szmigielski
// alberts@alumni.sfu.ca
// if needed increase stack size by calling main with arg: -Xss8m
// if needed increase heap size by calling main with arg: -Xmx500m
// java  -Xmx1441m main
// arguments 1: filename
// 			 2: print debug info  stage 1(if a 2nd arg exists - info printed
// 			 3: print debug info  stage 2(if a third arg exists - info printed

import java.io.*;
import java.util.Scanner;
import java.util.Date;
import java.util.BitSet;
import java.util.LinkedList;


public class DynamicTSP {

public static final int v_size=24;
public static final int num_of_subsets = (int)Math.pow(2,v_size-1);
static float [][] A = new float [num_of_subsets][v_size+1];	
public static final int inf = 999999999; //Float.MAX_VALUE;

public static boolean IntToBool(int intValue)
{
return (intValue != 0);
}

 public static BitSet convert(int value) {
    BitSet bits = new BitSet();
    int index = 0;
    while (value != 0) {
      if (value % 2 != 0) {
        bits.set(index);
      }
      ++index;
      value = value >>> 1;
    }
    return bits;
  }

  public static int convert(BitSet bits) {
    int value = 0;
    for (int i = 0; i < bits.length(); ++i) {
      value += bits.get(i) ? (1 << i) : 0;
    }
    return value;
  }

  public static void make_v_list(int sn, LinkedList List){
	BitSet bits = new BitSet(v_size-1);
	bits = convert(sn);
	for (int i = 0; i < bits.length(); ++i) {
      if (bits.get(i)) List.add(v_size-i);
    }
	List.add(1);
	//for (int j=0; j<List.size(); j++)
	//	System.out.print(List.get(j) + " ");
	//System.out.println();
  }
  
  public static int get_subset_number(int csn, int j){
	BitSet bits = new BitSet(v_size-1);
	bits = convert(csn);
	bits.clear(v_size-j);
	int subset_num = convert(bits);
	return subset_num;
  }

  ////////////////////////////////////////////////////////////////////////////////////////////
  
public static void main(String args[])throws java.io.IOException
	{
	String filename = "tsp.txt";
	if (args.length > 0) {filename = args[0];}
	
	
	Date date = new Date();
	System.out.println(date.toString()); System.out.println();
	
	
	//LinkedList<D_edge>[] Adjlist = new LinkedList[v_size+1]; 
	float[][] DistMatrix = new float[v_size+1][v_size+1];; 
	float[][] cities = new float [v_size+1][2];	
	////////////////// READ IN G  //////////////////////////
	System.out.println("Working... "); 
	int  v_size_l=0, index=0, vertex=0, distance=0;
	float x=0, y=0, z=0, w=0;
	try{
		Scanner scanner = new Scanner(new File(filename));
		v_size_l = scanner.nextInt(); // number of vertices
		
		System.out.println("number of vertices " + v_size);
				
		DistMatrix = new float[v_size+1][v_size+1];
		for (int i=0; i<v_size+1; i++){
			for (int j=0; j<v_size+1; j++){
				DistMatrix[i][j]=(float) 0.0;
			}
		}
		cities = new float [v_size+1][2]; 
		for (int i=0; i<v_size+1; i++){
			for (int j=0; j<2; j++){
				cities[i][j]= (float) 0.0;
			}
		}
		
		//System.out.println("before while");
		
		while(scanner.hasNextFloat() && index < v_size ){
			cities[index][0] = scanner.nextFloat();
			cities[index][1] = scanner.nextFloat();
			index++;
		}
		/*
		for (int i=0; i<10; i++){
			System.out.println(items[i].value +" "+ items[i].weight );
		}
		*/			
	}
		
	catch (Exception e){//Catch exception if any
		System.err.println("Error: " + e.getMessage());
	}// end catch
	////////////////////////////////////////////////////////////////////
	
	//calculate distance matrix
	
	for (int i=0; i<v_size+1; i++){
		for (int j=0; j<v_size+1; j++){
			if (i<j){
				x = cities[i][0];
				y = cities[i][1];
				z = cities[j][0];
				w = cities[j][1];
				DistMatrix[i][j] = (float) ( Math.sqrt  ( (x-z)*(x-z) + (y-w)*(y-w) ));
				DistMatrix[j][i] = DistMatrix[i][j]  ;
			}
		}
	}
	//print distance matrix
	if  (args.length > 2) {
		for (int i=0; i<v_size+1; i++){
			for (int j=0; j<v_size+1; j++){
				System.out.print(DistMatrix[i][j] + " ");
			}
			System.out.println();
		}
	}
	System.out.println("SUBSETS");
	
	System.out.println("num_of_subsets " + num_of_subsets);
	int [] cardinality = new int[num_of_subsets];
	LinkedList<Integer>[] Subsets_of_size = new LinkedList[v_size+1];
	for ( int i = 0; i < v_size+1; i++ ) {
		Subsets_of_size[i] = new LinkedList<Integer>();
	}
	int subset;
	for (int i=0; i<num_of_subsets; i++){
		subset = convert(i).cardinality() +1;
		cardinality[i] = subset;
		Subsets_of_size[subset].add(i);
	}
	cardinality = null; 
	for (int i=0; i<num_of_subsets; i++){
		for (int vi=0; vi<=v_size; vi++){
			A[i][vi]=inf;
		}
	}
	A[0][1] = 0;
		
	//// DYNAMIC TSP
	LinkedList<Integer> v_list = new LinkedList<Integer>();
	int index2=0, index1=0, current_subset_number=-1;
	int current_j=0, current_k=0;
	float calc=0,  min=inf;
	for (int m=2; m<=v_size; m++) {
		System.out.println("m = " + m);
		Subsets_of_size[m-1].clear();
		index2 = Subsets_of_size[m].size(); //list of subset numbers of size m
		for (int sn=0; sn<index2; sn++){
			//make a list of vertices for that subset
			v_list.clear();
			current_subset_number=Subsets_of_size[m].get(sn);
			make_v_list( current_subset_number, v_list);
			index1= v_list.size();
			min=inf;
				for (int vi=0; vi<index1; vi++){
					current_j = v_list.get(vi);
					if (current_j !=1) {
						for (int ki=0; ki<index1; ki++){
							current_k = v_list.get(ki);
							if (current_k != current_j)
								calc = A[get_subset_number(current_subset_number, current_j)][current_k] + 
											DistMatrix[current_k][current_j];
								if (calc<min) min=calc;
								A[current_subset_number][current_j] = min;
						}
					}
				}
			
		}
	
	}
	float final_min = inf;
	float final_tsp=0;
	for (int i=2; i<=v_size;i++){
		final_tsp = A[num_of_subsets-1][i] + DistMatrix[i][1];
		//System.out.println("i= " +i+ " A[S][" +i +"] = " + A[num_of_subsets-1][i]);
		if (final_tsp < final_min) final_min=final_tsp;
		
	}
	System.out.println("Final TSP tour = " + final_tsp);
	//print A matrix
	System.out.println("MATRIX A");
	if  (args.length > 2) {
		for (int i=1; i<num_of_subsets; i++){
			for (int j=1; j<v_size+1; j++){
				System.out.print(A[i][j] + " ");
			}
			System.out.println();
		}
	}
	
	
	System.out.println();
	date = new Date();
	System.out.println(date.toString());
	
	
	
}//end main	
	



}//end class

	
	
