package domain;
import java.awt.Color;


/**
 * Represents Grass that implements Entity<br>
 * <b>(color, habitat)</b>
 */
public class Grass implements Entity
{
    private Color color;
    private EcoSafari habitat;
    
    /**
     * Constructs a new Grass.
     * @param habitat is Grass' habitat.
     * @param row is the row position.
     * @param column is the column position.
     */
    public Grass(EcoSafari habitat,int row, int column){
        this.habitat=habitat;
        habitat.set((Entity)this, row, column);  
        color = new Color(0,100,0);
    }

    @Override
    /**
     * Gives the Grass' habitat.
     * @return Grass' habitat.
     */
    public EcoSafari getHabitat(){
        return habitat; 
    }
    
    @Override
    /**
     * Gives Grass' color depending its energy.
     * @return Grass' color.
     */
    public final Color getColor(){
        return color;
    }
    
    @Override
    /**
     * Reacts to tic
     */
    public void tic(){
    }
}