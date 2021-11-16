package projetTransport.parser.javacc.car;

import projetTransport.graph.Graph;
import projetTransport.graph.Graphable;
import projetTransport.graph.Node;
import projetTransport.parser.javacc.commons.ParseException;
import projetTransport.utils.Moyen;
import projetTransport.utils.Time;
import projetTransport.utils.TimeManager;

import java.util.ArrayList;

public class CarDTO implements Graphable {
    //Il faudra faire une conversion de durée vers Integer dans les lignes
    public ArrayList<CarLigne> ligne;
    public ArrayList<CarLigne> horaires;

    protected CarDTO(){
        ligne = new ArrayList<>();
        horaires = new ArrayList<>();
    }

    protected void addLigne(String gareDepart, String gareArrive, String temps) throws ParseException {
        ligne.add(new CarLigne(gareDepart, gareArrive, temps));
    }

    protected void addHoraire(String gareDepart, String gareArrive, String temps) throws ParseException {
        if (ligneExist(gareDepart, gareArrive)){
                horaires.add(new CarLigne(gareDepart, gareArrive, temps));
        }
        else{
            //On se fiche de la colonne précise seule la ligne compte
            int lineEnd = CarTxt.token.beginLine;
            String message = "Unknown journey from \"" + gareDepart
                    + "\" to \"" + gareArrive +
                    "\" at line " + lineEnd;
            ParseException p = new ParseException(message);
            //On envoie le token dans l'erreur en cas de besoin.
            p.currentToken = CarTxt.token;
            throw p;
        }
    }

    protected boolean ligneExist(String gareDepart, String gareArrive){
        for (CarLigne cl : ligne){
            if (cl.compare(gareDepart, gareArrive))
                return true;
        }
        return false;
    }

    @Override
    public void toGraph(Graph graph) {
        for(CarLigne cl : horaires){
            graph.addNode(new Node(cl.gareArrive));
            graph.addNode(new Node(cl.gareDepart));
            Node arrive = graph.getNode(cl.gareArrive);
            Node depart = graph.getNode(cl.gareDepart);
            depart.addDestination(arrive);
            Time duree = getDuree(cl);
            arrive.addTime(depart, new TimeManager(cl.temps, duree, Moyen.CAR));
        }
    }

    private Time getDuree(CarLigne other){
        for (CarLigne carLigne : ligne){
            if (carLigne.compare(other)){
                return carLigne.temps;
            }
        }
        //This should not happen
        return null;
    }
}