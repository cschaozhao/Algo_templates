package Algo_Templates;

import java.util.*;

public class Dijkstra {

    public void Dijkstra(Map<Integer, List<int[]>> graph, long[] dist, int s){
        int n = dist.length;
        int[] prev = new int[n];
        for(int i = 0; i < n; i++){
            dist[i] = i == s ? 0: Long.MAX_VALUE;
            prev[i] = -1;
        }
        PriorityQueue<long[]> pq = new PriorityQueue<>((a, b) -> (int)(a[1] - b[1]));
        pq.offer(new long[]{s, 0});
        boolean[] visited = new boolean[n];
        while(!pq.isEmpty()){
            long[] cur = pq.poll();
            if(visited[(int) cur[0]])
                continue;
            visited[(int) cur[0]] = true;
            for(int[] neighbor: graph.getOrDefault((int) cur[0], new ArrayList<int[]>())){
                if(dist[neighbor[0]] > dist[(int) cur[0]] + neighbor[1]){
                    dist[neighbor[0]] = dist[(int) cur[0]] + neighbor[1];
                    if(!visited[neighbor[0]]){
                        pq.offer(new long[]{neighbor[0], dist[neighbor[0]]});
                    }
                    prev[neighbor[0]] = (int) cur[0];
                }
            }
        }
    }

    public Map<Integer, List<int[]>> buildGraph(int[][] edges){
        Map<Integer, List<int[]>> graph = new HashMap<>();
        for(int[] edge: edges){
            graph.computeIfAbsent(edge[0], x -> new ArrayList<int[]>()).add(new int[]{edge[1], edge[2]});
        }
        return graph;
    }

    public long minimumWeight(int n, int[][] edges, int src1, int src2, int dest) {
        Map<Integer, List<int[]>> graph = buildGraph(edges);
        Map<Integer, List<int[]>> Rgraph = new HashMap<>();
        for(int[] edge: edges){
            Rgraph.computeIfAbsent(edge[1], x -> new ArrayList<int[]>()).add(new int[]{edge[0], edge[2]});
        }
        long[] dist1 = new long[n], dist2 = new long[n], dist3 = new long[n];
        long res = Long.MAX_VALUE;
        Dijkstra(graph, dist1, src1);
        Dijkstra(graph, dist2, src2);
        Dijkstra(Rgraph, dist3, dest);

        for(int i = 0; i < n; i++){
            if(dist1[i] == Long.MAX_VALUE || dist2[i] == Long.MAX_VALUE || dist3[i] == Long.MAX_VALUE){
                continue;
            }
            res = Math.min(dist1[i] + dist2[i] + dist3[i], res);
        }
        return res == Long.MAX_VALUE? -1: res;
    }
}
