package battleship2D.model;

/**
 * Ship types
 * @author xaviator
 */
public enum ShipType {
    /*=========================================================================*/
    /* Members                                                                 */       
    /*=========================================================================*/
    
    MARGUERITTE("Margueritte", "-fx-background-image: url(\"battleship2D/pictures/margueritte.png\")"),
    CERISIER("Cerisier", "-fx-background-image: url(\"battleship2D/pictures/cerisier.png\")"),
    TULIPE("Tulipe", "-fx-background-image: url(\"battleship2D/pictures/tulipe.png\")"),
    ROSE("Rose", "-fx-background-image: url(\"battleship2D/pictures/rose.png\")"),
    UNICORN("Unicorn", "-fx-background-image: url(\"battleship2D/pictures/unicorn.png\")");

    /** Rendering either as a color or an image */
    private final String appearance;

    /** Short description */    
    private final String description;
    
    
    /*=========================================================================*/
    /* Public methods                                                          */       
    /*=========================================================================*/
    
    /*
     * Getters / Setters     
     */
    
    /**
     * @return the description field
     */
    public String getDescription() {
        return this.description;    
    }
    
    /**
     * 
     * @return the appearance field
     */
    public String getAppearance() {
        return this.appearance;
    }
    
    
    /*=========================================================================*/
    /* Private methods                                                         */       
    /*=========================================================================*/
    
    /**
     * Constructor
     * @param description - short description
     * @param color - related color
     */
    private ShipType (final String description, final String appearance) {
        this.description = description;
        this.appearance = appearance;
    }
}
