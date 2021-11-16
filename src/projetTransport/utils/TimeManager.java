package projetTransport.utils;

public class TimeManager {
    private final Time heureDepart;
    private final Time duree;
    private final Moyen moyen;

    public TimeManager(Time heureDepart, Time duree, Moyen moyen){
        this.heureDepart = heureDepart;
        this.duree = duree;
        this.moyen = moyen;
    }

    public int getDuration(Time other){
        int until = (int) other.until(heureDepart);
        return (until < 0) ? Integer.MAX_VALUE : duree.getTimeInMinutes() + until;

    }

    public Moyen getMoyen(){return moyen;}
    @Override
    public String toString(){
        return heureDepart.toString() + " " +  duree.toString();
    }
}
