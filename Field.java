public class Field {
    private int mapSize = 10;
    private Ship[][] cellsWithShips;
    private int directions = 4;
    private int[] countFreeCells;
    private final int right = 0;
    private final int left = 1;
    private final int down = 2;
    private final int up = 3;
    private int shipsNumber;
    private int sunkShipsNumber;
    Field() {
        shipsNumber = 10;
        sunkShipsNumber = 0;
        cellsWithShips = new Ship[mapSize][mapSize];
        countFreeCells = new int[directions];
    }
    boolean isGameOver() {
        if (sunkShipsNumber < shipsNumber) return false;
        return true;
    }
    boolean checkCell(int x, int y) {
        //checking cell
        if (cellsWithShips[x][y] != null) return false;
        //checking cells in all directions
        if (x + 1 < 10 && cellsWithShips[x + 1][y] != null) return false;
        if (x - 1 >= 0 && cellsWithShips[x - 1][y] != null) return false;
        if (y + 1 < 10 && cellsWithShips[x][y + 1] != null) return false;
        if (y - 1 >= 0 && cellsWithShips[x][y - 1] != null) return false;
        //checking cell in diagonals
        if (x + 1 < 10 && y + 1 < 10 && cellsWithShips[x + 1][y + 1] != null) return false;
        if (x - 1 >= 0 && y - 1 >= 0 && cellsWithShips[x - 1][y - 1] != null) return false;
        if (x + 1 < 10 && y - 1 >= 0 && cellsWithShips[x + 1][y - 1] != null) return  false;
        if (x - 1 >= 0 && y + 1 < 10 && cellsWithShips[x - 1][y + 1] != null) return false;
        return true;
    }

    boolean isNotOutOfBounds(int coordinate) {
        if (coordinate >= 0 && coordinate < 10)
            return true;
        return false;
    }

    boolean isNextCellSuitable(int x, int y, int decks, int direction) {
        if (direction == left || direction == right) {
            if (!isNotOutOfBounds(y) && countFreeCells[direction] == decks)
                return true;
            if (isNotOutOfBounds(y)) {
                if (x + 1 < 10 && x - 1 >= 0 &&
                        cellsWithShips[x][y]     == null &&
                        cellsWithShips[x + 1][y] == null &&
                        cellsWithShips[x - 1][y] == null)
                    return true;
                else if (!isNotOutOfBounds(x + 1) &&
                        cellsWithShips[x][y]     == null &&
                        cellsWithShips[x - 1][y] == null)
                    return true;
                else if (!isNotOutOfBounds(x - 1) &&
                        cellsWithShips[x][y]     == null &&
                        cellsWithShips[x + 1][y] == null)
                    return true;
            }
        }
        else if (direction == up || direction == down) {
            if (!isNotOutOfBounds(x) && countFreeCells[direction] == decks)
                return true;
            if (isNotOutOfBounds(x)) {
                if (y + 1 < 10 && y - 1 >= 0 &&
                        cellsWithShips[x][y]     == null &&
                        cellsWithShips[x][y + 1] == null &&
                        cellsWithShips[x][y - 1] == null)
                    return true;
                else if (!isNotOutOfBounds(y + 1) &&
                        cellsWithShips[x][y]     == null &&
                        cellsWithShips[x][y - 1] == null)
                    return true;
                else if (!isNotOutOfBounds(y - 1) &&
                        cellsWithShips[x][y]     == null &&
                        cellsWithShips[x][y + 1] == null)
                    return true;
            }
        }
        return false;
    }
    boolean isPossibleToPlace(int x, int y, int decks) {
       if (!checkCell(x, y)) return false;
        int shift = 1;
        while (shift < decks + 1) {
            if (isNextCellSuitable(x, y + shift, decks, right)) countFreeCells[right]++;
            if (isNextCellSuitable(x, y - shift, decks, left))  countFreeCells[left]++;
            if (isNextCellSuitable(x + shift, y, decks, down))  countFreeCells[down]++;
            if (isNextCellSuitable(x - shift, y, decks, up))    countFreeCells[up]++;
            shift++;
        }
        for (int direction = right; direction < countFreeCells.length; direction++) {
            if (countFreeCells[direction] == decks)
                return true;
        }
        for (int i = 0; i < directions; i++) // reset counter
            countFreeCells[i] = 0;
        return false;
    }
    void setField() {
        int x, y;
        int decks;
        int numberOfSimilarShips;
        for (decks = 4; decks > 0; decks--) {
            numberOfSimilarShips = 5 - decks;
            for (int i = 0; i < numberOfSimilarShips; i++) {
                do {
                    x = (int)(Math.random() * 10);
                    y = (int)(Math.random() * 10);
                } while (!isPossibleToPlace(x, y, decks));
                setShip(x, y, decks);
            }
        }
    }
    void setShip(int x, int y, int decks) {
        boolean isPlaced = false;
        Ship ship = new Ship(x, y, decks);
        //todo if decks == 0 break
        cellsWithShips[x][y] = ship;
        int direction = (int) (Math.random() * 4);
        while (!isPlaced) {
            if (countFreeCells[direction] == decks) {
                isPlaced = true;
                if (direction == right)
                    for (int j = 0; j < decks; j++)
                        cellsWithShips[x][y++] = ship;
                if (direction == left)
                    for (int j = 0; j < decks; j++)
                        cellsWithShips[x][y--] = ship;
                if (direction == down)
                    for (int j = 0; j < decks; j++)
                        cellsWithShips[x++][y] = ship;
                if (direction == up)
                    for (int j = 0; j < decks; j++)
                        cellsWithShips[x--][y] = ship;
                for (int i = 0; i < directions; i++) // reset counter
                    countFreeCells[i] = 0;
            }
            direction = (int)(Math.random() * 4);
        }
    }
    int doShoot(int x, int y) {
        if (cellsWithShips[x][y] == null) {
            // todo set ship with 0 decks
            return 0; // miss
        }
        else {
            // if decks == 0, already shooted this cell, return 3
            if (cellsWithShips[x][y].getState(x, y) == false) return 3;
            cellsWithShips[x][y].getShoot(x, y);
            if (!cellsWithShips[x][y].isKilled()) return 1;
            else {
                sunkShipsNumber++;
                return 2;
            }
        }
    }
    void showMap1() {
        System.out.println("   | a b c d e f g h i j");
        System.out.println("---+--------------------");
        for (int i = 0; i < cellsWithShips.length; i++) {
            System.out.printf("%2d | ", i + 1);
            for (int j = 0; j < cellsWithShips.length; j++) {
                if (cellsWithShips[i][j] == null)
                    System.out.print("~ ");
                    // todo if decks == 0, println .
                else {
                    System.out.print("X ");
                }
            }
            System.out.println();
        }
    }
    void showMap() {
        System.out.println("   | a b c d e f g h i j");
        System.out.println("---+--------------------");
        for (int i = 0; i < cellsWithShips.length; i++) {
            System.out.printf("%2d | ", i + 1);
            for (int j = 0; j < cellsWithShips.length; j++) {
                if (cellsWithShips[i][j] == null)
                    System.out.print("~ ");
                // todo if decks == 0, println .
                else {
                    if (cellsWithShips[i][j].getState(i, j) == false) System.out.print("X ");
                    else System.out.print("~ ");;
                }
            }
            System.out.println();
        }
    }
}
