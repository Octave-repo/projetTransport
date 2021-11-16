package projetTransport.utils;

import projetTransport.graph.Graph;
import projetTransport.parser.javacc.bus.BusJson;
import projetTransport.parser.javacc.car.CarTxt;
import projetTransport.parser.javacc.commons.ParseException;
import projetTransport.parser.javacc.metro.MetroTxt;
import projetTransport.parser.javacc.train.TrainXml;
import projetTransport.parser.javacc.tram.TramXml;

import java.io.InputStream;
public enum Moyen {
    BUS("Bus"),
    CAR("Car"),
    METRO("Metro"),
    TRAIN("Train"),
    TRAM("Tram");

    public final String nom;
    private static BusJson busJson = null;
    private static CarTxt carTxt = null;
    private static MetroTxt metroTxt = null;
    private static TrainXml trainXml = null;
    private static TramXml tramXml = null;
    Moyen(String nom){
        this.nom = nom;
    }

    public void fileAsGraph(Graph graph, InputStream inputStream) throws Throwable {
        //Il faut mettre les Input() dans des try catch afin d'utiliser toGraph uniquement
        // Si on a pas eu d'erreur de parse
        switch (this){
            case BUS:
                if (busJson == null) busJson = new BusJson(inputStream); else BusJson.ReInit(inputStream);
                try{
                    BusJson.input();
                    BusJson.data.toGraph(graph);
                } catch (Throwable e){
                    throw e;
                }

            break;
            case CAR:
                if (carTxt == null) carTxt = new CarTxt(inputStream); else CarTxt.ReInit(inputStream);
                try {
                    CarTxt.input();
                    CarTxt.data.toGraph(graph);
                } catch (Throwable e){
                    throw e;
                }
            break;
            case METRO:
                if (metroTxt == null) metroTxt = new MetroTxt(inputStream); else MetroTxt.ReInit(inputStream);
                try{
                    MetroTxt.input();
                    MetroTxt.data.toGraph(graph);
                }catch (Throwable e){
                    throw e;
                }
            break;
            case TRAIN:
                if (trainXml == null) trainXml = new TrainXml(inputStream); else TrainXml.ReInit(inputStream);
                try {
                    TrainXml.input();
                    TrainXml.data.toGraph(graph);
                } catch (Throwable e){
                    throw e;
                }
            break;
            case TRAM:
                if (tramXml == null) tramXml = new TramXml(inputStream); else TramXml.ReInit(inputStream);
                try{
                    TramXml.input();
                    TramXml.data.toGraph(graph);
                } catch (Throwable e){
                    throw e;
                }

            break;
            default:break;
        }
    }

    @Override
    public String toString(){
        return nom;
    }
}
