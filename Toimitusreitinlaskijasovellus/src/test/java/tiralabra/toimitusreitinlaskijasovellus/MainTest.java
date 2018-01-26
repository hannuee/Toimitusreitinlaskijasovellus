/**
 *
 * @author Hannu
 */
package tiralabra.toimitusreitinlaskijasovellus;

import org.junit.Test;

import static org.junit.Assert.*;

public class MainTest {
    
    // Kopioitu härskisti syksy 2017 TIRA_08_01 tehtävästä testiverkot e1-e4 kun
    // ei vielä ehditty tehdä omia testiverkkoja. (Ja vastaukset jotka pitäisi saada näillä testiverkoilla.)
    @Test
    public void kauppamatkustajanReitinpituus(){
        int[][] e1={{0,3,2,1},
                    {3,0,4,2},
                    {2,4,0,4},
                    {1,2,4,0}};
        assertEquals(9, Algoritmit.kauppamatkustajaBruteForce(e1));
        
        int[][] e2={{0,2,1,1},
                    {2,0,1,1},
                    {1,1,0,2},
                    {1,1,2,0}};
        assertEquals(4, Algoritmit.kauppamatkustajaBruteForce(e2));
        
        int[][] e3={{0,1,2,1},
                    {1,0,1,2},
                    {2,1,0,1},
                    {1,2,1,0}};
        assertEquals(4, Algoritmit.kauppamatkustajaBruteForce(e3));
        
        int[][] e4={{0,1,2,3},
                    {1,0,4,5},
                    {2,4,0,6},
                    {3,5,6,0}};
        assertEquals(14, Algoritmit.kauppamatkustajaBruteForce(e4));
     }
    
}
