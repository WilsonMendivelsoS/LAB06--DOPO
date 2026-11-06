package domain;

import java.awt.Color;

/**
 * Represents a Bush that is an organism and implements Entity<br>
 * <b>(habitat, tics, recentlyCreatedByBush)</b>
 */
public class Bush extends Organism implements Entity{
    private int tics;
    private boolean recentlyCreatedByBush;
    private EcoSafari habitat;
    
    /**
     * Constructs a new Bush.
     * @param habitat is Bushes' habitat.
     * @param row is the row position.
     * @param column is the column position.
     */
    public Bush(EcoSafari habitat,int row, int column, boolean recentlyCreatedByBush){
        this.habitat=habitat;
        habitat.set((Entity)this, row, column);  
        tics = 0;
        this.recentlyCreatedByBush = recentlyCreatedByBush;
    }
    
    @Override
    /**
     * Gives the Bushes' habitat.
     * @return Bushes' habitat.
     */
    public EcoSafari getHabitat(){
        return habitat;
    }
    
    @Override
    /**
     * Gives Bushes' color depending its energy.
     * @return Bushes' color.
     */
    public final Color getColor(){
        if(tics<4){
            return Color.green;
        }
        else{
            return Color.yellow;
        }
    }
    
    @Override
    /**
     * Creates a new bush near to it, if a place is empty, first looks the North, then South, then 
     * East and finally West. This just happens if there are two tics. But, it must be outside the grasslandZone.
     * If there are four tics it changes it colors.
     */
    public void tic(){
        if(!recentlyCreatedByBush){
            tics++;
        }
        
        if(tics ==2){
            int[] position = habitat.find(this);
            if(habitat.get(position[0]-1, position[1]) instanceof Earth && habitat.isInside(position[0]-1, position[1]) && !habitat.isInsideGrasslandZone(position[0]-1, position[1])){
                new Bush(habitat, position[0]-1, position[1],true);    
            }
            else if(habitat.get(position[0]+1, position[1]) instanceof Earth && habitat.isInside(position[0]+1, position[1]) && !habitat.isInsideGrasslandZone(position[0]+1, position[1])){
                new Bush(habitat, position[0]+1, position[1],true);    
            }
            else if(habitat.get(position[0], position[1]+1) instanceof Earth && habitat.isInside(position[0], position[1]+1) && !habitat.isInsideGrasslandZone(position[0], position[1]+1)){
                new Bush(habitat, position[0], position[1]+1, true);    
            }
            else if(habitat.get(position[0], position[1]-1) instanceof Earth && habitat.isInside(position[0], position[1]-1) && !habitat.isInsideGrasslandZone(position[0], position[1]-1)){
                new Bush(habitat, position[0], position[1]-1,true);    
            }
        }
    }
    
    @Override
    /**
     * Looks if there's an elephant near, if it happens then the bush disappears.
     */
    public void tac(){     
        if(recentlyCreatedByBush){
            recentlyCreatedByBush = false;
        }
        
        int[] bushPos = habitat.find(this);
        for(int i = -1; i <=1; i++){
            for(int j= -1; j <=1; j++){
                if(i != 0 || j != 0){
                    if(habitat.get(bushPos[0]+i, bushPos[1]+j) instanceof Elephant){
                        disappear();
                        break;
                    }
                }
            }
        }
    }
    
    
}