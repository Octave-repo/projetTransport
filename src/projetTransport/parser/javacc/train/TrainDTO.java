package projetTransport.parser.javacc.train;

import projetTransport.graph.Graph;
import projetTransport.graph.Graphable;

import java.util.ArrayList;

public class TrainDTO implements Graphable {
    //Il faut noter que la liste des lignes sont dans le bon ordres et pas dans un ordre inversé
    public ArrayList<TrainLigne> horaires;

    public TrainDTO(){
        horaires = new ArrayList<>();
    }

    //Le retour de train ligne permet d'ajouter  ensuite les jonctions.
    public TrainLigne addLigne(String id){
        TrainLigne n = new TrainLigne(id);
        horaires.add(n);
        return n;
    }

    @Override
    public void toGraph(Graph graph) {
        for (TrainLigne tl : horaires){
            tl.addToGraph(graph);
        }
    }
}