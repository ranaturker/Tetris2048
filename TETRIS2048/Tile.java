import java.awt.Color;
import java.awt.Point;
import java.util.Random;
import java.util.Arrays;

public class Tile implements Comparable<Tile> {
    private final static Integer[] numbers = {2, 4, 8, 16, 32, 64, 128, 256, 512, 1024, 2048};
    private final static Color[] colors = {
            new Color(255, 229, 204),   // 2
            new Color(255, 204, 153),   //4
            new Color(255, 178, 102),   //8
            new Color(255, 153, 51),    //16
            new Color(236, 92, 92),     //32
            new Color(238, 50, 50),     //64
            new Color(226, 226, 49),    //128
            new Color(191, 191, 20),    //256
            new Color(156, 156, 30),    //512
            new Color(124, 124, 43),    //1024
            new Color(144, 144, 66)};   //2048
    private int number;
    private Color backgroundColor;
    private Color foregroundColor = Color.BLACK;
    private Point position;
    Tile(Point pos){
        Random rand = new Random();
        int i = rand.nextInt(2);
        this.number = numbers[i];
        this.backgroundColor = colors[i];
        this.position = (Point) pos.clone();
    }
    public Point getPosition(){
        return (Point) position.clone();
    }
    public void setPosition(Point position){
        this.position = position;
    }
    public void move(int x, int y){
        position.translate(x, y);
    }
    public Integer getNumber() {
        return this.number;
    }
    public void incNumber(int value) {
        this.number += value;
        if (this.number > 2048)
            this.number = 2048;

        int i = Arrays.asList(numbers).indexOf(this.number);
        this.backgroundColor = colors[i];
    }
    public Color getBackgroundColor() {
        return this.backgroundColor;
    }
    public void display() {
        StdDraw.setPenColor(backgroundColor);
        StdDraw.filledSquare(position.x, position.y, 0.5);
        StdDraw.setPenColor(foregroundColor);
        StdDraw.text(position.x, position.y, Integer.toString(number));
    }

    public int compareTo(Tile other)  {
        return other.getPosition().y - this.getPosition().y;
    }
}