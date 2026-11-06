package domain;
import java.awt.Color;

/**
 * It represents what all entities should can do.<br>
 * <b>(SQUARE, ROUND)</b>
 */
public interface Entity{
    public static final int SQUARE = 2;
    public static final int ROUND = 1;
  
    /**
    * All Entity must react to "tic" moment.
    */
    public void tic();
  
    /**
    * All Entity should react to "tac" moment, but Entity can do nothing in that moment.
    */
    public default void tac(){
    }

    /**
    * All Entity has a shape, by default it is a Square.
    * @return the shape number.
    */
    public default int shape(){
        return SQUARE;
    }
  
    /**
    * All Entity must have a color to return
    * @return the colors.
    */
    public abstract Color getColor();
   
    /**
    * An Entity is not an Organism.
    * @return if it is an Organism.
    */
    public default boolean isOrganism(){
        return false;
    }

    /**
    * An Entity must know its habitat.
    * @return its habitat.
    */
    public abstract EcoSafari getHabitat();

    /**
    * An Entity can disappear.
    * @return if entity disappear correctly.
    */
    public default boolean disappear(){
        boolean ok=false;
        int [] position=this.getHabitat().find(this);
        if (position!=null){
            new Earth(getHabitat(), position[0], position[1]);
            ok=true;
        }
        return ok;
    }
    
    /**
     * An Entity can move, by default it moves diagonally
     * @param deltaRows are rows quantity it will move
     * @param deltaColumns are columns quantity it will move
     * @return if it could move.
     */
    public default  boolean move(int deltaRows, int deltaColumns){
        int [] position=getHabitat().find(this);
        EcoSafari habitat=getHabitat();
        boolean ok=false;
        if (position!=null){
            int r = position[0];
            int c = position[1];
            if (habitat.isInside(r+deltaRows,c+deltaColumns)){
                habitat.set(null,r,c);
                new Earth(getHabitat(), r, c);
                habitat.set(this,r+deltaRows,c+deltaColumns);
                ok=true;
            }
        }
        return ok;
    }
    
}
