package projetTransport.parser.javacc.metro;

import projetTransport.graph.Graph;
import projetTransport.graph.Graphable;
import projetTransport.graph.Node;
import projetTransport.parser.javacc.commons.ParseException;
import projetTransport.utils.Moyen;
import projetTransport.utils.Time;
import projetTransport.utils.TimeManager;

import java.util.ArrayList;

public class MetroDTO implements Graphable {
    //Refaire le
    public Time premierDepart;
    public Time dernierDepart;
    public Time interval;
    public ArrayList<String> stations;
    public ArrayList<MetroLiaison> liasons;

    public MetroDTO(){
        stations = new ArrayList<>();
        liasons = new ArrayList<>();
    }
    protected void addStation(String station){
        stations.add(station);
    }

    protected void addLiasion(String depart, String arrive, int duree) throws ParseException {
        if (stations.contains(depart) && stations.contains(arrive))
            liasons.add(new MetroLiaison(depart, arrive, duree));
        else{
            boolean isArrive = (stations.contains(depart) && !stations.contains(arrive));
            int lineBeg = MetroTxt.token.endLine;

            String message = "Unknown station \"" + (isArrive ? arrive : depart) + "\" " +
                    "at line: " + lineBeg;
            ParseException p = new ParseException(message);
            //On envoie le token dans l'erreur en cas de besoin.
            p.currentToken = MetroTxt.token;
            throw p;
        }
    }

    public void setTimes(String premierDepart, String dernierDepart, String interval) throws ParseException {
        try{
            this.premierDepart = new Time(premierDepart);
            this.dernierDepart = new Time(dernierDepart);
            this.interval = new Time(interval);
        } catch (Exception e){
            int lineEnd = MetroTxt.token.beginLine;
            String message = "Incorrect time format at line: " + lineEnd;
            ParseException p = new ParseException(message);
            //On envoie le token dans l'erreur en cas de besoin.
            p.currentToken = MetroTxt.token;
            throw p;
        }

    }

    @Override
    public void toGraph(Graph graph) {
        for(String str : stations){
            graph.addNode(new Node(str));
        }
        Time heurePremierDepart = premierDepart.clone();
        while(heurePremierDepart.until(dernierDepart) >= 0){
            Time tmpHeure = heurePremierDepart.clone();
            addAdjacency(graph, tmpHeure);
            heurePremierDepart = Time.sum(tmpHeure, interval);
        }
    }

    private void addAdjacency(Graph graph, Time current){
        for (MetroLiaison l : liasons){
            Node depart = graph.getNode(l.depart);
            Node arrive = graph.getNode(l.arrive);
            depart.addDestination(arrive);
            arrive.addTime(depart, new TimeManager(current, l.duree, Moyen.METRO));
            current = Time.sum(current, l.duree);
        }
    }
}