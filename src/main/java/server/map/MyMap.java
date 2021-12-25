package server.map;
import java.util.ArrayList;
import java.util.Stack;

import server.mainWindow.EchoNIOServer;

import java.util.Random;
import java.util.HashMap;
import java.util.Map;
import java.lang.Math;

public class MyMap {
    private Stack<Node> stack = new Stack<>();
    private Random rand = new Random();
    private int[][] map;
    private int width,height;
    private int[] startX=new int[EchoNIOServer.PlayerNumber];
    private int[] startY=new int[EchoNIOServer.PlayerNumber];
    private int endX=width-1,endY=height-1;

    public static final int wall=0,tile=1,start=2;
    public Map<Integer,Integer> monsterPositions=new HashMap<Integer,Integer>();

    //TODO:build different Maps Based on gameStage
    //private int monsterNumber;

    /**
     * 
     * @param width
     * @param height
     * @param gameStage 游戏第几关
     */
    public MyMap(int width,int height,int gameStage) {
        map = new int[width][height];
        this.width=width;
        this.height=height;
        generateMap();
        generateMonsters(gameStage);
    }

    public void generateMap() {
        //generate random start&end points
        for(int i=0;i<startX.length;i++){
            switch(i%4){//(rand.nextInt(4)){
                case 0:
                    startX[i]=0;
                    startY[i]=rand.nextInt(height);
                    break;
                case 1:
                    startX[i]=width-1;
                    startY[i]=rand.nextInt(height);
                    break;
                case 2:
                    startY[i]=height-1;
                    startX[i]=rand.nextInt(width);
                    break;
                case 3:
                    startY[i]=0;
                    startX[i]=rand.nextInt(width);
                    break;
            }
        }
        

        //perform DFS,set the endPoint as the FURTHEST node away from the start!!
        int maxDis=0;
        stack.push(new Node(startX[0],startY[0]));
        while (!stack.empty()) {
            Node next = stack.pop();
            if (validNextNode(next)) {
                
                map[next.x][next.y] = tile;
                ArrayList<Node> neighbors = findNeighbors(next);
                randomlyAddNodesToStack(neighbors);
            }
        }
    }

    /**
     * 
     * @param gameStage
     * @return number of Monsters successfully generated
     */
    public int generateMonsters(int gameStage){
        int totalMonsterNumber=15+5*gameStage;
        int countMonsterNumber=totalMonsterNumber;
        for (int i = 0; i < width; i++) 
            for (int j = 0; j < height; j++){
                if(i==startX && j==startY)continue;
                if(map[i][j]==wall)continue;
                if(rand.nextInt(width*height/totalMonsterNumber)==0){
                    countMonsterNumber--;
                    monsterPositions.put(i,j);
                }
                if(countMonsterNumber==0)return totalMonsterNumber;
            }
        return totalMonsterNumber-countMonsterNumber;
    }
    private int getDistance(int X1,int Y1,int X2,int Y2){
        return Math.abs(X1-X2)+Math.abs(Y1-Y2);
    }
    public int[] getStartX(){
        return startX;
    }
    public int[] getStartY(){
        return startY;
    }
    public int getEndX(){
        return endX;
    }
    public int getEndY(){
        return endY;
    }
    public int getType(int x,int y){
        return map[x][y];
    }


    private boolean validNextNode(Node node) {    //here!
        if(map[node.x][node.y] == tile)
            return false;
        int numNeighboringOnes = 0;
        for (int y = node.y-1; y < node.y+2; y++) {
            for (int x = node.x-1; x < node.x+2; x++) {
                if (pointOnGrid(x, y) && pointNotNode(node, x, y) && map[x][y] == tile) {
                    numNeighboringOnes++;
                }
            }
        }
        return (numNeighboringOnes < 8); //必然小于九，此时这个函数等价于只有第一行
    }

    private void randomlyAddNodesToStack(ArrayList<Node> nodes) {
        int targetIndex;
        while (!nodes.isEmpty()) {
            targetIndex = rand.nextInt(nodes.size());
            stack.push(nodes.remove(targetIndex));
        }
    }

    private ArrayList<Node> findNeighbors(Node node) {      //找到相邻节点
        ArrayList<Node> neighbors = new ArrayList<>();
        for (int y = node.y-1; y < node.y+2; y++) {
            for (int x = node.x-1; x < node.x+2; x++) {
                if (pointOnGrid(x, y) && pointNotCorner(node, x, y)
                    && pointNotNode(node, x, y)) {
                    neighbors.add(new Node(x, y));
                }
            }
        }
        return neighbors;
    }

    private Boolean pointOnGrid(int x, int y) {
        return x >= 0 && y >= 0 && x < width && y < height;
    }

    private Boolean pointNotCorner(Node node, int x, int y) {
        return (x == node.x || y == node.y);
    }
    
    private Boolean pointNotNode(Node node, int x, int y) {
        return !(x == node.x && y == node.y);
    }
        /*public String getRawMap() {
        StringBuilder sb = new StringBuilder();
        for (int[] row : map) {
            sb.append(Arrays.toString(row) + "\n");
        }
        return sb.toString();
    }

    public String getSymbolicMap() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                sb.append(map[i][j] == 1 ? "*" : " ");
                sb.append("  "); 
            }
            sb.append("\n");
        }
        return sb.toString();
    }*/
    //TODO:可用于保存地图
}