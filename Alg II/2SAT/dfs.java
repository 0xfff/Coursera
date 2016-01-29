// Albert Szmigielski
// alberts@alumni.sfu.ca

import java.io.*;
import java.util.Scanner;
import java.util.LinkedList;

class dfs {
	int vertex_num = 800000*2; //875714
	int n=vertex_num+2 ;
	int h=0;
	int[] leaders = new int [(vertex_num+2)]; // v_size+1 
	int count=0;
	LinkedList <Integer> KStack = new LinkedList();
	/*
	public dfs(int num){
		int vertex_num = num *2;
		int n=vertex_num+2;
		int[] leaders = new int [(vertex_num+2)];
	}
	*/
	/////////////////////////////// DFS LOOP  ///////////////////////////////////////////
	public int dfsloop(LinkedList<Integer>[] G, int[] exp, int v_size, int[] order) 
	{
		KStack.addFirst(999999999);
		for (int i=0; i<vertex_num+2; i++) { leaders[i]=0;}
		int[] t = new int [1];
		int s;
		// go through the vertices in order[] and run dfs
		for (int k=0; k<v_size; k++) //(int s=9; s>0; s--)
		{
			if (k  == 100000){System.out.print("100K ");}
			if (k  == 250000){System.out.print("250K ");}
			if (k  == 500000){System.out.print("500K ");}
			if (k  == 750000){System.out.print("750K ");}
			//System.out.print("order[k]=" + order[k] + "\n");
			
			s=order[k];
			
			//System.out.print("Looking for " + G[j][0] + "\n");
			if (exp[s]==0) {
				//System.out.print("leader: " + s + "\n");
				leaders[h]=s;
				h++;
				dfs(G, s, exp); 
				KStack.addFirst(999999999);	
			}	
		}// end for
		/*
		System.out.print("leaders= ");
		for (int i=0; i<v_size; i++){
			System.out.print( leaders[i] + " ");
			}
		System.out.println();
		*/
		
		return h-1;
		
	}// end dfs loop
	////////////////////////////////////////////////////////////////////////////////////////
	
	
	/////////////////////////////// DFS  ///////////////////////////////////////////
	public int dfs(LinkedList<Integer>[] G, int start_v, int[] exp) 
	{
		
		if (exp[start_v] == 0){
			
			exp[start_v]=1;
			
			///////////////////// CHECK DFS ///////////////
			//System.out.print("Found vertex: " + start_v + "\n");
			/*System.out.print("exp="); 
			for (int i=0; i<10; i++){
				System.out.print( exp[i] +" ");
			}
			*/
			///////////////////////////////////////////////
			
			int current;
			for ( int i1 = 0; i1 < G[start_v].size(); i1++ ) {
				current = G[start_v].get(i1);
				if (exp[current] == 0){
					dfs(G, current, exp);
				}
			}
		KStack.addFirst(start_v);
		}//end if		
		return 0;
	}//end dfs
	////////////////////////////////////////////////////////////////////////////////////////
	
	
	/////////////////////////////// DFS LOOP REV ///////////////////////////////////////////
	public int dfsloop_rev(LinkedList<Integer>[] G, int[] exp, int v_size, int finish_time[], int[] order) 
	{
		int[] t = new int [1];
		for (int s=v_size-1; s>0; s--)
		{
			if (s  == 1000){System.out.print("1K ");}
			if (s  == 10000){System.out.print("10K ");}
			if (s  == 50000){System.out.print("50K ");}
			if (s  == 100000){System.out.print("100K ");}
			if (s  == 250000){System.out.print("250K ");}
			if (s  == 350000){System.out.print("350K ");}
			if (s  == 500000){System.out.print("500K ");}
			if (s  == 750000){System.out.print("750K ");}
			if (s  == 800000){System.out.print("800K ");}
			if (s  == 850000){System.out.print("850K ");}
			if (s  == 875000){System.out.print("875K ");}
			//System.out.print("Looking for " + G[j][0] + "\n");
			dfs_rev(G, s, exp, finish_time, t, order); 					
			
		}// end for
		System.out.println();
		
		
		return 0;
		
	}// end dfsloop_rev
	////////////////////////////////////////////////////////////////////////////////////////
	
	/////////////////////////////// DFS REV ///////////////////////////////////////////
	public int dfs_rev(LinkedList<Integer>[] G_rev, int start_v, int[] exp, int[] finish_time, int[] t, int[] order) 
	{	
		if (exp[start_v] == 0){
			
			exp[start_v]=1;
			/*System.out.print("Found vertex: " + s + "\n");
			System.out.print("exp="); 
			for (int i=0; i<10; i++){
				System.out.print( exp[i] +" ");
			}
			*/
			int current;
			for ( int i1 = 0; i1 < G_rev[start_v].size(); i1++ ) {
				current = G_rev[start_v].get(i1);
				if (exp[current] == 0){
					dfs_rev(G_rev, current, exp, finish_time, t, order);
				}
			}
			
			t[0]++;
			finish_time[start_v]=t[0];
			n--; order[n]=start_v;
			
			//System.out.print("assigning t= " + t[0] + " to vertex " + s + "\n");
		
		}
		return 0;
	}// end dfs_rev
	////////////////////////////////////////////////////////////////////////////////////////
	
	
	/////////////////////////////// DFS COUNT ///////////////////////////////////////////
	public int dfs_count(LinkedList<Integer>[] G, int start_v, int[] explored) 
	{
		
		
		if (explored[start_v] == 0){
			count ++;
			explored[start_v]=1;
			/*
			System.out.print("Found vertex: " + start_v + "\n");
			System.out.print("exp="); 
			for (int i=0; i<10; i++){
				System.out.print( exp[i] +" ");
			}
			*/
			int current;
			for ( int i1 = 0; i1 < G[start_v].size(); i1++ ) {
				current = G[start_v].get(i1);
				if (explored[current] == 0){
					dfs_count(G, current, explored);
				}
			}
		}
		return count;
	}
	////////////////////////////////////////////////////////////////////////////////////////
	

	
	//////////////////////////////////////MAX 10//////////////////////////////////////////
	public int[] max10(int[] numbers, int size, int max_a){
		int temp, temp1, current; 
		int[] max = new int[max_a]; 
		for (int k=0; k<size; k++){
			current = numbers[k];
			//System.out.println("current= "+current);
			for (int i=0; i<max_a; i++){
				if (current > max[i]){
					//System.out.println("current is bigger than "+ max[i]+ " position "+i);
					temp = max[i];
					max[i] = current;
					for (int j=max_a-i; j>i; j--){
						if (j<max_a){max[j] = max[j-1];}
					}
					if (i<max_a-1){max[i+1] = temp;}
					//System.out.println("temp= "+temp);
					//for (int i1=0; i1<5; i1++){System.out.print(max[i1]+" ");}
					//
					break;	
				}
			}
					
		}
		for (int i=0; i<max_a; i++){System.out.print(max[i]+" ");}
		System.out.println();
		return max;
	}
		////////////////////////////////////////////////////////////////////////////////////////
	
/////////////////////////////// DFS check ///////////////////////////////////////////
	public boolean dfs_check_for_x_and_x_not(LinkedList<Integer>[] G, int s, int[] exp, int num_of_variables) {
		//count number of vertices in a scc ?
		// returns TRUE if both x and x not reside in the same SCC
		// returns FALSE if no pair of variables x(i) and x(i) not are in the SCC
	
		int [][] scc_v = new int [num_of_variables+1][2];
		for (int i=0; i<num_of_variables+1; i++){
			scc_v[i][0] = 0; scc_v[i][1] = 0;
		}
		if (s%2000 ==0) 
			System.out.println("SCC with start v # " + s + " is being checked");
		return dfs_check_helper ( G,  s,  exp,  num_of_variables, scc_v);
	}
	
	public boolean 	dfs_check_helper (LinkedList<Integer>[] G, int start_v, int[] explored, int num_of_variables, int[][] scc_v){
		boolean result=true;
		if (explored[start_v] == 0){			
			explored[start_v]=1;
			int current_list_size = G[start_v].size();
			int current;
			//Object[] Array = G[start_v].toArray();
			for ( int i1 = 0; i1 < current_list_size; i1++ ) {
				current = G[start_v].removeFirst();
				//System.out.print( " cls: " + current_list_size + " " );
				//System.out.print(current + ", ");
				//current = G[start_v].get(i1);
				if (explored[current] == 0){
					if (current <= num_of_variables) {
						scc_v[current][0] = 1;
						if (scc_v[current][1] == 1) // we have x(i) and x(i)-not in the same SCC return FALSE
							return false;
					}
					else { // current > num_of_vars
						scc_v[current-num_of_variables][1] =1;
						if (scc_v[current-num_of_variables][0] == 1) // we have x(i) and x(i)-not in the same SCC return FALSE
							return false;
					}
				
					result = dfs_check_helper(G, current, explored, num_of_variables,  scc_v);
					if(!result) return false;
				}
			}
		}
		//System.out.println();
		return result;
	}
	////////////////////////////////////////////////////////////////////////////////////////	
	
}// end class dfs
