package it.polito.tdp.dizionariograph.model;

import java.util.ArrayList;

import java.util.List;

import org.jgrapht.Graph;
import org.jgrapht.Graphs;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleGraph;
import org.jgrapht.graph.SimpleWeightedGraph;

import it.polito.tdp.dizionariograph.db.WordDAO;

public class Model {

	private Graph<String, DefaultEdge> graph;
	private WordDAO dao;
	private List<String> listaParole;
	private int numeroLettere = 0;
	
	public Model() {
		dao = new WordDAO();
	}
	
	/*se devo lavorare con singola parola come in questo caso conviene java
	 *se devo lavorare su elenchi di parole filtrarle e fare operazioni uso query*/
	
	
	//PRIMO PULSANTE CREA GRAFICO
	public void createGraph(int numeroLettere) {
		
//      esempio per il text model al posto di salvare le cose dal dao
//		listaParole= new ArrayList<String>();
//		listaParole.add("casa");
//		listaParole.add("case");
//		listaParole.add("cara");
//		listaParole.add("care");
//		listaParole.add("caro");
//		listaParole.add("cure");
//		listaParole.add("fila");
//		listaParole.add("pila");
//		listaParole.add("pile");

		this.numeroLettere = numeroLettere;
		List<String> paroleSimili= new ArrayList<String>();
		
		/*prendo tutte le parole della lunghezza data
		 * diventeranno i vertiti del mio grafo*/
		this.listaParole= dao.getAllWordsFixedLength(numeroLettere);
		
		//creo grafo e ci aggiungo tutti i vertici
		this.graph= new SimpleGraph<>(DefaultEdge.class);
		Graphs.addAllVertices(this.graph, this.listaParole);
		
		// Per ogni parola aggiungo un arco di collegamento con le parole
		// che differiscono per una sola lettera (stessa lunghezza)
		for(String parola: listaParole) {
			 paroleSimili = this.getAllSimilarWords(listaParole, parola, numeroLettere);
			 
			 //collego i vetici in parole simili alla parola di partenza tramite arch, nel grafo
			 for(String p: paroleSimili) {
				 graph.addEdge(parola, p);
			 }
		}
		
		System.out.println(graph);
		
	}

	
	private List<String> getAllSimilarWords(List<String> listaParole2, String parola, int numeroLettere) {
		List<String> result= new ArrayList<String>();
		
		
		for(String p: listaParole) {
			
			if (p.length() != parola.length())
				throw new RuntimeException("Le due parole hanno una lunghezza diversa.");
			
			int cont=0;
			
			for(int i=0; i<parola.length(); i++) {
				if(parola.charAt(i)==p.charAt(i))
					cont++;
			}
			
			if(cont==parola.length()-1)
				result.add(p);
			}
		
		return result;
	}


	//SECONDO PULSANTE PER TROVARE I VICINI
	public List<String> displayNeighbours(String parolaInserita) {
		List<String> result= new ArrayList<String>();
		
		//controllo lista parole
		if (numeroLettere != parolaInserita.length())
			throw new RuntimeException("La parola inserita ha una lunghezza differente rispetto al numero inserito.");

		//verifico se la parola esiste nel mio dizionario
		if(!listaParole.contains(parolaInserita))
			throw new RuntimeException("La parola inserita non è presente nella lista di parole.");
		
		//vado a cercare tutti i vicini della parola passata nei vertici del grafo
		for(String p: graph.vertexSet()) { 
			if(p.equals(parolaInserita))
				result= Graphs.neighborListOf(graph, p); //funzione che mi restituisce la lista di vicini
		}
			
		return result;
	}

	
	//TERZO PULSANTE PER TROVARE IL GRADO MAX E QUALE SIA IL NODO CON IL GRADO MAX
	public String findMaxDegree() {
		int gradoMax=0; //numero di archi
		String temp = null;
		
		for(String p: graph.vertexSet()) {
			if (graph.degreeOf(p) > gradoMax) {
			gradoMax= graph.degreeOf(p); //da numero di archi collegati alla parola passata
			temp=p; //vertice del grafo	con il grado massimo
			}
		}
		
		return temp+" con grado: "+gradoMax;
		
	}
}
