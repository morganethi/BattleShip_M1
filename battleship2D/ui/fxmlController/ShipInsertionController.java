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

public class ShipInsertionController implements Initializable {

    /*=========================================================================*/
    /* Members                                                                 */       
    /*=========================================================================*/
    @FXML
    private ChoiceBox choiceBox;
    
    @FXML
    private HBox root;
    
    @FXML Button doneButton;
    
    /** Listeners management */
    private final PropertyChangeSupport pcsListeners = new PropertyChangeSupport(this); 
    
    /*=========================================================================*/
    /* Public methods                                                          */       
    /*=========================================================================*/
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
    }    
    
    /*=========================================================================*/   
    public void construct(Fleet fleet){
         for (Ship ship : fleet.getShips()) {
            this.choiceBox.getItems().add(ship.getDescription());            
        }
         
    }
    /*=========================================================================*/ 
    public void doneAction(){
        this.pcsListeners.firePropertyChange("shipInsertionAreAllShipsOnBoard", false, true);
    }
    /*=========================================================================*/    
    public void hide(){
        this.root.setVisible(false);
    } 
    /*=========================================================================*/
    public void addPropertyChangeListener(PropertyChangeListener propertyChangeListener) {
        this.pcsListeners.addPropertyChangeListener(propertyChangeListener);
    }
    /*=========================================================================*/    
    public void removePropertyChangeListener (PropertyChangeListener propertyChangeListener) {
        this.pcsListeners.removePropertyChangeListener(propertyChangeListener);
    }
    /*=========================================================================*/    
    public int selectedShipIndex() {
        return this.choiceBox.getSelectionModel().getSelectedIndex();        
    }
}
