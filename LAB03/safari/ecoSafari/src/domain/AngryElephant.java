package domain;
import java.awt.Color;


/**
 * Creates a new AngryElephant 
 */
public class AngryElephant extends Elephant{
    /**
     * Constructs a new AngryElephant.
     * @param habitat is Angry Elephant's habitat.
     * @param row is the row position.
     * @param column is the column position.
     */
    public AngryElephant(EcoSafari habitat, int row, int column){
       super(habitat, row, column);
    }
    
    /**
     * It moves the Angry Elephant if it hasn't acted and it loses energy. If energy is 0 it disappears.
     */
    @Override
    public void tic(){
        if ((!this.getHasActed())){
            killElephant();
            if(move(2, 2)) {
                this.changeEnergy(-15);
            }
        }
        if (this.getEnergy()==0){
            this.disappear();
        }
        this.setHasActed(true);
    }
    
        
    /**
     * calls the killElephant method and change hasActed to false
     */
    @Override
    public void tac(){
        killElephant();
        this.setHasActed(false);
    }
    
    
    /**
     * Angry Elephant kills a normal Elephant, provided that the elephant is one square away from the Angry Elephant
     */
    public void killElephant(){
        EcoSafari habitat = this.getHabitat();
        int[] angryElephantPos = habitat.find(this);
        if(angryElephantPos != null){
            for(int i = -1 + angryElephantPos[0]; i <=1+ angryElephantPos[0]; i++){
                for(int j= -1+ angryElephantPos[1]; j <=1+ angryElephantPos[1]; j++){
                    if((i != 0 || j != 0) && habitat.isInside(i,j)){
                        if(habitat.get(i, j) instanceof Elephant && !(habitat.get(i,j) instanceof AngryElephant)){
                            Elephant e = (Elephant)habitat.get(i,j);
                            this.changeEnergy(e.getEnergy());
                            e.disappear();
                    
                        }
                    }
                }
            }
        }
    }
    
    @Override
    /**
     * Gives AngryElephant's color depending its energy.
     * @return AngryElephant's color.
     */
    public final Color getColor(){
        return(this.getEnergy()>=40? Color.RED: Color.ORANGE);
    }
    
    
    @Override
    /**
     * An Entity can move, by default it moves diagonally
     * @return if it was moved.
     */
    public boolean move(int deltaRows, int deltaColumns){
        int [] position=getHabitat().find(this);
        EcoSafari habitat=getHabitat();
        boolean ok=false;
        if (position!=null){
            int r = position[0];
            int c = position[1];
            if (habitat.isInside(r+deltaRows,c+deltaColumns) && habitat.get(r+deltaRows,c+deltaColumns) instanceof Earth && !habitat.isInsideGrasslandZone(r+deltaRows,c+deltaColumns) && this.getEnergy()>=40){
                new Earth(getHabitat(), r, c);
                habitat.set(this,r+deltaRows,c+deltaColumns);
                ok=true;
            }else if(habitat.isInside(r+1, c+1) && habitat.get(r+1,c+1) instanceof Earth && !habitat.isInsideGrasslandZone(r+1,c+1)){
                new Earth(getHabitat(), r, c);
                habitat.set(this,r+1,c+1);
                killElephant();
                ok=true;
            }
        }
        return ok;
    }
}