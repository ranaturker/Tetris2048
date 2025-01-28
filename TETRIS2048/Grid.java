import java.awt.Point;
import java.util.List;
import java.util.ListIterator;
import java.util.ArrayList;
import java.util.Collections;


public class Grid {


    public volatile static boolean paused = false;

    private int Height;
    private int Width;
    private List<Tile> TileList = new ArrayList<Tile>();
    private int Score;

    Grid (int n_rows, int n_cols) {
        this.Height = n_rows;
        this.Width = n_cols;
        this.Score = 0;
    }


    public boolean isOccupied(Point blockPos) {
        Boolean response = false;
        for (Tile tile : TileList) {

            if (tile.getPosition().equals(blockPos)) {
                response = true;
                break;
            }
        }
        return response;
    }


    public void updateGrid(Tetrominoes tet) {

        TileList.addAll(tet.getTileList());

        List<Tile> list = new ArrayList<Tile>();
        list = tet.getTileList();

        Collections.sort(list, Collections.reverseOrder());
        for (Tile tile : list) {
            Point position = tile.getPosition();
            int i;
            for (i = position.y-1; i >= 0; i--) {
                if (isOccupied(new Point(position.x, i))) {
                    break;
                }
            }
            if (i+1 != position.y) {
                Point newPosition = new Point(position.x, i+1);
                TileList.get( TileList.indexOf(tile) ).setPosition(newPosition);
            }
        }
    }

    public void display() throws InterruptedException {
        for (int row = 0; row < this.Width; row++)
            for (int col = 0; col < this.Height; col++) {
                StdDraw.setPenColor(StdDraw.WHITE);
                StdDraw.filledSquare(row, col, 0.5);
            }


        StdDraw.setPenColor(StdDraw.GRAY);
        for (double x = -0.5; x < this.Width; x++)
            StdDraw.line(x, -0.5, x, this.Height - 0.5);
        for (double y = -0.5; y < this.Height; y++)
            StdDraw.line(-0.5, y, this.Width - 0.5, y);

        for (Tile tile : TileList){
            tile.display();
        }

        Button btn = new Button(14, 13, 100, 100, "PAUSE");
        btn.draw();


        if (StdDraw.isMousePressed()) {

            if (paused == true){
                setAction(false);
            }
            else{
                setAction(true);
            }
        }
    }

    public static void setAction(boolean x) {
        paused = x;
    }

    public static boolean getAction() {
        return paused;
    }

    public void checkFullLines() {
        int[] tileCounts = new int[this.Height];
        for (int i = 0; i < this.Height; i++) {
            tileCounts[i] = 0;
        }

        List<Integer> lineList = new ArrayList<Integer>();
        for (Tile tile : TileList) {
            Point position = tile.getPosition();
            tileCounts[position.y]++;
            if (tileCounts[position.y] == this.Width) {
                lineList.add(position.y);
            }
        }

        if (lineList.size() > 0) {
            List<Tile> removeList = new ArrayList<Tile>();
            for (Tile tile : TileList) {
                if (lineList.indexOf(tile.getPosition().y) > -1) {
                    removeList.add(tile);
                    this.Score += tile.getNumber();
                }
            }

            TileList.removeAll(removeList);
            Collections.sort(lineList, Collections.reverseOrder());


            for (int line_no : lineList) {
                for (Tile tile : TileList) {

                    if (tile.getPosition().y > line_no) {
                        tile.move(0, -1);
                    }
                }
            }
        }
    }

    public void do2048(Tetrominoes tet) {
        List<Integer> x_list = new ArrayList<Integer>();
        for (Tile tile : tet.getTileList()) {
            Point position = tile.getPosition();
            if (!x_list.contains(position.x))
                x_list.add(position.x);
        }

        for (int X : x_list) {
            checkColumn(X);
        }
    }

    private void checkColumn(int column) {
        List<Tile> list = new ArrayList<Tile>();

        for (Tile tile : TileList) {
            Point position = tile.getPosition();
            if (column == position.x)
                list.add(tile);
        }

        if (list.size() > 1) {
            Collections.sort(list);
            ListIterator<Tile> it = list.listIterator();
            Tile prevTile = it.next();
            Tile currentTile;
            while (it.hasNext()) {
                currentTile = it.next();
                if (currentTile != prevTile) {
                    if (currentTile.getNumber() == prevTile.getNumber()) {
                        currentTile.incNumber(prevTile.getNumber());
                        this.Score += currentTile.getNumber();
                        removeTile(prevTile);
                        checkColumn(column);
                    }
                    prevTile = currentTile;
                }
            }
        }
    }

    private void removeTile(Tile tile) {

        TileList.remove(tile);
        List<Tile> list = new ArrayList<Tile>();
        Point position = tile.getPosition();
        for (int i = position.y+1; i < this.Height; i++) {

            Point newPosition = new Point(position.x, i);
            for (Tile T : TileList) {

                if (T.getPosition().equals(newPosition)) {
                    list.add(T);
                }
            }
        }

        if (list.isEmpty()) return;
        int differences = list.get(0).getPosition().y - position.y;
        for (Tile aTile : list) {
            aTile.move(0, -differences);
        }
    }

    public String getScoreAsString() {
        return Integer.toString(this.Score);
    }

}