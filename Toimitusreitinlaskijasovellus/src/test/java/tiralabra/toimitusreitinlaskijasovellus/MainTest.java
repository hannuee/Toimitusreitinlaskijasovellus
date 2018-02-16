/**
 *@author Hannu Erälaukko
 */
package tiralabra.toimitusreitinlaskijasovellus;

import java.util.Arrays;
import org.junit.Assert;
import org.junit.Test;
import static org.junit.Assert.*;

import tiralabra.algoritmit.*;

public class MainTest {
   
    // Testifunktioiden testit:
    
    @Test
    public void vastauksenTarkistajaTestit(){
        //TRUE
        assertTrue(Testialgoritmit.reitinTarkistaja(2, new int[]{0, 1, 0}));
        assertTrue(Testialgoritmit.reitinTarkistaja(3, new int[]{0, 1, 2, 0}));
        assertTrue(Testialgoritmit.reitinTarkistaja(3, new int[]{0, 2, 1, 0}));
        assertTrue(Testialgoritmit.reitinTarkistaja(4, new int[]{0, 2, 1, 3, 0}));
        assertTrue(Testialgoritmit.reitinTarkistaja(4, new int[]{0, 3, 2, 1, 0}));
        assertTrue(Testialgoritmit.reitinTarkistaja(5, new int[]{0, 3, 2, 1, 4, 0}));
        
        //FALSE
        assertFalse(Testialgoritmit.reitinTarkistaja(2, new int[]{}));
        assertFalse(Testialgoritmit.reitinTarkistaja(2, new int[]{0}));
        assertFalse(Testialgoritmit.reitinTarkistaja(2, new int[]{0, 0}));
        assertFalse(Testialgoritmit.reitinTarkistaja(2, new int[]{0, 0, 0}));
        assertFalse(Testialgoritmit.reitinTarkistaja(2, new int[]{0, 2, 0}));
        assertFalse(Testialgoritmit.reitinTarkistaja(3, new int[]{0, 2, 1, 2}));
    }
    
    @Test
    public void verkonArpojaTestit(){
        // Luuppi joka luo ja testaa asymmetriset etäisyysmatriisit kokoväliltä 2-100. (2x2, 3x3, ..., 100x100)
        for(int verkonKoko = 2; verkonKoko <= 100; ++verkonKoko){  
            
            int[][] verkko = Testialgoritmit.verkonArpoja(verkonKoko);  // Luodaan etäisyysmatriisi.
            
            assertTrue(verkko.length == verkonKoko);    // Onhan etäisyysmatriisissa oikea määrä rivejä
            for(int i = 0; i < verkonKoko; ++i){        // Jos on niin tämä luuppi käy kaikki rivit läpi.
                
                assertTrue(verkko[i].length == verkonKoko);  // Onhan rivissä oikea määrä alkioita.
                for(int i2 = 0; i2 < verkonKoko; ++i2){      // Jos on niin tämä luuppi käy kaikki aliot läpi.
                    
                    if(i == i2){
                        assertTrue(verkko[i][i2] == 0);
                    } else {
                        assertTrue(verkko[i][i2] >= 1);
                    }
                }
            } 
        }
    }  
    
    
    // Varsinaisten algoritmien testit:
    
    //@Test
//    public void kauppamatkustajaBruteForceReittiTestit(){
//        // Luuppi joka jokaisella kerralla arpoo aina yhden solmun verran suuremman
//        // täydellisen asymmetrisen verkon ja sitten testaa että kun tämä verkko
//        // annetaan testatulle algorirmille niin onko testatun algoritmin vastaus
//        // reitti joka käy verkon jokaisessa solmussa tasan kerran.
//        for(int verkonKoko = 2; verkonKoko <= 16; ++verkonKoko){
//            
//            int[][] verkko = Testialgoritmit.verkonArpoja(verkonKoko);
//            int[] vastaus = Algoritmit.kauppamatkustajaBruteForce(verkko);
//            boolean onkoReittiOk = Testialgoritmit.reitinTarkistaja(verkonKoko, vastaus);
//            assertTrue(onkoReittiOk);
//        }
//    }
    
    // Kopioitu härskisti syksy 2017 TIRA_08_01 tehtävästä testiverkot e1-e4.
    @Test
    public void kauppamatkustajaBruteForceReitinPituusTestit(){
//        int[][] e1={{0,3,2,1},
//                    {3,0,4,2},
//                    {2,4,0,4},
//                    {1,2,4,0}};
//        assertEquals(9, Algoritmit.kauppamatkustajaBruteForce(e1));
//        
//        int[][] e2={{0,2,1,1},
//                    {2,0,1,1},
//                    {1,1,0,2},
//                    {1,1,2,0}};
//        assertEquals(4, Algoritmit.kauppamatkustajaBruteForce(e2));
//        
//        int[][] e3={{0,1,2,1},
//                    {1,0,1,2},
//                    {2,1,0,1},
//                    {1,2,1,0}};
//        assertEquals(4, Algoritmit.kauppamatkustajaBruteForce(e3));
//        
//        int[][] e4={{0,1,2,3},
//                    {1,0,4,5},
//                    {2,4,0,6},
//                    {3,5,6,0}};
//        assertEquals(14, Algoritmit.kauppamatkustajaBruteForce(e4));
                 

                 int[][] e5=   // 22 Bf/Dyn
       {{0, 3, 49, 109, 83, 3},
        {3, 0, 63, 27, 5, 56},
        {49, 63, 0, 7, 3, 21},
        {109, 27, 7, 0, 70, 1},
        {83, 5, 3, 70, 0, 33},
        {3, 56, 21, 1, 33, 0}};
                 
                 int[][] e6=   // 319 Bf/Dyn
       {{0, 68, 108, 53, 77, 104},
        {68, 0, 45, 117, 116, 76},
        {108, 45, 0, 38, 112, 68},
        {53, 117, 38, 0, 112, 108},
        {77, 116, 112, 112, 0, 30},
        {104, 76, 68, 108, 30, 0}};
                 
                 int[][] e7=   // 346 Bf/Dyn
       {{0, 63, 32, 101, 103, 94},
        {63, 0, 107, 78, 54, 46},
        {32, 107, 0, 110, 71, 104},
        {101, 78, 110, 0, 71, 108},
        {103, 54, 71, 71, 0, 24},
        {94, 46, 104, 108, 24, 0}};
                 
                 int[][] e8= // 29 varmasti!  05123640    
       {{0, 23, 36, 114, 1, 3, 104},
        {23, 0, 7, 58, 44, 1, 61},
        {36, 7, 0, 3, 92, 67, 107},
        {114, 58, 3, 0, 82, 109, 11},
        {1, 44, 92, 82, 0, 70, 3},
        {3, 1, 67, 109, 70, 0, 23},
        {104, 61, 107, 11, 3, 23, 0}};
                 
                 int[][] e9=  // 36 varmasti! 06549732180
           {{0, 30, 28, 25, 21, 40, 3, 79, 1, 109},
            {30, 0, 7, 24, 78, 62, 98, 73, 3, 119},
            {28, 7, 0, 3, 22, 112, 37, 109, 99, 44},
            {25, 24, 3, 0, 45, 62, 48, 1, 44, 97},
            {21, 78, 22, 45, 0, 1, 23, 35, 79, 11},
            {40, 62, 112, 62, 1, 0, 3, 109, 94, 58},
            {3, 98, 37, 48, 23, 3, 0, 51, 92, 86},
            {79, 73, 109, 1, 35, 109, 51, 0, 24, 3},
            {1, 3, 99, 44, 79, 94, 92, 24, 0, 105},
            {109, 119, 44, 97, 11, 58, 86, 3, 105, 0}};

         
        int[] vastaus;
        int[][] testiverkko;
                 
        System.out.println("TEST1bf");
        testiverkko = e5;
        vastaus = KauppamatkustajaBruteForce.ratkaise(testiverkko);
        System.out.println(Arrays.toString(vastaus) + "  -  " + Testialgoritmit.reitinPituudenTarkistaja(testiverkko, vastaus));
        
        System.out.println("TEST2bf"); 
        testiverkko = e6;
        vastaus = KauppamatkustajaBruteForce.ratkaise(testiverkko);
        System.out.println(Arrays.toString(vastaus) + "  -  " + Testialgoritmit.reitinPituudenTarkistaja(testiverkko, vastaus));
        
        System.out.println("TEST3bf");
        testiverkko = e7;
        vastaus = KauppamatkustajaBruteForce.ratkaise(testiverkko);
        System.out.println(Arrays.toString(vastaus) + "  -  " + Testialgoritmit.reitinPituudenTarkistaja(testiverkko, vastaus));
        
        System.out.println("TEST4bf");
        testiverkko = e8;
        vastaus = KauppamatkustajaBruteForce.ratkaise(testiverkko);
        System.out.println(vastaus + "  -  " + Testialgoritmit.reitinPituudenTarkistaja(testiverkko, vastaus));
        
        System.out.println("TEST5bf");
        testiverkko = e9;
        vastaus = KauppamatkustajaBruteForce.ratkaise(testiverkko);
        System.out.println(vastaus + "  -  " + Testialgoritmit.reitinPituudenTarkistaja(testiverkko, vastaus));
    }
    
    @Test
    public void kauppamatkustajaDynaaminenTestit(){
                 int[][] e1=   
       {{0, 3, 49, 109, 83, 3},
        {3, 0, 63, 27, 5, 56},
        {49, 63, 0, 7, 3, 21},
        {109, 27, 7, 0, 70, 1},
        {83, 5, 3, 70, 0, 33},
        {3, 56, 21, 1, 33, 0}};
                 
                 int[][] e2=   
       {{0, 68, 108, 53, 77, 104},
        {68, 0, 45, 117, 116, 76},
        {108, 45, 0, 38, 112, 68},
        {53, 117, 38, 0, 112, 108},
        {77, 116, 112, 112, 0, 30},
        {104, 76, 68, 108, 30, 0}};
                 
               int[][] e3=   
       {{0, 63, 32, 101, 103, 94},
        {63, 0, 107, 78, 54, 46},
        {32, 107, 0, 110, 71, 104},
        {101, 78, 110, 0, 71, 108},
        {103, 54, 71, 71, 0, 24},
        {94, 46, 104, 108, 24, 0}};
               
             int[][] e4= 
       {{0, 23, 36, 114, 1, 3, 104},
        {23, 0, 7, 58, 44, 1, 61},
        {36, 7, 0, 3, 92, 67, 107},
        {114, 58, 3, 0, 82, 109, 11},
        {1, 44, 92, 82, 0, 70, 3},
        {3, 1, 67, 109, 70, 0, 23},
        {104, 61, 107, 11, 3, 23, 0}};
             
             int[][] e5=  
           {{0, 30, 28, 25, 21, 40, 3, 79, 1, 109},
            {30, 0, 7, 24, 78, 62, 98, 73, 3, 119},
            {28, 7, 0, 3, 22, 112, 37, 109, 99, 44},
            {25, 24, 3, 0, 45, 62, 48, 1, 44, 97},
            {21, 78, 22, 45, 0, 1, 23, 35, 79, 11},
            {40, 62, 112, 62, 1, 0, 3, 109, 94, 58},
            {3, 98, 37, 48, 23, 3, 0, 51, 92, 86},
            {79, 73, 109, 1, 35, 109, 51, 0, 24, 3},
            {1, 3, 99, 44, 79, 94, 92, 24, 0, 105},
            {109, 119, 44, 97, 11, 58, 86, 3, 105, 0}};

        
        int[] vastaus2;
        int[][] testiverkko2;
        
        System.out.println("--------------------------");
        
        System.out.println("TEST1Dyn");
        testiverkko2 = e1;
        vastaus2 = KauppamatkustajaDynaaminen.ratkaise(testiverkko2);
        System.out.println(vastaus2 + "  -  " + Testialgoritmit.reitinPituudenTarkistaja(testiverkko2, vastaus2));
        
        System.out.println("TEST2Dyn"); 
        testiverkko2 = e2;
        vastaus2 = KauppamatkustajaDynaaminen.ratkaise(testiverkko2);
        System.out.println(Arrays.toString(vastaus2) + "  -  " + Testialgoritmit.reitinPituudenTarkistaja(testiverkko2, vastaus2));
        
        System.out.println("TEST3Dyn");
        testiverkko2 = e3;
        vastaus2 = KauppamatkustajaDynaaminen.ratkaise(testiverkko2);
        System.out.println(Arrays.toString(vastaus2) + "  -  " + Testialgoritmit.reitinPituudenTarkistaja(testiverkko2, vastaus2));
        
        System.out.println("TEST4Dyn");
        testiverkko2 = e4;
        vastaus2 = KauppamatkustajaDynaaminen.ratkaise(testiverkko2);
        System.out.println(Arrays.toString(vastaus2) + "  -  " + Testialgoritmit.reitinPituudenTarkistaja(testiverkko2, vastaus2));
        
        System.out.println("TEST5Dyn");
        testiverkko2 = e5;
        vastaus2 = KauppamatkustajaDynaaminen.ratkaise(testiverkko2);
        System.out.println(Arrays.toString(vastaus2) + "  -  " + Testialgoritmit.reitinPituudenTarkistaja(testiverkko2, vastaus2));
        
        System.out.println("--------------------------");
    }
    
    // Nämä testit tehty itse
    @Test
    public void kauppamatkustajaHeuristinenTestit(){
        int[][] m1={{0,1,5,5},
                    {5,5,5,5},
                    {2,5,5,5},
                    {5,5,1,5}};
        assertArrayEquals(new int[]{0,1,3,2,0}, KauppamatkustajaHeuristinen.ratkaise(m1));
        
        int[][] m2={{0,2,5,5},
                    {5,5,5,5},
                    {1,5,5,5},
                    {5,5,1,5}};
        assertArrayEquals(new int[]{0,1,3,2,0}, KauppamatkustajaHeuristinen.ratkaise(m2));
        
        int[][] m3={{0,5,5,1},
                    {5,5,3,5},
                    {2,5,5,5},
                    {2,5,5,5}};
        assertArrayEquals(new int[]{0,3,1,2,0}, KauppamatkustajaHeuristinen.ratkaise(m3));
    }
    
}
