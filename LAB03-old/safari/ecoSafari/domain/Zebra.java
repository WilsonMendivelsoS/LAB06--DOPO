package domain;
import java.awt.Color;
import java.util.ArrayList;

/**
 * Represents a Zebra that is an organism and implements Entity<br>
 * <b>(habitat, hasActed, color)</b>
 */
public class Zebra extends Organism implements Entity{
    private final EcoSafari habitat;
    private boolean hasActed;
    private Color color;
    
    /**
     * Constructs a new Zebra.
     * @param habitat is Zebra's habitat.
     * @param row is the row position.
     * @param column is the column position.
     */
    public Zebra(EcoSafari habitat,int row, int column){
        this.habitat=habitat;
        habitat.set((Entity)this, row, column);  
        hasActed=false;
        color = Color.BLACK;
    }
    
    @Override
    /**
     * Gives the Zebra's habitat.
     * @return Zebra's habitat.
     */
    public EcoSafari getHabitat(){
        return habitat;
    }

    @Override
    /**
     * Gives Zebra's color depending its energy.
     * @return Zebra's color.
     */
    public final Color getColor(){
        return(getEnergy()>=40? color: Color.GRAY);
    }
    
    @Override
    /**
     * It moves the Zebra if it hasn't acted and it loses energy. If energy is 0 it disappears.
     */
    public void tic(){
        if(!hasActed){
            int[] nextMove = selectNextMove();
            if(nextMove[0] != 0 || nextMove[1] != 0){
                if(move(nextMove[0], nextMove[1])){
                    changeEnergy(-10);
                    if (getEnergy()==0){
                        disappear();
                    } 
                }
            }
        }
    
        hasActed=true;
    }
    
    /**
     * Selects the next Zebra's move, if it can't move it will be in the same position
     * @return the x and y position of the selected next move.
     */
    private int[] selectNextMove(){
        int[] nextMove = new int[]{0,0};
        int cont =0;
        ArrayList<int[]> opciones = new ArrayList<>();
        for(int i =-2; i <=2; i=i+2){
            for(int j = -2; j<= 2; j= j+2){
                if(i!=0 || j!=0){
                    int[] añadir = new int[]{i,j};
                    opciones.add(añadir);
                }
            }
        }
        int start = (int)(Math.random()*8);
        while(cont < 8){
            int[] actualOpc = opciones.get((cont+start)%(8));
            int r= actualOpc[0] + habitat.find(this)[0];
            int c = actualOpc[1] + habitat.find(this)[1];
            if(habitat.isInside(r,c) && habitat.isInsideGrasslandZone(r,c) && habitat.get(r,c) instanceof Earth){
                return actualOpc;
            }
            else{
                cont++;
            }
        }
        
        return nextMove;
    }
    
    
    /**
     * It change hasActed to false and eats grass.
     */
    public void tac(){
        if(this.getEnergy() < 50){
            if(eatGrass()){
                changeEnergy((float) 0.25);
            }
            
        }else if(procreate()){
            
        }else if(eatGrass()){
            changeEnergy((float) 0.25);
        }
        
        hasActed=false;
    }    
    
    /**
     * Zebra eats one grass if it is near to it.
     * @return if Zebra could eat grass
     */
    private boolean eatGrass(){
        int[]pos = habitat.find(this);
        if(habitat.countGrassInMooreNeighborhood(pos[0], pos[1]) > 0){
            for(int i = -1; i <=1; i++){
                for(int j= -1; j <=1; j++){
                    if(i != 0 || j != 0){
                        if(habitat.get(pos[0]+i, pos[1]+j) instanceof Grass){
                            habitat.get(pos[0]+i, pos[1]+j).disappear();
                            return true;
                        }
                    }
                }
            }
        }
        return false;
    }
    
    /**
     * Zebra reproduce if they are within a distance of two blocks of each other and there is earth between them.
     * @return if animals can reproduce 
     */
    public boolean procreate(){
        int[] pos = habitat.find(this);
        int f = pos[0];
        int c = pos[1];
        for(int i = -1; i<=1; i++){
            for(int j = -1; j<=1; j++){
                if(habitat.get(2*i+f , 2*j+c) instanceof Zebra && habitat.get(f+i, c+j) instanceof Earth  && !(i==0 && j==0)){
                    new Zebra(habitat, f+i, c+j);
                    return true;
                }
            }
        }
        return false;
    }
    
    @Override
    /**
     * Moves the Lyon to a specific position, but it must be inside the grassland zone.
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
            if (habitat.isInside(r+deltaRows,c+deltaColumns) && habitat.isInsideGrasslandZone(r+deltaRows,c+deltaColumns) && habitat.get(r+deltaRows,c+deltaColumns)instanceof Earth){
                habitat.set(new Earth(getHabitat(), r, c),r,c);
                habitat.set(this,r+deltaRows,c+deltaColumns);
                ok=true;
            }
        }
        return ok;
    }

}
