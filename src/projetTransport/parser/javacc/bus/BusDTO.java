package projetTransport.parser.javacc.bus;

import projetTransport.graph.Graph;
import projetTransport.graph.Graphable;

import java.util.ArrayList;

public class BusDTO implements Graphable {
    public String ligne;
    public ArrayList <BusHoraire> horaires;

    public BusDTO(String ligne){
        this.ligne = ligne;
        horaires = new ArrayList<>();
    }

    public BusHoraire addHoraire(String direction){
        BusHoraire busHoraire = new BusHoraire(direction.replace("\"", ""));
        horaires.add(busHoraire);
        return busHoraire;
    }

    @Override
    public void toGraph(Graph graph) {
        for (BusHoraire bh : horaires){
            bh.addToGraph(graph);
        }
    }
}