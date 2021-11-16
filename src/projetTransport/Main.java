package projetTransport;

import projetTransport.graph.Dijkstra;
import projetTransport.graph.Graph;

public class Main {
	private static final String exceptionPath = "src/ressources/every_exceptions";
	private static final String filePath = "src/ressources/good_files";

	/**
	 * Fonction pour lever toute les exceptions possible.
	 */
	public static void triggerEveryExceptions(){
		System.out.println("Voici une liste exhaustive des exceptions pouvant être levée: ");
		Graph.graphFromFolder(new Graph(), exceptionPath);
	}

	/**
	 * Fonction pour faire le trajet de l'Ecole à la Gare à 3h00
	 * On prends le train pour un trajet direct
	 */
	public static void shortestPathFromGareToEcoleAtThree(){
		//On génère un graphe depuis des fichiers contenant des trajets
		Graph graph = new Graph();
		Graph.graphFromFolder(graph, filePath);

		//On affiche le plus court chemin de la gare à l'école en partant à 3h00
		System.out.println("\nChemin le plus court entre la gare et l'école en partant à 3h00: \n");
		Dijkstra.printShortestPath(graph, "Gare", "Ecole", "0300");
	}

	/**
	 * Fonction pour faire le trajet de l'Ecole à la Gare à 7h00
	 * On prends le bus
	 */
	public static void shortestPathFromGareToEcoleAtSeven(){
		//On génère un graphe depuis des fichiers contenant des trajets
		Graph graph = new Graph();
		Graph.graphFromFolder(graph, filePath);

		//On affiche le plus court chemin de la gare à l'école en partant à 3h00
		System.out.println("\nChemin le plus court entre la gare et l'école en partant à 7h00: \n");
		Dijkstra.printShortestPath(graph, "Gare", "Ecole", "0700");
	}

	/**
	 * Fonction pour faire le trajet de l'Ecole à la Gare à 23h00
	 * Le trajet étant réalisé trop tard, il est impossible de trouver un trajet
	 * (pour le jour meme)
	 */
	public static void unableToFindShortestPath(){
		Graph graph = new Graph();
		Graph.graphFromFolder(graph, filePath);

		//On affiche le plus court chemin de la gare à l'école en partant à 3h00
		System.out.println("\nChemin le plus court entre la gare et l'école en partant à 23h00: \n");
		Dijkstra.printShortestPath(graph, "Gare", "Ecole", "2300");
	}

	public static void main(String [] args)  {
		//Commenter les lignes pour tester individuellement les fonction
		triggerEveryExceptions();
		shortestPathFromGareToEcoleAtThree();
		shortestPathFromGareToEcoleAtSeven();
		unableToFindShortestPath();
	}
}
