/**
 *
 * @author Hannu
 */
package tiralabra.toimitusreitinlaskijasovellus;

import org.junit.Assert;
import org.junit.Test;

import static org.junit.Assert.*;      // Testit joissa testataan vaan että kaikki solmut käyty läpi tasan kerran, random taulukkoja?

public class MainTest { 
    
    // Kopioitu härskisti syksy 2017 TIRA_08_01 tehtävästä testiverkot e1-e4 kun
    // ei vielä ehditty tehdä omia testiverkkoja. (Ja vastaukset jotka pitäisi saada näillä testiverkoilla.)
    //@Test
    public void kauppamatkustajaBruteForceTestit(){
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
    
    // Nämä testit tehty itse
    @Test
    public void kauppamatkustajaHeuristinenTestit(){
        int[][] m1={{0,1,5,5},
                    {5,5,5,5},
                    {2,5,5,5},
                    {5,5,1,5}};
        assertArrayEquals(new int[]{0,1,3,2,0}, Algoritmit.kauppamatkustajaBruteForce(m1));
        
        int[][] m2={{0,2,5,5},
                    {5,5,5,5},
                    {1,5,5,5},
                    {5,5,1,5}};
        assertArrayEquals(new int[]{0,1,3,2,0}, Algoritmit.kauppamatkustajaBruteForce(m2));
        
        int[][] m3={{0,5,5,1},
                    {5,5,3,5},
                    {2,5,5,5},
                    {2,5,5,5}};
        assertArrayEquals(new int[]{0,3,1,2,0}, Algoritmit.kauppamatkustajaBruteForce(m3));
    }
    
}
