/**
 * @author Hannu Erälaukko
 */
package tiralabra.toimitusreitinlaskijasovellus;

import java.util.Random;

public class Testialgoritmit {

    /**
     * Palauttaa asymmetrisen etäisyysmatriisin halutun kokoiselle täydelliselle verkolle.
     * Etäisyydet verkon solmujen välillä arvotaan väliltä 1 - 1000.
     * @param solmuja Haluttu verkon solmujen määrä.
     * @return Täydellisen asymmetrisen verkon etäisyysmatriisin.
     */
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
    
    public static int reitinPituudenTarkistaja(int[][] verkko, int[] vastaus){
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
    
}
