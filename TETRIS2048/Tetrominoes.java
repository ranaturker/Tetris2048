import java.awt.Point;
import java.util.List;
import java.util.ArrayList;
public class Tetrominoes {
    private int gridWidth, gridHeight;
    private Tile[][] tileMatrix;
    private Point bottomLeftCorner;
    private char Name;
    public Boolean GameOver = false;

    Tetrominoes (char randomName, int gridHeight, int gridWidth) {
        this.gridHeight = gridHeight;
        this.gridWidth = gridWidth;
        this.Name = randomName;
        if (randomName == 'I') {
            tileMatrix = new Tile[4][4];
            tileMatrix[1][0] = new Tile(new Point(5, gridHeight + 3));
            tileMatrix[1][1] = new Tile(new Point(5, gridHeight + 2));
            tileMatrix[1][2] = new Tile(new Point(5, gridHeight + 1));
            tileMatrix[1][3] = new Tile(new Point(5, gridHeight));
        }
        else if (randomName == 'Z') {
            tileMatrix = new Tile[3][3];
            tileMatrix[1][0] = new Tile(new Point(6, gridHeight + 2));
            tileMatrix[2][0] = new Tile(new Point(7, gridHeight + 2));
            tileMatrix[0][1] = new Tile(new Point(5, gridHeight + 1));
            tileMatrix[1][1] = new Tile(new Point(6, gridHeight + 1));
        }
        else if (randomName == 'S') {
            tileMatrix = new Tile[3][3];
            tileMatrix[0][0] = new Tile(new Point(5, gridHeight + 2));
            tileMatrix[1][0] = new Tile(new Point(6, gridHeight + 2));
            tileMatrix[1][1] = new Tile(new Point(6, gridHeight + 1));
            tileMatrix[2][1] = new Tile(new Point(7, gridHeight + 1));
        }
        else if (randomName == 'O') {
            tileMatrix = new Tile[2][2];
            tileMatrix[0][0] = new Tile(new Point(5, gridHeight));
            tileMatrix[0][1] = new Tile(new Point(5, gridHeight + 1));
            tileMatrix[1][0] = new Tile(new Point(6, gridHeight));
            tileMatrix[1][1] = new Tile(new Point(6, gridHeight + 1));
        }
        else if (randomName == 'T') {
            tileMatrix = new Tile[3][3];
            tileMatrix[0][1] = new Tile(new Point(5, gridHeight + 1));
            tileMatrix[1][1] = new Tile(new Point(6, gridHeight + 1));
            tileMatrix[2][1] = new Tile(new Point(7, gridHeight + 1));
            tileMatrix[1][2] = new Tile(new Point(6, gridHeight));
        }
        else if (randomName == 'L') {
            tileMatrix = new Tile[3][3];
            tileMatrix[1][0] = new Tile(new Point(5, gridHeight + 2));
            tileMatrix[1][1] = new Tile(new Point(5, gridHeight + 1));
            tileMatrix[1][2] = new Tile(new Point(5, gridHeight));
            tileMatrix[2][2] = new Tile(new Point(6, gridHeight));
        }
        else {
            tileMatrix = new Tile[3][3];
            tileMatrix[1][0] = new Tile(new Point(6, gridHeight + 2));
            tileMatrix[1][1] = new Tile(new Point(6, gridHeight + 1));
            tileMatrix[0][2] = new Tile(new Point(5, gridHeight));
            tileMatrix[1][2] = new Tile(new Point(6, gridHeight));
        }
        bottomLeftCorner = new Point(5, gridHeight);
    }
    public char Name() {
        return Name;
    }
	 for rotate the tetromino clockwise by 90 degrees
    public boolean Rotate(Grid gameGrid) {
        if (bottomLeftCorner.y + tileMatrix.length > gridHeight)
            return false;
        int n = tileMatrix.length;
        for (int row = 0; row < n; row++) {
            for (int col = 0; col < n; col++) {
                Point position = new Point();

                if(tileMatrix[row][col] != null)
                    position = tileMatrix[row][col].getPosition();
                else {
                    position.x = bottomLeftCorner.x + col;
                    position.y = bottomLeftCorner.y + (n-1) - row;
                }
                if(position.x < 0 || position.x >= gridWidth)
                    return false;
                if (gameGrid.isOccupied(position))
                    return false;
            }
        }

        Tile [][] rotatedMatrix = new Tile[n][n];
        for (int row = 0; row < n; row++) {
            for (int col = 0; col < n; col++) {
                if(tileMatrix[row][col]==null)
                    continue;
                Point position = tileMatrix[row][col].getPosition();
                rotatedMatrix[col][n-1-row] = tileMatrix[row][col];
                position.x=bottomLeftCorner.x + (n-1-row);
                position.y=bottomLeftCorner.y + (n-1) - col;
                tileMatrix[row][col].setPosition(position);
            }
        }
        tileMatrix = rotatedMatrix;
        return true;
    }
    public void display() {
        for (Tile tile : getTileList()){
            tile.display();
        }
    }
    public boolean canMove(Grid gameGrid, Point move)
    {
        for (Tile tile : getTileList())
        {
            Point nextPosition = new Point(tile.getPosition().x + move.x, tile.getPosition().y + move.y);
            if (nextPosition.x < 0 || nextPosition.x >= gridWidth || nextPosition.y < 0)
                return false;
            if (gameGrid.isOccupied(nextPosition))
                return false;
        }
        return true;
    }
    public boolean goDown(Grid gameGrid) {
        if (!canMove(gameGrid, new Point(0, -1)))
        {
            if (bottomLeftCorner.y + tileMatrix.length >= gridHeight) {
                GameOver = true;
            }
            return false;
        }

        for (Tile tile : getTileList())	{
            tile.move(0, -1);
        }
        bottomLeftCorner.translate(0, -1);
        return true;
    }
    public boolean goLeft(Grid gameGrid) {
        if (!canMove(gameGrid, new Point(-1, 0)))
            return false;

        for (Tile tile : getTileList()){
            tile.move(-1, 0);
        }
        bottomLeftCorner.translate(-1, 0);
        return true;
    }
    public boolean goRight(Grid gameGrid) {
        if (!canMove(gameGrid, new Point(1, 0)))
            return false;

        for (Tile tile : getTileList())	{
            tile.move(1, 0);
        }
        bottomLeftCorner.translate(1, 0);
        return true;
    }
    public List<Tile> getTileList() {
        List<Tile> aList = new ArrayList<Tile>(4);
        int n = tileMatrix.length;
        for (int row = 0; row < n; row++) {
            for (int col = 0; col < n; col++) {
                if(tileMatrix[row][col]==null)
                    continue;
                aList.add(tileMatrix[row][col]);
            }
        }
        return aList;
    }
}