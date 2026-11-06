package test;
import domain.*;

import org.junit.Test;
import org.junit.After;
import org.junit.Before;
import static org.junit.Assert.*;
import java.awt.Color;

/**
 * The test class Test.
 *
 * @author  (your name)
 * @version (a version number or a date)
 */
public class test
{  
    private EcoSafari es;
    /**
     * Sets up the test fixture.
     *
     * Called before every test case method.
     */
    @Before
    public void setUp()
    {
        es = new EcoSafari();
        for(int i=0; i<25; i++){
            for(int j=0; j<25; j++){
                new Earth(es, i,j);
            }
        }
    }
    
    /**
     * Dumbo and babar should change their position and move diagonally.
     */
    @Test
    public void shouldDumboAndBabarMoveDiagonally(){
        
        Elephant dumbo = new Elephant(es, 4,4);
        
        Elephant babar = new Elephant(es, 6,6);
           
        es.ticTac();
        assertTrue(es.get(4,4) instanceof Earth && es.get(6,6) instanceof Earth);
        assertTrue(!(es.get(5,5) instanceof Earth) && !(es.get(7,7) instanceof Earth));
    }

    /**
     * Dumbo and babar should have less energy after they move.
     */
    @Test
    public void shouldDumboAndBabarHasLessEnergyAfterMove(){
        
        Elephant dumbo = new Elephant(es, 4,4);
        
        Elephant babar = new Elephant(es, 6,6);
           
        int startDumboEnergy = dumbo.getEnergy();
        
        int startBabarEnergy = babar.getEnergy();
        
        es.ticTac();
        
        assertTrue(startDumboEnergy > dumbo.getEnergy());
        assertTrue(startBabarEnergy > babar.getEnergy());
    }
    
    
    /**
     * Should create correct new bushes at north and south
     */
    @Test
    public void shouldCreateCorrectNewBushesAtNorthAndSouth(){
        Bush bush1 = new Bush(es,0,0,false); //Should create a new bush at south
        Bush bush2= new Bush(es,1,1,false); //Should create a new bush at north
                
        es.ticTac();
        es.ticTac();
        
        assertTrue(es.get(0,1) != null);
        assertTrue(es.get(1,0) != null);
    }

    /**
     * Should create correct new bushes at east and west
     */
    @Test
    public void shouldCreateCorrectNewBushesAtEastAndWest(){
        Bush bush1 = new Bush(es,0,0,false); 
        Bush bush2= new Bush(es,0,24,false);
        
        es.ticTac();
        
        Bush bush3 = new Bush(es,1,0,false); //Now bush1 should create a new bush at east.
        Bush bush4 = new Bush(es,0,23,false); //Now bus2 should create a new bush at west.
        es.ticTac();
        
        assertTrue((es.get(0,1) instanceof Bush));
        assertTrue((es.get(1,24) instanceof Bush));
    }
    
    /**
     * Bush should change its color after four tics.
     */
    @Test
    public void shouldBushChangeColorAfterFourTics(){
        Bush bush1 = new Bush(es,0,0,false); 
        for(int i = 0; i < 4; i++){
            es.ticTac();
        }
        
        assertEquals(bush1.getColor(), Color.yellow);
    }
    
    /**
     * Should bush be eaten if there's an elephant near
     */
    @Test
    public void shouldBushBeEatenIfThereIsAnElephantNear(){
        Bush bush1 = new Bush(es,2,2,false);
        Elephant eater = new Elephant(es,0,0);
        es.ticTac();
        assertTrue(es.get(2,2) instanceof Earth);
    }
    
    /**
     * Twister should move diagonally in direction northeast
     */
    @Test
    public void shouldTwisterMoveDiagonally(){
        Twister twister = new Twister(es, 7,7);
        es.ticTac();
        
        assertTrue(es.get(7,7) instanceof Earth);
        assertEquals(twister, es.get(6,8));
    }
    
    /**
     * Twister should kill entities.
     */
    @Test
    public void shoudlTwisterKillEntities(){
        Twister twister = new Twister(es, 7,7);
        Bush bush1 = new Bush(es,6,8,false);
        es.ticTac();
        es.ticTac();
        
        assertTrue(es.get(6,8) instanceof Earth);
    }
    
    /**
     * Twister teleports correctly to the other side of the map if it is in a border. 
     */
    @Test
    public void twisterTeleportsCorrectly(){
        Twister twister1 = new Twister(es, 0,1);
        Twister twister2 = new Twister(es, 23,24);
        es.ticTac();
        
        assertEquals(es.get(1,0), twister1);
        assertEquals(es.get(24,23), twister2);
    }
    
    /**
     * If a bush dessapears by an elephant it will transform in earth
     */
    @Test
    public void shouldEntityBushDessapearsItWillBecomeEarth(){
        Bush bush = new Bush(es,3,15,false);
        Elephant elephant = new Elephant(es,1,13);
        
        es.ticTac();
        assertTrue(es.get(3,15) instanceof Earth);
    }
    
    /**
     * If an elephant walks in the place he were there will be earth
     */
    @Test
    public void shouldElephantWalksThePlaceHeWereWillBeEarth(){
        Elephant elephant = new Elephant(es,1,13);
        es.ticTac();
        
        assertTrue(es.get(1,13)instanceof Earth);
    }
    
    /**
     * If a twister kills an elephant the place the elephant were will be earth
     */
    @Test
    public void shouldElephantsDieThePlaceHeWereWillBeEarth(){
        Elephant elephant = new Elephant(es,1,13);
        Twister twister1 = new Twister(es,3,13);
        es.ticTac();
        es.ticTac();
        
        assertTrue(es.get(2,14)instanceof Earth);
    }
    
    /**
     * At least one of the earth will become grass in the grassland zone.
     */
    @Test
    public void shouldAtLeastOneOfEarthWillBecomeGrassInGrasslandZone(){
        for(int i = 0; i < 25; i++){
            for(int j = 0; j <25; j++){
                new Grass(es,i,j);
            }
        }
        es.ticTac();
        boolean theresGrass = false;
        for(int i = 14; i < 25; i++){
            for(int j = 4; j <25; j++){
                theresGrass = true;
                break;
            }
        }
        
        assertTrue(theresGrass);
    }
    
    /**
     * Should give 8 Earth if we know the Moore neighborhood is just Earth.
     */
    @Test
    public void shouldGiveEightEarthInMooreNeighborhood(){
        for(int i = 0; i <=2; i++){
            for(int j = 0; j<= 2; j++){
                new Earth(es,i,j);
            }
        }
        assertEquals(8, es.countEarthInMooreNeighborhood(1,1));
    }
    
    /**
     * Should zebra move 2 cells.
     */
    @Test
    public void shouldZebraMoveTwoCells(){
        Zebra zebra1 = new Zebra(es,24,24);
        
        Grass grass1 = new Grass(es,24,22);
        Grass grass2 = new Grass(es,23,22);
        Grass grass3 = new Grass(es, 22,22);
        Grass grass4 = new Grass(es,22,23);
        
        zebra1.tic();
        zebra1.tac();
        assertEquals(zebra1, es.get(22,24));
    }
    
    /**
     * Should give 8 Grass if we know the Moore neighborhood is just Grass.
     */
    @Test
    public void shouldGiveEightGrassInMooreNeighborhood(){
        for(int i = 0; i <=2; i++){
            for(int j = 0; j<= 2; j++){
                new Grass(es,i,j);
            }
        }
        assertEquals(8, es.countGrassInMooreNeighborhood(1,1));
    }

    
    /**
     * Should zebra eat grass and recover all his energy
     */
    @Test
    public void shouldZebraRecoverEnergyEatingAGrass(){
        Zebra zebra1 = new Zebra(es,24,24);
        
        Grass grass1 = new Grass(es,24,22);
        Grass grass2 = new Grass(es,23,22);
        Grass grass3 = new Grass(es, 22,22);
        Grass grass4 = new Grass(es,22,23);
        
        es.ticTac();
        assertEquals(zebra1.getEnergy(), 100);
    }
    
    /**
     * Zebra should eat just one grass per Tic-tac.
     */
    @Test
    public void shouldZebraEatJustOneGrassPerTicTac(){
        Zebra zebra1 = new Zebra(es,22,24);
        
        Grass grass1 = new Grass(es,22,23);
        Grass grass2 = new Grass(es,21,23);
        Grass grass3 = new Grass(es,21,24);
            
        zebra1.tac();
    
        assertEquals(2, es.countGrassInMooreNeighborhood(22,24));
    }
    
    /**
     * Lyon not must eat a grass
     */
    @Test
    public void shouldNotLyonEatGrass(){
        Lyon lyon1 = new Lyon(es,23,18);
        
        Grass grass1 = new Grass(es,22,18);
        Grass grass2 = new Grass(es,24,19);
        Grass grass3 = new Grass(es,24,17);
        Grass grass4 = new Grass(es,23,19);
        Grass grass5 = new Grass(es,23,17);
        Grass grass6 = new Grass(es,22,17);
        Grass grass7 = new Grass(es,22,19);
        
        lyon1.tic();
        lyon1.tac();
        
        
        assertEquals(7, es.countGrassInMooreNeighborhood(23,18)); 
    }
    
    /**
    * Zebra should eat just one grass per Tic-tac.
    */
    @Test
    public void shouldLyonEatAZebraPerTicTac(){
        Lyon lyon1 = new Lyon(es,22,24);
        
        Zebra zebra1 = new Zebra(es,22,23);
        Zebra zebra2 = new Zebra(es,21,23);
        Zebra zebra3 = new Zebra(es,21,24);
            
        lyon1.tac();
    
        assertEquals(2, es.countZebraInMooreNeighborhood(22,24));
    }    
    
    /**
     * Angry elephant must kill a normal elephant and walk one step if an elephant is 2 squares away
     */
    @Test
    public void shouldAngryElephantKillaElephantAndWalkOneStep(){
        Elephant elephant1 = new Elephant(es, 2, 2);
        AngryElephant elephant2 = new AngryElephant(es, 0, 0);
        es.ticTac();
        
        assertEquals(es.get(1,1), elephant2);
        assertTrue(es.get(0,0) instanceof Earth);
        
    }
    
    /**
     * Angry Elephant eats the elephants surrounding it and moves two spaces diagonally downwards.
     */
    @Test
    public void shouldAngryElephantEatElephantsAndMove(){
        AngryElephant Mendivelso = new AngryElephant(es, 7, 16);
        
        Elephant e1 = new Elephant(es, 7, 17);
        Elephant e2 = new Elephant(es, 8, 15);
        es.ticTac();
        
        assertTrue(es.get( 7, 17) instanceof Earth);
        assertTrue(es.get( 8, 15) instanceof Earth);
        
        assertEquals(es.get(9,18), Mendivelso);
    }
    
    /**
     * Angry Elephats must walk two steps
     */
    @Test
    public void shouldWalkTwoSteps(){
        AngryElephant elephant1 = new AngryElephant(es, 0, 0);
        es.ticTac();
        
        assertEquals(es.get(2,2), elephant1);
    }
    
    /**
     * Ovni must kill all's entities except a Twisters 
     */
    @Test
    public void shouldKillEntities(){
        Ovni alien = new Ovni(es, 20, 3);
        es.ticTac();
        
        int[] position = es.find(alien);
        int f = position[0];
        int c = position[1];
        for(int i=-1 ; i<=1; i++){
            for(int j=-1 ; j<=1; j++){
                if(i!=0 || j!=0){
                    assertTrue(es.get(f+i, c+j) instanceof Earth || es.get(f+i, c+j)instanceof Twister || es.get(f+i, c+j)==null);
                }
            }
        }
    }
    
    /**
     * Alien 1 and Alien 2 must always be in different positions.
     */
    @Test 
    public void shouldNotBeOnTopOfAnotherOvni(){
        Ovni alien1 = new Ovni(es, 20, 3);
        alien1.tic();
        
        Ovni alien2 = new Ovni( es, 20, 8);
        alien2.tic();
        
        
        int[] p1= es.find(alien1);
        int[] p2= es.find(alien2);
        
        assertFalse(p1 == p2);
        
    }

    /**
     * Ovni(David) must kill a Entities that are one square away.
     */
    @Test
    public void shouldOvniKillAEntitiesThatAreOneSquareAway(){
        Ovni David= new Ovni(es, 20, 3);
        
        Bush mopane = new Bush(es, 20,4,false);
        Bush acacia = new Bush(es, 21,3,false);
        
        AngryElephant elephant1 = new AngryElephant(es, 21, 4);  
        David.tac();
        
        assertTrue(es.get(21,4) instanceof Earth);
        assertTrue(es.get(20,4) instanceof Earth);
        assertTrue(es.get(21,3) instanceof Earth);
    }
    
    /**
     * Lyon must procreate
     */
    @Test
    public void shouldProcreateLyon(){
        Lyon lyon1 = new Lyon(es,14, 6);
        Lyon lyon2 = new Lyon(es,16, 6);
        lyon1.tac();
        
        assertTrue(es.get(15, 6) instanceof Lyon);
    }
    
    /**
     * Zebra must procreate
     */
    @Test
    public void shouldProcreateZebra(){
        Zebra zebra1 = new Zebra(es,14, 8);
        Zebra zebra2 = new Zebra(es,16, 8);
        zebra1.tac();
        
        assertTrue(es.get(15, 8) instanceof Zebra);
    }
    
    /**
     * Tears down the test fixture.
     *
     * Called after every test case method.
     */
    @After
    public void tearDown()
    {
    }
}