/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package battleship2D.ui.fxmlController;

import battleship2D.model.Fleet;
import battleship2D.model.Ship;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.layout.HBox;

/**
 * FXML Controller class
 *
 * @author Jeremy
 */
public class FXML_ShipInsertionController implements Initializable {

    @FXML
    private ChoiceBox choiceBox;
    @FXML
    private HBox root;
    @FXML Button doneButton;
    
    /** Listeners management */
    private final PropertyChangeSupport pcsListeners = new PropertyChangeSupport(this); 
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
    
    public void construct(Fleet fleet){
         for (Ship ship : fleet.getShips()) {
            this.choiceBox.getItems().add(ship.getDescription());            
        }
         
        doneButton.setOnAction(e -> {
            this.pcsListeners.firePropertyChange("shipInsertionAreAllShipsOnBoard", false, true);            
        });
    }
    
    public void hide(){
        this.root.setVisible(false);
    }    
    /*
     * Property Change Listeners management
     */
    public void addPropertyChangeListener(PropertyChangeListener propertyChangeListener) {
        this.pcsListeners.addPropertyChangeListener(propertyChangeListener);
    }
    
    public void removePropertyChangeListener (PropertyChangeListener propertyChangeListener) {
        this.pcsListeners.removePropertyChangeListener(propertyChangeListener);
    }
    
      /**
     * @return the user-selected ship
     */
    public int selectedShipIndex() {
        return this.choiceBox.getSelectionModel().getSelectedIndex();        
    }
}
