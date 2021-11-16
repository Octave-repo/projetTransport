package projetTransport.parser.javacc.bus;

import projetTransport.graph.Graph;
import projetTransport.graph.Node;
import projetTransport.parser.javacc.commons.ParseException;
import projetTransport.utils.Moyen;
import projetTransport.utils.Time;
import projetTransport.utils.TimeManager;

import java.util.ArrayList;
import java.util.List;

public class BusHoraire {
    public String direction;
    public ArrayList<BusStation> stations;

    public BusHoraire(String direction){
        this.direction = direction.replace("\"", "");
        stations = new ArrayList<>();
    }

    public void addStation(String nom){
        stations.add(new BusStation(nom.replace("\"", "")));
    }

    public void addTimeAtIndex(int index, String time) throws ParseException {
        if (index >= stations.size()) {
            int lineBeg = BusJson.token.beginLine;
            int colBeg = BusJson.token.beginColumn;
            String message = "Too many hours at line: " + lineBeg + ", column " + colBeg + ".";
            ParseException p = new ParseException(message);
            //On envoie le token dans l'erreur en cas de besoin.
            p.currentToken = BusJson.token;
            throw p;
        }
        try {
            stations.get(index).passages.add(new Time(time.replace("\"", "")));
        } catch (Exception e){
            int lineEnd = BusJson.token.beginLine;
            int colEnd = BusJson.token.beginColumn;
            String message = "Incorrect time format at line: " + lineEnd +
                    " column " + colEnd;
            ParseException p = new ParseException(message);
            //On envoie le token dans l'erreur en cas de besoin.
            p.currentToken = BusJson.token;
            throw p;
        }
    }
    public void checkTimeIntegrity(int index) throws ParseException {
        if (index+1 < stations.size()){
            int lineEnd = BusJson.token.endLine;
            int colEnd = BusJson.token.endColumn;
            String message = "Too few hours at line: "+lineEnd+", column "+colEnd+".";
            ParseException p = new ParseException(message);
            //On envoie le token dans l'erreur en cas de besoin.
            p.currentToken = BusJson.token;
            throw p;
        }
    }

    public void checkStationIntegrity() throws ParseException {
        if (!stations.get(stations.size()-1).nom.equals(direction)){
            String message = "Last station should be \"" + direction + "\" not \"" +
                    stations.get(stations.size()-1).nom + "\"";
            ParseException p = new ParseException(message);
            //On envoie le token dans l'erreur en cas de besoin.
            p.currentToken = BusJson.token;
            throw p;
        }
    }

    protected void addToGraph(Graph graph){
        int size = stations.size();
        for(BusStation station : stations){
            graph.addNode(new Node(station.nom));
        }
        for (int i = 0 ; i < size-1 ; i++){
            List<Time> tmp = stations.get(i).passages;
            List<Time> tmp2 = stations.get(i+1).passages;
            graph.getNode(stations.get(i).nom).addDestination(graph.getNode((stations.get(i+1).nom)));
            int sizeTwo = tmp.size();
            for (int j = 0 ; j < sizeTwo ;j++){
                int until = (int) tmp.get(j).until(tmp2.get(j));
                TimeManager tm = new TimeManager(tmp.get(j), new Time(until), Moyen.BUS);
                graph.getNode(stations.get(i+1).nom).addTime(graph.getNode(stations.get(i).nom), tm);
            }
        }
    }

    public String toString(){
        return "Direction : " + direction + "\nStations: " + stations;
    }
}