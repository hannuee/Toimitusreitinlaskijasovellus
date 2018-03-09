/**
 *@author Hannu Erälaukko
 */
package tiralabra.toimitusreitinlaskijasovellus;

import org.junit.Assert;
import org.junit.Test;
import static org.junit.Assert.*;

import tiralabra.algoritmit.*;

public class MainTest {
   
    // Joukko verkkoja jotka ajetaan kaikkien algoritmien läpi (Brute,Dyn,Heur).
    // Paketoitu nämä verkot Testiverkko-olioihin jotta mukana saadaan menemään 
    // myös lyhyimmän reitin kulkuohjeet sekä verkon id bugien etsintää varten.
    // Symmetrisiä verkkoja jotka luotu "käsin" ja täten lyhin reitti niissä tiedetään.
    // (Lätkitty kasa solmuja näytölle http://graphonline.ru/en/  avulla, sitten
    //  yhdistetty solmut reittiin joka menee kaikista solmuista. Kopioitu
    //  sivuston tarjoama etäisyysmatriisi ja liitetty se Testialgoritmit.symmetrisenVerkonLuojaPohjasta()
    //  työkäluun (jota käytetty hiekkalaatikossa) joka tehnyt sen perusteella symmetriset etäisyysmatriisit joissa alussa
    //  tehty reitti on varmasti edelleen lyhin.)
    // Verkkojen rakenteen takia myös Heuristinen antaa lyhyimmän vastauksen verkoissa.
    private static final Testiverkko[] TESTIVERKOT = {
        new Testiverkko(3,       // Testiverkon id.
            new int[][]{         // Itse testiverkko.
            {0, 3, 5},
            {3, 0, 3},
            {5, 3, 0}},
            new int[]{0,2,1,0})  // Lyhimmän reitin ohjeet.
        ,
        new Testiverkko(5,       
            new int[][]{        
            {0, 96, 1, 5, 69},
            {96, 0, 47, 1, 11},
            {1, 47, 0, 77, 3},
            {5, 1, 77, 0, 99},
            {69, 11, 3, 99, 0}},
            new int[]{0,3,1,4,2,0})  
        ,
        new Testiverkko(6,      
            new int[][]{        
            {0, 3, 44, 1, 64, 79},
            {3, 0, 1, 46, 57, 99},
            {44, 1, 0, 81, 77, 7},
            {1, 46, 81, 0, 3, 28},
            {64, 57, 77, 3, 0, 5},
            {79, 99, 7, 28, 5, 0}},
            new int[]{0,1,2,5,4,3,0})  
        ,
        new Testiverkko(7,                 
            new int[][]{                   
            {0, 23, 36, 114, 1, 3, 104},
            {23, 0, 7, 58, 44, 1, 61},
            {36, 7, 0, 3, 92, 67, 107},
            {114, 58, 3, 0, 82, 109, 11},
            {1, 44, 92, 82, 0, 70, 3},
            {3, 1, 67, 109, 70, 0, 23},
            {104, 61, 107, 11, 3, 23, 0}},
            new int[]{0,5,1,2,3,6,4,0})     
        ,
        new Testiverkko(8,       
            new int[][]{                   
            {0, 47, 11, 29, 33, 95, 22, 5},
            {47, 0, 5, 95, 1, 117, 86, 29},
            {11, 5, 0, 21, 50, 89, 32, 55},
            {29, 95, 21, 0, 49, 118, 5, 1},
            {33, 1, 50, 49, 0, 11, 49, 105},
            {95, 117, 89, 118, 11, 0, 7, 47},
            {22, 86, 32, 5, 49, 7, 0, 115},
            {5, 29, 55, 1, 105, 47, 115, 0}},
            new int[]{0,7,3,6,5,4,1,2,0})     
        ,
        new Testiverkko(10,
            new int[][]{
            {0, 30, 28, 25, 21, 40, 3, 79, 1, 109},
            {30, 0, 7, 24, 78, 62, 98, 73, 3, 119},
            {28, 7, 0, 3, 22, 112, 37, 109, 99, 44},
            {25, 24, 3, 0, 45, 62, 48, 1, 44, 97},
            {21, 78, 22, 45, 0, 1, 23, 35, 79, 11},
            {40, 62, 112, 62, 1, 0, 3, 109, 94, 58},
            {3, 98, 37, 48, 23, 3, 0, 51, 92, 86},
            {79, 73, 109, 1, 35, 109, 51, 0, 24, 3},
            {1, 3, 99, 44, 79, 94, 92, 24, 0, 105},
            {109, 119, 44, 97, 11, 58, 86, 3, 105, 0}},
            new int[]{0,6,5,4,9,7,3,2,1,8,0})
        ,
        new Testiverkko(13,       
            new int[][]{                   
            {0, 1, 59, 70, 86, 23, 51, 61, 90, 85, 29, 1, 22},
            {1, 0, 100, 55, 30, 3, 117, 41, 110, 97, 26, 29, 82},
            {59, 100, 0, 5, 90, 108, 1, 77, 109, 83, 29, 25, 55},
            {70, 55, 5, 0, 1, 56, 85, 108, 36, 94, 83, 113, 80},
            {86, 30, 90, 1, 0, 32, 110, 11, 90, 62, 42, 58, 52},
            {23, 3, 108, 56, 32, 0, 113, 103, 109, 45, 112, 67, 7},
            {51, 117, 1, 85, 110, 113, 0, 57, 38, 68, 91, 62, 7},
            {61, 41, 77, 108, 11, 103, 57, 0, 5, 65, 71, 43, 60},
            {90, 110, 109, 36, 90, 109, 38, 5, 0, 5, 36, 92, 41},
            {85, 97, 83, 94, 62, 45, 68, 65, 5, 0, 7, 67, 45},
            {29, 26, 29, 83, 42, 112, 91, 71, 36, 7, 0, 3, 90},
            {1, 29, 25, 113, 58, 67, 62, 43, 92, 67, 3, 0, 107},
            {22, 82, 55, 80, 52, 7, 7, 60, 41, 45, 90, 107, 0}},
            new int[]{0,1,5,12,6,2,3,4,7,8,9,10,11,0})
    };
    
    
    
    // Testifunktioiden testit:
    
    @Test
    public void vastauksenTarkistajaFunktionTestit(){
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
    public void verkonArpojaFunktionTestit(){
        // Luuppi joka luo ja testaa etäisyysmatriisit kokoväliltä 2-100. (2x2, 3x3, ..., 100x100)
        for(int verkonKoko = 2; verkonKoko <= 100; ++verkonKoko){  
            
            int[][] verkko = Testialgoritmit.verkonArpoja(verkonKoko, 87413867);  // Luodaan etäisyysmatriisi.
            
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
    
    @Test
    public void reittienVertailijanTestit(){
        assertTrue(ReittienVertailija.ratkaise(new int[]{0, 1, 2, 3, 0}, new int[]{0, 1, 2, 3, 0}));
        assertTrue(ReittienVertailija.ratkaise(new int[]{0, 1, 2, 3, 0}, new int[]{0, 3, 2, 1, 0}));
        assertFalse(ReittienVertailija.ratkaise(new int[]{0, 1, 2, 3, 0}, new int[]{0, 1, 2, 1, 0}));
    }
    
    
    
    // Pääalgoritmien testit:
    
    @Test
    public void kauppamatkustajaBruteForceTestit(){
        
        for(Testiverkko verkko : TESTIVERKOT){
            int[] vastaus = KauppamatkustajaBruteForce.ratkaise(verkko.getVerkko());
            boolean vastaakoMallivastausta = ReittienVertailija.ratkaise(verkko.getReittiOhjeet(), vastaus);
            Assert.assertTrue("FAIL BruteForce Verkko ID " + verkko.getId(), vastaakoMallivastausta);
        }

    }
    
    @Test
    public void kauppamatkustajaDynaaminenTestit(){
        
        for(Testiverkko verkko : TESTIVERKOT){
            int[] vastaus = KauppamatkustajaDynaaminen.ratkaise(verkko.getVerkko());
            boolean vastaakoMallivastausta = ReittienVertailija.ratkaise(verkko.getReittiOhjeet(), vastaus);
            Assert.assertTrue("FAIL Dynaaminen Verkko ID " + verkko.getId(), vastaakoMallivastausta);
        }
        
    }
    
    @Test
    public void kauppamatkustajaHeuristinenTestit(){
        
        for(Testiverkko verkko : TESTIVERKOT){
            int[] vastaus = KauppamatkustajaHeuristinen.ratkaise(verkko.getVerkko());
            boolean vastaakoMallivastausta = ReittienVertailija.ratkaise(verkko.getReittiOhjeet(), vastaus);
            Assert.assertTrue("FAIL Heuristinen Verkko ID " + verkko.getId(), vastaakoMallivastausta);
        }
        
    }
    
    @Test
    public void bruteForceVsDynaaminenRandomVerkoilla(){
        // Luuppi joka jokaisella kerralla arpoo aina yhden solmun verran suuremman
        // täydellisen symmetrisen verkon ja sitten testaa että kun tämä verkko
        // annetaan Brutelle ja Dynille niin onko vastaukset samat sekä käykö reitit
        // verkon jokaisessa solmussa tasan kerran.
        for(int verkonKoko = 2; verkonKoko <= 16; ++verkonKoko){
            
            int[][] verkko = Testialgoritmit.verkonArpoja(verkonKoko, 87413867);
            
            int[] vastaus1 = KauppamatkustajaBruteForce.ratkaise(verkko);
            int[] vastaus2 = KauppamatkustajaDynaaminen.ratkaise(verkko);
            
            boolean onkoReitti1OK = Testialgoritmit.reitinTarkistaja(verkonKoko, vastaus1);
            boolean onkoReitti2OK = Testialgoritmit.reitinTarkistaja(verkonKoko, vastaus2);
            boolean ovatkoSamaReitti = ReittienVertailija.ratkaise(vastaus1, vastaus2);
            
            assertTrue("Brute Fail kun verkon koko " + verkonKoko, onkoReitti1OK);
            assertTrue("Dyn Fail kun verkon koko " + verkonKoko, onkoReitti2OK);
            assertTrue("Brute ja Dyn erivastaus kun verkon koko " + verkonKoko, ovatkoSamaReitti);
        }
    }
    
    // Testi jolla selvitetty suurin mahdollinen verkon koko jolla KauppamatkustajaDynaaminen
    // antaa vastauksen vielä kohtuullisessa ajassa:
    // verkonKoko = 25 => ERROR Java heap space
    // verkonKoko = 24 => 16.021 sec
    // verkonKoko = 23 => 7.413 sec
    // verkonKoko = 22 => 3.703 sec
    // verkonKoko = 21 => 1.492 sec
    // verkonKoko = 20 => 0.672 sec
    @Test
    public void kauppamatkustajaDynaaminenMaxVerkko(){
        int verkonKoko = 20;

        int[][] verkko = Testialgoritmit.verkonArpoja(verkonKoko, 87413867);

        int[] vastaus = KauppamatkustajaDynaaminen.ratkaise(verkko);

        boolean onkoReittiOK = Testialgoritmit.reitinTarkistaja(verkonKoko, vastaus);

        assertTrue("Dyn Fail kun verkon koko " + verkonKoko, onkoReittiOK);
    }
    
    // Testi jolla selvitetty suurin mahdollinen verkon koko jolla KauppamatkustajaBruteForce
    // antaa vastauksen vielä kohtuullisessa ajassa:
    // verkonKoko = 18 => 103.842 sec
    // verkonKoko = 17 => 5.922 sec
    // verkonKoko = 16 => 1.119 sec
    // verkonKoko = 15 => 0.547 sec
    @Test
    public void kauppamatkustajaBruteForceMaxVerkko(){
        int verkonKoko = 15;

        int[][] verkko = Testialgoritmit.verkonArpoja(verkonKoko, 87413867);

        int[] vastaus = KauppamatkustajaBruteForce.ratkaise(verkko);

        boolean onkoReittiOK = Testialgoritmit.reitinTarkistaja(verkonKoko, vastaus);

        assertTrue("Dyn Fail kun verkon koko " + verkonKoko, onkoReittiOK);
    }
    
    
    // Heuristisen reitinpituus verrattuna parhaaseen reittiin:
    
    //@Test
    public void reitinPituudenEroDynaaminenVsHeuristinen(){
        for(int verkonKoko = 2; verkonKoko <= 20; ++verkonKoko){
            System.out.println("Verkon koko: " + verkonKoko);
            
            for(int siemen = 1; siemen <= 10; ++siemen){
                int[][] verkko = Testialgoritmit.verkonArpoja(verkonKoko, 87413850+siemen);
                
                int[] vastausDyn = KauppamatkustajaDynaaminen.ratkaise(verkko);
                int[] vastausHeur = KauppamatkustajaHeuristinen.ratkaise(verkko);
                
                System.out.println("" + (( (1.0*Reitinpituus.ratkaise(verkko, vastausHeur)) / (1.0*Reitinpituus.ratkaise(verkko, vastausDyn)) ) * 100.00) );
                
                boolean onkoReittiOkDyn = Testialgoritmit.reitinTarkistaja(verkonKoko, vastausDyn);
                boolean onkoReittiOkHeur = Testialgoritmit.reitinTarkistaja(verkonKoko, vastausHeur);
                assertTrue("Dyn Fail kun verkon koko " + verkonKoko, onkoReittiOkDyn);
                assertTrue("Heur Fail kun verkon koko " + verkonKoko, onkoReittiOkHeur);
            }
        }
    }
    
   
    // Suorituskykytestit:
    
    //@Test
    public void suorituskykyTestitBruteForce(){
        
        for(int verkonKoko = 2; verkonKoko <= 15; ++verkonKoko){
            System.out.println("Verkon koko: " + verkonKoko);
            
            for(int siemen = 1; siemen <= 10; ++siemen){
                int[][] verkko = Testialgoritmit.verkonArpoja(verkonKoko, 87413850+siemen);
                
                long alku = System.nanoTime();
                int[] vastaus = KauppamatkustajaBruteForce.ratkaise(verkko);
                long loppu = System.nanoTime();
                System.out.println("" + (loppu - alku));
                
                boolean onkoReittiOK = Testialgoritmit.reitinTarkistaja(verkonKoko, vastaus);
                assertTrue("Dyn Fail kun verkon koko " + verkonKoko, onkoReittiOK);
            }
        }
        
    }
    
    //@Test
    public void suorituskykyTestitDynaaminen(){
        
        for(int verkonKoko = 2; verkonKoko <= 20; ++verkonKoko){
            System.out.println("Verkon koko: " + verkonKoko);
            
            for(int siemen = 1; siemen <= 10; ++siemen){
                int[][] verkko = Testialgoritmit.verkonArpoja(verkonKoko, 87413850+siemen);
                
                long alku = System.nanoTime();
                int[] vastaus = KauppamatkustajaDynaaminen.ratkaise(verkko);
                long loppu = System.nanoTime();
                System.out.println("" + (loppu - alku));
                
                boolean onkoReittiOK = Testialgoritmit.reitinTarkistaja(verkonKoko, vastaus);
                assertTrue("Dyn Fail kun verkon koko " + verkonKoko, onkoReittiOK);
            }
        }
        
    }
    
    //@Test
    public void suorituskykyTestitHeuristinen(){
        
        for(int verkonKoko = 2; verkonKoko <= 30; ++verkonKoko){
            System.out.println("Verkon koko: " + verkonKoko);
            
            for(int siemen = 1; siemen <= 10; ++siemen){
                int[][] verkko = Testialgoritmit.verkonArpoja(verkonKoko, 87413850+siemen);
                
                long alku = System.nanoTime();
                int[] vastaus = KauppamatkustajaHeuristinen.ratkaise(verkko);
                long loppu = System.nanoTime();
                System.out.println("" + (loppu - alku));
                
                boolean onkoReittiOK = Testialgoritmit.reitinTarkistaja(verkonKoko, vastaus);
                assertTrue("Dyn Fail kun verkon koko " + verkonKoko, onkoReittiOK);
            }
        }
        
    }
}
