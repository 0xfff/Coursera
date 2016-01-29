/*
	Albert Szmigielski
	alberts@alumni.sfu.ca

	increase stack by -Xss1500m,  
	heap size -Xmx1024m
*/

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.util.Scanner;
import java.util.LinkedList;
import java.util.Date;



public class main {
	//////////////////////////////////////MAIN  ////////////////////////////////////////////
	public static void main(String args[])throws java.io.IOException
	{
		boolean read_in = true;
		boolean small_ex = false;
		//if (arg.length>0) {  }
		int v_size= 875714;
		Date date = new Date();
		System.out.println(date.toString());
		if (small_ex) {v_size=70+1;}
		LinkedList<Integer>[] Adjlist = new LinkedList[v_size+1]; 
		LinkedList<Integer>[] Adjlist_rev = new LinkedList[v_size+1];
			
		if (read_in) {
			////////////////// READ IN G and G-REV //////////////////////////
			
			//LinkedList<Integer>[] Adjlist = new LinkedList[v_size+1];
			//LinkedList<Integer>[] Adjlist_rev = new LinkedList[v_size+1];
			for ( int i = 0; i < Adjlist.length; i++ ) {
				Adjlist[i] = new LinkedList();
				Adjlist_rev[i] = new LinkedList();
				}

				
			System.out.println("Working... "); 
			int index=0, value;
				
			try{

				Scanner scanner = new Scanner(new File("SCC.txt"));
				while(scanner.hasNextInt() && index <= v_size ){
					index = scanner.nextInt();
					value = scanner.nextInt();
					Adjlist[index].add(value);  
					Adjlist_rev[value].add(index);
				}// end while
			} //end try
			catch (Exception e){//Catch exception if any
				System.err.println("Error: " + e.getMessage());
			}// end catch
			System.out.println("First 2 values: ");
			System.out.print("Vertex 1: " );
			System.out.println(Adjlist[1].get(0) + " " + Adjlist[1].get(1));
			System.out.print("Vertex 875714: " );
			System.out.println(Adjlist[875714].get(0) + " " + Adjlist[875714].get(1));
			date=new Date(); System.out.println(date.toString());
		}//end if
		//////////////////////////////////////////////////////////////
		
		
		////////////////////SMALL EXAMPLE DATA ////////////////////////////////////////////////
		if (small_ex==true) {
			 
			for (int i = 0; i < Adjlist.length; i++) {
					Adjlist[i] = new LinkedList();
					Adjlist_rev[i] = new LinkedList();
			}
			
			int a_size=119;
			int[][] I1= { {1,1},{1,2},{1,3},{1,4},{1,5},{1,6},{1,7},{1,8},{1,9},{1,10},
			{2,2}, {2,9},{3,3}, {3,4}, {3,9}, {4,4}, {4,10}, {5,5} , {5,6},
			{6,6}, {6,7},
			{7,5},{7,7}, {8,1}, {8,8}, 
			{9,1}, {9,8}, {9,9}, {9,10}, {10,3},{10,10}, 
			{11,12}, {12,13}, {13,14}, {14,15}, {15,16}, {16,17}, {17,18}, {18,19}, {19,20}, {20,21}, 
			{21,22}, {22,11}, {23,24}, {23,25}, {23,29},{24,25},
			{25,26}, {25,27}, {26,24}, {27,28}, {28,26}, {29,30}, {30,23},
			{31,31}, {31,32}, {32,32}, {32,33}, {33,33}, {33,34}, {34,34}, {34,35}, {35,35}, {35,36}, {36,36},
			{37,37}, {38,38}, {39,39}, {40,40},
			{41,41}, {41,42}, {42,42}, {42,43}, {43,43}, {43,44}, {44,44}, {44,45}, {45,45}, {45,46}, {46,46},
			{47,47}, {48,48}, {49,49}, {50,50},
			{51,51}, {51,52}, {52,52}, {52,53}, {53,53}, {53,54}, {54,54}, {54,55}, {55,55}, {55,56}, {56,56},
			{57,57}, {58,58}, {59,59}, {60,60},
			{61,62}, {61,70}, {62,61}, {62,63}, {63,62}, {63,64}, {64,63}, {64,65}, {65,64}, {65,66}, 
			{66,65}, {66,67}, {67,66}, {67,68}, {68,67}, {68,69}, {69,68}, {69,70}, {70,61}, {70,69}
			};
			int vertex =0, vertex_rev=0;
			
				for (int i = 0; i < a_size; i++) {
						vertex = I1[i][0];
						vertex_rev = I1[i][1];
						Adjlist[vertex].add(I1[i][1]);
						Adjlist_rev[vertex_rev].add(I1[i][0]);
						
				}
		}
		//Adjlist[1].add(2); Adjlist[1].add(9); 
		//System.out.println(Adjlist[1].get(0) + " " + Adjlist[1].get(1));
		/*
		for (int j = 1; j < v_size; j++ ) {  
			System.out.print("Vertex " + j + ": ");
			for (int i = 0; i < Adjlist_rev[j].size(); i++) {
				System.out.print(Adjlist_rev[j].get(i) + " ");
			}
			System.out.println();
		}
		*/
		////////////////////////////////////////////////////////////////////////////////////////
		
		dfs R = new dfs();
		int []scc_size= new int[v_size];
		int ftime[] = new int [v_size+1];
		int explored[] = new int [v_size+1];
		int[] order = new int[v_size+1];
		for (int i=0; i<v_size; i++){
		explored[i]=0; ftime[i]=0; order[i]=0; R.leaders[i]=0;
		}
		
		int start_vertex =11;
		int result;
		
		
		
		//////////////////    STEP 1 and 2 DFS ON G-REV  /////////////////////////////////////////
		System.out.println("Starting STEP 1 and 2 DFS on G-REV");
		System.out.println(date.toString());
		result=R.dfsloop_rev(Adjlist_rev, explored, v_size, ftime, order);
		
			//PRINT first 10 f times
			System.out.print("f times=");
			for (int i=0; i<100; i++){
				if (i<100) {System.out.print( ftime[i] +" ");}
				else {break;}
			}
			System.out.println();
			//PRINT first 100 order
			System.out.print("order= ");
			for (int i=0; i<100; i++){
				System.out.print( order[i] + " ");
				if (v_size==100){break;}
			}
			System.out.println();
		////////////////////////////////////////////////////////////////////////////////////////
		
		
		///////    STEP 3 DFS ON G  ////////////////////////////////////////////////////////////
		System.out.println("Starting STEP 3 DFS on G");
			//RESET EXPLORED ARRAY 
		for (int i=0; i<v_size; i++){
		explored[i]=0; 
		}
		result = R.dfsloop (Adjlist, explored, v_size, order);
		/////////////////////////////////////////////////////////////////////////////////////////
	
		
		
		//////////////// COUNT and PRINT SCC's //////////////////////////////////////////////////////
		System.out.println("Starting Count of SCC's");
		int indx=0;
		for (int i=0; i<v_size; i++){ explored[i]=0;
		}
		
		for (int i=0; i<v_size; i++){
			if (R.leaders[i] != 0){
				R.count=0;
				scc_size[indx] = R.dfs_count(Adjlist, R.leaders[i], explored);
				if (scc_size[indx] > 100) {
					System.out.println("leader = " + R.leaders[i] + " size = " + scc_size[indx] );
				}
		
				indx++;
			}
		}
		/////////////////////////////////////////////////////////////////////////////////////////
		
		/////////////////////////////////// MAX 10  //////////////////////////////////////////////
		System.out.println("Starting MAX10");
		int max_a=15;
		int[] max_scc = new int[max_a];
		max_scc=R.max10(scc_size,v_size,max_a);
		/////////////////////////////////////////////////////////////////////////////////////////
		
		/////////////////////// WRITE TO FILE /////////////////////////////////////////////////
		BufferedWriter writer = new BufferedWriter(new FileWriter("output.txt"));
		writer.write ("max 10: " );
		writer.newLine();
		for (int j=0; j<max_a; j++){
			writer.write (String.valueOf(max_scc[j]));
			writer.write (" " );
		}
		writer.newLine();
		writer.newLine();
		
		writer.write ("leaders and size " );
		writer.newLine();
		for (int j=0; j<v_size; j++){
			if (scc_size[j] >100) {
				writer.write (String.valueOf(R.leaders[j]));
				writer.write (": " );
				writer.write (String.valueOf(scc_size[j]));
				writer.newLine();
			}
			if (R.leaders[j] == 0){break;}
		}
		if (writer != null) {
			writer.flush();
			writer.close();
		}
		
		//////////////////////////////////////////////////////////////////////////////////////////
		
		//result = R.dfs(Adjlist, start_vertex, explored); 
		
		
		
		
		
		
		System.out.println("Done");
		date=new Date();
		System.out.println(date.toString());
		
	} //end main
	

}	//end class
