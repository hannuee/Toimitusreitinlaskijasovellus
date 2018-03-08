/**
 * @author Hannu Erälaukko
 */
package tiralabra.algoritmit;

public class Reitinpituus {
    
    public static int ratkaise(int[][] verkko, int[] vastaus){
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
