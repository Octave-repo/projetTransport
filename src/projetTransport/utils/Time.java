package projetTransport.utils;

import java.time.DateTimeException;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import static java.time.temporal.ChronoUnit.MINUTES;

/*
* Classe time avec un constructeur String
* Workaround étrange car il est impossible d'étendre LocalTime
* On pourras ajouter des méthodes de LocalTime supplémentaire
 */
public class Time {
    private final LocalTime time;
    private String stringValue;
    public static final String STRING_FORMAT = "HHmm";


    public Time(String time) throws DateTimeException {
        if (time.length() < 4){
            time = addZero(time);
        }
        stringValue = time;
        this.time = LocalTime.parse(time, DateTimeFormatter.ofPattern(STRING_FORMAT));
    }

    public Time(int time) throws DateTimeException{
        stringValue = Integer.toString(time);
        if (stringValue.length() < 4){
            stringValue = addZero(stringValue);
        }
        this.time = LocalTime.parse(stringValue, DateTimeFormatter.ofPattern(STRING_FORMAT));
    }

    public int getHour(){
        return this.time.getHour();
    }

    public int getMinute(){
        return this.time.getMinute();
    }

    public long until(Time other){
        return  time.until(other.getTime(), MINUTES);
    }
    /*
    * Ajoute des 0 jusqu'à avoir un format du type HHmm
    * 1 -> 0001 -> 1 minute
     */
    private String addZero(String s){
        //Merci intelij
        StringBuilder sBuilder = new StringBuilder(s);
        while (sBuilder.length() < 4){
            sBuilder.insert(0, "0");
        }
        return sBuilder.toString();
    }

    @Override
    public String toString(){
        return stringValue;
    }

    private LocalTime getTime(){
        return time;
    }

    //Réalise une somme de deux temps
    public static Time sum(Time t1, Time t2){
      int sumMin = t1.getMinute() + t2.getMinute();
      int newMin = sumMin % 60;
      int tmpHr = sumMin / 60;
      int newHr = (t1.getHour() + t2.getHour() + tmpHr) % 24;
      String tmpStrMin = String.valueOf(newMin);
      if (tmpStrMin.length() < 2){
          tmpStrMin = "0"+tmpStrMin;
      }
      String tmpStrHr = String.valueOf(newHr);
      if (tmpStrHr.length() < 2){
            tmpStrHr = "0"+tmpStrHr;
        }
      return new Time(tmpStrHr+tmpStrMin);
    }

    public static Time sum(Time t1, int duration){
        int newMin = (t1.getMinute() + duration %60);
        int newHr = (t1.getHour() + (duration / 60) + (newMin /60))%24;
        newMin = newMin%60;
        String tmpStrMin = String.valueOf(newMin);
        if (tmpStrMin.length() < 2){
            tmpStrMin = "0"+tmpStrMin;
        }
        String tmpStrHr = String.valueOf(newHr);
        if (tmpStrHr.length() < 2){
            tmpStrHr = "0"+tmpStrHr;
        }
        return new Time(tmpStrHr+tmpStrMin);

    }

    @Override
    public Time clone() {
        return new Time(toString());
    }
    public int getTimeInMinutes(){
        return time.getMinute() + (time.getHour() * 60);
    }
}
