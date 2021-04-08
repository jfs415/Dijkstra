package com.jon.assignment5;

// Author: Jon Healy
// Assignment 5

import java.util.LinkedList;
import java.util.ListIterator;

class UnweightedGraph {

	private int N; // number of nodes in the graph
	private char[] names; // names of each node
	private LinkedList<Integer> adj[]; // adjacency list for each node

	public UnweightedGraph(int numNodes, char[] nodeNames) {
		N = numNodes;
		adj = new LinkedList[N]; // create an adjacency list for each node
		names = new char[N];

		for (int i = 0; i < N; i++) {
			adj[i] = new LinkedList(); // create empty list of edges
			names[i] = nodeNames[i]; // name the current node
		}
	}

	public void addEdge(int startNode, int endNode) {
		adj[startNode].add(endNode);
	}

	public int getNumNodes() {
		return N;
	}

	public char[] getNodeNames() {
		return names;
	}

	public LinkedList<Integer>[] getAdjList() {
		return adj;
	}

	public String toString() {
		String output = "";

		for (int i = 0; i < N; i++) {
			output += names[i] + ": ";

			ListIterator iter = adj[i].listIterator();

			while (iter.hasNext()) {
				output += names[(int) iter.next()] + " ";
			}

			output += "\n";
		}

		return output;
	}

}

public class Dijkstra {

	private static char[] nodeNames;

	private static int[][] dijkstra(int graph[][], int source) {
		int N = graph.length;
		// denotes shortest distance from source node to all other nodes
		int distances[] = new int[N];
		// indicates if the node has been visited or not (defaults to false)
		boolean visited[] = new boolean[N];

		// initialize shortest distance to all nodes as "infinity"
		for (int i = 0; i < N; i++) {
			distances[i] = Integer.MAX_VALUE;
		}

		distances[source] = 0; // distance from source vertex to itself is 0

		// find shortest path to all nodes
		for (int count = 0; count < N - 1; count++) {
			// choose the minimum distance node from the set of nodes
			// not yet visited
			int min = Integer.MAX_VALUE;
			int minIndex = -1;

			for (int i = 0; i < N; i++) {
				if (!visited[i] && distances[i] <= min) {
					min = distances[i];
					minIndex = i;
				}
			}

			// mark the minimum distance node as visited
			visited[minIndex] = true;

			for (int i = 0; i < N; i++) {
				for (int j = 0; j < distances[i] + 1; j++){
					// Update distances only if node has not been visited,
					// there is an edge from minimum distance node to node i,
					// and the total length of path from source to node i
					// via minimum distance node is smaller than current value
					// stored in distances
					if (!visited[i] && graph[minIndex][i] != 0 && distances[minIndex] + graph[minIndex][i] < distances[i]) {
						//					System.out.println("DIST: " + distances[minIndex] + "\n GRAPH: " + graph[minIndex][i] + "\n NODE: " + nodeNames[i]);
						distances[i][j] = distances[minIndex] + graph[minIndex][i];
					}
				}
			}
		}

		return distances;
	}

	private static void printDistances(int distances[], char[] nodes) {
		int N = distances.length;
		System.out.println("Node  Distance");

		for (int i = 0; i < N; i++) {
			System.out.println(nodes[i] + "\t\t" + distances[i]);
		}
	}

	public static void main(String[] args) {
		nodeNames = new char[] { 'A', 'B', 'C', 'D', 'E' }; // Nodes 0, 1, 2, 3, 4
		int graph[][] = new int[][] { { 0, 6, 0, 1, 0 },
		                              { 6, 0, 5, 2, 2 },
		                              { 0, 5, 0, 0, 5 },
		                              { 1, 2, 0, 0, 1 },
		                              { 0, 2, 5, 1, 0 } };

		int distances[] = dijkstra(graph, 0);

		printDistances(distances, nodeNames);
	}

}