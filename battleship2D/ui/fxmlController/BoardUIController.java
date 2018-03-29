/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package battleship2D.ui.fxmlController;

import battleship2D.model.BoardModel;
import battleship2D.model.CellModel;
import battleship2D.model.CellType;
import battleship2D.model.Direction;
import battleship2D.ui.CellUI;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.ArrayList;
import javafx.beans.property.DoubleProperty;
import javafx.scene.Node;
import javafx.scene.layout.GridPane;


public abstract class BoardUIController  {
    /*=========================================================================*/
    /* Members                                                                 */       
    /*=========================================================================*/
    
    /** Related board model */
    protected  BoardModel boardModel;
    /** Board name */
    private  String name;
    
    /** Listeners management */
    protected final PropertyChangeSupport pcsListeners = new PropertyChangeSupport(this);  
    
    
    /** Designated cells for missile  trajectorey endpoints */
    protected CellUI missileDestination, missileSource;
    
    /** This class listens for each of its cells */
    protected PropertyChangeListener propertyChangeListener;
    
    /** Set of oriented cells available for placing a ship on the board */
    protected ArrayList<CellModel> eastCellSpan, northCellSpan, southCellSpan, westCellSpan; 
    
    /*=========================================================================*/
    /* Public methods                                                          */       
    /*=========================================================================*/
    public void construct(String name, BoardModel boardModel, Boolean isBound){
        this.name = name;
        this.missileDestination = null;
        this.missileSource = null;
        this.boardModel = boardModel;
        
        initCellSpans();        
        initCellUIListener(); /* This method must be called before initCells() to initialize propertyChangeListener in a proper way.*/
        initCells(isBound);
    }
    /*=========================================================================*/
    protected abstract void add (CellUI c , int row , int column);
    /*=========================================================================*/
    protected abstract GridPane getRoot();
    /*=========================================================================*/
     public DoubleProperty layoutYProperty(){
        return this.getRoot().layoutYProperty();
    }
    /*=========================================================================*/
    public DoubleProperty layoutXProperty(){
        return this.getRoot().layoutXProperty();
    }
    /*=========================================================================*/
    /**
     * Searches for the CellUI associated with a CellModel
     * @param cellModel - associated CellModel 
     * @return the associated CellUI if found, null otherwise
     */
    public CellUI findCellUIFromModel(CellModel cellModel) {
        for (Node cellUI :  this.getRoot().getChildren()) {
            if (((CellUI)(cellUI)).getCellModel() == cellModel) {
                return (CellUI) cellUI;
            }
        }
        return null;
    }
    /*=========================================================================*/
    /**
     * Checks the presence of a ship, depending on a given type
     * @param cellType - ship type
     * @return true if a ship has been placed on the board
     */
    public Boolean isShipOnBoard(CellType cellType) {
        /*
            We search for one part of ship only over the board.
            We assume that if one part of the ship has been placed on the board,
            the whole ship has also been placed.
        */
        return this.boardModel.isCellTypeInside(cellType);        
    }
    /*=========================================================================*/
    /*
     * Property Change Listeners management
     */
    public void addPropertyChangeListener(PropertyChangeListener propertyChangeListener) {
        this.pcsListeners.addPropertyChangeListener(propertyChangeListener);
    }
    /*=========================================================================*/
    public void removePropertyChangeListener (PropertyChangeListener propertyChangeListener) {
        this.pcsListeners.removePropertyChangeListener(propertyChangeListener);
    }
    /*=========================================================================*/
    /*
     * Getters / setters
     */
    
    public BoardModel getBoardModel() {
        return this.boardModel;
    }
    
    public String getName() {
        return this.name;
    }
    
    public CellUI getMissileDestination() {
        return this.missileDestination;
    }
    
    public void setMissileDestination(CellUI cellUI) {
        this.missileDestination = cellUI;
    }
    
    public CellUI getMissileSource() {
        return this.missileSource;
    }
    
    public void setMissileSource(CellUI cellUI) {
        this.missileSource = cellUI;
    }

    
    /*=========================================================================*/
    /* Protected methods                                                         */       
    /*=========================================================================*/
    
    /**
     * Computes a set of adjacent cells along a given direction
     * @param cellModel - first cell of the span
     * @param direction - direction to follow to get successive cells
     * @param nbCells - span length
     * @return the set of cells if there are enough available cells 
     * (i.e. the span does not expand beyond the grid), null otherwise
     */
    protected ArrayList<CellModel> cellSpan(CellModel cellModel,
                                          Direction direction, int nbCells) {
        ArrayList<CellModel> cellSpan = null;
        
        switch(direction) {
            case NORTH:
                cellSpan = this.northCellSpan;
                break;  
            case WEST: 
                cellSpan = this.westCellSpan;
                break;  
            case SOUTH:
                cellSpan = this.southCellSpan;
                break;  
            case EAST: 
                cellSpan = this.eastCellSpan;
                break;     
            default:
                break;
        }       
        
        if (cellSpan != null) {
            cellSpan.clear();        

            /* currentCell is the first cellSpan's cell  */
            CellModel currentCell = cellModel;
            cellSpan.add(currentCell);

            CellModel nextCell;
            for (int i = 1 ; i < nbCells; i++) {
                nextCell = this.boardModel.adjacentCell(currentCell, direction);

                if (nextCell == null) {
                    /* The span goes beyond the board */
                    cellSpan.clear();
                    break;
                }
                else {
                    cellSpan.add(nextCell);
                    currentCell = nextCell;
                }            
            }
        }            
        return cellSpan;
    }
    /*=========================================================================*/
    /**
     * Initializes the cells listener
     * @see BoardUI#BoardUI
     */
    protected final void initCellUIListener() {
        this.propertyChangeListener = (PropertyChangeEvent propertyChangeEvent) -> {
            initSpecificCellUIListener(propertyChangeEvent);
        };
    }
    /*=========================================================================*/
    /**
     * Manages notification sent by CellUIs
     * @param propertyChangeEvent - event to deal with 
     */
    protected abstract void initSpecificCellUIListener(PropertyChangeEvent propertyChangeEvent);
    
    
    /*=========================================================================*/
    /* Private methods                                                         */       
    /*=========================================================================*/
    
    /**
     * Initializes contents
     * @param isBound - determines whether CellUI's styles are bound to cell models'
     * @see BoardUI()
     */
    private void initCells(Boolean isBound) {
        for (int row = 0; row < BoardModel.BOARD_SIZE; row++) {        
            for (int column = 0; column < BoardModel.BOARD_SIZE; column++) {
                CellUI cellUI = new CellUI(this.boardModel.getCellModel(row, column), isBound);                
                add(cellUI, row, column);
                cellUI.addPropertyChangeListener(this.propertyChangeListener);
            }
        }
        /* To ensure that both missileDestination and missileSource are never null, 
            set their default value as the first cell of this. */
        this.missileDestination = this.missileSource = (CellUI) this.getRoot().getChildren().get(0);
    }
    /*=========================================================================*/
    /**
     * Initializes the different cell spans
     * @see BoardUI()
     */
    private void initCellSpans() {
        this.northCellSpan = new ArrayList<>();
        this.westCellSpan = new ArrayList<>();
        this.southCellSpan = new ArrayList<>();
        this.eastCellSpan = new ArrayList<>();        
    }
    
}
