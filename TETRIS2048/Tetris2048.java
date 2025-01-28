import java.util.Random;
import java.util.concurrent.TimeUnit;
import java.awt.event.KeyEvent;
import java.awt.Font;
import java.awt.Point;


public class Tetris2048 {


	public static Tetrominoes createIncomingTetromino(int gridHeight, int gridWidth) {

		char[] tetrominoNames = {'I', 'S', 'Z', 'O', 'T', 'L', 'J'};
		Random random = new Random();
		int randomIndex = random.nextInt(7);
		char randomName = tetrominoNames[randomIndex];

		Tetrominoes tet = new Tetrominoes(randomName, gridHeight, gridWidth);
		return tet;
	}

	public static void drawNextTetromino(Tetrominoes tet) {

		for (Tile tile: tet.getTileList()) {
			Point p = tile.getPosition();
			p.translate(8, -20);

			StdDraw.setPenColor(tile.getBackgroundColor());
			StdDraw.filledSquare(p.x, p.y, 0.5);
			StdDraw.setPenColor(StdDraw.BLACK);
			StdDraw.text(p.x, p.y, Integer.toString(tile.getNumber()));
		}

		StdDraw.setPenColor(StdDraw.BLACK);
		StdDraw.text(14, 4 , "NEXT");
		StdDraw.text(14, 18 , "SCORE");
	}

	public static void main(String[] args) throws InterruptedException {

		int gridWidth = 12, gridHeight = 20;
		StdDraw.setCanvasSize(660, 800);
		StdDraw.setXscale(-1.5, gridWidth + 4.5);
		StdDraw.setYscale(-1.5, gridHeight - 0.5);
		StdDraw.enableDoubleBuffering();
		Grid gameGrid = new Grid(gridHeight, gridWidth);
		Tetrominoes tet = createIncomingTetromino(gridHeight, gridWidth);

		Tetrominoes nextTetromino = createIncomingTetromino(gridHeight, gridWidth);
		boolean createANewTetromino = false;


		label1:
		while (true)  {

			if (StdDraw.isMousePressed()) {
				if (Grid.getAction() == true)
				{Grid.setAction(false);}
				else {
					Grid.setAction(true);
					TimeUnit.SECONDS.sleep(1);
				}
			}

			if (Grid.getAction() == true){
				continue label1;
			}

			boolean success = false;
			if(StdDraw.isKeyPressed(KeyEvent.VK_LEFT)) {
				success = tet.goLeft(gameGrid);
			}
			else if(StdDraw.isKeyPressed(KeyEvent.VK_RIGHT)) {
				success = tet.goRight(gameGrid);
			}
			else if(StdDraw.isKeyPressed(KeyEvent.VK_UP)) {
				success = tet.Rotate(gameGrid);
			}


			if (!success)
				success = tet.goDown(gameGrid);

			if(!success && tet.GameOver) {
				String gameOverMsg = "Game Over!";
				StdDraw.setPenColor(StdDraw.BLACK);
				Font gameOverFont = new Font("Arial", Font.BOLD, 50);
				StdDraw.setFont(gameOverFont);
				StdDraw.clear(StdDraw.GRAY);
				StdDraw.text((gridWidth + 3)/2, gridHeight/2, gameOverMsg);
				StdDraw.show();
				break;
			}

			createANewTetromino = !success;
			if (createANewTetromino) {
				gameGrid.updateGrid(tet);
				gameGrid.do2048(tet);
				gameGrid.checkFullLines();
				tet = nextTetromino;
				nextTetromino = createIncomingTetromino(gridHeight, gridWidth);
			}


			StdDraw.clear(StdDraw.GRAY);
			drawNextTetromino(nextTetromino);
			gameGrid.display();
			tet.display();
			StdDraw.setPenColor(StdDraw.GREEN);
			StdDraw.text(14, 16, gameGrid.getScoreAsString());
			StdDraw.show();
			StdDraw.pause(150);
		}
	}
}