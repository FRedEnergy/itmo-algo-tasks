package s2lab1;


import java.io.*;
import java.util.*;

public class TaskF {

    private static int MAX = Integer.MAX_VALUE - 1;

    public static void main(String[] args) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader("input.txt"));

        String firstLine = reader.readLine();
        int h = Integer.parseInt(firstLine.split(" ")[0]);
        int w = Integer.parseInt(firstLine.split(" ")[1]);
        int start = 0, end = 0;

        Map map = new Map(w, h);

        for (int y = 0; y < h; y++) {
            String line = reader.readLine();
            for (int x = 0; x < w; x++) {
                char c = line.charAt(x);
                if(c == '.'){
                    continue;
                } else if(c == '#'){
                    map.setWall(x, y);
                } else if(c == 'S'){
                    start = map.ind(x, y);
                } else if(c == 'T'){
                    end = map.ind(x, y);
                }
            }
        }

        int n = w * h;
        map.get(start).distance = 0;
        for(int i = 0; i < n; i++){
            if(map.isWall(i))
                continue;

            MapNode node = null;
            for(int j = 0; j < n; j++) {
                MapNode another = map.get(j);
                if(another.isVisited)
                    continue;

                if (node == null || another.distance < node.distance)
                    node = another;
            }

            if(node == null || node.distance == MAX) break;
            node.isVisited = true;

            for(int adjPos: getAdjacentTiles(map, node.pos)){
                MapNode adj = map.get(adjPos);

                if(node.distance + 1 < adj.distance){
                    adj.distance = node.distance + 1;
                    adj.parent = node;
                }
            }
        }

        MapNode endNode = map.get(end);
        int endDistance = endNode.distance;

        PrintWriter out = new PrintWriter("output.txt");

        if(endDistance == MAX){
            out.print("-1");
            out.close();
            return;
        }

        char[] path = new char[endDistance];
        int i = endDistance - 1;
        MapNode v = endNode;

        while (i >= 0){
            int source = v.parent.pos;
            int sourceX = map.getX(source);
            int sourceY = map.getY(source);
            int x = map.getX(v.pos);
            int y = map.getY(v.pos);

            path[i--] = getMovement(sourceX, sourceY, x, y).display;
            v = v.parent;
        }

        out.println(endDistance);
        out.println(new String(path));
        out.close();
    }

    private static Side getMovement(int sourceX, int sourceY, int x, int y) {
        Side move = null;
        if(sourceX < x) move = Side.RIGHT;
        if(sourceY < y) move = Side.DOWN;
        if(sourceX > x) move = Side.LEFT;
        if(sourceY > y) move = Side.UP;
        return move;
    }

    private static List<Integer> getAdjacentTiles(Map map, int source){
        int x = map.getX(source);
        int y = map.getY(source);
        List<Integer> result = new ArrayList<>(4);

        if(!map.isWall(x - 1, y))
            result.add(map.ind(x - 1, y));

        if(!map.isWall(x + 1, y))
            result.add(map.ind(x + 1, y));

        if(!map.isWall(x, y - 1))
            result.add(map.ind(x, y - 1));

        if(!map.isWall(x, y + 1))
            result.add(map.ind(x, y + 1));

        return result;
    }

    private enum Side {
        UP,
        RIGHT,
        DOWN,
        LEFT;

        public final char display;

        Side() {
            this.display = name().charAt(0);
        }
    }

    private static class MapNode {

        final int pos;
        int distance = MAX;
        MapNode parent = null;
        boolean isVisited = false;

        MapNode(int pos) {
            this.pos = pos;
        }
    }

    private static class Map {

        private int w, h;
        private boolean level[];
        private MapNode[] data;

        Map(int w, int h){
            this.w = w;
            this.h = h;
            this.level = new boolean[w * h];
            this.data = new MapNode[w * h];
        }

        void setWall(int x, int y){
            level[x + y * w] = true;
        }

        public boolean isWall(int x, int y){
            if(x < 0 || x >= w || y < 0 || y >= h)
                return true;

            return level[x + y * w];
        }

        boolean isWall(int v){
            if(v < 0 || v >= (w * h))
                return true;

            return level[v];
        }

        public MapNode get(int v){
            MapNode d = data[v];
            if(d != null) return d;
            return data[v] = new MapNode(v);
        }

        int ind(int x, int y){
            return x + y * w;
        }

        int getX(int ind){
            return ind % w;
        }

        int getY(int ind){
            return ind / w;
        }

    }
}
