import java.util.Scanner;

/*
 * This is the main class having all the functions implementations for the project requirement
 */
public class DijkstraAlgorithm {
	// Static variable to store the paths from one router to all other router
	static String[] paths;
	// Static variable to store the distance from one router to all other router
	static Integer[] totalCost;

	/**
	 * This function is calculating the shortest path from source node to all other
	 * nodes.Also it display the connection table of selected router and shortest
	 * path from source to destination router
	 * 
	 * @param Mat
	 *            -- Topology matrix
	 * @param Matsize
	 *            -- Topology matrix size
	 * @param source
	 *            -- Source router
	 * @param dest
	 *            -- Destination router
	 * @param choice
	 *            -- To display connection table or Shortest path from source to
	 *            destination router
	 */
	public static int[][] calDijkstra(Integer[][] Mat, Integer Matsize, Integer source, Integer dest, Integer choice) {
		// TODO Auto-generated method stub

		// Allocating the memory for paths
		paths = new String[Matsize];
		// Allocating the memory for the totalCost
		totalCost = new Integer[Matsize];
		// Storing the connections between the routers
		int[][] data = new int[Matsize][2];
		int[][] temp = new int[Matsize][Matsize];
		// Storing the distance of each router from other routers
		int distance[] = new int[Matsize];
		// Storing the previously visited routers
		int preDet[] = new int[Matsize];
		// Storing the visited status of the routers
		boolean visited[] = new boolean[Matsize];
		int min = 0;
		int nextNode = 0;
		// For loop for replacing the -1 value to the Short's max value in original
		// topology matrix
		for (int i = 0; i < Matsize; ++i) {
			for (int j = 0; j < Matsize; ++j) {
				if (Mat[i][j] == -1) {
					Mat[i][j] = (int) Short.MAX_VALUE;
				}
				temp[i][j] = Mat[i][j];
			}
		}
		// Initializing the visited,distance,preDest,path and totalCost
		for (int i = 0; i < Matsize; i++) {
			visited[i] = false;
			distance[i] = temp[source][i];
			preDet[i] = source;
			paths[i] = "";
			totalCost[i] = 0;
		}
		distance[source] = 0;
		visited[source] = true;
		// For Loop for calculating the optimal distances of the other nodes from the
		// given source node and storing the previously
		// visited node for calculating the path from source to destination
		for (int itr = 1; itr < Matsize; itr++) {

			min = Short.MAX_VALUE;
			for (int i = 0; i < Matsize; i++) {
				if (distance[i] < min && !(visited[i])) {
					min = distance[i];
					nextNode = i;
				}
			}

			visited[nextNode] = true;
			for (int i = 0; i < Matsize; i++) {
				if (!(visited[i])) {
					if (min + temp[nextNode][i] < distance[i]) {
						distance[i] = min + temp[nextNode][i];
						preDet[i] = nextNode;
					}
				}
			}
		}
		// For Loop for calculating the paths and cost of other routers from source
		// router
		for (int i = 0; i < Matsize; i++) {
			paths[i] = (i + 1) + "R";
			if (distance[i] != Short.MAX_VALUE) {
				totalCost[i] = distance[i];
				data[i][0] = distance[i];
				if (i != source) {
					data[i][1] = i + 1;
					int j = i;
					j = i;
					do {
						j = preDet[j];
						paths[i] = paths[i] + ">-" + (j + 1);
						paths[i] = paths[i] + "R";
						if (j != source) {
							data[i][1] = j + 1;
						}
					} while (j != source);
				}
			} else {
				totalCost[i] = (int) Short.MAX_VALUE;
				data[i][0] = 0;
				data[i][1] = -1;
				paths[i] = "Not Reachable";
			}

		}
		// Printing the connection table of given source router
		if (choice == 2) {
			System.out.println("\nRouter " + (source + 1) + " Connection Table");
			System.out.println("\nDestination\tInterface");
			System.out.println("==========================");
			for (int i = 0; i < Matsize; ++i) {
				System.out.println("");
				if (data[i][1] > 0) {
					System.out.format("R%d\t\tR%d", i + 1, data[i][1]);
				} else if (data[i][1] == 0) {
					System.out.format("R%d\t\t%s", i + 1, "-");
				} else {
					System.out.format("R%d\t\t%s", i + 1,
							"R" + (i + 1) + " not reachable from " + (source + 1) + " or R" + (i + 1) + " is down.");
				}
			}
		}
		// Printing the shortest path from source to destination
		if (choice == 3) {
			String tempPath = new String();
			if (!paths[dest].contains("Not Reachable")) {
				for (int i = paths[dest].length() - 1; i >= 0; i--) {
					tempPath = tempPath + paths[dest].charAt(i);
				}
				System.out.println("Shortest Path from R" + (source + 1) + " to R" + (dest + 1) + " is: " + tempPath
						+ ",the total cost is " + totalCost[dest] + ".");
			} else {
				tempPath = "Not Reachable";
				System.out.println("Shortest Path from R" + (source + 1) + " to R" + (dest + 1) + " is: " + tempPath
						+ ",the total cost is 0.");
			}

		}
		return data;
	}

	/**
	 * This function is used to call the method calculating the shortest from source
	 * to destination and taking the user inputs for source and destination
	 * 
	 * @param inputMatrix
	 *            -- original topology matrix
	 * @param matrixSize
	 *            -- topology matrix size
	 */
	public static void shortestPath(Integer[][] inputMatrix, Integer matrixSize) {
		// TODO Auto-generated method stub
		Integer source, dest = 0;
		Boolean correctInput;
		Scanner scan = new Scanner(System.in);
		do {
			correctInput = false;
			System.out.println("\nSelect a source router:");
			source = Integer.parseInt(scan.next());
			System.out.println("\nSelect a destination router:");
			dest = Integer.parseInt(scan.next());
			if ((source >= 1 && source <= matrixSize) && (dest >= 1 && dest <= matrixSize)) {
				correctInput = true;
				// Function call for calculating the shortest path
				calDijkstra(inputMatrix, matrixSize, source - 1, dest - 1, 3);
			}else {
				System.out.println("Please select the source and destination routers from 1 to "+matrixSize+" only !!!");
			}
		} while (correctInput != true);
		return;
	}

	/**
	 * This function is used to find the best router for the broadcasting which is
	 * having least distance from other routers.
	 * 
	 * @param Mat
	 *            -- original topology matrix
	 * @param Matsize
	 *            -- topology matrix size
	 */
	public static void bestBroadcast(Integer[][] Mat, Integer Matsize) {
		// TODO Auto-generated method stub
		// Stores the sum of cost of the router from all other routers
		int allRoutersTotal[] = new int[Matsize];
		Integer bestRouter = 0;
		// For Loop for calculating the total cost of other routers from the source
		// router.
		for (int i = 0; i < Matsize; i++) {
			// Function call for calculating the cost of other router from ith router
			calDijkstra(Mat, Matsize, i, 0, 0);
			for (int j = 0; j < totalCost.length; j++) {
				if (totalCost[j] == Short.MAX_VALUE)
					totalCost[j] = 0;
			}
			for (int j = 0; j < totalCost.length; j++) {
				allRoutersTotal[i] += totalCost[j];
			}
		}
		int min_value = Integer.MAX_VALUE;
		// For Loop for finding the router with least cost from all other routers
		for (int i = 0; i < allRoutersTotal.length; i++) {
			if (allRoutersTotal[i] < min_value && allRoutersTotal[i] > 0) {
				min_value = allRoutersTotal[i];
				bestRouter = i + 1;
			}
		}
		System.out.println("The Best Router for broadcasting is : R" + bestRouter + "\n");
		calDijkstra(Mat, Matsize, bestRouter - 1, 0, 0);
		String tempPath = new String();
		for (int j = 0; j < paths.length; j++) {
			for (int k = paths[j].length() - 1; k >= 0; k--) {
				tempPath = tempPath + paths[j].charAt(k);

			}
			System.out.println("The Path from R" + bestRouter + " to R" + (j + 1) + " is: " + tempPath + " \tCost="
					+ totalCost[j]);
			tempPath = "";
		}
		System.out.println("\nThe total costs of broadcast from this router to all other routers is :"
				+ allRoutersTotal[bestRouter - 1]);
	}

	/**
	 * This function is used for modifying the topology.User can delete the router
	 * from existing topology.
	 * 
	 * @param Mat
	 *            -- original topology matrix
	 * @param Matsize
	 *            -- topology matrix size
	 * @param delNode
	 *            -- router to be deleted
	 * @param option
	 *            -- for printing the shortest path or for printing the connection
	 *            tables of all routers after the deletion
	 */
	public void updateTopology(Integer[][] Mat, Integer Matsize, int delNode, boolean option) {
		// TODO Auto-generated method stub
		// For Loop for updating the original topology matrix for deletion of selected
		// router
		for (int i = 0; i < Matsize; ++i) {
			for (int j = 0; j < Matsize; ++j) {
				if (Mat[i][j] == Short.MAX_VALUE) {
					Mat[i][j] = -1;
				}
				if (i == delNode || j == delNode) {
					Mat[i][j] = -1;
				}
				if (i == delNode && j == delNode) {
					Mat[i][j] = 0;
				}
			}
		}
		System.out.println(
				"The router is down and  all the connections are set to -1.\nThe Topology is updated as below");
		printMatrix(Mat);
		if (option == true)
			shortestPath(Mat, Matsize);
		else {
			System.out.println("Printing routing tables of all router");
			for (int i = 0; i < Matsize; i++) {
				calDijkstra(Mat, Matsize, i, 0, 2);
			}
		}
	}

	/**
	 * This function is used for printing the topology matrix
	 * 
	 * @param inputMatrix
	 *            -- topology matrix
	 */
	public static void printMatrix(Integer[][] inputMatrix) {
		// TODO Auto-generated method stub
		for (int i = 0; i < inputMatrix.length; i++) {
			for (int j = 0; j < inputMatrix.length; j++) {
				System.out.print(inputMatrix[i][j] + "\t");
			}
			System.out.println();
		}
		return;
	}
}
