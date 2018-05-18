package battleship2D.model;

/**
 * Cell Types 
 * @author xaviator
 */
public enum CellType{ 
    /*=========================================================================*/
    /* Members                                                                 */       
    /*=========================================================================*/
    
    AVAILABLE_LOCATION("Available", "-fx-background-color: magenta"), // the cell is available to receive a (piece of) ship,
    BATTLESHIP(ShipType.MARGUERITTE),
    CARRIER(ShipType.CERISIER),
    CRUISER(ShipType.TULIPE),
    DESTROYER(ShipType.ROSE),
    HIT("Hit", "-fx-background-image: url(\"battleship2D/pictures/destroyed.png\")"), // a piece of ship has been hit by a foe missile
    OCEAN("Ocean", "-fx-background-image: url(\"battleship2D/pictures/ocean.gif\")"), // nothing on this tile    
    SUBMARINE(ShipType.UNICORN),   
    UNKNOWN("Unknown", "-fx-background-image: url(\"battleship2D/pictures/unknom.gif\")"); // the cell is hidden by some "fog of war"

    /** Rendering either as a color or an image */
    private final String appearance;
    
    /** Short description */    
    private final String description;
    
        
    /*=========================================================================*/
    /* Public methods                                                          */       
    /*=========================================================================*/
    
    /**
     * Check whether this CellType is a ship
     * @return true if this is a ship, false otherwise
     */
    public boolean isAShip() {
        return (this == CellType.BATTLESHIP || this == CellType.CARRIER
                || this == CellType.CRUISER || this == CellType.DESTROYER
                || this == CellType.SUBMARINE);
    }
    
    /**
     * Converts a CellType into a ShipType
     * @param cellType - cell type to deal with
     * @return the matching ship type
     */
    public static ShipType cellTypeToShipType(CellType cellType) {
        switch (cellType) {
            case BATTLESHIP:
                return ShipType.MARGUERITTE;
            case CARRIER:
                return ShipType.CERISIER;
            case CRUISER:
                return ShipType.TULIPE;
            case DESTROYER:
                return ShipType.ROSE;
            case SUBMARINE:
                return ShipType.UNICORN;
            default:
                return null;
        }
    }
    
    /**
     * Converts a ShipType into a CellType
     * @param shipType - the ship type to convert
     * @return the matching cell type
     */
    public static CellType shipTypeToCellType(ShipType shipType) {
        switch (shipType) {
            case MARGUERITTE:
                return CellType.BATTLESHIP;
            case CERISIER:
                return CellType.CARRIER;
            case TULIPE:
                return CellType.CRUISER;
            case ROSE:
                return CellType.DESTROYER;
            case UNICORN:
                return CellType.SUBMARINE;
            default:
                return null;
        }
    }
    
    /*
     * Getters / Setters     
     */
    
    public String getDescription() {
        return this.description;    
    }
    
    public String getAppearance() {
        return this.appearance;
    }

    
    /*=========================================================================*/
    /* Private methods                                                         */       
    /*=========================================================================*/
    
    /**
     * Constructor
     * @param description - short description
     * @param appearance - related appearance
     */
    private CellType (final String description, final String appearance) {
        this.description = description;
        this.appearance = appearance;
    }

    /**
     * Constructor
     * @param description - short description
     * @param appearance - related appearance
     */
    private CellType (ShipType shipType) {
        this.description = shipType.getDescription();
        this.appearance = shipType.getAppearance();
    }
}