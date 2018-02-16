/**
 * @author Hannu Erälaukko
 */
package tiralabra.toimitusreitinlaskijasovellus;

import java.util.Random;

public class Testialgoritmit {

    /**
     * Palauttaa symmetrisen etäisyysmatriisin halutun kokoiselle täydelliselle verkolle.
     * Etäisyydet verkon solmujen välillä arvotaan väliltä 1 - 1000.
     * @param solmuja Haluttu verkon solmujen määrä.
     * @param seed Siemen satunnaislukuarpojalle jolloin arvottu verkko ei olekkaan enää niin satunnainen.
     * @return Täydellisen symmetrisen verkon etäisyysmatriisin.
     */
    public static int[][] verkonArpoja(int solmuja, long seed){
        Random arpoja = new Random(seed);
        
        int[][] verkko = new int[solmuja][solmuja];
        // Verkon kaikki aliot 0:ksi.
        for(int index = 0; index < solmuja; ++index){
            for(int index2 = 0; index2 < solmuja; ++index2){
                verkko[index][index2] = 0;
            }
        }
        
        int i = 0;
        while(i < solmuja){
            int i2 = 0;
            while(i2 < solmuja){
                if(i == i2){  // Koska etäisyys jostakin solmusta itseensä on 0.
                    
                } else if(verkko[i][i2] == 0 && verkko[i2][i] != 0){
                    verkko[i][i2] = verkko[i2][i];
                } else if(verkko[i][i2] == 0 && verkko[i2][i] == 0) {
                    verkko[i][i2] = arpoja.nextInt(1000) + 1;
                }
                ++i2;
            }
            ++i;
        }
        
        return verkko;
    }
    
    /**
     * Tarkistaa että metodin parametrinaan saama vastaus-taulukko muodostaa reitin verkossa
     * (metodin toisen parametrin määräämän kokoisessa verkossa) joka lähtee solmusta 0,
     * vierailee sitten kerran kaikissa verkon muissa solmuissa ja palaa sitten takaisin 
     * solmuun 0.
     * @param solmuja Solmujen määrä verkossa jonka pohjalta vastaus-taulukko on luotu.
     * @param vastaus Tarkistettava taulukko.
     * @return <code>true</code> jos vastaus-taulukko muodostaa oikeanlaisen reitin määritetyn
     * kokoisessa verkossa, <code>false</code> jos ei muodosta.
     */
    public static boolean reitinTarkistaja(int solmuja, int[] vastaus){
        if(vastaus.length != solmuja + 1){  // Tarkistetaan että vastaus-taulukko on oikean pituinen.
            return false;
        }
        if(vastaus[0] != 0 || vastaus[solmuja] != 0){  // Ensimmäinen ja viimeinen alkio on 0.
            return false;
        }
        
        // Luodaan ja alustetaan lukujen määrän tarkistava taulukko.
        int[] tarkistin = new int[solmuja];
        int i = 0;
        while(i < solmuja){
            tarkistin[i] = 0;
            ++i;
        }
        
        // Käydään vastaus-taulukko läpi poislukien ensimmäinen ja viimeinen indexi,
        // ja pidetään tarkistin-taulukolla kirjaa montako kertaa kukin luku on esiintynyt.
        i = 1;
        while(i < vastaus.length - 1){
            if(vastaus[i] >= solmuja){  // Jos jostain kumman syystä vastauksessa on lukuja jotka
                return false;           // ovat suurempia kuin suurimman solmun numero niin ei anneta
            }                           // tämän testifunktion kaatua IndexOutOfBoundiin vaan palautetaan false.
            
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
    
    public static int reitinPituudenLaskija(int[][] verkko, int[] vastaus){
        int reitinPituus = 0;
        
        int mista = 0;
        int mihin = 1;
        while(mihin < vastaus.length){
            reitinPituus += verkko[vastaus[mista]][vastaus[mihin]];
            ++mista;
            ++mihin;
        }
        
        return reitinPituus;
    }
    
    /**
     * Tarkistaa ovatko annetut taulukot täsmälleen samanlaisia tai toistensa peilikuvia
     * vai ovatko taulukot täysin erilaisia.
     * @param taulukko1
     * @param taulukko2
     * @return Palauttaa <code>true</code> jos annetut taulukot ovat täsmälleen samanlaisia tai toistensa peilikuvia,
     * muuten <code>false</code>.
     */
    public static boolean ovatkoTaulukotSymmetriset(int[] taulukko1, int[] taulukko2){
        if(taulukko1.length != taulukko2.length){
            return false;
        }
        
        boolean samanlaiset = true;
        boolean samanlaisetPeilina = true;
        int i = 0;
        int i2 = taulukko1.length - 1;
        while(i < taulukko1.length){
            if(taulukko1[i] != taulukko2[i]){ 
                samanlaiset = false;
            }
            if(taulukko1[i] != taulukko2[i2]){ 
                samanlaisetPeilina = false;
            }
            ++i;
            --i2;
        }
        
        return samanlaiset || samanlaisetPeilina;
    }
    
    
    // Työkälu jota käytetty hiekkalaatikossa.
    public static void symmetrisenVerkonLuojaPohjasta(){
                Random arpoja = new Random();
        
                int[][] ex=
{{0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0}, 
{0, 0, 0, 0, 0, 3, 0, 0, 0, 0, 0, 0, 0}, 
{0, 0, 0, 5, 0, 0, 0, 0, 0, 0, 0, 0, 0}, 
{0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0}, 
{0, 0, 0, 0, 0, 0, 0, 11, 0, 0, 0, 0, 0}, 
{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 7}, 
{0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0}, 
{0, 0, 0, 0, 0, 0, 0, 0, 5, 0, 0, 0, 0}, 
{0, 0, 0, 0, 0, 0, 0, 0, 0, 5, 0, 0, 0}, 
{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 7, 0, 0}, 
{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 3, 0}, 
{1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0}, 
{0, 0, 0, 0, 0, 0, 7, 0, 0, 0, 0, 0, 0}}; 

                
        for(int i = 0; i < ex.length; ++i){
            System.out.print("{");
            for(int i2 = 0; i2 < ex[0].length; ++i2){
                if(i == i2){
                    
                } else if(ex[i][i2] == 0 && ex[i2][i] != 0 ){
                    ex[i][i2] = ex[i2][i];
                } else if(ex[i][i2] == 0 && ex[i2][i] == 0 ){
                    ex[i][i2] = arpoja.nextInt(100) + 20;
                }
                System.out.print("" + ex[i][i2]);
                if(i2 != ex.length - 1){
                    System.out.print(", ");
                }
            }
            System.out.print("},\n");
        }
    }
    
}
