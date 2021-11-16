package projetTransport.graph;


import projetTransport.parser.javacc.commons.ParseException;
import projetTransport.utils.DetectFile;
import projetTransport.utils.Moyen;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.*;

public class Graph {
    private final Map<String, Node> nodes = new HashMap<>();
    //On fait un putIfAbsent pour pas override la node déjà crée
    public void addNode(Node nodeA) {
        nodes.putIfAbsent(nodeA.getName(), nodeA);
    }

    public Node getNode(String nom){
        return nodes.get(nom);
    }

    public static void graphFromFile(Graph graph, File file) throws Throwable, FileNotFoundException {
        Moyen m = DetectFile.getParser(file);
        m.fileAsGraph(graph, new FileInputStream(file));
    }

    /*
    * Graph depuis un dossier
     */
    public static void graphFromFolder(Graph graph, File folder){
        graphFromFolder(graph, folder, true);
    }

    /*
    * Graph depuis un dossier
    * Avec possibilité de désactiver l'affichage des erreurs.
     */
    public static void graphFromFolder(Graph graph, File folder, boolean printParsingErrors){
        if(folder.isDirectory()){
            File[] listFiles = folder.listFiles();
            for(File file : listFiles){
                if(file.isFile()) {
                    try{
                        graphFromFile(graph, file);
                    } catch (java.lang.Throwable e){
                        if(printParsingErrors)
                            System.out.println("\nUnable to parse file: "  + file.getName() + " : \n " + e.getMessage());
                    }
                }
            }
        }
        else{
            System.out.println(folder.getName() + " is not a folder.");
        }
    }

    /*
    Graph depuis un chemin de dossier
     */
    public static void graphFromFolder(Graph graph, String folderName){
        graphFromFolder(graph, new File(folderName), true);
    }

    /*
    * Graph depuis un chemin de dossier
    * Avec possibilité de désactiver l'affichage des erreurs.
     */
    public static void graphFromFolder(Graph graph, String folderName, boolean printParsingErrors){
        graphFromFolder(graph, new File(folderName), printParsingErrors);
    }
}
