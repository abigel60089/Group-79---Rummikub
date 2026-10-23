/**
 * Class that creates a new Tile object with a number and color.
 */
public class Tile
{
    private int number;
    private String color;
    /**
     * default color (black) code
     */
    public static final String def = "\u001B[0m";

    /**
     * red color code
     */
    public static final String red = "\u001B[31m";

    /**
     * yellow color code
     */
    public static final String yellow = "\u001B[33m";

    /**
     * blue color code
     */
    public static final String blue = "\u001B[34m";

    // ----------------------------------------------------------
    /**
     * Create a new Tile object.
     * 
     * @param number
     *            number value of tile (1-14).
     * @param color
     *            color of tile (black, yellow, red, or blue).
     */
    public Tile(int number, String color)
    {
        this.number = number;
        this.color = color;
    }


    // ----------------------------------------------------------
    /**
     * Getter for tile's number.
     * 
     * @return number of tile
     */
    public int getNumber()
    {
        return number;
    }


    // ----------------------------------------------------------
    /**
     * Getter for tile's color.
     * 
     * @return color of tile
     */
    public String getColor()
    {
        return color;
    }


    /**
     * Two tiles are equal is they have the same number and color.
     * 
     * @param other
     *            the other object to compare to this
     * @return true if tiles are equal and false otherwise
     */
    public boolean equals(Object other)
    {
        if (other == this)
        {
            return true;
        }

        if (other == null)
        {
            return false;
        }

        if (other.getClass() == this.getClass())
        {
            return number == ((Tile)other).getNumber()
                && color.equals(((Tile)other).color);
        }

        return false;
    }


    /**
     * Returns tile as a string with the number and color.
     * 
     * @returns string of tile's number and color
     */
    public String toString()
    {
        if (number == 14)
        {
            return def + "joker";
        }
        switch (color)
        {
            case "black":
                return "[" + number + "]";
            case "red":
                return red + "[" + number + "]" + def;
            case "yellow":
                return yellow + "[" + number + "]" + def;
            case "blue":
                return blue + "[" + number + "]" + def;
            default:
                return "(invalid color) [" + number + "]";
        }
    }
}
