/**
 *@author Hannu Erälaukko
 */
package tiralabra.toimitusreitinlaskijasovellus;

import org.junit.Assert;
import org.junit.Test;

import java.util.Random;

import static org.junit.Assert.*;

public class MainTest {
    
    // Funktio joka arpoo asymmetrisen etäisyysmatriisin verkolle jossa on solmuja parametrinaan saaman luvun verran.
    // Etäisyydet verkon solmujen välillä tulevat olemaan välillä 1 - 1000.
    public static int[][] verkonArpoja(int solmuja){
        Random arpoja = new Random();
        
        int[][] verkko = new int[solmuja][solmuja];
        int i = 0;
        while(i < solmuja){
            int i2 = 0;
            while(i2 < solmuja){
                if(i == i2){  // Koska etäisyys jostakin solmusta itseensä on 0.
                    verkko[i][i2] = 0;
                } else {
                    verkko[i][i2] = arpoja.nextInt(1000) + 1;
                }
                ++i2;
            }
            ++i;
        }
        
        return verkko;
    }
    
    // Funktio joka tarkistaa että sen parametrinaan saaman vastaus-taulukon
    // ensimmäinen ja viimeinen alkio on 0 ja että taulukossa on lukuja väliltä
    // 1 - (solmuja-1) tasan yksi jokaista.
    public static boolean vastauksenTarkistaja(int solmuja, int[] vastaus){
        if(vastaus.length != solmuja + 1){  // Tarkistetaan että vastaus-taulukko on oikean pituinen.
            return false;
        }
        if(vastaus[0] != 0 || vastaus[solmuja] != 0){  // Ensimmäinen ja viimeinen alkio on 0.
            return false;
        }
        
        int[] tarkistin = new int[solmuja];
        int i = 0;
        while(i < solmuja){
            tarkistin[i] = 0;
            ++i;
        }
        
        // Käydään vastaus-taulukko läpi poislukien ensimmäinen ja viimeinen indexi,
        // ja pidetään tarkistin-taulukolla kirjaa montako kertaa kussakin solmussa on vierailtu.
        i = 1;
        while(i < vastaus.length - 1){
            ++tarkistin[vastaus[i]];
            ++i;
        }
        
        // Tarkastetaan tarkistin-taulukko:
        i = 1;
        while(i < solmuja){
            if(tarkistin[i] != 1){
                return false;
            }
            ++i;
        }
        
        return true;
    }
    
    
    // Kopioitu härskisti syksy 2017 TIRA_08_01 tehtävästä testiverkot e1-e4.
    @Test
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
    
    @Test
    public void kauppamatkustajaDynaaminenTestit(){
        int[][] e1={{0,3,2,1},
                    {3,0,4,2},
                    {2,4,0,4},
                    {1,2,4,0}};
        assertEquals(9, Algoritmit.kauppamatkustajaDynaaminen(e1));
        
        int[][] e2={{0,2,1,1},
                    {2,0,1,1},
                    {1,1,0,2},
                    {1,1,2,0}};
        assertEquals(4, Algoritmit.kauppamatkustajaDynaaminen(e2));
        
        int[][] e3={{0,1,2,1},
                    {1,0,1,2},
                    {2,1,0,1},
                    {1,2,1,0}};
        assertEquals(4, Algoritmit.kauppamatkustajaDynaaminen(e3));
        
        int[][] e4={{0,1,2,3},
                    {1,0,4,5},
                    {2,4,0,6},
                    {3,5,6,0}};
        assertEquals(14, Algoritmit.kauppamatkustajaDynaaminen(e4));
    }
    
    // Nämä testit tehty itse
    @Test
    public void kauppamatkustajaHeuristinenTestit(){
        int[][] m1={{0,1,5,5},
                    {5,5,5,5},
                    {2,5,5,5},
                    {5,5,1,5}};
        assertArrayEquals(new int[]{0,1,3,2,0}, Algoritmit.kauppamatkustajaHeuristinen(m1));
        
        int[][] m2={{0,2,5,5},
                    {5,5,5,5},
                    {1,5,5,5},
                    {5,5,1,5}};
        assertArrayEquals(new int[]{0,1,3,2,0}, Algoritmit.kauppamatkustajaHeuristinen(m2));
        
        int[][] m3={{0,5,5,1},
                    {5,5,3,5},
                    {2,5,5,5},
                    {2,5,5,5}};
        assertArrayEquals(new int[]{0,3,1,2,0}, Algoritmit.kauppamatkustajaHeuristinen(m3));
    }
    
}
