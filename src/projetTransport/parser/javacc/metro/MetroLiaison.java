package projetTransport.parser.javacc.metro;

import projetTransport.utils.Time;

public class MetroLiaison{
    public String depart;
    public String arrive;
    public Time duree;

    public MetroLiaison(String depart, String arrive, int duree){
        this.depart = depart;
        this.arrive = arrive;
        this.duree = new Time(duree);
    }

    @Override
    public String toString(){
        return "Depart: " + depart + "\nArrive: " + arrive + "\nDuree: " + duree;
    }
}