/**
 * @author Hannu Erälaukko
 */
package tiralabra.algoritmit;

public class KauppamatkustajaDynaaminen {
    
    // Tämä toteutettu itse, ei sisältynyt pääalgoritmin lähteeseen.
    /**
     * Luo reittiohjetaulukon selvittämällä lyhyimmän reitin dynaamisen ohjelmoinnin taulukosta.
     * @param paras Dynaamisen ohjelmoinnin taulukko.
     * @param verkko Verkko jonka lyhintä reittiä ollaan selvittämässä.
     * @return reittiohje-taulukko.
     */
    private static int[] luoReittiOhjeet(int[][] paras, int[][] verkko){  
        // Reittiohjeiden selvittäminen Dynaamisen reittitaulukon "paras" avulla.
        // Lähdetään siis rakentamaan lyhimmän reitin ohjeita alkaen viimeisestä solmusta ennen loppusolmua
        // ja edeten tästä reitin alkuun.
       
        int solmuja = verkko.length;
        int[] reittiOhjeet = new int[solmuja - 1];
        
        int vieraillutSolmut = (1<<(solmuja - 1)) - 1;
        
        // Ensin valitaan viimeinen solmu lyhimmän matkan perusteella.
        
        int kokomatkanVastaus = Integer.MAX_VALUE;
        int kokomatkanViimeinen = 0;
        for(int viimeinen = 0; viimeinen < solmuja - 1; ++viimeinen){

            int kokomatka = paras[vieraillutSolmut][viimeinen] + verkko[viimeinen][solmuja - 1];

            if(kokomatka < kokomatkanVastaus){
                kokomatkanVastaus = kokomatka;
                kokomatkanViimeinen = viimeinen;
            }}

        // poistetaan viimeinen vierailluista.
        vieraillutSolmut = vieraillutSolmut ^ (1<<kokomatkanViimeinen);
        // Asetetaan viimeinen reittiOhjeet-taulukkoon.
        reittiOhjeet[solmuja - 2] = kokomatkanViimeinen;


        int alkumatkaJaMatkaSeuraavaan = kokomatkanVastaus - verkko[kokomatkanViimeinen][solmuja - 1];
        int seuraava = kokomatkanViimeinen;
            
        // Sitten etsitään aina se solmu(viimeinen) joka oli edellistä(seuraava) solmua ennen.
        // Se ei siis ole välttämättä se solmu joka on kyseisten vierailtujen solmujen
        // järjestyksistä lyhimmän matkan muodostama viimeinen-solmu. 
        
        for(int index = solmuja - 3; 0 <= index; --index){
        
            for(int viimeinen = 0; viimeinen < solmuja - 1; ++viimeinen){
                
                int alkumatka = paras[vieraillutSolmut][viimeinen];
                int matkaSeuraavaan = verkko[viimeinen][seuraava];

                if(alkumatkaJaMatkaSeuraavaan == alkumatka + matkaSeuraavaan){
                    alkumatkaJaMatkaSeuraavaan = alkumatka;
                    seuraava = viimeinen;
                    
                    // poistetaan viimeinen vierailluista.
                    vieraillutSolmut = vieraillutSolmut ^ (1<<viimeinen);
                    // Asetetaan viimeinen reittiOhjeet-taulukkoon.
                    reittiOhjeet[index] = viimeinen;
                    
                    break;
                }}}   
        
        return reittiOhjeet;
    }
    
    // Tämäkin toteutettu itse, ei sisältynyt pääalgoritmin lähteeseen.
    /**
     * Koska käytetty pääalgoritmi muodostaa lyhimmän reitin lähtien ja palaten
     * viimeiseisestä solmusta mutta halutaan lyhin reitti alkaen ja palaten
     * ensimmäisestä solmusta niin muutetaan reittiohjetaulukkoa.
     * @param reittiOhjeet reittiohje-taulukko.
     * @param verkko Verkko jonka lyhintä reittiä ollaan selvittämässä.
     * @return reittiohje-taulukko.
     */
    private static int[] muunnaReittiOhjeet(int[] reittiOhjeet, int[][] verkko){
        int solmuja = verkko.length;
        
        // Muutetaan reittiOhjeet lähtemään ja palaamaan solmusta 0.
        int copyIndex = 0;
        while(copyIndex < reittiOhjeet.length){  // Luuppi joka etsii solmun 0 paikan reittiOhjeissa.
            if(reittiOhjeet[copyIndex] == 0){
                break;
            }
            ++copyIndex;
        }
        
        int[] uudetReittiOhjeet = new int[reittiOhjeet.length + 2];
        
        int pasteIndex = 0;
        while(pasteIndex < uudetReittiOhjeet.length){ 
            
            uudetReittiOhjeet[pasteIndex] = reittiOhjeet[copyIndex];
            
            ++copyIndex;
            ++pasteIndex;
            
            if(copyIndex == reittiOhjeet.length && pasteIndex != uudetReittiOhjeet.length){
                copyIndex = 0;
                uudetReittiOhjeet[pasteIndex] = solmuja - 1;
                ++pasteIndex;
            }
        }
        
        return uudetReittiOhjeet;
    }
    
    // Kommentointi vielä hieman kesken.
    // Otettu mallia:
    // https://www.quora.com/Are-there-any-good-examples-of-the-Held-Karp-algorithm-in-C++-Hard-to-find-example-code-to-solve-the-traveling-salesman-problem-Everyone-wants-to-just-talk-about-theory-and-not-show-how-to-actually-do-it-What-is-the-big-secret
    /**
     * Selvittää lyhimmän reitin symmetrisessä verkossa käyttäen hyväksi dynaamista ohjelmointia.
     * @param verkko Verkko matriisimuodossa.
     * @return reittiohje-taulukko.
     */
    public static int[] ratkaise(int[][] verkko){
        // Erikoistapaukset:
        if(verkko.length == 1){
            return new int[]{0};
        }
        else if(verkko.length == 2){
            return new int[]{0,1,0};
        }
        
        final int solmuja = verkko.length;  // Solmujen määrä verkossa.
        
        int[][] paras = new int[1<<(solmuja - 1)][solmuja];  // Dynaamisen ohjelmoinnin taulukko.
        int i = 0;
        while(i < (1<<(solmuja - 1))){
            int i2 = 0;
            while(i2 < solmuja){
                paras[i][i2] = Integer.MAX_VALUE;
                ++i2;
            }
            ++i;
        }
        
        // Dynaamisen ohjelmoinnin luuppi.
        for(int vierailtu = 1; vierailtu < (1<<(solmuja - 1)); ++vierailtu){
            for(int viimeinen = 0; viimeinen < (solmuja - 1); ++viimeinen){
                
                if( (vierailtu & (1<<viimeinen)) == 0 ){
                    continue;
                }
                
                if(vierailtu == (1<<viimeinen)){
                    paras[vierailtu][viimeinen] = verkko[solmuja - 1][viimeinen];
                } else {
                    int aiemminVierailtu = vierailtu ^ (1<<viimeinen);
                    for(int aiempi = 0; aiempi < solmuja - 1; ++aiempi){
                        if((aiemminVierailtu & (1<<aiempi)) == 0){
                            continue;
                        }
                        paras[vierailtu][viimeinen] = Math.min(
                                paras[vierailtu][viimeinen], 
                                verkko[viimeinen][aiempi] + paras[aiemminVierailtu][aiempi]);
                    }
                }
                
            }
        }
        
        return muunnaReittiOhjeet(luoReittiOhjeet(paras, verkko), verkko);
    }
    
}