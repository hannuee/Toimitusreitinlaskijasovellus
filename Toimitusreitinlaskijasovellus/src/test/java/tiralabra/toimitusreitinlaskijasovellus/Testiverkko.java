/**
 * @author Hannu Erälaukko
 */
package tiralabra.toimitusreitinlaskijasovellus;

public class Testiverkko {
    
    private int id;                     // Testiverkon id.
    private int[][] verkko;             // Itse testiverkko.
    private int[] reittiOhjeet;         // Lyhimmän reitin ohjeet.
     
    public Testiverkko(int id, int[][] verkko, int[] reittiOhjeet){
        this.id = id;
        this.verkko = verkko;
        this.reittiOhjeet = reittiOhjeet;
    }
    
    public int getId(){
        return this.id;
    }
    
    public int[][] getVerkko(){
        return this.verkko;
    }
    
    public int[] getReittiOhjeet(){
        return this.reittiOhjeet;
    }
    
}
