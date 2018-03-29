/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package battleship2D.ui.fxml;

import battleship2D.ui.GameStages;
import battleship2D.ui.fxmlController.FXML_MainFrameController;
import java.io.IOException;
import java.net.URL;
import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

/**
 *
 * @author Jeremy
 */
public class FXML_BattleShip2D extends Application   {
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
        try {
      // Localisation du fichier FXML.
      final URL url = getClass().getResource("./FXML_MainFrame.fxml");
      // Création du loader.
      final FXMLLoader fxmlLoader = new FXMLLoader(url);
      // Chargement du FXML.
      root = (Pane) fxmlLoader.load();
      // config du controller
      ((FXML_MainFrameController)fxmlLoader.getController()).changeState(GameStages.PLACE_SHIPS_ON_PLAYER_BOARD); 
      
        
      // Création de la scène.;
    } catch (IOException ex) {
      System.err.println("Erreur au chargement: " + ex);
    }   
        
       
        Scene scene = new Scene(FXML_BattleShip2D.root, 900,600);
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
