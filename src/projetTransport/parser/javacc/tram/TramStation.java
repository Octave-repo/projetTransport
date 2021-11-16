package projetTransport.parser.javacc.tram;

import projetTransport.parser.javacc.commons.ParseException;
import projetTransport.utils.Time;

import java.util.ArrayList;

public class TramStation {
    public String station;
    public ArrayList<Time> heuresPassage;

    public TramStation(String station){
        this.station = station;
        heuresPassage = new ArrayList<>();
    }

    public void addHeure(String heure) throws ParseException {
        try {
            heuresPassage.add(new Time(heure));
        } catch (Exception e){
            int lineEnd = TramXml.token.beginLine;
            String message = "Incorrect time format at line: " + lineEnd;
            ParseException p = new ParseException(message);
            //On envoie le token dans l'erreur en cas de besoin.
            p.currentToken = TramXml.token;
            throw p;
        }
    }

    //TODO Supprimer (methode utilisée pour vérifier le bon fonctionnement)
    @Override
    public String toString(){
        return "Station: " + station + "\n" + heuresPassage;
    }
}