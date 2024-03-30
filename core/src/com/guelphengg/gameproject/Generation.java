package com.guelphengg.gameproject;

import com.guelphengg.gameproject.griditems.GridObject;

import java.util.Random;

public class Generation {

  public static void generateLandmarks() {
    final Random randNum = new Random();
    final GridObject[][] gridObjects = Accessor.getGameManager().getGridObjectArray();

    // generates a random position for the castle in the middle 4 squares of the grid
    final int castleRow = randNum.nextInt(4, 6);
    final int castleCol = randNum.nextInt(4, 6);

    gridObjects[castleRow][castleCol] = GridObject.CASTLE; // Only one castle

    // Generate in quadrant 1
    for (int i = 1; i <= 4; i++) {

      // 2 treasure houses per quadrant (8 total)
      generateObject(i, GridObject.TREASURE_HOUSE);
      generateObject(i, GridObject.TREASURE_HOUSE);

      // 3 lost item houses per quadrant (12 total)
      generateObject(i, GridObject.LOST_ITEM_HOUSE);
      generateObject(i, GridObject.LOST_ITEM_HOUSE);
      generateObject(i, GridObject.LOST_ITEM_HOUSE);

      // 1 trapped house per quadrant (4 total)
      generateObject(i, GridObject.TRAPPED_HOUSE);

      // 1 market per quadrant (4 total)
      generateObject(i, GridObject.MARKET);

    }
  }

  // Warning this will cause and infinite loop if the quadrant is completely full
  private static void generateObject(int quadrant, GridObject object) {
    final GridObject[][] gridObjects = Accessor.getGameManager().getGridObjectArray();
    final Random randNum = new Random();

    boolean success = false;

    // Keep generating until we find a spot that is empty
    while (!success) {
      final int randRow;
      final int randCol;

      switch (quadrant) {
        case 1:
          randRow = randNum.nextInt(0, 5);
          randCol = randNum.nextInt(0, 5);
          break;
        case 2:
          randRow = randNum.nextInt(5, 10);
          randCol = randNum.nextInt(0, 5);
          break;
        case 3:
          randRow = randNum.nextInt(0, 5);
          randCol = randNum.nextInt(5, 10);
          break;
        case 4:
          randRow = randNum.nextInt(5, 10);
          randCol = randNum.nextInt(5, 10);
          break;
        default:
          randRow = 0;
          randCol = 0;
          break;
      }

      if (gridObjects[randRow][randCol] == null) {
        gridObjects[randRow][randCol] = object;
        success = true;
      }
    }
  }
}
