package it.polito.tdp.dizionariograph;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import it.polito.tdp.dizionariograph.model.Model;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class DizionarioGraphController {

	private Model model;
	
	
    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button btngrafo;

    @FXML
    private Button btnvicini;

    @FXML
    private Button btngrado;

    @FXML
    private TextField txtLetters;

    @FXML
    private TextField txtWord;

    @FXML
    private TextArea txtLog;

    
    public void setModel(Model model) {
		this.model = model;
		btngrado.setDisable(true); 
		btnvicini.setDisable(true);
	
		
	}

	@FXML
    void doGeneraGrafo(ActionEvent event) {
		//grafo di delle stesse dimensioni della parola cercata 
		
		txtLog.clear();
    	txtWord.clear();
    	
    	try {
    		int numLettere= Integer.parseInt(txtLetters.getText());
    		model.createGraph(numLettere);
			System.out.println("numero di Lettere: " + numLettere);
			
			btngrafo.setDisable(true); //disattivo il pulsante dopo averlo usato
			btnvicini.setDisable(false); //li attiva
			btngrado.setDisable(false);
		
       
			
		} catch (RuntimeException re) {
			txtLog.setText("Inserisci numero di lettere");
		}

    }

    @FXML
    void doReset(ActionEvent event) {
    	txtLog.clear();
    	txtWord.clear();
    	txtLetters.clear();
    	
    	btngrado.setDisable(true); 
		btnvicini.setDisable(true);
		btngrafo.setDisable(false);
    }

    @FXML
    void doTrovaGradoMax(ActionEvent event) {
    	
    	txtLog.clear();
    	
	    try {
	    	String result=model.findMaxDegree();
	    	txtLog.appendText(result);
	    	
	    	
	    } catch (RuntimeException re) {
	    	txtLog.setText("Crea prima grafo");
		}
    
    }

    @FXML
    void doTrovaVicini(ActionEvent event) {
    	
    	txtLog.clear();
    	
    	
    	try {
    		
    		String parola= txtWord.getText();
    		if (parola == null || parola.length() == 0) {
				txtLog.setText("Inserire una parola da cercare");
				return;
			} 
    		
    		
    	txtLog.appendText("Vicini della parola inserita: \n");
    	List<String> result= new ArrayList<String>();
    	result= model.displayNeighbours(parola);
    		
    	if (result != null) {
	    	for(String s: result)
	    	txtLog.appendText(s+"\n");
		} else {
			txtLog.setText("Non è stato trovato nessun risultato");
		}
    	
    		
    		
    	}catch (RuntimeException re) {
				txtLog.setText("Crea prima grafo");
			}
    		

    }

    @FXML
    void initialize() {
        assert txtLetters != null : "fx:id=\"txtLetters\" was not injected: check your FXML file 'DizionarioGraph.fxml'.";
        assert txtWord != null : "fx:id=\"txtWord\" was not injected: check your FXML file 'DizionarioGraph.fxml'.";
        assert txtLog != null : "fx:id=\"txtLog\" was not injected: check your FXML file 'DizionarioGraph.fxml'.";
        assert btngrafo != null : "fx:id=\"btngrafo\" was not injected: check your FXML file 'DizionarioGraph.fxml'.";
        assert btnvicini != null : "fx:id=\"btnvicini\" was not injected: check your FXML file 'DizionarioGraph.fxml'.";
        assert btngrado != null : "fx:id=\"btngrado\" was not injected: check your FXML file 'DizionarioGraph.fxml'.";

    }
}