import java.util.Arrays;
import java.util.Scanner;

/**
 * Created by mehulagarwal on 20/8/16.
 */
public class Graph {

    public static NodeList<Integer> graph[];
    public static int SIZE;
    private static int edges,vertices;


    Graph()
    {
        SIZE = 5;
        graph = new NodeList[SIZE];
        int i;
        for(i=0;i<SIZE;i++)
        {
            graph[i] = new NodeList<>();
        }
    }


    Graph(int num)
    {
        SIZE = num;
        graph = new NodeList[SIZE];
        int i;
        for(i=0;i<SIZE;i++)
        {
            graph[i] = new NodeList<>();
        }
    }

    void getSize(int num)
    {
        if(num > SIZE)
        graph = Arrays.copyOf(graph,num);
    }



    public void displayGraph()
    {

        int i = 0;
        System.out.println("Graph Adjacency List..");
        for(i=0;i<SIZE;i++)
        {

            System.out.print((i+1) + " ("+graph[i].colour+"),"+"\t"+" (Parent = "+(graph[i].parent+1)+"), (Distance from source = "+graph[i].d+") :--> ");
            graph[i].displayList();
        }
    }

    public void addEdge(int vertex, int edge)
    {

        if(vertex < 1 || vertex > SIZE)
        {
            System.out.println("Invalid Vertex input");
            return;
        }
        if(edge < 1 || edge > SIZE)
        {
            System.out.println("Invalid edge value ");
            return;
        }
        System.out.println("Adding edge "+edge+" to vertex "+vertex);
        graph[vertex-1].add(Integer.valueOf(edge-1));
    }

    public void BFS(int source)
    {

        int i = 0;
        for(i=0;i<SIZE;i++)
        {
            if(i != (source-1))
            {
                graph[i].colour = "WHITE";
                graph[i].parent = graph[i].d = 0;
            }
        }

        int s = source-1;
        graph[s].colour = "GRAY";
        graph[s].parent = 0;
        graph[s].d = 0;
        int u,v,size;
        NodeList<Integer> queue = new NodeList<>();
        queue.add(Integer.valueOf(s));
        Scanner sc = new Scanner(System.in);
        while(queue.isEmpty() != true)
        {

            u = (int)queue.remove();
            size = graph[u].getSize();
            Integer array[] = new Integer[size];
            graph[u].toArray(array);
            for(i=0;i<size;i++)
            {
                sc.nextLine();
                displayGraph();
                v = (int)array[i];
                NodeList vNode = graph[v];
                if(vNode.colour == "WHITE")
                {
                    vNode.colour = "GRAY";
                    vNode.d = (graph[u].d) + 1;
                    vNode.parent = u;
                    graph[v] = vNode;
                    queue.add(Integer.valueOf(v));
                }
            }
            graph[u].colour = "BLACK";
        }

    }




}
