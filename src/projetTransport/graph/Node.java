package projetTransport.graph;

import projetTransport.utils.Moyen;
import projetTransport.utils.Time;
import projetTransport.utils.TimeManager;

import java.util.*;

public class Node {
    private String name;
    private Integer distance = Integer.MAX_VALUE;
    private Moyen moyen;
    private final Map<Node, List<TimeManager>> moyenTemps = new HashMap<>();
    private List<Node> shortestPath = new LinkedList<>();
    private List<Moyen> shortestMoyen = new LinkedList<>();
    private final Set<Node> adjacentNodes = new HashSet<>();
    public void addDestination(Node destination) {
        adjacentNodes.add(destination);
    }

    public Node(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Node> getShortestPath() {
        return shortestPath;
    }

    public void setShortestPath(List<Node> shortestPath) {
        this.shortestPath = shortestPath;
    }
    public void setShortestMoyen(List<Moyen> shortestMoyen) {this.shortestMoyen = shortestMoyen;}

    public int getEdgeWeight(Node node, Time current) {
        return findSmallestTimeList(moyenTemps.get(node), current);
    }

    public int getDistance(){return distance;}

    public void setDistance(Integer distance) {
        this.distance = distance;
    }

    public Set<Node> getAdjacentNodes() {
        return adjacentNodes;
    }

    private int findSmallestTimeList(List<TimeManager> times, Time currentTime){
        int min = Integer.MAX_VALUE;
        int i;
        if (times==null) return Integer.MAX_VALUE;
        if (times.isEmpty())
            return Integer.MAX_VALUE;
        for (TimeManager t : times){
            i = t.getDuration(currentTime);
            if (min > i){
                min = i;
                this.moyen = t.getMoyen();
            }
        }
        return min;
    }

    public void addTime(Node n, TimeManager timeManager){
        if (moyenTemps.containsKey(n)){
            moyenTemps.get(n).add(timeManager);
        } else{
            ArrayList<TimeManager> tmp = new ArrayList<>();
            tmp.add(timeManager);
            moyenTemps.put(n, tmp);
        }
    }

    public List<Moyen> getShortestMoyen() {
        return shortestMoyen;
    }

    @Override
    public String toString(){return name;}

    public Moyen getMoyen(){
        return moyen;
    }
}
