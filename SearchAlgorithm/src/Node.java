/**
 * Created by dell on 4/10/2015.
 */
public class Node implements Comparable
{
    private int i;
    private int j;
    private int g;
    private int h;
    private String path;
    private String move;








    public Node(int i, int j, String move)
    {
        this.i = i;
        this.j = j;
        this.move = move;

    }



    public Node(int i, int j, String move,String path)
    {
        this.i = i;
        this.j = j;
        this.move = move;
        this.path = path;

    }
    public Node(int i, int j, String move,String path, int g)
    {
        this.i = i;
        this.j = j;
        this.move = move;
        this.path = path;
        this.g = g;

    }


    public Node(int i, int j)
    {
        this.i = i;
        this.j = j;
    }










    public int getI()
    {
        return i;
    }

    public int getJ()
    {
        return j;
    }



    public String getPath()
    {
        return path;
    }



    @Override
    public int hashCode()
    {
        return i * j + i + j;
    }


    //Method for comparing two nodes, the nodes are the same if they have the same indexes in map array
    @Override
    public boolean equals(Object obj)
    {
        if (!(obj instanceof Node)) {
            return false;
        }
        Node node = (Node) obj;
        if (this.i == node.i && this.j == node.j)
            return true;
        else
            return false;

    }

    //Return the priority of the node
    //The higher the number is, the higher the priority it means
    public int returnPriority()
    {
        switch (move)
        {
            case Move.RIGHT_UP: return 0;
            case Move.UP: return 1;
            case Move.LEFT_UP: return 2;
            case Move.LEFT: return 3;
            case Move.LEFT_DOWN: return 4;
            case Move.DOWN: return 5;
            case Move.RIGHT_DOWN: return 6;
            default:return 7;


        }

    }


    //Return cost of the step arrival this node
    public int returnMoveCost()
    {
        switch (move)
        {

            case Move.UP:
            case Move.LEFT:
            case Move.RIGHT:
            case Move.DOWN:
                return 2;
            default:return 1;
        }
    }


    //return the total move cost for BFS and DFS algorithms
    public int returnTotalMoveCostForBFSAndDFS()
    {
        String path = getPath();
        String[] moves = path.split("-");
        int totalMoveCost = 0;

        for(int i =1; i < moves.length - 1; i++)
        {

            switch (moves[i])
            {
                case Move.UP:
                case Move.LEFT:
                case Move.RIGHT:
                case Move.DOWN:
                    totalMoveCost = totalMoveCost + 2;break;
                default:totalMoveCost = totalMoveCost + 1;break;
            }
        }
        return totalMoveCost;


    }


    @Override
    public int compareTo(Object o)
    {
        return Integer.compare(this.returnPriority(),((Node)o).returnPriority());
    }


    public int getG()
    {
        return g;
    }

    public int getH()
    {
        return h;
    }

    public String getMove()
    {
        return move;
    }




    public void setG(int g)
    {
        this.g = g;
    }

    public void setH(int h)
    {
        this.h = h;
    }



    public void setPath(String path)
    {
        this.path = path;
    }

    public void setMove(String move)
    {
        this.move = move;
    }

    public int returnF()
    {
        return g + h;
    }
}
