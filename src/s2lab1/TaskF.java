package s2lab1;


import java.io.*;
import java.util.*;

public class TaskF {

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

        int goalX = map.getX(end);
        int goalY = map.getY(end);

        int[] temp = new int[]{-1, 0, 0, 0};

        PriorityQueue<Integer> q = new PriorityQueue<>(Comparator.comparing(a -> {
            int x = map.getX(a);
            int y = map.getY(a);
            String data = map.get(x, y);
            return data.length() + Math.abs(goalX - x) + Math.abs(goalY - y);
        }));
        map.set(map.getX(start), map.getY(start), "");
        q.offer(start);
        outer: while (!q.isEmpty()){
            int source = q.poll();
            int sourceX = map.getX(source);
            int sourceY = map.getY(source);

            String sourceNode = map.get(sourceX, sourceY);

            getAdjacentTiles(map, source, temp);
            for (int adjacent : temp) {
                if(adjacent == -1)
                    break;

                int x = map.getX(adjacent);
                int y = map.getY(adjacent);
                String mapNode = map.get(x, y);

                if(mapNode != null && mapNode.length() < sourceNode.length() + 1)
                    continue;
                else
                    q.remove(adjacent);

                Side move = null;
                if(sourceX < x)
                    move = Side.RIGHT;
                if(sourceY < y)
                    move = Side.DOWN;
                if(sourceX > x)
                    move = Side.LEFT;
                if(sourceY > y)
                    move = Side.UP;

                String node = sourceNode + move.display;

                map.set(x, y, node);
                if(adjacent == end)
                    break outer;

                q.offer(adjacent);
            }
        }

        PrintWriter out = new PrintWriter("output.txt");
        String smallestPath = map.get(map.getX(end), map.getY(end));

        if(smallestPath == null){
            out.print("-1");
            out.close();
            return;
        }

        out.println(smallestPath.length());
        out.println(smallestPath);
        out.close();
    }

    private static void getAdjacentTiles(Map map, int source, int[] nodes){
        int x = map.getX(source);
        int y = map.getY(source);
        int i = 0;

        if(!map.isWall(x - 1, y))
            nodes[i++] = (map.ind(x - 1, y));

        if(!map.isWall(x + 1, y))
            nodes[i++] = (map.ind(x + 1, y));

        if(!map.isWall(x, y - 1))
            nodes[i++] = (map.ind(x, y - 1));

        if(!map.isWall(x, y + 1))
            nodes[i++] = (map.ind(x, y + 1));

        if(i != nodes.length)
            nodes[i] = -1;
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


    private static class Map {

        private int w, h;
        private boolean level[];
        private String[] data;

        public Map(int w, int h){
            this.w = w;
            this.h = h;
            this.level = new boolean[w * h];
            this.data = new String[w * h];
        }

        public void setWall(int x, int y){
            level[x + y * w] = true;
        }

        public boolean isWall(int x, int y){
            if(x < 0 || x >= w || y < 0 || y >= h)
                return true;

            return level[x + y * w];
        }

        public String get(int x, int y){
            return data[ind(x, y)];
        }

        public void set(int x, int y, String node){
            data[ind(x, y)] = node;
        }

        public int ind(int x, int y){
            return x + y * w;
        }

        public int getX(int ind){
            return ind % w;
        }

        public int getY(int ind){
            return ind / w;
        }

    }
}
