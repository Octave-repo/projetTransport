package projetTransport.parser.javacc.tram;

import java.util.ArrayList;
import java.util.List;

import projetTransport.graph.Graph;
import projetTransport.parser.javacc.commons.ParseException;
import projetTransport.utils.Moyen;
import projetTransport.utils.Time;
import projetTransport.utils.TimeManager;

public class TramLigne {
    public int id;
    public ArrayList<TramStation> stations;
    public ArrayList<String> existingStations;

    public TramLigne(String id, ArrayList<String> existingStations){
        this.id = Integer.parseInt(id);
        stations = new ArrayList<>();
        this.existingStations = existingStations;
    }

    public void addStation(String station) throws ParseException {
        //On vérifie que la station "existe"
        if (existingStations.contains(station))
                stations.add(new TramStation(station));
        else{
            int lineBeg = TramXml.token.beginLine;
            int colBeg = TramXml.token.beginColumn;
            String message = "Unkown station \""+ TramXml.token.toString() +
                    "\" at line: "+lineBeg+", column "+colBeg+".";
            ParseException p = new ParseException(message);
            //On envoie le token dans l'erreur en cas de besoin.
            p.currentToken = TramXml.token;
            throw p;
        }
    }

    public void addHeureAtIndex (int i, String heure) throws ParseException {
        if (i >= stations.size()){
            int lineBeg = TramXml.token.beginLine;
            int colBeg = TramXml.token.beginColumn;
            String message = "Too many hours at line: "+lineBeg+", column "+colBeg+".";
            ParseException p = new ParseException(message);
            //On envoie le token dans l'erreur en cas de besoin.
            p.currentToken = TramXml.token;
            throw p;
        }
        else{
            stations.get(i).addHeure(heure);
        }
        
    }

    public void checkValidity(int i) throws ParseException
    {
        if (i != stations.size()){
            int lineEnd = TramXml.token.endLine;
            int colEnd = TramXml.token.endColumn;
            String message = "Too few hours at line: "+lineEnd+", column "+colEnd+".";
            ParseException p = new ParseException(message);
            //On envoie le token dans l'erreur en cas de besoin.
            p.currentToken = TramXml.token;
            throw p;
        }
    }

    //TODO Supprimer (methode utilisée pour vérifier le bon fonctionnement)
    @Override
    public String toString(){
        return "Ligne : " + id + "\n" + stations.toString();
    }

    public void addToGraph(Graph graph){
        int size = stations.size();
        for (int i = 0 ; i < size-1 ; i++){
            List<Time> tmp = stations.get(i).heuresPassage;
            List<Time> tmp2 = stations.get(i+1).heuresPassage;
            graph.getNode(stations.get(i).station).addDestination(graph.getNode((stations.get(i+1).station)));
            int sizeTwo = tmp.size();
            for (int j = 0 ; j < sizeTwo ;j++){
                int until = (int) tmp.get(j).until(tmp2.get(j));
                TimeManager tm = new TimeManager(tmp.get(j), new Time(until), Moyen.TRAM);
                graph.getNode(stations.get(i+1).station).addTime(graph.getNode(stations.get(i).station), tm);
            }
        }
        /*
        for existing station
            suivant -> départ + duree dans un TimeManager
         */
    }
}