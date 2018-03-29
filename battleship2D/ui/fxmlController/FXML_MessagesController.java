/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package battleship2D.ui.fxmlController;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;

/**
 * FXML Controller class
 *
 * @author Jeremy
 */
public class FXML_MessagesController implements Initializable {

    @FXML
    private ScrollPane root;
    @FXML
    private TextArea text;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
      this.root.setFitToHeight(true);
       root.setFitToWidth(true);
    }  
    
    public void append (String m){
        text.appendText(m);
    }
    
}
