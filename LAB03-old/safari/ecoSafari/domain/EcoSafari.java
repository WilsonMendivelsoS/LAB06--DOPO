package domain;

/**
 * Represents an EcoSafari<br>
 * <b>(SIZE, cells)</b>
 */
public class EcoSafari{
 
    private static final int SIZE=25;
    private Entity[][] cells;
    private boolean[][] affectedEntities;
    
    /**
     * Constructs a new EcoSafari
     */
    public EcoSafari() {
        cells=new Entity[SIZE][SIZE];
        affectedEntities = new boolean[SIZE][SIZE];
        placeEarth();
        someEntities();
        randomEntities();
    }
    
    /**
     * Puts all the EcoSafari with earth.
     */
    public void placeEarth(){
        for(int i = 0; i < SIZE; i++){
            for(int j = 0; j < SIZE; j++){
                new Earth(this, i,j);
            }
        }
    }
    
    /**
     * Populates the EcoSafari with some entities
     */
    public void someEntities(){
        //We create dumbo and babar:
        //Dumbo will be in position (5,5), but in a matrix it represents [4][4]
        Elephant dumbo = new Elephant(this, 4,4);
        
        //Babar will be in position (6,6) but in a matrix it represents [5][5]
        Elephant babar = new Elephant(this, 6,6);
           
        //We create mopane and acacia.
        Bush mopane = new Bush(this, 10,12,false);
        Bush acacia = new Bush(this, 8,3,false);
        
        
        //We put two twisters Thor y Tempest
        Twister thor = new Twister(this, 3,4);
        thor.tac();
        Twister tempest = new Twister(this, 15,0);
        tempest.tac();
        
        //We create Garzon and Mendivelso in positions (1, 12) and (7, 16)
        AngryElephant Garzon = new AngryElephant(this, 1, 12);
        AngryElephant Mendivelso = new AngryElephant(this, 7, 16);
        
        
        //We put a zebra
        Zebra zebra = new Zebra(this,24,24);
        
        //We put a lyon
        Lyon lyon = new Lyon(this, 22, 18);
        
        //We create david and wilson
        Ovni david= new Ovni(this, 20, 3);
        Ovni wilson = new Ovni(this,9,8);
        
    }
    
    /**
     * Returns the size of the EcoSafari 
     * @return 
     */
    public int  getSize(){
        return SIZE;
    }

    /**
     * Determines whether a position is inside the EcoSafari
     * @param r the row
     * @param c the column
     * @return if is inside the zone
     */
    public boolean isInside(int r, int c){
        return ((0<=r) && (r<SIZE) && (0<=c) && (c<SIZE));
    }
    
    /**
     * Returns the entity located at a specified position
     * @param r the row
     * @param c the column
     * @return 
     */
    public Entity get(int r,int c){
        return (isInside(r,c)? cells[r][c]: null);
    }

    /**
     * Places an entity at a specified position there must not be a twister there.
     * @param r the row
     * @param c the column
     */
    public void set(Entity e, int r, int c){
        if (isInside(r,c)){ 
            if(e == null){
                cells[r][c]=e;
            }
            else if(!(get(r,c) instanceof Twister)){
                cells[r][c]=e;
            }
        }
    }

    
    /**
     * Finds the position of a specified entity
     * @param e the entity
     * @return an array {row, column} if the entity is found. null otherwise
     */
    public int[]  find(Entity e){
       int[] position=null;
       for (int r=0 ; r<SIZE && position== null; r++){
           for (int c=0 ; c<SIZE && position==null ;c++){
               if (cells[r][c]==e){
                   position=new int [] {r,c};
               }
           }
       }
       return position;
    }
    
 
    /**
     * Advances the simulation by one time step
     */
    //First, all entities execute their tic() action
    //Then, all entities execute their tac() actions
    public void ticTac(){
        affectedEntities = new boolean[SIZE][SIZE];
        for(int i=0; i<SIZE; i++){
            for(int j=0; j<SIZE; j++){
                if(cells[i][j] != null){
                    cells[i][j].tic();
                }
            }
        }
        for(int i=0; i<SIZE; i++){
            for(int j=0; j<SIZE; j++){
                if(cells[i][j] != null){
                    cells[i][j].tac();
                }
            }
        }
    }
    
    /**
     * Gets the affected entities
     * @return the affected entities
     */
    public boolean[][] getAffectedEntities(){
        return affectedEntities;
    }
    
    /**
     * Returns if an entity is affected by the twister
     * @param e is the entity to evaluate if it is affected
     * @return if an entity is affected
     */
    
    public boolean isAffected(Entity e){
        int[] pos = find(e);
        return affectedEntities[pos[0]][pos[1]];
    }
    
    /**
     * Determines whether a position is inside the grasslandZone
     * @param r the row
     * @param c the column
     * @return if is inside the zone
     */
    public boolean isInsideGrasslandZone(int r, int c){
        return ((12<=r) && (r<SIZE) && (4<=c) && (c<SIZE));
    }
    
    /**
     * Looks how much of Earth there are in the spaces around a specific position
     * @param r is the row 
     * @param c is the column
     * @return the number of times that earth appears in the Moree neighborhood
     */
    public int countEarthInMooreNeighborhood(int r, int c){
        int cant = 0;
        for(int i =-1; i<=1; i++){
            for(int j= -1; j<=1; j++){
                if((i != 0 || j != 0) && isInside(i + r, j + c)){
                    if(get(i+r,j+c) instanceof Earth){
                        cant++;
                    }
                }
            }
        }
        return cant;
    }
    
    /**
     * Looks how much of Grass there are in the spaces around a specific position
     * @param r is the row 
     * @param c is the column
     * @return the number of times that grass appears in the Moree neighborhood
     */
    public int countGrassInMooreNeighborhood(int r, int c){
        int cant = 0;
        for(int i =-1; i<=1; i++){
            for(int j= -1; j<=1; j++){
                if(i!=0 || j!=0 && isInside(i+r,j+c)){
                    if(get(i+r,j+c) instanceof Grass){
                        cant++;
                    }
                }
            }
        }
        return cant;
    }
    
    /**
     * Looks how much of Zebra's there are in the spaces around a specific position
     * @param r is the row 
     * @param c is the column
     * @return the number of times that zebra's appears in the Moree neighborhood
     */
    public int countZebraInMooreNeighborhood(int r, int c){
        int cant = 0;
        for(int i =-1; i<=1; i++){
            for(int j= -1; j<=1; j++){
                if(i!=0 || j!=0 && isInside(i+r,j+c)){
                    if(get(i+r,j+c) instanceof Zebra){
                        cant++;
                    }
                }
            }
        }
        return cant;
    }
    
    /**
     * Gives a random integer between two limits
     * @param infLimit the inferior limit  
     * @param supLimit the superior limit   
     * @return a random integer between the two limits  
     */
    public static int selectRandInt(int infLimit, int supLimit){
        int numeroRand = infLimit + (int)(Math.random()*(supLimit+1));
        return numeroRand;
    }
    
    /**
     * Puts random entities in ecoSafari.
     */
    private void randomEntities(){
        for(int i =0; i < SIZE; i++){
            for(int j =0; j < SIZE; j++){
                if(get(i,j) instanceof Earth){
                    int randNum = selectRandInt(0,100);
                    if(!isInsideGrasslandZone(i,j)){
                        if(1 > randNum){
                            new Ovni(this, i, j);
                        }
                        else if(15 > randNum){
                            new Elephant(this, i,j);
                        }
                        else if(20 > randNum){
                            new AngryElephant(this, i, j);
                        }
                        else if(30 > randNum){
                            new Bush(this,i,j, false);
                        }
                    }
                    else{
                        if(7> randNum){
                            new Zebra(this,i,j);
                        }
                        else if(11 > randNum){
                            new Lyon(this, i, j);
                        }
                        else if(12> randNum){
                            new Grass(this,i,j);
                        }
                    }
                
                }
            }
        }
    }
}
