/**
 * @author Hannu Erälaukko
 */
package tiralabra.algoritmit;

public class KauppamatkustajaHeuristinen {
    
    /**
     * Selvittää lyhimmän reitin verkossa käyttäen hyväksi heuristista taktiikkaa.
     * Lisää jo rakennettuun reittiin aina solmun joka on lähimpänä jo rakennetun reitin
     * häntää tai päätä.
     * @param verkko Verkko matriisimuodossa.
     * @return reittiohje-taulukko.
     */
    public static int[] ratkaise(int[][] verkko){    // IDEA: wikistä, lähin naapuri taktiikka. IMPLEMENTAATIO: itse.
        // Erikoistapaukset:
        if(verkko.length == 1){
            return new int[]{0};
        }
        else if(verkko.length == 2){
            return new int[]{0,1,0};
        }
        
        final int solmuja = verkko.length;  // Solmujen määrä verkossa.
        
        
        int[] reittiOhjeet = new int[solmuja + 1];  // Heuristisesti lyhimmän reitin kulkemisohjeet, somusta 0 solmuun 0.
        reittiOhjeet[0] = 0;
        reittiOhjeet[solmuja] = 0;
        int seuraavaPaa = 1;
        int seuraavaHanta = solmuja - 1;
        
        
        boolean[] vierailtu = new boolean[solmuja];
        for(int i = 0; i < solmuja; ++i){
            vierailtu[i] = false;
        }
        vierailtu[0] = true;
        
        
        // Luuppi joka lisää solmuja reitin päähän tai häntään, kunnes kaikki solmut on lisätty reittiin.
        while(seuraavaPaa <= seuraavaHanta){
            
            
            // Päätä lähin ei-vierailtu solmu.
            int paataLahimmanEtaisyys = Integer.MAX_VALUE;
            int paataLahinSolmu = -1;
            
            int paaSolmu = reittiOhjeet[seuraavaPaa - 1];
            int i = 0;
            while(i < solmuja){  // Luuppi joka määrittää mikä solmu on pääsolmua lähimpänä.
                //verkko[paaSolmu][]
                if(!vierailtu[i] && verkko[paaSolmu][i] < paataLahimmanEtaisyys){
                    paataLahimmanEtaisyys = verkko[paaSolmu][i];
                    paataLahinSolmu = i;
                }
                ++i;
            }
            
            
            // Häntää lähin ei-vierailtu solmu, eli mistä solmusta pienin etäisyys häntään.
            int hantaaLahimmanEtaisyys = Integer.MAX_VALUE;
            int hantaaLahinSolmu = -1;
            
            int hantaSolmu = reittiOhjeet[seuraavaHanta + 1];
            i = 0;                                   
            while(i < solmuja){  // Luuppi joka määrittää mikä solmu on häntäsolmua lähimpänä.
                //verkko[paaSolmu][]
                if(!vierailtu[i] && verkko[i][hantaSolmu] < hantaaLahimmanEtaisyys){
                    hantaaLahimmanEtaisyys = verkko[i][hantaSolmu];
                    hantaaLahinSolmu = i;
                }
                ++i;
            }
            
            
            // Valitaan päätä ja häntää lähimmistä solmuista lähempi ja lisätään se reittiin.
            if(paataLahimmanEtaisyys < hantaaLahimmanEtaisyys){
                reittiOhjeet[seuraavaPaa] = paataLahinSolmu;
                ++seuraavaPaa;
                vierailtu[paataLahinSolmu] = true;
            } else{
                reittiOhjeet[seuraavaHanta] = hantaaLahinSolmu;
                --seuraavaHanta;
                vierailtu[hantaaLahinSolmu] = true;
            }
        }
        
        return reittiOhjeet;
    }
    
}
