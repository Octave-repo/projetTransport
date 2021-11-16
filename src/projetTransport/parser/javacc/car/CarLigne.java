package projetTransport.parser.javacc.car;

import projetTransport.parser.javacc.commons.ParseException;
import projetTransport.utils.Time;

public class CarLigne{
    public String gareDepart;
    public String gareArrive;
    //Ici le temps symbolise ou la durée de trajet ou l'heure de départ
    public Time temps;

    public CarLigne(String gareDepart, String gareArrive, String temps) throws ParseException {
        this.gareDepart = gareDepart;
        this.gareArrive = gareArrive;
        try{
            this.temps = new Time(temps);
        }
         catch (Exception e){
            int lineEnd = CarTxt.token.beginLine;
            String message = "Incorrect time format \"" + temps +
                    "\" at line " + lineEnd;
            ParseException p = new ParseException(message);
            //On envoie le token dans l'erreur en cas de besoin.
            p.currentToken = CarTxt.token;
            throw p;
        }
    }

    public boolean compare(CarLigne other){
        return compare(other.gareDepart, other.gareArrive);
    }

    public boolean compare(String gareDepart, String gareArrive){
        boolean sameDir = this.gareArrive.equals(gareArrive) && this.gareDepart.equals(gareDepart);
        boolean oppositeDir = this.gareArrive.equals(gareDepart) && this.gareDepart.equals(gareArrive);
        return (sameDir || oppositeDir);

    }
    //TODO Supprimer (methode utilisée pour vérifier le bon fonctionnement)
    @Override
    public String toString() {
        return "Gare d'arrivé :" + gareArrive + "\nGare de départ: " + gareDepart + "\nTemps: " + temps;
    }
}