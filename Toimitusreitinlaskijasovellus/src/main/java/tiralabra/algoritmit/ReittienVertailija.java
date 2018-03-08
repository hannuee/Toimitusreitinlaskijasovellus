/**
 * @author Hannu Erälaukko
 */
package tiralabra.algoritmit;

public class ReittienVertailija {
     
    /**
     * Tarkistaa ovatko annetut taulukot täsmälleen samanlaisia tai toistensa peilikuvia
     * vai ovatko taulukot täysin erilaisia.
     * @param taulukko1
     * @param taulukko2
     * @return Palauttaa <code>true</code> jos annetut taulukot ovat täsmälleen samanlaisia tai toistensa peilikuvia,
     * muuten <code>false</code>.
     */
    public static boolean ratkaise(int[] taulukko1, int[] taulukko2){
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
    
}
