package domain;
import java.awt.Color;

/**
 * Represents an Elephant that is an organism and implements Entity<br>
 * <b>(habitat, hasActed)</b>
 */
public class Elephant extends Organism implements Entity{
    private final EcoSafari habitat;
    private boolean hasActed;
    /**
     * Constructs a new Elephant.
     * @param habitat is Elephant's habitat.
     * @param row is the row position.
     * @param column is the column position.
     */
    public Elephant(EcoSafari habitat,int row, int column){
        this.habitat=habitat;
        habitat.set((Entity)this, row, column);  
        hasActed=false;
    }
    
    @Override
    /**
     * Gives the Elephant's habitat.
     * @return Elephant's habitat.
     */
    public EcoSafari getHabitat(){
        return habitat;
    }
    
    @Override
    /**
     * Gives Elephant's color depending its energy.
     * @return Elephant's color.
     */
    public Color getColor(){
        return(getEnergy()>=80? Color.DARK_GRAY: Color.LIGHT_GRAY);
    }
    
    @Override
    /**
     * Gives how Elephants looks like.
     * @return the number that represents the shape.
     */
    public final int shape(){
        return Entity.ROUND;
    }
    
    @Override
    /**
     * It moves the Elephant if it hasn't acted and it loses energy. If energy is 0 it disappears.
     */
    public void tic(){
        if ((! hasActed) && (move(1, 1))) {
            changeEnergy(-10);
            if (getEnergy()==0){
                disappear();
            }
        }
        hasActed=true;
    }
    
    @Override
    /**
     * It change hasActed to false.
     */
    public void tac(){
        hasActed = false;
    }
    
    @Override
    /**
     * Moves the elephant to a specific position, but it must be outside the grassland zone.
     * @param deltaRows are rows quantity it will move
     * @param deltaColumns are columns quantity it will move
     * @return if it could move.
     */
    public boolean move(int deltaRows, int deltaColumns){
        int [] position=getHabitat().find(this);
        EcoSafari habitat=getHabitat();
        boolean ok=false;
        if (position!=null){
            int r = position[0];
            int c = position[1];
            if (habitat.isInside(r+deltaRows,c+deltaColumns) && !habitat.isInsideGrasslandZone(r+deltaRows,c+deltaColumns) && habitat.get(r+deltaRows,c+deltaColumns)instanceof Earth){
                habitat.set(new Earth(getHabitat(), r, c),r,c);
                habitat.set(this,r+deltaRows,c+deltaColumns);
                ok=true;
            }
        }
        return ok;
    }
    
        /**
     * return if has acted
     * @return a boolean hasActed
     */
    public boolean getHasActed(){
        return hasActed;
    }
    
    /**
     * set true or false for hasActed
     * @param hasActed for change the attribute hasActed
     */
    public void setHasActed(boolean hasActed){
        this.hasActed = hasActed;
    }
    
    
}
