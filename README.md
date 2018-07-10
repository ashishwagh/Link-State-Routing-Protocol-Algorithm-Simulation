# Link-State-Routing-Protocol-Algorithm-Simulation

Suppose we have a network with an arbitrary number of routers. The network
topology is given by a matrix, called the original topology (graph) matrix, which only
indicates the costs of links between all directly connected routers. We assume each
router only knows its own information and has no knowledge about others at the
beginning.

In this project, to implement Link-State Routing Protocol, first, the program is
required to create the state of the links by each router after the input file containing
the network information been loaded. By reading the topology matrix file a network
graph can be determined. A Dijkstra’s algorithm could be applied to find the
shortest path between two entities: source and destination nodes. Finally, the
program should be able to output the connection table of any router and output the
optimal path between any two selected routers.
