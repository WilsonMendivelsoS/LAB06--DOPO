package domain;

import java.awt.Color;

/**
 * Represents a Twister implements Entity<br>
 * <b>(habitat, tics, color)</b>
 */
public class Twister implements Entity{
    
    private Color color;
    private EcoSafari habitat;
    private boolean hasActed;
    
    /**
     * Constructs a new Twister.
     * @param habitat is Twister's habitat.
     * @param row is the row position.
     * @param column is the column position.
     */
    public Twister(EcoSafari habitat,int row, int column){
        this.habitat=habitat;
        habitat.set((Entity)this, row, column);  
        color = Color.black;
    }
    
    @Override
    /**
     * Gives the Twister's habitat.
     * @return Twister's habitat.
     */
    public EcoSafari getHabitat(){
        return habitat;
    }
    
    @Override
    /**
     * Gives Twister's color depending its energy.
     * @return Twister's color.
     */
    public final Color getColor(){
        return color;
    }
    
    @Override
    /**
     * Creates a new bush near to it, if a place is empty, first looks the North, then South, then 
     * East and finally West. This just happens if there are two tics
     */
    public void tic(){
        if(!hasActed){    
            if(!move(-1,1)){
                int[] posTwister = habitat.find(this);
                if(!move(posTwister[1],-posTwister[1])){
                    move(-posTwister[0]+posTwister[1],posTwister[0]-posTwister[1]);
                }
            }
        }
        hasActed = true;
    }
    
    @Override
    /**
     * It change hasActed to false.
     */
    public void tac(){
        setAffectedZone();
        hasActed=false;
    }    
    
    @Override
    /**
     * Gives how Twister looks like.
     * @return the number that represents the shape.
     */
    public final int shape(){
        return Entity.ROUND;
    }
    
    /**
     * Changes the affectedZone for entities
     */
    public void setAffectedZone(){
        boolean[][] affectedEntities = habitat.getAffectedEntities();
        int[] posTwister = habitat.find(this);
        
        for(int i = -1+posTwister[0]; i<=1+posTwister[0];i++){
            for(int j = -1+posTwister[1]; j<=1+posTwister[1]; j++){
                if(habitat.isInside(i,j) && !(habitat.get(i,j) instanceof Twister)){
                    affectedEntities[i][j] = true;
                }
            }
        }
    }
}