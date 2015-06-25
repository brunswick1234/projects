import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.PrintWriter;
import java.util.*;

/**
 * Created by dell on 4/10/2015.
 */
public class PlanPath
{
    private int[][] map;
    private ArrayList<Node> closedList = new ArrayList<>();
    private int numberOfIterations = 0;
    private int[] goalPosition = new int[2]; //Indexes of goal in map, the goal is in map[goalPosition[0]][goalPosition[1]]
    private Node startNode  = null;



    public static void main(String[] args)
    {
        PlanPath planPath = new PlanPath();
        planPath.run(args[0],args[1]);

    }


    private void run(String inputFile, String outputFile)
    {
        PrintWriter printWriter = null;
        try
        {
            printWriter = new PrintWriter(outputFile);
        } catch (FileNotFoundException e)
        {
            e.printStackTrace();
        }
        String choice = readInputFile(inputFile);
        switch (choice)
        {
            case "B" :printWriter.print(bFS());printWriter.close();break;
            case "D" :printWriter.print(dFS());printWriter.close();break;
            case "U" :printWriter.print(uCS());printWriter.close();break;
            case "A" :printWriter.print(aStar());printWriter.close();break;
            default:System.out.println("Invalid input file, please check your input file"); System.exit(1);break;

        }
    }

    private String readInputFile(String fileName)
    {
        try
        {
            FileReader fileReader = new FileReader(fileName);
            Scanner parser = new Scanner(fileReader);
            String choice = parser.nextLine();
            numberOfIterations = Integer.parseInt(parser.nextLine());
            int mapSize = Integer.parseInt(parser.nextLine());

            map = new int[mapSize][mapSize];
            for (int i = 0; i < mapSize; i++)
            {
                char[] symbols = parser.nextLine().toCharArray();
                for (int j = 0; j < mapSize; j++)
                {
                    switch (symbols[j])
                    {
                        case 'R':
                            map[i][j] = TileType.NORMAL;
                            break;
                        case 'X':
                            map[i][j] = TileType.MOUNTAIN;
                            break;
                        case 'S':
                            map[i][j] = TileType.START;
                            break;
                        case 'G':
                            map[i][j] = TileType.GOAL;
                            break;
                        default:
                            break;

                    }
                }
            }
            goalPosition = getGoalPosition();
            startNode = getStartNode();

           return choice;




        } catch (FileNotFoundException e)
        {
            e.printStackTrace();
        }
        return null;


    }



    //Return the start node
    private Node getStartNode()
    {
        Node node = null;
        for (int i = 0; i < map.length; i++)
            for (int j = 0; j < map[i].length; j++)
            {
                if (map[i][j] == TileType.START)
                {
                    node = new Node(i, j, "S", "S", 0);
                    node.setH(0);
                    break;

                }
            }
        return node;
    }


    //Method for finding all nodes that current node can expands to.
    private List<Node> expandNode(Node currentNode)
    {
        boolean topBorder = false;
        boolean bottomBorder = false;
        boolean leftBorder = false;
        boolean rightBorder = false;
        int i = currentNode.getI();
        int j = currentNode.getJ();
        if (i - 1 < 0)
            topBorder = true;
        if (i + 1 > map.length - 1)
            bottomBorder = true;
        if (j - 1 < 0)
            leftBorder = true;
        if (j + 1 > map.length - 1)
            rightBorder = true;

        //Possible nodes is the nodes that existed in map, for example, if current node with position map[i][j] is not in the top row of the map,
        // node with possible[i-1][j] is a possible node
        //For the nodes in the fours corners, if them are not out of boundary, and not a mountain, they are possible nodes.
        HashSet<Node> possibleNodes = new HashSet<>();
        if (!topBorder)
        {
            Node uNode = new Node(i - 1, j, Move.UP);
            possibleNodes.add(uNode);
            if (!rightBorder && map[i - 1][j + 1] != TileType.MOUNTAIN)
            {
                Node urNode = new Node(i - 1, j + 1, Move.RIGHT_UP);
                possibleNodes.add(urNode);
            }

            if (!leftBorder && map[i - 1][j - 1] != TileType.MOUNTAIN)
            {
                {
                    Node ulNode = new Node(i - 1, j - 1, Move.LEFT_UP);
                    possibleNodes.add(ulNode);
                }
            }
        }

        if (!bottomBorder)
        {
            Node dNode = new Node(i + 1, j, Move.DOWN);
            possibleNodes.add(dNode);
            if (!rightBorder && map[i + 1][j + 1] != TileType.MOUNTAIN)
            {
                Node drNode = new Node(i + 1, j + 1, Move.RIGHT_DOWN);
                possibleNodes.add(drNode);
            }

            if (!leftBorder && map[i + 1][j - 1] != TileType.MOUNTAIN)
            {
                Node dlNode = new Node(i + 1, j - 1, Move.LEFT_DOWN);
                possibleNodes.add(dlNode);
            }
        }

        if (!rightBorder)
        {
            Node rNode = new Node(i, j + 1, Move.RIGHT);
            possibleNodes.add(rNode);
        }


        if (!leftBorder)
        {
            Node rNode = new Node(i, j - 1, Move.LEFT);
            possibleNodes.add(rNode);
        }


        //Impossible nodes are the nodes that current nodes cannot move to due to moving constraints:
        // it cannot move diagonally if one of the x, y directions composing the diagonal contains a mountainous tile.
        HashSet<Node> impossibleNodes = new HashSet<>();
        if (!topBorder)
            if (map[i - 1][j] == TileType.MOUNTAIN)
            {
                impossibleNodes.add(new Node(i - 1, j));
                impossibleNodes.add(new Node(i - 1, j - 1));
                impossibleNodes.add(new Node(i - 1, j + 1));
            }

        if (!rightBorder)
            if (map[i][j + 1] == TileType.MOUNTAIN)
            {
                impossibleNodes.add(new Node(i, j + 1));
                impossibleNodes.add(new Node(i - 1, j + 1));
                impossibleNodes.add(new Node(i + 1, j + 1));
            }


        if (!bottomBorder)
            if (map[i + 1][j] == TileType.MOUNTAIN)
            {
                impossibleNodes.add(new Node(i + 1, j));
                impossibleNodes.add(new Node(i + 1, j - 1));
                impossibleNodes.add(new Node(i + 1, j + 1));
            }


        if (!leftBorder)
            if (map[i][j - 1] == TileType.MOUNTAIN)
            {
                impossibleNodes.add(new Node(i, j - 1));
                impossibleNodes.add(new Node(i - 1, j - 1));
                impossibleNodes.add(new Node(i + 1, j - 1));
            }


        //The expand nodes set is those set that in possibleNodes set, and not in impossibleNodes set
        for (Iterator<Node> iterator = possibleNodes.iterator(); iterator.hasNext(); )
        {
            Node node = iterator.next();
            for (Node nodeImpossible : impossibleNodes)
            {
                if (node.equals(nodeImpossible))
                {
                    iterator.remove();
                    break;
                }

            }

        }


        //The expand nodes cannot be closedList which is a list of node that have already been visited
        if (closedList.size() != 0)
        {
            for (Iterator<Node> iterator = possibleNodes.iterator(); iterator.hasNext(); )
            {

                Node node = iterator.next();
                for (Node nodeInClosedList : closedList)
                {
                    if (node.equals(nodeInClosedList))
                    {

                        iterator.remove();
                        break;
                    }
                }


            }
        }


        //Generate the final expand node list
        ArrayList<Node> arrayList = new ArrayList<>();
        for (Node node : possibleNodes)
        {
            Node n = new Node(node.getI(), node.getJ(), node.getMove(), currentNode.getPath() + "-" + node.getMove());
            n.setG(currentNode.getG());
            arrayList.add(n);
        }
        List<Node> nodeList = arrayList;

        //Sort the final nodes list according to their moving priority
        Collections.sort(nodeList,Collections.reverseOrder());
        return nodeList;


    }


    private String bFS()
    {

        StringBuilder stringBuilder = new StringBuilder();
        Queue<Node> openList = new LinkedList<>();
        //Put start node to Open list
        openList.add(startNode);

        while (!openList.isEmpty())
        {

            //Select first from open list, remove it from open, put it on closed
            Node node = openList.poll();
            if (!closedList.contains(node))
                closedList.add(node);

            //Check if this node is goal, if find the goal, get path and return
            if (map[node.getI()][node.getJ()] == TileType.GOAL)
            {
                node.setPath(node.getPath() + "-G");
                stringBuilder.insert(0, node.getPath() + " " + node.returnTotalMoveCostForBFSAndDFS() + "\r\n");
                return stringBuilder.toString();
            }

            //Expand node, add expand nodes to open list
            List<Node> expandNodesList = expandNode(node);
            for (Node n : expandNodesList)
            {
                n.setG(n.getG() + 1);
                n.setH(0);
                openList.add(n);

            }

            //Output path, open list and closed list information
            if (numberOfIterations > 0)
            {

                stringBuilder.append(node.getPath() + " " + node.getG() + " " + node.getH() + " " + node.returnF() + "\r\n" + "OPEN" + " ");

                for (Node openNode : openList)
                    stringBuilder.append(openNode.getPath() + " ");
                stringBuilder.append("\r\n" + "CLOSED" + " ");
                for (Node closedNode : closedList)
                    stringBuilder.append(closedNode.getPath() + " ");
                stringBuilder.append("\r\n");
                numberOfIterations--;

            }



        }
        //If no goal was found, return no path
        return stringBuilder.insert(0,"NO-PATH").toString();


    }


    private String dFS()
    {
        StringBuilder stringBuilder = new StringBuilder();
        Stack<Node> openList = new Stack<>();
        //Put start node to Open list
        openList.add(startNode);

        while (!openList.isEmpty())
        {
            //Select first from open list, remove it from open, put it on closed
            Node node = openList.pop();
            if (!closedList.contains(node))
                closedList.add(node);

            //Check if this node is goal, if find the goal, get path and return
            if (map[node.getI()][node.getJ()] == TileType.GOAL)
            {
                node.setPath(node.getPath() + "-G");
                stringBuilder.insert(0, node.getPath() + " " + node.returnTotalMoveCostForBFSAndDFS() + "\r\n");
                return stringBuilder.toString();
            }

            //Expand node, add expand nodes to open list
            List<Node> expandNodesList = expandNode(node);
            for (int i = expandNodesList.size() -1; i >= 0; i--)
            {
                expandNodesList.get(i).setG(expandNodesList.get(i).getG() + 1);
                expandNodesList.get(i).setH(0);
                openList.add(expandNodesList.get(i));
            }

            //Output path, open list and closed list information
            if (numberOfIterations > 0)
            {
                stringBuilder.append(node.getPath() + " " + node.getG() + " " + node.getH() + " " + node.returnF() + "\r\n" + "OPEN" + " ");

                for (int i = openList.size() - 1; i >= 0 ; i--)
                    stringBuilder.append(openList.get(i).getPath() + " ");
                stringBuilder.append("\r\n" + "CLOSED" + " ");
                for (Node closedNode : closedList)
                    stringBuilder.append(closedNode.getPath() + " ");
                stringBuilder.append("\r\n");
                numberOfIterations--;
            }
        }
        //If no goal was found, return no path
        return stringBuilder.insert(0,"NO-PATH").toString();

    }



    private String uCS()
    {


        StringBuilder stringBuilder = new StringBuilder();
        ArrayList<Node> openList = new ArrayList<>();
        //Put start node to Open list
        openList.add(startNode);

        while (!openList.isEmpty())
        {

            //Select first from open list, remove it from open, put it on closed
            Node node = openList.get(0);
            openList.remove(node);
            if (!closedList.contains(node))
                closedList.add(node);

            //Check if this node is goal, if find the goal, get path and return
            if (map[node.getI()][node.getJ()] == TileType.GOAL)
            {
                node.setPath(node.getPath() + "-G");
                stringBuilder.insert(0, node.getPath() + " " + node.getG() + "\r\n");
                return stringBuilder.toString();
            }

            //Expand node, add expand nodes to open list
            List<Node> expandNodesList = expandNode(node);
            for (Node n : expandNodesList)
            {
                n.setG(n.getG() + n.returnMoveCost());
                n.setH(0);
                openList.add(n);
            }
            //Sort open list according to g and priority
            for (int i = 0; i < openList.size() -1 ; i++)
                for (int j = 0; j < openList.size() - i -1; j++)
                    if (openList.get(j).returnMoveCost() > openList.get(j + 1).returnMoveCost()
            || (openList.get(j).returnMoveCost() == openList.get(j + 1).returnMoveCost()
                            && openList.get(j).returnPriority() < openList.get(j+1).returnPriority()))
                        Collections.swap(openList,j,j+1);


            //Output path, open list and closed list information
            if (numberOfIterations > 0)
            {

                stringBuilder.append(node.getPath() + " " + node.getG() + " " + node.getH() + " " + node.returnF() + "\r\n" + "OPEN" + " ");
                for (Node openNode : openList)
                    stringBuilder.append(openNode.getPath() + " ");
                stringBuilder.append("\r\n" + "CLOSED" + " ");
                for (Node closedNode : closedList)
                    stringBuilder.append(closedNode.getPath() + " ");
                stringBuilder.append("\r\n");
                numberOfIterations--;

            }

        }
        //If no goal was found, return no path
        return stringBuilder.insert(0,"NO-PATH").toString();

    }


    private String aStar()
    {

        StringBuilder stringBuilder = new StringBuilder();
        ArrayList<Node> openList = new ArrayList<>();
        //Put start node to Open list
        openList.add(startNode);

        while (!openList.isEmpty())
        {

            //Select first from open list, remove it from open, put it on closed
            Node node = openList.get(0);
            openList.remove(node);
            if (!closedList.contains(node))
                closedList.add(node);

            //Check if this node is goal, if find the goal, get path and return
            if (map[node.getI()][node.getJ()] == TileType.GOAL)
            {
                node.setPath(node.getPath() + "-G");
                stringBuilder.insert(0, node.getPath() + " " + node.getG() + "\r\n");
                return stringBuilder.toString();
            }


            //Expand node, add expand nodes to open list
            List<Node> expandNodesList = expandNode(node);
            for (Node n : expandNodesList)
            {
                n.setG(n.getG() + n.returnMoveCost());
                n.setH(h(n));
                openList.add(n);
            }
            //Sort open list according to f and priority
            for (int i = 0; i < openList.size() -1 ; i++)
                for (int j = 0; j < openList.size() - i -1; j++)
                    if (openList.get(j).returnF() > openList.get(j + 1).returnF() || (openList.get(j).returnF() == openList.get(j + 1).returnF()
                            && openList.get(j).returnPriority() < openList.get(j+1).returnPriority()))
                        Collections.swap(openList,j,j+1);




            //Output path, open list and closed list information
            if (numberOfIterations > 0)
            {
                stringBuilder.append(node.getPath() + " " + node.getG() + " " + node.getH() + " " + node.returnF() + "\r\n" + "OPEN" + " ");
                for (Node openNode : openList)
                    stringBuilder.append(openNode.getPath() + " ");
                stringBuilder.append("\r\n" + "CLOSED" + " ");
                for (Node closedNode : closedList)
                    stringBuilder.append(closedNode.getPath() + " ");
                stringBuilder.append("\r\n");
                numberOfIterations--;
            }
            
        }
        //If no goal was found, return no path

        return stringBuilder.insert(0,"NO-PATH").toString();

    }


    //h function for a* algorithm
    private int h(Node node)
    {
        goalPosition = getGoalPosition();
        int differenceOfI = Math.abs(node.getI() - goalPosition[0]);
        int differenceOfJ = Math.abs(node.getJ() - goalPosition[1]);
       // System.out.println("i" + node.getI() + "j" + node.getJ() + "df" + differenceOfI + "df2" + differenceOfJ);
        return differenceOfI >= differenceOfJ ? differenceOfI : differenceOfJ;
    }



    //Method for find the position of goal
    private int[] getGoalPosition()
    {
        int[] position = new int[2];
        for (int i = 0; i < map.length; i++)
            for (int j = 0; j < map[i].length; j++)
            {
                if (map[i][j] == TileType.GOAL)
                {
                    position[0] = i;
                    position[1] = j;
                    return position;

                }
            }
        return null;
    }


}
