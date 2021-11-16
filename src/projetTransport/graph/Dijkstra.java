package projetTransport.graph;

import projetTransport.utils.Moyen;
import projetTransport.utils.Time;

import java.util.*;

public class Dijkstra {
    static Time current = new Time("1000");

    public static Graph calculateShortestPathFromSource(Graph graph, String source, String time) {
        try{
            calculateShortestPathFromSource(graph, graph.getNode(source), new Time(time));
        }catch (Exception e){
            return null;
        }
        return graph;
    }

    private static Graph calculateShortestPathFromSource(Graph graph, Node source, Time time) {
        Map<Node, Time> departureTime = new HashMap<>();
        departureTime.put(source, time);
        source.setDistance(0);
        Set<Node> settledNodes = new HashSet<>();
        Set<Node> unsettledNodes = new HashSet<>();
        unsettledNodes.add(source);
        Node currentNode;

        while (unsettledNodes.size() != 0) {
            currentNode = getLowestDistanceNode(unsettledNodes);
            current = departureTime.get(currentNode);
            unsettledNodes.remove(currentNode);
            for (Node adjacentNode : currentNode.getAdjacentNodes()){
                Integer edgeWeight = adjacentNode.getEdgeWeight(currentNode, current);
                if (!settledNodes.contains(adjacentNode)) {
                    calculateMinimumDistance(adjacentNode, edgeWeight, currentNode, departureTime);
                    unsettledNodes.add(adjacentNode);
                }
            }
            settledNodes.add(currentNode);
        }
        return graph;
    }
    private static void calculateMinimumDistance(Node evaluationNode,Integer edgeWeight, Node sourceNode,
                                                 Map<Node, Time> departureTime) {
        int sourceDistance = sourceNode.getDistance();
        if (sourceDistance + edgeWeight< evaluationNode.getDistance()) {
            int newDistance = sourceDistance + edgeWeight;
            evaluationNode.setDistance(newDistance < 0 ? Integer.MAX_VALUE : newDistance);
            LinkedList<Node> shortestPath = new LinkedList<>(sourceNode.getShortestPath());
            LinkedList<Moyen> shortestMoyen = new LinkedList<>(sourceNode.getShortestMoyen());
            shortestPath.add(sourceNode);
            shortestMoyen.add(sourceNode.getMoyen());
            evaluationNode.setShortestPath(shortestPath);
            evaluationNode.setShortestMoyen(shortestMoyen);
            departureTime.putIfAbsent(evaluationNode, Time.sum(current, evaluationNode.getDistance()));
        }
    }

    private static Node getLowestDistanceNode(Set<Node> unsettledNodes) {
        Node lowestDistanceNode = (Node) unsettledNodes.toArray()[0];
        int lowestDistance = Integer.MAX_VALUE;
        for (Node node : unsettledNodes) {
            int nodeDistance = node.getDistance();
            if (nodeDistance < lowestDistance) {
                lowestDistance = nodeDistance;
                lowestDistanceNode = node;
            }
        }
        return lowestDistanceNode;
    }


    public static void printShortestPath(Graph graph, String depart, String destination, String heureDep){
        Graph tmp = calculateShortestPathFromSource(graph, depart, heureDep);
        if (tmp == null){
            System.out.println("Impossible de calculer des plus courts chemins.");
        } else {
            List<Node> path = tmp.getNode(destination).getShortestPath();
            for (Node n : path){
                System.out.print(n.getName()+" ---- ");
            }
            System.out.println(destination);
        }
    }
}
