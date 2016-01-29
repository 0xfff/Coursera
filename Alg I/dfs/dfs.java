/*
	Simple DFS
	Albert Szmigielski
	alberts@alumni.sfu.ca
*/
import java.io.*;
import java.util.Scanner;
import java.util.LinkedList;

class dfs {
	int vertex_num = 875714; //875714
	int n=vertex_num ;
	int h=0;
	int[] leaders = new int [vertex_num+1]; // v_size+1 875715
	int count=0;
	
	/////////////////////////////// DFS LOOP  ///////////////////////////////////////////
	public int dfsloop(LinkedList<Integer>[] G, int[] exp, int v_size, int[] order) 
	{
		int[] t = new int [1];
		int s;
		for (int k=0; k<v_size; k++) //(int s=9; s>0; s--)
		{
			if (k  == 1000){System.out.print("1K ");}
			if (k  == 10000){System.out.print("10K ");}
			if (k  == 50000){System.out.print("50K ");}
			if (k  == 100000){System.out.print("100K ");}
			if (k  == 250000){System.out.print("250K ");}
			if (k  == 350000){System.out.print("350K ");}
			if (k  == 500000){System.out.print("500K ");}
			if (k  == 750000){System.out.print("750K ");}
			if (k  == 800000){System.out.print("800K ");}
			if (k  == 850000){System.out.print("850K ");}
			if (k  == 875000){System.out.print("875K ");}//System.out.print("order[k]=" + order[k] + "\n");
			s=order[k];
			
			//System.out.print("Looking for " + G[j][0] + "\n");
			if (exp[s]==0) {
				//System.out.print("leader: " + s + "\n");
				leaders[h]=s;
				h++;
				dfs(G, s, exp); 	
			}
								
			
		}// end for
		/*
		System.out.print("leaders= ");
		for (int i=0; i<v_size; i++){
			System.out.print( leaders[i] + " ");
			}
		System.out.println();
		*/
		
		return 0;
		
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
	public int dfs_count(LinkedList<Integer>[] G, int s, int[] exp) 
	{
		
		if (exp[s] == 0){
			count ++;
			exp[s]=1;
			/*
			System.out.print("Found vertex: " + s + "\n");
			System.out.print("exp="); 
			for (int i=0; i<10; i++){
				System.out.print( exp[i] +" ");
			}
			*/
			int current;
			for ( int i1 = 0; i1 < G[s].size(); i1++ ) {
				current = G[s].get(i1);
				if (exp[current] == 0){
					dfs_count(G, current, exp);
				}
			}
			
			
		
		}
		
		return count;
	}
	////////////////////////////////////////////////////////////////////////////////////////
	

	
	//////////////////////////////////////MAX 10//////////////////////////////////////////
	public int[] max10(int[] numbers, int size, int max_a){
			int temp, temp1, current;
			int[] max = new int[max_a]; //{0,0,0,0,0,0,0,0,0,0};
				
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
		
	
}// end class dfs
