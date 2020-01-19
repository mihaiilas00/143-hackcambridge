https://docs.microsoft.com/en-us/azure/azure-maps/

https://docs.microsoft.com/en-us/azure/azure-maps/tutorial-route-location

https://devpost.com/software/143-hackcambridge/joins/xP9uT5SGALgMfqrc0hmkGg


The data is modeled as a graph where the nodes represent bins and the edges are roads that connect them. The cities are split into regions and each day all the bins from one region need to be emptied. The algorithm we implemented analyzes the data from the hardware on the bins and selects the most critical region that needs to  be cleaned. It then finds a path to that region and back to the waste center that minimizes fuel and maximizes how much trash is collected on our way, using a modified Dijkstra techique for minimal paths.
