package domain;
import java.awt.Color;


/**
 * Represents an Ovni that implements an Entity
 * <b>(habitat, hasActed, color)</b>
 */
public class Ovni implements Entity
{
    private final EcoSafari habitat;
    private boolean hasActed;
    /**
     * Constructor for objects of class Ovni
     */
    public Ovni(EcoSafari habitat, int row, int column){
        this.habitat = habitat;
        habitat.set((Entity)this, row, column);
        hasActed = false;
    }
    
    @Override
    /**
     * Gives the Ovni's habitat.
     * @return Ovni's habitat.
     */
    public EcoSafari getHabitat(){
        return habitat;
    }

    @Override
    /**
     * Gives Ovni's color.
     * @return Ovni's color
     */
    public Color getColor(){
        return Color.GRAY;
    }
    
    @Override
    /**
     *  It moves freely (randomly) throughout the simulator if it hasn't acted and if in the square when he will be stay is null
     */
    public void tic(){
        int size = habitat.getSize();
        int f= habitat.selectRandInt(0, size - 1);
        int c= habitat.selectRandInt(0, size - 1);
        if ((!hasActed)){
            boolean temp = false;
            killEntities();
            while(temp==false){
                if(move(f, c)){
                    temp=true;
                }else{
                    f= habitat.selectRandInt(0, size - 1);
                    c= habitat.selectRandInt(0, size - 1);
                }
            }
        }
        hasActed=true;
    }
    
    @Override
    /**
     * calls the killEntities method and change hasActed to false
     */
    public void tac(){
        killEntities();
        hasActed=false;
    }
    
    @Override
    /**
     * An Ovni can move
     * @return if it was moved.
     */
    public boolean move(int newRow, int newColumn){
        int [] position=getHabitat().find(this);
        EcoSafari habitat=getHabitat();
        boolean ok=false;
        if (position!=null){
            int r = position[0];
            int c = position[1];
            if (habitat.get(newRow,newColumn)instanceof Earth && !habitat.isInsideGrasslandZone(newRow,newColumn) && habitat.isInside(newRow, newColumn)){
                new Earth(getHabitat(), r, c);
                habitat.set(this,newRow,newColumn);
                ok=true;
            }
        }
        return ok;
    }
    
    @Override
    /**
     * Gives how Ovni looks like.
     * @return the number that represents the shape.
     */
    public final int shape(){
        return Entity.ROUND;
    }

    
    /**
     * Kills any entity that is within a one square distance of him
     */
    private void killEntities(){
        int[] OvniPos = habitat.find(this);
        if(OvniPos != null){
            for(int i = -1 + OvniPos[0]; i <=1+ OvniPos[0]; i++){
                for(int j= -1+ OvniPos[1]; j <=1+ OvniPos[1]; j++){
                    if((i != 0 || j != 0) && habitat.isInside(i,j)){
                        Entity e = habitat.get(i, j);
                        if(!(e instanceof Twister) && !(e instanceof Ovni)){
                            e.disappear();
                        }
                    }
                }
            }
        } 
    }
}