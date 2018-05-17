package battleship2D.ui.fxml;

import battleship2D.ui.GameStages;
import battleship2D.ui.fxmlController.MainFrameController;
import java.io.File;
import java.io.IOException;
import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Stage;


public class BattleShip2D extends Application   {
    /*=========================================================================*/
    /* Members                                                                 */       
    /*=========================================================================*/
    
    /** Game manager */    
    private static Pane root;
   
    
    /*=========================================================================*/
    /* Public methods                                                          */       
    /*=========================================================================*/
    
    @Override
    public void start(Stage primaryStage) throws Exception {
     
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("./MainFrame.fxml"));
         
         try {
            root = (Pane) fxmlLoader.load();
         }
         catch(IOException ioe) {
            System.err.println("Erreur au chargement du FXML dans BattelShip2D");
            ioe.printStackTrace();
         }
         
        
       //On configure le controller
      ((MainFrameController)fxmlLoader.getController()).changeState(GameStages.PLACE_SHIPS_ON_PLAYER_BOARD); 
       //====================================================
        Scene scene = new Scene(BattleShip2D.root, 900,600);
        primaryStage.setScene(scene);
        primaryStage.show();          
    }
    
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {    
        
        
        launch(args);
    }
    
}
