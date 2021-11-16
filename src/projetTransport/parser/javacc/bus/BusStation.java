package projetTransport.parser.javacc.bus;

import projetTransport.utils.Time;
import java.util.ArrayList;

public class BusStation {
    public String nom;
    public ArrayList<Time> passages;

    public BusStation(String nom){
        this.nom = nom;
        passages = new ArrayList<>();
    }

    @Override
    public String toString(){
        return "Nom: " + nom + "\nHeures de passage: " + passages;
    }
}