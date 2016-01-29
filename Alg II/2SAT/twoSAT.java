// if needed increase stack size by calling main with arg: -Xss8m
// if needed increase heap size by calling main with arg: -Xmx500m
// java  -Xmx2441m 2SAT
// arguments 1: filename
// 			 2: print debug info  stage 1(if a 2nd arg exists - info printed
// 			 3: print debug info  stage 2(if a third arg exists - info printed

import java.io.*;
import java.util.Scanner;
import java.util.Date;
import java.util.BitSet;
import java.util.LinkedList;



public class twoSAT {

public static final int inf = 999999999; //Float.MAX_VALUE;


  ///////////////////////////////////   MAIN ///////////////////////////////////////////
  
public static void main(String args[])throws java.io.IOException
	{
	String filename = "2sat1.txt";
	if (args.length > 0) {filename = args[0];}
		
	Date date = new Date();
	System.out.println(date.toString()); System.out.println();
	
	
	LinkedList<Integer>[] AdjList = new LinkedList[1]; 
	LinkedList<Integer>[] AdjList_rev = new LinkedList[1];
		// positive variables from 1 to num_of_variables
		// negative variables from num_of_variables+1 to (num_of_variables+1)*2
	
	////////////////// READ IN CLAUSES  //////////////////////////
	System.out.println("Working... "); 
	int num_of_clauses = 0;
	int  num_variables=0, index=0, v_size=0;
	
	try{
		Scanner scanner = new Scanner(new File(filename));
		num_variables = scanner.nextInt(); 
		num_of_clauses = num_variables; 
			// in this problem num of clauses is the same as num of variables
		System.out.println("number of clauses = " + num_variables);
		v_size = 	(num_variables+1)*2;
		AdjList = new LinkedList[(num_variables+1)*2];
		AdjList_rev = new LinkedList[(num_variables+1)*2];
		for ( int i = 0; i < AdjList.length; i++ ) {
				AdjList[i] = new LinkedList();
				AdjList_rev[i] = new LinkedList();
				}
		int a,b;		
		while(scanner.hasNextInt() && index < num_variables+1 ){
			//read in a clasue (composed by two variables v1 OR v2)
			a = scanner.nextInt();
			b = scanner.nextInt();
			if (a>0 && b>0){
				AdjList[a+num_variables].add(b);
				AdjList[b+num_variables].add(a);
				
				AdjList_rev[b].add(a+num_variables);
				AdjList_rev[a].add(b+num_variables);
			}
			if (a<0 && b<0){
				AdjList[a*-1].add(b*-1+num_variables);
				AdjList[b*-1].add(a*-1+num_variables);
				
				AdjList_rev[b*-1+num_variables].add(a*-1);
				AdjList_rev[a*-1+num_variables].add(b*-1);
			}
			if (a<0 && b>0){
				AdjList[a*(-1)].add(b); // (a' or b) => edge from a to b. correct
				AdjList[b+num_variables].add((a*-1 +num_variables)); // (a' or b) => edge from b' to a'
				
				AdjList_rev[b].add(a*(-1));
				AdjList_rev[a*-1+num_variables].add(b+num_variables);
			}
			if (a>0 && b<0){
				AdjList[b*(-1)].add(a); // edge b to a. correct
				AdjList[a+num_variables].add(b*-1+num_variables);   // edge a' to b'
				
				AdjList_rev[a].add(b*-1);
				AdjList_rev[b*-1+num_variables].add(a+num_variables);
			}
			
			
			
			// now make the appropriate edges and add to AdjList
			index++;
		}
	}
		
	catch (Exception e){//Catch exception if any
		System.err.println("Error: " + e.getMessage());
	}// end catch
	////////////////////////////////////////////////////////////////////
	
	dfs R = new dfs();
	//boolean []scc_result= new int[v_size];
	int ftime[] = new int [v_size+1];
	int explored[] = new int [v_size+1];
	int[] order = new int[v_size+1];
	for (int i=0; i<v_size; i++){
		explored[i]=0; 
		ftime[i]=0; 
		order[i]=0; 
		R.leaders[i]=0;
	}
	
	int start_vertex =11;
	int result;
		
		
	//run DFS on the graph
	///////    STEP 1 and 2 DFS ON G-REV  /////////////////////////////////////////
	System.out.println("Starting STEP 1 and 2 DFS on G-REV");
	System.out.println(date.toString());
	result=R.dfsloop_rev(AdjList_rev, explored, v_size, ftime, order);
	
	//PRINT first 10 f times
	System.out.println("f times= ");
	for (int i=1; i<15; i++){
		if (i<100) {System.out.print( ftime[i] +", ");}
		else {break;}
	}
	System.out.println("\n");
	//PRINT first 10 order
	System.out.println("order= ");
	for (int i=1; i<v_size; i++){
		if (order[i] > v_size) System.out.print("order[" + i + "] = " + order[i] + " ");
	}
	System.out.println();
	////////////////////////////////////////////////////////////////////////////////////////

	///////    STEP 3 DFS ON G  ////////////////////////////////////////////////////////////
	System.out.println("Starting STEP 3 DFS on G");
		//RESET EXPLORED ARRAY 
	for (int i=0; i<v_size; i++){ explored[i]=0; }
	result = R.dfsloop (AdjList, explored, v_size, order);
	System.out.println("There are " + result + " SCC's");
	System.out.println("Done STEP 3 DFS on G");
	/////////////////////////////////////////////////////////////////////////////////////////
	
	
	/* /////////// CHECK FOR x(i) and x(i)-not in the same SCC ///////////////////////
	boolean scc_result;
	System.out.println("Starting search for x(i) and x(i)-not in the same SCC");
	
	for (int i=0; i<v_size; i++){ explored[i]=0;}
	int num_leaders=0;	
	for (int i=0; i<v_size; i++){
		if (R.leaders[i] != 0){
			num_leaders++;
			//R.count=0;
			scc_result = R.dfs_check_for_x_and_x_not(AdjList, R.leaders[i], explored, num_variables);
			if (!scc_result) {
				System.out.println("SCC with leader " + R.leaders[i] + " reported FALSE");
				break;
			}
		}
	}
	System.out.println("Number of leaders: " + num_leaders);
	*/////////////////////////////////////////////////////////////////////////////////////////
	
	//////////// CHECK FOR x(i) and x(i)-not in the same SCC  based on KStack/////////////////
	System.out.println("Starting search for x(i) and x(i)-not in the same SCC");
	int current=0, scc=0;
	boolean break_now = true;
	int [][] scc_v = new int [num_variables+2][2];
		//clearA(scc_v, num_variables+2);
	int KStack_size = R.KStack.size();
	System.out.println("KStack size is "+ KStack_size);
	
	while ((R.KStack.size()>0) && break_now){
		
		scc++;
		if (scc % 10000 ==0) System.out.print(scc+ ", ");
		current = R.KStack.removeFirst();
		while ( current != 999999999){
			if (current <= num_variables) {
				scc_v[current][0] = scc;
				if (scc_v[current][1] == scc) {
					// we have x(i) and x(i)-not in the same SCC return FALSE
					System.out.println("vertex " + current + " & its complement.  FALSE");
					break_now = false;
					break; 
				}
			}
			else { // current > num_of_vars
				scc_v[current-num_variables][1] =scc;
				if (scc_v[current-num_variables][0] == scc) {
					// we have x(i) and x(i)-not in the same SCC return FALSE
					System.out.println("vertex " + current + " & its complement.  FALSE");
					break_now = false;
					break; 
				}
			}
			current = R.KStack.removeFirst();	
		}
		//clearA(scc_v, num_variables+1);
	}
	if  (args.length > 2) {
	
		/////////////////////// WRITE TO FILE /////////////////////////////////////////////////
		int max_a=10;
		BufferedWriter writer = new BufferedWriter(new FileWriter("output.txt"));
				
		writer.write ("leaders and size " );
		writer.newLine();
		for (int j=0; j<v_size; j++){
			
				writer.write (String.valueOf(R.leaders[j]));
				writer.write (": " );
				
				writer.newLine();
			
			if (R.leaders[j] == 0){break;}
		}
		if (writer != null) {
			writer.flush();
			writer.close();
		}
		
		//////////////////////////////////////////////////////////////////////////////////////
				
	}
	


	
	System.out.println("FINISHED");
	if (break_now) System.out.println("THIS FORMULA IS SATISFIABLE");
	else System.out.println("~~THIS FORMULA IS NOT SATISFIABLE~~");
	date = new Date();
	System.out.println(date.toString());
	
	
	
}//end main	
	
public static void clearA(int[][] a, int size){
	for (int i=0; i<size ; i++){
			a[i][0] = 0; a[i][1] = 0;
		}
}


}//end class

	
	
