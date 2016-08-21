/**
 * Created by mehulagarwal on 21/8/16.
 */
public class MyProg {

    public static void main(String args[])
    {

        System.out.println("Graph : ");
        Graph mygraph = new Graph(5);
        mygraph.addEdge(1,2);
        mygraph.addEdge(1,4);
        mygraph.addEdge(1,3);
        mygraph.addEdge(2,5);
        //mygraph.addEdge(3,6);
        mygraph.addEdge(3,5);
        mygraph.addEdge(4,2);
        mygraph.addEdge(5,4);

        mygraph.displayGraph();
        mygraph.BFS(1);
        mygraph.displayGraph();
    }


}
