import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;

/*
 * LinkStateRouteSim class for screen menu options and for calling the methods of DijkstraAlgorithm
 */
public class LinkStateRouteSim {
	// static variable to store the data in matrix read from topology file
	static Integer inputMatrix[][];
	// static variable to store the size of topology matrix
	static Integer matrixSize = 0;
	static DijkstraAlgorithm da = new DijkstraAlgorithm();

	/**
	 * main method for displaying the Menu options and calling related functions
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		// Object for calling the class method
		LinkStateRouteSim lsr = new LinkStateRouteSim();
		// Object of scanner class for scanning the input
		Scanner sc = new Scanner(System.in);
		String choice, filename;
		Integer source;
		int delRouter;
		boolean option3 = false, option1 = false, correctInput;
		// Do-While loop for iterative menu display
		do {
			System.out.println("\n\nCS542 Link State Routing Simulator\n\n" + "(1) Create a Network Topology\n"
					+ "(2) Build a Forward Table\n" + "(3) Shortest Path to Destination Router\n"
					+ "(4) Modify a Topology (Change the status of the Router)\n" + "(5) Best Router for Broadcast\n"
					+ "(6) Exit\n" + "Please enter your choice:");

			choice = sc.next();
			switch (choice) {
			case "1":// case for reading the input topology file and displaying the matrix
				do {
					correctInput = false;
					System.out.println("\nInput original network topology matrix data file:");
					filename = sc.next();
					if (filename.contains(".txt")) {
						correctInput = true;
					} else {
						System.out.println("Enter the filename with extension");
					}
				} while (!correctInput);
				// Function call for reading the input topology file
				lsr.readFile(filename);
				System.out.println("\nReview original topology matrix:\n");
				// Function call for displaying the input topology matrix
				da.printMatrix(inputMatrix);
				option3 = false;
				option1 = true;
				break;
			case "2":// case for displaying the Interface table of selected router
				if (option1 == true) {
					do {
						correctInput = false;
						System.out.println("\nSelect a source router:");
						source = Integer.parseInt(sc.next());
						if (source >= 1 && source <= matrixSize) {
							correctInput = true;
							// Function call for calculating the shortest path and printing the Interface
							// table
							da.calDijkstra(inputMatrix, matrixSize, source - 1, 0, 2);
						}else {
							System.out.println("Please select the router from 1 to "+matrixSize+" only !!!");
						}
					} while (correctInput != true);
				} else {
					System.out.println("Please attend the Create a Network Topology function first!!! ");
				}
				break;
			case "3":// case for calculating the shortest path between the source and destination
				if (option1 == true) {
					// Function call for calculating the shortest path between the source and
					// destination
					da.shortestPath(inputMatrix, matrixSize);
					option3 = true;
				} else {
					System.out.println("Please attend the Create a Network Topology function first!!! ");
				}
				break;
			case "4":// case for modifying the the topology
				if (option1 == true) {
					do {
						correctInput = false;
						System.out.println("Delete a router from this network topology");
						System.out.println("Enter the router that you want to removed:");
						delRouter = sc.nextInt();
						if (delRouter >= 1 && delRouter <= matrixSize) {
							correctInput = true;
							// Function call for updating the topology and displaying the updated topology
							da.updateTopology(inputMatrix, matrixSize, delRouter - 1, option3);
						}else {
							System.out.println("Please select the router from 1 to "+matrixSize+" only !!!");
						}
					} while (correctInput != true);
				} else {
					System.out.println("Please attend the Create a Network Topology function first!!! ");
				}
				break;
			case "5":// case for displaying the best router for broadcast
				if (option1 == true) {
					da.bestBroadcast(inputMatrix, matrixSize);
				} else {
					System.out.println("Please attend the Create a Network Topology function first!!! ");
				}
				break;
			case "6":// Exit call
				System.out.println("Exit CS542-04 2017 Fall project. Good Bye!");
				option3 = false;
				System.exit(0);
				break;
			default: // Default case
				System.out.println("Please enter correct choice !!!");
				option3 = false;
				break;
			}
		} while (choice != "6");
		sc.close();
	}

	/**
	 * method for reading the topology input file from project folder
	 * 
	 * @param filename
	 */
	void readFile(String filename) {
		File inFile = new File(filename);
		String line = null, rowLine[];
		Integer rowLen = 0, colLen = 0, temp = 0;
		try {
			FileReader fileReader = new FileReader(inFile);
			BufferedReader bufferedReader = new BufferedReader(fileReader);
			while ((line = bufferedReader.readLine()) != null) {
				rowLine = line.split(" ");
				if (colLen < rowLine.length)
					colLen = rowLine.length;
				rowLen++;
			}
			bufferedReader.close();
			BufferedReader reader = new BufferedReader(new FileReader(inFile));
			inputMatrix = new Integer[rowLen][colLen];
			while ((line = reader.readLine()) != null && temp <= rowLen) {
				rowLine = line.split(" ");
				for (int i = 0; i < colLen; i++) {
					inputMatrix[temp][i] = Integer.parseInt(rowLine[i]);
				}
				temp++;
			}
			matrixSize = inputMatrix.length;
			reader.close();
		} catch (FileNotFoundException ex) {
			System.out.println("Unable to open file '" + filename + "'");
		} catch (IOException ex) {
			System.out.println("Error reading file '" + filename + "'");
		}
		return;
	}
}
