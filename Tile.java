public class Tile
{
    private int number;
    private String color;
    public static final String def = "\u001B[0m";
    public static final String red = "\u001B[31m";
    public static final String yellow = "\u001B[33m";
    public static final String blue = "\u001B[34m";

    
    public Tile(int number, String color) {
        this.number = number;
        this.color = color;
    }
    
    public int getNumber() {
        return number;
    }
    
    public String getColor() {
        return color;
    }
    
    public boolean equals(Tile t) {
        //
    }
    
    public String toString() {
        //
    }
}
