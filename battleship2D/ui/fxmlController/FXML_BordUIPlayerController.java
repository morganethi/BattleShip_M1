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
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.GridPane;

/**
 * FXML Controller class
 *
 * @author Jeremy
 */
public class FXML_BordUIPlayerController extends FXML_BordUIController implements Initializable {

    @FXML
    private GridPane root;
        /** User-selected cell span to place a ship on the board */
    private ArrayList<CellModel> selectedCellSpan;
    
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        root.prefWidthProperty().bind(root.widthProperty());
    }    
    
    public void construct (String name, BoardModel boardModel, Boolean isBound){
        super.construct(name, boardModel, isBound);
        this.selectedCellSpan = null;
    }
    
     @Override
     protected  GridPane getRoot(){
         return root;
     }
    
    public BoardModel getBoardModel(){
        return super.getBoardModel();
    }
    
    @Override
    protected  void add (CellUI c , int row , int column){
        root.add(c,row,column);
    }
    /**
     * Manages notification sent by CellUIs
     * @param propertyChangeEvent - event to deal with
     */
    @Override
    protected void initSpecificCellUIListener(PropertyChangeEvent propertyChangeEvent) {
        String property = propertyChangeEvent.getPropertyName();
       // this.missileDestination = (CellUI) propertyChangeEvent.getSource();

        /* Mouse button 3 has been clicked: cancel current action */
        if ("cellUICancel".equals(property)) {  
            this.pcsListeners.firePropertyChange("boardUIPlayerCancel", false, true);                
        }

        if ("cellUIOcean".equals(property)) {
            this.pcsListeners.firePropertyChange("boardUIPlayerOcean", null, propertyChangeEvent.getNewValue());                
        }

        /* Inserts a ship on an available cell span*/
        if ("cellUIAvailableLocation".equals(property)) {                 
            CellModel cellModel = (CellModel) propertyChangeEvent.getNewValue();

            findSelectedCellSpan(cellModel);
            if (this.selectedCellSpan == null) {
                System.err.println("selectedCellSpan == null");
            }
            else {
                /* Same behaviour as if the mouse button 3 had been clicked */
                this.pcsListeners.firePropertyChange("boardUIPlayerCancel", false, true);  

                /* In order to actually insert the ship on the board,
                    we need information from an observer. */
                this.pcsListeners.firePropertyChange("boardUIPlayerAvailableLocation", null, this.selectedCellSpan);
            }
        }
    }
    
     /**
     * Searches for one of the cell spans available for inserting a ship
     * and sets selectedCellSpan accordingly.
     * @param cellModel - one of the cells constituting the span to retrieve.
     * Note that this cell may belong to several cell spans. In such a case, 
     * the returned cell span is the first one
     * following the directions: North, West, South, East in this order
     * @return one cell span containing cellModel
     * @see initCellUIListener()
     */
    private void findSelectedCellSpan(CellModel cellModel) {
        if (this.northCellSpan.contains(cellModel)) {            
            this.selectedCellSpan = this.northCellSpan;
            return;
        }
        if (this.westCellSpan.contains(cellModel)) {
            this.selectedCellSpan = this.westCellSpan;
            return;
        }
        if (this.southCellSpan.contains(cellModel)) {
            this.selectedCellSpan = this.southCellSpan;
            return;
        }
        if (this.eastCellSpan.contains(cellModel)) {
            this.selectedCellSpan = this.eastCellSpan;
            return;
        }
        this.selectedCellSpan = null;
    }
    
        /**
     * Validates the user choice by actually inserting a ship on board
     * @param cellType - type related to the ship to insert
     */
    public void placeShipOnBoard(CellType cellType) {
        if (this.selectedCellSpan != null) {
            for (CellModel cellModel : this.selectedCellSpan) {
                cellModel.setCellType(cellType);
            }
        }
    }
    
    /** 
     * Highlights the cells that can receive a ship in a given direction     
     * @param cellModel - first cell of the span representing the ship
     * @param direction direction to follow to get successive cells
     * @param shipSize ship's span
     */
    public void availableCellSpanByDirection(CellModel cellModel,
            Direction direction, int shipSize) {
        
        ArrayList<CellModel> cellSpanByDirection = cellSpan(cellModel, direction, shipSize);
        
        /* Checks whether all cells are available for placing the ship
         Cells must match one of the following conditions:
         - cell is empty
         - cell is already available for location
         - cell represents the same ship as the currently selected one */
        Boolean validLocation = true;        
        
        for (CellModel currentCell : cellSpanByDirection) {            
            validLocation = ((currentCell.getCellType() == this.boardModel.getDefaultCellType()) || 
                    (currentCell.getCellType() == CellType.AVAILABLE_LOCATION));
            if (! validLocation) {
                break;
            }
        }
        
        /* If every cell is available for a given direction, highlight those cells */
		/* cellSpanByDirection.size() == shipSize iif the span does not go beyond the 				board along the given direction */
        if (validLocation && cellSpanByDirection.size() == shipSize) {           
            for (CellModel currentCell : cellSpanByDirection) {            
                currentCell.setCellType(CellType.AVAILABLE_LOCATION);
            }
        }
        else {
            cellSpanByDirection.clear();
        }
    }
    
    /**
     * Erases the cells:
     * - showing the currently selected ship
     * - highlighting the available locations to insert a ship
     * @param shipType - type of the currenly selected ship, expressed as a CellType
     */
    public void eraseSelectedShipLocations(CellType shipType) {
        this.boardModel.replaceAll(shipType,
                                   this.boardModel.getDefaultCellType());
        
        this.boardModel.replaceAll(CellType.AVAILABLE_LOCATION,
                                   this.boardModel.getDefaultCellType());
    }
    
}
