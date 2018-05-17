package battleship2D.ui.fxmlController;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.util.Duration;

/**
 * FXML Controller class
 *
 * @author morga
 */
public class EndGameController  implements Initializable {

    /*=========================================================================*/
    /* Members                                                                 */       
    /*=========================================================================*/
    
    @FXML 
    private Pane root; 
    
    /** Animation images */ 
    @FXML
    private ImageView winnerImage,winsImage;

    /** Lapse time between two frame iterations */
    private int duration;
    
    /** Dynamic scaling ration to make image appear */
    private SimpleDoubleProperty scaleRatioProparty;
    
    /** Animation player */
    private Timeline timeline;
    /*=========================================================================*/
    /* Public methods                                                          */       
    /*=========================================================================*/
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        root.prefWidthProperty().bind(root.widthProperty());
    }    
    /*=========================================================================*/
    public void construct(boolean playerWins){
        this.duration = 80;
        this.scaleRatioProparty = new SimpleDoubleProperty();
        
        
        if (playerWins) {
            this.winnerImage.setImage(new Image(getClass().getResource("/battleship2D/pictures/player.png").toExternalForm()));
        }
        else {
            this.winnerImage.setImage(new Image(getClass().getResource("/battleship2D/pictures/computer.png").toExternalForm()));
        }
        
        initWinner();    
        
        this.winsImage.setImage(new Image(getClass().getResource("/battleship2D/pictures/wins.png").toExternalForm()));                
        initWins();
        
        this.timeline = new Timeline();
        initTimeline();   
    }
    /*=========================================================================*/ 
     /**
     * Plays the animation
     */    
    public void start() {
        this.scaleRatioProparty.setValue(0.0);
        this.root.setVisible(true);
        this.timeline.play(); 
    }
    /*=========================================================================*/
    /**
     * Stops the animation
     */
    public void stop() { 
        this.timeline.stop(); 
        this.root.setVisible(false);
    }

    
    /*=========================================================================*/
    /* Private methods                                                         */       
    /*=========================================================================*/
    
    /** 
     * Initializes timeline properties 
     * @see EndGame
     */
    private void initTimeline() {        
        this.timeline.setCycleCount(Timeline.INDEFINITE);
        
        KeyFrame keyFrame = new KeyFrame(new Duration(this.duration), (ActionEvent event) -> {            
                runAnimation();
        });
        timeline.getKeyFrames().add(keyFrame);
    }
    /*=========================================================================*/
    /** 
     * Initializes winnerImage's properties 
     * @see EndGame()
     */
    private void initWinner() {        
        this.winnerImage.fitHeightProperty().bind(this.root.prefHeightProperty().divide(3));
        this.winnerImage.fitWidthProperty().bind(this.root.prefWidthProperty().divide(1.5));
        this.winnerImage.setFocusTraversable(false); 
        this.winnerImage.scaleXProperty().bind(this.scaleRatioProparty);
        this.winnerImage.scaleYProperty().bind(this.scaleRatioProparty);
        //this.root.getChildren().add(this.winnerImage);
    }
    /*=========================================================================*/
    /** 
     * Initializes winsImage's properties 
     * @see EndGame()
     */
    private void initWins() {        
        this.winsImage.fitHeightProperty().bind(this.root.prefHeightProperty().divide(3));
        this.winsImage.fitWidthProperty().bind(this.root.prefWidthProperty().divide(2));
        this.winsImage.setFocusTraversable(false);     
        this.winsImage.scaleXProperty().bind(this.scaleRatioProparty);
        this.winsImage.scaleYProperty().bind(this.scaleRatioProparty);
        //this.root.getChildren().add(this.winsImage);
    }
    /*=========================================================================*/
    /**
     * The animation moves images at each step
     * @see initTimeline()
     */
    private void runAnimation() {                
        this.winnerImage.setTranslateX(this.root.getWidth()/2 - this.winnerImage.getFitWidth()/2);
        this.winnerImage.setTranslateY(this.root.getHeight()*0.3 - this.winnerImage.getFitHeight()/2);
        
        
        this.winsImage.setTranslateX(this.root.getWidth()/2 - this.winsImage.getFitWidth()/2);
        this.winsImage.setTranslateY(this.root.getHeight()*0.75 - this.winsImage.getFitHeight()/2);
        
        if (this.scaleRatioProparty.getValue() <= 1.0) {
            this.scaleRatioProparty.setValue(this.scaleRatioProparty.getValue() + 0.1);
        }
    }
}
