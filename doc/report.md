The data structure in this project is the adjacency list. Comparing to the adjacency matrix, the adjacency list can save some space.

DFS algorithm is used to traversal the graph. It is tested on a simple version graph which i has created myself and works fine, then it is tested on the reduced data in the project. Because my computer has a limited memory, so i only run the algorithm on the first 1000000 contigs. But we can easily change the max whenever the computer has enough memory to run the full data.

In order to check the neighbor of each vertices, a set is created to store the vertices which takes extra memory.
Anyway the time complexity for the algorithm is O（V+E).
 
The result is as below:
Total vertices: 365194 total edges: 999889 for first 1000000 records
The number of components of G with at least three vertices is 11971.
The number of cliques in G is 15.
The fraction of above components that are cliques is 0.0012530282.

Histogram --- the number of nodes with 1 - 10 neighbors
---------------------------------------------------------------
1         219699    
2         33067     
3         14592     
4         11667     
5         10327     
6         10241     
7         9994      
8         10008     
9         8285      
10        6878         


