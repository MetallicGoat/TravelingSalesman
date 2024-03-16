package com.guelphengg.gameproject;

import com.guelphengg.gameproject.griditems.GridObject;
import com.guelphengg.gameproject.GameManager;
import java.util.Random;

public class Generation {

    public static void generateLandmarks(){
        Random randNum = new Random();
        GridObject[][] gridObjects = Accessor.getGameManager().getGridObjectArray();

        //generates a random position for the castle in the middle 4 squares of the grid
        int castleRow = randNum.nextInt(4,6);
        int castleCol = randNum.nextInt(4,6);
        gridObjects[castleRow][castleCol] = GridObject.CASTLE;

        //generates random ints between 0-9
        int randRow;
        int randCol;

        //array of all structures in the game
        GridObject[] gridObjList = {GridObject.TREASURE_HOUSE, GridObject.TRAPPED_HOUSE, GridObject.LOST_ITEM_HOUSE, GridObject.MARKET};


        //logic for generating treasure houses
        int numTreasureHouses = 2;
        //Generate in quadrant 1
        for(int i = 0; i < numTreasureHouses; i++) {
            randRow = randNum.nextInt(0,5);
            randCol = randNum.nextInt(0,5);
            if (gridObjects[randRow][randCol] == null) {
                gridObjects[randRow][randCol] = gridObjList[0];
            }
        }
        //Generate in Quad 2
        for(int i = 0; i < numTreasureHouses; i++) {
            randRow = randNum.nextInt(5,10);
            randCol = randNum.nextInt(0,5);
            if (gridObjects[randRow][randCol] == null) {
                gridObjects[randRow][randCol] = gridObjList[0];
            }
        }
        //Generate in Quad 3
        for(int i = 0; i < numTreasureHouses; i++) {
            randRow = randNum.nextInt(0,5);
            randCol = randNum.nextInt(5,10);
            if (gridObjects[randRow][randCol] == null) {
                gridObjects[randRow][randCol] = gridObjList[0];
            }
        }
        //Generate in Quad 4
        for(int i = 0; i < numTreasureHouses; i++) {
            randRow = randNum.nextInt(5,10);
            randCol = randNum.nextInt(5,10);
            if (gridObjects[randRow][randCol] == null) {
                gridObjects[randRow][randCol] = gridObjList[0];
            }
        }

        //logic for generating trapped houses
        int numTrappedHouses = 1;
        //Generate in quadrant 1
        for(int i = 0; i < numTrappedHouses; i++) {
            randRow = randNum.nextInt(0,5);
            randCol = randNum.nextInt(0,5);
            if (gridObjects[randRow][randCol] == null) {
                gridObjects[randRow][randCol] = gridObjList[1];
            }
        }
        //Generate in Quad 2
        for(int i = 0; i < numTreasureHouses; i++) {
            randRow = randNum.nextInt(5,10);
            randCol = randNum.nextInt(0,5);
            if (gridObjects[randRow][randCol] == null) {
                gridObjects[randRow][randCol] = gridObjList[1];
            }
        }
        //Generate in Quad 3
        for(int i = 0; i < numTreasureHouses; i++) {
            randRow = randNum.nextInt(0,5);
            randCol = randNum.nextInt(5,10);
            if (gridObjects[randRow][randCol] == null) {
                gridObjects[randRow][randCol] = gridObjList[1];
            }
        }
        //Generate in Quad 4
        for(int i = 0; i < numTreasureHouses; i++) {
            randRow = randNum.nextInt(5,10);
            randCol = randNum.nextInt(5,10);
            if (gridObjects[randRow][randCol] == null) {
                gridObjects[randRow][randCol] = gridObjList[1];
            }
        }

        //logic for generating lost items
        int numLostItems = 4;
        //Generate in quadrant 1
        for(int i = 0; i < numLostItems; i++) {
            randRow = randNum.nextInt(0,5);
            randCol = randNum.nextInt(0,5);
            if (gridObjects[randRow][randCol] == null) {
                gridObjects[randRow][randCol] = gridObjList[2];
            }
        }
        //Generate in Quad 2
        for(int i = 0; i < numLostItems; i++) {
            randRow = randNum.nextInt(5,10);
            randCol = randNum.nextInt(0,5);
            if (gridObjects[randRow][randCol] == null) {
                gridObjects[randRow][randCol] = gridObjList[2];
            }
        }
        //Generate in Quad 3
        for(int i = 0; i < numLostItems; i++) {
            randRow = randNum.nextInt(0,5);
            randCol = randNum.nextInt(5,10);
            if (gridObjects[randRow][randCol] == null) {
                gridObjects[randRow][randCol] = gridObjList[2];
            }
        }
        //Generate in Quad 4
        for(int i = 0; i < numLostItems; i++) {
            randRow = randNum.nextInt(5,10);
            randCol = randNum.nextInt(5,10);
            if (gridObjects[randRow][randCol] == null) {
                gridObjects[randRow][randCol] = gridObjList[2];
            }
        }

        //logic for generating markets
        int numMarkets = 1;
        //Generate in quadrant 1
        for(int i = 0; i < numMarkets; i++) {
            randRow = randNum.nextInt(0,5);
            randCol = randNum.nextInt(0,5);
            if (gridObjects[randRow][randCol] == null) {
                gridObjects[randRow][randCol] = gridObjList[3];
            }
        }
        //Generate in Quad 2
        for(int i = 0; i < numMarkets; i++) {
            randRow = randNum.nextInt(5,10);
            randCol = randNum.nextInt(0,5);
            if (gridObjects[randRow][randCol] == null) {
                gridObjects[randRow][randCol] = gridObjList[3];
            }
        }
        //Generate in Quad 3
        for(int i = 0; i < numMarkets; i++) {
            randRow = randNum.nextInt(0,5);
            randCol = randNum.nextInt(5,10);
            if (gridObjects[randRow][randCol] == null) {
                gridObjects[randRow][randCol] = gridObjList[3];
            }
        }
        //Generate in Quad 4
        for(int i = 0; i < numMarkets; i++) {
            randRow = randNum.nextInt(5,10);
            randCol = randNum.nextInt(5,10);
            if (gridObjects[randRow][randCol] == null) {
                gridObjects[randRow][randCol] = gridObjList[3];
            }
        }
    }
}
