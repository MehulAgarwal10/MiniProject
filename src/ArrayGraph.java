import com.mxgraph.swing.mxGraphComponent;
import com.mxgraph.util.mxConstants;
import com.mxgraph.view.mxGraph;

import javax.swing.*;
import java.awt.*;
import java.util.LinkedList;
import java.util.Scanner;

/**
 * Created by mehulagarwal on 17/11/16.
 */
public class ArrayGraph {

    static int V;
    static JFrame frame;
    static Object vertices[];
    static mxGraph mxgraph;
    static Object mxDefaultParent;
    static int graph[][];
    static Object edgeMap[][];
    static int rGraph[][];
    static int flowGraph[][];
    static LinkedList<Object> edgeList = new LinkedList<>();
    public static void main2(String args[])
    {

        Scanner sc = new Scanner(System.in);
        String str;
        System.out.println("Enter number of vertices : ");
        V = sc.nextInt();
        flowGraph = new int[V][V];
        vertices = new Object[V];
        frame = new JFrame("Network Flow graph");
        frame.setSize(700, 700);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        mxgraph= new mxGraph();
        mxGraphComponent graphComponent= new mxGraphComponent(mxgraph);
        mxDefaultParent = mxgraph.getDefaultParent();

        frame.add(graphComponent, BorderLayout.CENTER);
        frame.setVisible(true);
        int i;
        double x = 150.00;
        double y = 150.00;
        double height = 60;
        double width = 80;
        String id[] = new String[V];
        for(i=0;i<V;i++)
        {
            if(i == 0)
            {
                id[i] = "s";
            }
            else if(i == V-1)
            {
                id[i] = "t";
            }
            else
                id[i] = "v" + i;
        }
        for(i=0;i<V;i++)
        {
            mxgraph.getModel().beginUpdate();
            try
            {

                vertices[i] = mxgraph.insertVertex(mxDefaultParent,null,id[i],x,y,width,height,"fillColor=yellow");
            }
            finally
            {
                mxgraph.getModel().endUpdate();
            }
            //str = sc.nextLine();


        }
        sc.nextLine();
        System.out.println("Press enter to map edges .. ");
        str = sc.nextLine();
        graph =new int[][] {
                {0, 9, 9, 0, 0, 0},
                {0, 0, 10, 8, 0, 0},
                {0, 0, 0, 1, 3, 0},
                {0, 0, 0, 0, 0, 10},
                {0, 0, 0, 8, 0, 7},
                {0, 0, 0, 0, 0, 0}
        };

        edgeMap = new Object[V][V];
        //createMap();
        int ans = fordFulkerson(0,5);

    }


    static boolean bfs(int rGraph[][], int s, int t, int parent[])
    {
        // Create a visited array and mark all vertices as not
        // visited
        boolean visited[] = new boolean[V];
        for(int i=0; i<V; ++i)
            visited[i]=false;

        // Create a queue, enqueue source vertex and mark
        // source vertex as visited
        LinkedList<Integer> queue = new LinkedList<Integer>();
        queue.add(s);
        visited[s] = true;
        parent[s]=-1;

        // Standard BFS Loop
        while (queue.size()!=0)
        {
            int u = queue.poll();

            for (int v=0; v<V; v++)
            {
                if (visited[v]==false && rGraph[u][v] > 0)
                {
                    queue.add(v);
                    parent[v] = u;
                    visited[v] = true;
                }
            }
        }

        // If we reached sink in BFS starting from source, then
        // return true, else false
        return (visited[t] == true);
    }

    static int fordFulkerson(int s, int t)
    {
        int u, v;
        Scanner sc = new Scanner(System.in);
        String temp;
        // Create a residual graph and fill the residual graph
        // with given capacities in the original graph as
        // residual capacities in residual graph

        // Residual graph where rGraph[i][j] indicates
        // residual capacity of edge from i to j (if there
        // is an edge. If rGraph[i][j] is 0, then there is
        // not)
        rGraph = new int[V][V];
        for (u = 0; u < V; u++)
            for (v = 0; v < V; v++)
                rGraph[u][v] = graph[u][v];

        // This array is filled by BFS and to store path
        int parent[] = new int[V];

        int max_flow = 0;  // There is no flow initially

        // Augment the flow while tere is path from source
        // to sink
        int i,j;
        while (bfs(rGraph, s, t, parent))
        {
            for(i=0;i<V;i++)
            {
                for(j=0;j<V;j++)
                {
                    flowGraph[i][j] = graph[i][j] - rGraph[i][j];
                }
            }
            createMap();
            String str;
            str = sc.nextLine();
            // Find minimum residual capacity of the edhes
            // along the path filled by BFS. Or we can say
            // find the maximum flow through the path found.
            int path_flow = Integer.MAX_VALUE;
            for (v=t; v!=s; v=parent[v])
            {
                u = parent[v];
                path_flow = Math.min(path_flow, rGraph[u][v]);
            }

            // update residual capacities of the edges and
            // reverse edges along the path
            for (v=t; v != s; v=parent[v])
            {
                u = parent[v];
                rGraph[u][v] -= path_flow;
                rGraph[v][u] += path_flow;
            }

            // Add path flow to overall flow
            max_flow += path_flow;
            System.out.println("Current Flow : " + max_flow);
            //temp = sc.nextLine();
        }
        createMap();
        // Return the overall flow
        return max_flow;
    }

    static void createMap()
    {

        int i,j;
        mxgraph.getModel().beginUpdate();
        try
        {
            while(edgeList.isEmpty() == false)
            {
                Object ed1 = edgeList.pop();
                mxgraph.getModel().remove(ed1);
            }
        }
        finally
        {
            mxgraph.getModel().endUpdate();
        }
        edgeList.clear();
        System.out.println("Beginning mapping ");
        Scanner sc = new Scanner(System.in);
        String wait = sc.nextLine();
        int value;
        for(j=0;j<V;j++)
        {
            for(i=0;i<V;i++)
            {
                if(flowGraph[i][j] > 0)
                {
                    if(flowGraph[j][i] > flowGraph[i][j])
                    {
                        continue;
                    }
                    else
                    {
                        value = flowGraph[i][j] - flowGraph[j][i];
                    }
                    mxgraph.getModel().beginUpdate();
                    try
                    {
                        edgeMap[i][j] = mxgraph.insertEdge(mxDefaultParent,"","" + value,vertices[i],vertices[j]);
                        Object edge1 = edgeMap[i][j];
                        edgeList.add(edge1);
                        mxgraph.setCellStyles(mxConstants.STYLE_VERTICAL_ALIGN, mxConstants.ALIGN_MIDDLE, new Object[] {edge1});
                    }
                    finally
                    {
                        mxgraph.getModel().endUpdate();
                    }

                }
            }
        }
    }
}
