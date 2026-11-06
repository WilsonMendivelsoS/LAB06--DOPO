package domain;
import java.awt.Color;
import java.lang.Math;

/**
 * Represents Earth that implements Entity<br>
 * <b>(color, habitat)</b>
 */
public class Earth implements Entity
{
    private Color color;
    private EcoSafari habitat;
    
    /**
     * Constructs a new Earth.
     * @param habitat is Earth's habitat.
     * @param row is the row position.
     * @param column is the column position.
     */
    public Earth(EcoSafari habitat,int row, int column){
        this.habitat=habitat;
        habitat.set((Entity)this, row, column);  
        color = new Color(181, 138, 99);
    }

    
    @Override
    /**
     * Gives the Earth's habitat.
     * @return Earth's habitat.
     */
    public EcoSafari getHabitat(){
        return habitat; 
    }
    
    @Override
    /**
     * Gives Earth's color depending its energy.
     * @return Earth's color.
     */
    public final Color getColor(){
        return color;
    }
    
    @Override
    /**
     * If earth is inside the grasslandZone there is a 10% of probability it will become grass.
     */
    public void tic(){
        int [] position=getHabitat().find(this);
        if(0.1>= Math.random() && habitat.isInsideGrasslandZone(position[0],position[1])){
            new Grass(getHabitat(), position[0], position[1]);
        }
    }
}