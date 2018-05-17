/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package battleship2D.ui.fxmlController;

import battleship2D.ui.CellUI;
import battleship2D.ui.Particle;
import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.ResourceBundle;
import javafx.animation.AnimationTimer;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.effect.BlendMode;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.paint.CycleMethod;
import javafx.scene.paint.Paint;
import javafx.scene.paint.RadialGradient;
import javafx.scene.paint.Stop;


public class ExplosionController implements Initializable {
    /*=========================================================================*/
    /* Members                                                                 */       
    /*=========================================================================*/
    @FXML 
    private Pane root; 
    
    /** Animation images */ 
    @FXML
    private ImageView backgroundImage;
    
    /** Graphics zone for displaying fireworks */
    private Canvas canvas;
    
    /** Fireworks canvas palette */
    private Paint[] colors;
    
    /** Timer between two particles sprays */
    private int countDownTillNextFirework = 40;
    
    /** Particles constituting the fireworks */
    private List<Particle> particles = new ArrayList<>();
    
    /** Fireworks animation timer */
    private AnimationTimer timer;

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
     public void construct(double defaultWidth, double defaultHeight){
        
        this.colors = new Paint[181];
        initColors();        
             
        initBackground();
                
        this.canvas = new Canvas(defaultWidth,defaultHeight);        
        initCanvas();

        initAnimationTimer();
       
    }    
    /*=========================================================================*/     
     public void binding(SimpleDoubleProperty missileDestX, SimpleDoubleProperty missileDestY, CellUI cell){
         
         this.root.translateXProperty().bind(missileDestX.subtract(cell.widthProperty().divide(2)));                        
         this.root.translateYProperty().bind(missileDestY.subtract(cell.heightProperty().divide(2)));                
            
         this.root.prefWidthProperty().bind(cell.widthProperty()); // calls explosion.layoutChildren();
         this.root.prefHeightProperty().bind(cell.heightProperty()); // calls explosion.layoutChildren();
     }
    /*=========================================================================*/
     /**
     * Plays the animation
     */
    public void start() { 
        this.root.setVisible(true);
        this.timer.start(); 
    }
    /*=========================================================================*/
    /**
     * Stops the animation
     */
    public void stop() { 
        this.timer.stop(); 
        this.root.setVisible(false);
    }
    /*=========================================================================*/
    /* 
     * Getters / Setters 
     */    
    
    public ImageView getBackgroundImage() {
        return this.backgroundImage;
    }
    
    
    /*=========================================================================*/
    /* Protected methods                                                         */       
    /*=========================================================================*/
    
    /**
     * Makes canvas resizable
     */
    protected void layoutChildren() {   
        canvas.setWidth(this.root.prefWidthProperty().getValue());
        canvas.setHeight(this.root.prefHeightProperty().getValue());
    }
    
    
    /*=========================================================================*/
    /* Private methods                                                         */       
    /*=========================================================================*/
    
    /** 
     * Generates particles for fireworks 
     * @param graphicsContext - Context associated with canvas for drawing fireworks
     * initAnimationTimer()
     */
    private void drawFireworks(GraphicsContext graphicsContext) {
        Iterator<Particle> iter = particles.iterator();
        List<Particle> newParticles = new ArrayList<Particle>();
        while(iter.hasNext()) {
            Particle firework = iter.next();
            /* If the update returns true then particle has expired */
            if(firework.update()) {
                /* Remove particle from those drawn */
                iter.remove();
                /* Check if it should be exploded */
                if(firework.getShoundExploreChildren()) {
                    if(firework.getSize() == 6) {
                        explodeCircle(firework, newParticles);
                    } else if(firework.getSize() == 5) {
                        explodeSmallCircle(firework, newParticles);
                    }
                }
            }
            firework.draw(graphicsContext);
        }
        particles.addAll(newParticles);
    }
    /*=========================================================================*/    
    /**
     * Transforms a particle into a big circle for simulating an explosion and generates new particles
     * @param firework - Particle to deal with
     * @param newParticles - set receiving new Particles
     * @see drawFireworks()
     */
    private void explodeCircle(Particle firework, List<Particle> newParticles) {
        final int count = 20 + (int)(60*Math.random());
        final boolean shouldExplodeChildren = Math.random() > 0.5;
        final double angle = (Math.PI * 2) / count;
        final int color = (int)(Math.random()*colors.length);
        for(int i=count; i>0; i--) {
            double randomVelocity = 4 + Math.random() * 4;
            double particleAngle = i * angle;
            newParticles.add(
                new Particle(
                    firework.getPosX(), firework.getPosY(), 
                    Math.cos(particleAngle) * randomVelocity, Math.sin(particleAngle) * randomVelocity,
                    0, 0, 
                    colors[color], 
                    5,
                    true, shouldExplodeChildren, true));
        }
    }
    /*=========================================================================*/
    /**
     * Transforms a particle into a small circle for simulating an explosion and generates new particles
     * @param firework - Particle to deal with
     * @param newParticles - set receiving new Particles
     * @see drawFireworks()
     */
    private void explodeSmallCircle(Particle firework, List<Particle> newParticles) {
        final double angle = (Math.PI * 2) / 12;
        for(int count=12; count>0; count--) {
            double randomVelocity = 2 + Math.random() * 2;
            double particleAngle = count * angle;
            newParticles.add(
                new Particle(
                    firework.getPosX(), firework.getPosY(), 
                    Math.cos(particleAngle) * randomVelocity, Math.sin(particleAngle) * randomVelocity,
                    0, 0, 
                    firework.getColor(), 
                    3,
                    true, false, false));
        }
    }
    /*=========================================================================*/
    /**
     * Relaunches a firework
     * @see initAnimationTimer()
     */
    private void fireParticle() {
        particles.add(new Particle(
            canvas.getWidth()*0.5, canvas.getHeight()+10,
            Math.random() * 5 - 2.5, 0, 
            0, 15 + Math.random() * 10,
            colors[0], 6,
            false, true, true));
    }
    /*=========================================================================*/
    /**
     * Initializes global animation parameters
     */
    private void initAnimationTimer() {
        /* Create animation timer that will be called every frame */
        this.timer = new AnimationTimer() {

            @Override public void handle(long now) {
                GraphicsContext gc = canvas.getGraphicsContext2D();
                // clear area with transparent black
                gc.setFill(Color.rgb(0, 0, 0, 0.2));                
                gc.fillRect(0, 0, 1024, 708);
                
                /* Draw fireworks */                
                drawFireworks(gc);
                
                /* Countdown to launching the next firework */
                if (countDownTillNextFirework == 0) {
                    countDownTillNextFirework = 10 + (int)(Math.random()*30);
                    fireParticle();
                }
                countDownTillNextFirework --;
            }
        };
    }
    /*=========================================================================*/
    /**
     * Initializes background 
     * @see Explosion()
     */
    private void initBackground() {
        this.backgroundImage.fitWidthProperty().bind(this.root.prefWidthProperty());
        this.backgroundImage.fitHeightProperty().bind(this.root.prefHeightProperty());
        
    }
    /*=========================================================================*/
    /**
     * Initializes canvas 
     * @see Explosion()
     */
    private void initCanvas() {
        this.canvas.setBlendMode(BlendMode.ADD);         
        this.root.getChildren().add(this.canvas);
    }
    /*=========================================================================*/
    /**
     * Initializes color palette 
     * @see Explosion()
     */
    private void initColors() {
        /* Create a color palette of 180 colors */
        colors[0] = new RadialGradient(0, 0, 0.5, 0.5, 0.5, true, CycleMethod.NO_CYCLE, 
                    new Stop(0, Color.WHITE),
                    new Stop(0.2, Color.hsb(59, 0.38, 1)),
                    new Stop(0.6, Color.hsb(59, 0.38, 1,0.1)),
                    new Stop(1, Color.hsb(59, 0.38, 1,0))
                    );
        for (int h=0;h<360;h+=2) {
            colors[1+(h/2)] = new RadialGradient(0, 0, 0.5, 0.5, 0.5, true, CycleMethod.NO_CYCLE, 
                    new Stop(0, Color.WHITE),
                    new Stop(0.2, Color.hsb(h, 1, 1)),
                    new Stop(0.6, Color.hsb(h, 1, 1,0.1)),
                    new Stop(1, Color.hsb(h, 1, 1,0))
                    );
        }
    }
}

    

