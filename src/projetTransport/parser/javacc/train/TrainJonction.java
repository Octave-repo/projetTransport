package projetTransport.parser.javacc.train;

import projetTransport.graph.Graph;
import projetTransport.graph.Node;
import projetTransport.parser.javacc.commons.ParseException;
import projetTransport.utils.Moyen;
import projetTransport.utils.Time;
import projetTransport.utils.TimeManager;

public class TrainJonction {
    public String stationDepart;
    public String stationArrive;
    public Time heureDepart;
    public Time heureArrive;

    public TrainJonction(String stationDepart,String stationArrive, String heureDepart,String heureArrive)
            throws ParseException {
        this.stationDepart = stationDepart;
        this.stationArrive = stationArrive;
        try {
            this.heureDepart = new Time(heureDepart);
            this.heureArrive = new Time(heureArrive);
        } catch (Exception e) {
            //TODO Comprendre pq la ligne est pas bonne
            int lineEnd = TrainXml.token.beginLine;
            String message = "Incorrect time format at line: " + lineEnd;
            ParseException p = new ParseException(message);
            //On envoie le token dans l'erreur en cas de besoin.
            p.currentToken = TrainXml.token;
            throw p;
        }
    }

    //TODO Supprimer (methode utilisée pour vérifier le bon fonctionnement)
    @Override
    public String toString(){
        return "\nStation Départ : " + stationDepart
            + "\nStation Arrive: " + stationArrive
            + "\nHeure Depart: " + heureDepart
            + "\nHeure Arrive: " + heureArrive;
    }

    public void addToGraph(Graph graph){
        graph.addNode(new Node(stationDepart));
        graph.addNode(new Node(stationArrive));
        Node n = graph.getNode(stationDepart);
        Node n2 = graph.getNode(stationArrive);
        n.addDestination(graph.getNode(stationArrive));
        n2.addTime(n, new TimeManager(heureDepart, new Time((int) heureDepart.until(heureArrive)), Moyen.TRAIN));
    }
}