// Albert Szmigielski
// alberts@alumni.sfu.ca
// g++ -g kruskal.cpp -o kruskal

#include <iostream>
#include <fstream>
#include <list>
using namespace std;

typedef struct
{
    int vertex1;
    int vertex2;
    float weight;
	
} EDGE;

typedef struct
{
    int adj_to;
	int cost;
} adj_V;

//forward declaration
void readInput(char * filename);
void inputTest(int size);



int num_of_vertices = 0, num_of_edges =0;
EDGE * EDGES;
list<adj_V> * AdjList;


/*****************************    MAIN     *********************************/
int main(int argc, char** argv)
{

	char f[]="clustering1.txt";
	char  * filename;
	filename=f;
	
	
	readInput(filename);
	
	inputTest(10);


 
return 0;
}

void readInput(char * filename){
	int i=0, v1, v2, weight;
	adj_V adj_vertex;
	ifstream myfile;
	myfile.open (filename);
	myfile >> num_of_vertices;
	//There is one edge (i,j) for each choice of 1≤i<j≤n, where n is the number of nodes
	num_of_edges = num_of_vertices * (num_of_vertices -1) / 2 ;
	// create Adj List, now that you know the number of vertices
	EDGES = new EDGE[num_of_edges];
	AdjList = new list<adj_V>[num_of_vertices+1];

	while(myfile >>  v1 >>  v2 >> weight ) //!myfile.eof()
		{
		EDGES[i].vertex1 = v1;	
		EDGES[i].vertex2 = v2;
		EDGES[i].weight = weight;
		adj_vertex.adj_to=v2;
		adj_vertex.cost=weight;
		AdjList[v1].push_back(adj_vertex);
		//add the other way to adj list as well if (a adj b) then (b adj a)
		adj_vertex.adj_to=v1;
		AdjList[v2].push_back(adj_vertex);
		
		i++;
		}
	cout << i <<endl;
	myfile.close();



}

void inputTest(int size){
	cout << num_of_vertices << " " << num_of_edges << "\n";
	
	for (int i=0; i<size; i++) {
		if (i<num_of_edges){
			cout << i << "\t" << EDGES[i].vertex1 << "\t" << EDGES[i].vertex2 << "\t" 			 << EDGES[i].weight << "\n";
		}
		else
			cout << "Size exceeds number of edges \n";
	}
	
}


