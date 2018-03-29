package battleship2D.ui.fxmlController;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;

public class MessagesController implements Initializable {
    /*=========================================================================*/
    /* Members                                                                 */       
    /*=========================================================================*/
    @FXML
    private ScrollPane root;
    @FXML
    private TextArea text;
    
    /*=========================================================================*/
    /* Public methods                                                          */       
    /*=========================================================================*/
    @Override
    public void initialize(URL url, ResourceBundle rb) {
      
    }  
    /*=========================================================================*/
    public void append (String m){
        text.appendText(m);
    }
    
}
