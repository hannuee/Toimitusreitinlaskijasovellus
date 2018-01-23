/**
 *
 * @author Hannu
 */
package tiralabra.toimitusreitinlaskijasovellus;

public class Algoritmit {
    
    public static void kmoBfRekursio(int pituus, int[] vierailtu, int nykyinen, int k, int[] paras, int n, int[][] verkko){
        if(k == n){
            if(pituus + verkko[nykyinen][1] < paras[0]){
                paras[0] = pituus + verkko[nykyinen][1];
            }
            return;
        }
        for(int i = 2; i <= n; i++){
            if(vierailtu[i] == 0 && pituus + verkko[nykyinen][i] < paras[0]){
                int[] vierailtu2 = vierailtu.clone();  // Kopioidaan vierailtu-taulukko.
                vierailtu2[i] = k;
                kmoBfRekursio(pituus + verkko[nykyinen][i], vierailtu2, i, k + 1, paras, n, verkko);
            }
            ++i;
        }
    }
    
    
    // Pitkälti TIRA kalvon 390 mukaan, mutta muokattu ominaisuus joka muistaa lyhimmän reitin pituuden lisäki myös itse reitin.
    // Toteutettu tämä pitämällä kirjaa vierailuissa solmuista vierailtu-taulukon avulla joka muutettu booleanista intiksi,
    // ja jossa pidetään kirjaa milloin mihinkin solmuun on menty.
    // Kun kyseisestä taulukosta tulee lyhin niin kopioidaan sen sisältö parasReitti-taulukkoon.
    public static int kauppamatkustajaBruteForce(int[][] verkko){
        
        int n = verkko.length;
        
        int[] paras = {Integer.MAX_VALUE};
        int[] parasReitti = new int[n + 1];
        
        int[] vierailtu = new int[n + 1];
        for(int i = 0; i < n; i++){
            vierailtu[i] = 0;
        }
        vierailtu[1] = 1; 
        
        kmoBfRekursio(0, vierailtu, 1, 1, paras, n, verkko);
        
        return 1;
    }
    
}
