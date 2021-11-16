package projetTransport.parser.javacc.tram;

import projetTransport.graph.Graph;
import projetTransport.graph.Graphable;
import projetTransport.graph.Node;

import java.util.ArrayList;

public class TramDTO implements Graphable {
    public ArrayList<TramLigne> lignes;
    public ArrayList<String> stations;

    public TramDTO(){
        this.lignes = new ArrayList<>();
        this.stations = new ArrayList<>();
    }

    public TramLigne addLigne(String id){
        TramLigne tramLigne = new TramLigne(id, stations);
        lignes.add(tramLigne);
        return tramLigne;
    }

    public void addStation(String s){ stations.add(s);}

    @Override
    public void toGraph(Graph graph) {
        for (String str : stations){
            graph.addNode(new Node(str));
        }
        for (TramLigne tl : lignes){
            tl.addToGraph(graph);
        }
    }
}