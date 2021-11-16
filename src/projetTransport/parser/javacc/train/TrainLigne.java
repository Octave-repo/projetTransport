package projetTransport.parser.javacc.train;

import projetTransport.graph.Graph;
import projetTransport.parser.javacc.commons.ParseException;

import java.util.ArrayList;

public class TrainLigne {
    public int id;
    public ArrayList<TrainJonction> jonctions;

    public TrainLigne(int id){
        this.id = id;
        jonctions = new ArrayList<>();
    }

    public TrainLigne(String id){
        this.id = Integer.parseInt(id);
        jonctions = new ArrayList<>();
    }

    public void addJonction(String stationDepart,String stationArrive, String heureDepart,String heureArrive) throws ParseException {
        jonctions.add(new TrainJonction(stationDepart, stationArrive, heureDepart, heureArrive));
    }

    //TODO Supprimer (methode utilisée pour vérifier le bon fonctionnement)
    @Override
    public String toString(){
        return "Ligne : " + id + "\nJonctions: " + jonctions.toString();

    }

    protected void addToGraph(Graph graph){
        for (TrainJonction tj : jonctions){
            tj.addToGraph(graph);
        }
    }
}