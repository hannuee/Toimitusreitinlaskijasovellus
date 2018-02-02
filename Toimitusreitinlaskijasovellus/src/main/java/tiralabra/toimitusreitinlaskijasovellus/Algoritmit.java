/**
 *@author Hannu Erälaukko
 */
package tiralabra.toimitusreitinlaskijasovellus;

import java.util.HashSet;

public class Algoritmit {
    
    /**
     * Tämä metodi on apumetodi metodille kauppamatkustajaBruteForce.
     * Tämä metodi käy läpi kaikki reittivaihtoehdot rekursiivisesti   KUNHAN EI OLE JO PITEMPI.
     * ja laittaa muistiin lyhimmän löytämänsä reitin.
     * @param pituus Tähänastisen reitin pituus
     * @param vierailtu Missä solmuissa on jo käyty ja milloin.
     * @param nykyinen Missä solmussa ollaan nyt.
     * @param k Monessako solmussa on jo vierailtu.
     * @param paras Lyhimmän reitin pituus.
     * @param parasReitti Lyhimmän reitin kulkemisohjeet.
     * @param solmuja Solmujen määrä verkossa.
     * @param verkko Verkko matriisimuodossa.
     * @see #kauppamatkustajaBruteForce(int[][] verkko)
     */
    private static void kmBFrekursio(int pituus, int[] vierailtu, int nykyinen, int k, int[] paras, int[] parasReitti, final int solmuja, int[][] verkko){
        // JOS ollaan vierailtu kaikissa solmuissa NIIN tämä rekursiohaara loppuu.
        if(k == solmuja){  
            // JA JOS reitti on tähänastisista reiteistä lyhin huomioiden matka solmuun 1 
            // NIIN laitetaan reitti muistiin.
            if(pituus + verkko[nykyinen][0] < paras[0]){  
                paras[0] = pituus + verkko[nykyinen][0];
                // Kopioidaan paras tähänastinen reitti muistiin:
                for(int i = 0; i < solmuja; i++){
                    parasReitti[i] = vierailtu[i];
                }
            }
            return;
        }
        
        // Luuppi joka haarauttaa rekursion. Nykyisestä solmusta(nykyinen) mennään jokaiseen 
        // ei-vierailtuun solmuun kunhan reitti ei silloin olisi jo lyhintä reittiä pitempi.
        for(int i = 1; i < solmuja; i++){
            if(vierailtu[i] == 0 && pituus + verkko[nykyinen][i] < paras[0]){  
                int[] vierailtu2 = vierailtu.clone();  // Kopioidaan vierailtu-taulukko.
                vierailtu2[i] = k + 1;  // KOMMENTOI!!!!
                kmBFrekursio(pituus + verkko[nykyinen][i], vierailtu2, i, k + 1, paras, parasReitti, solmuja, verkko);
            }
        }
    }
    
    
    // Pitkälti TIRA kalvon 390 mukaan, mutta muokattu ominaisuus joka muistaa lyhimmän reitin pituuden lisäki myös itse reitin.
    // Toteutettu tämä pitämällä kirjaa vierailluista solmuista vierailtu-taulukon avulla joka muutettu booleanista intiksi,
    // ja jossa pidetään kirjaa milloin mihinkin solmuun on menty.
    // Kun läpikäyty reitti huomataan lyhimmäksi siihenastiseksi reitiksi niin kopioidaan vierailtu-taulukon sisältö parasReitti-taulukkoon.
    /**
     * Palautta lyhimmän reitin joka käy verkon kaikki solmut läpi, alkaen solmusta 1 ja loppuen solmuun 1.
     * Lyhin reitti lasketaan käymällä läpi kaikki reittimahdollisuudet jotka alkavat solmusta 1 ja loppuvat solmuun 1.
     * Optimoinnit: ei katsota loppuun jos selvää että reitistä ei tule lyhintä     !!!!!!!!!!!
     * @param verkko Verkko matriisimuodossa. 
     * (Ulomman taulukon indexi kertoo mistä solmusta kaari lähtee, 
     * sisemmän taulukon indexi kertoo mihin solmuun kaari menee,
     * sisemmän taulukon indexiä vastaava arvo kertoo kuinka pitkä kyseinen kaari on.)     etäisyysmatriisi!!!!!!!!!!!
     * @return       
     */
    public static int kauppamatkustajaBruteForce(int[][] verkko){
        final int solmuja = verkko.length;  // Solmujen määrä verkossa.

        int[] paras = {Integer.MAX_VALUE};  // Lyhimmän reitin pituus.  (selitys miksi taulukko)
        int[] parasReitti = new int[solmuja];  // Lyhimmän reitin kulkemisohjeet.

        // Taulukko joka kertoo missä solmuissa on jo käyty ja milloin.
        // Taulukon indexi kertoo solmun, 
        // taulukon indexiä vastaava arvo kertoo milloin solmussa on vierailtu.
        // (0=ei vierailtu, 1=vierailtu ekana, 2=vierailtu tokana, ...)
        int[] vierailtu = new int[solmuja];
        // Alustetaan vierailtu-taulukko.
        for(int i = 0; i < solmuja; i++){
            vierailtu[i] = 0;
        }
        vierailtu[0] = 1;
        
        // Käydään rekursion avulla läpi reittivaihtoehdot.
        kmBFrekursio(0, vierailtu, 0, 1, paras, parasReitti, solmuja, verkko);
        
        return paras[0];  // PALAUTUS!!!!!!!!
    }
    
    
    public static int kauppamatkustajaDynaaminen(int[][] verkko){
        
        
        return 0;
    }
    
    
    // erikoistapaukset??
    public static int[] kauppamatkustajaHeuristinen(int[][] verkko){    // IDEA: wikistä, lähin naapuri taktiikka. IMPLEMENTAATIO: itse.
        final int solmuja = verkko.length;  // Solmujen määrä verkossa.
        
        int[] parasReitti = new int[solmuja + 1];  // Lyhimmän reitin kulkemisohjeet, somusta 0 solmuun 0.
        parasReitti[0] = 0;
        parasReitti[solmuja] = 0;
        int seuraavaPaa = 1;
        int seuraavaHanta = solmuja - 1;
        
        HashSet<Integer> vierailtu = new HashSet<>(solmuja);
        vierailtu.add(0);
        
        // Luuppi joka lisää solmuja reitin päähän tai häntään, kunnes kaikki solmut on lisätty reittiin.
        while(seuraavaPaa <= seuraavaHanta){
            
            
            // Päätä lähin ei-vierailtu solmu.
            int paataLahimmanEtaisyys = Integer.MAX_VALUE;
            int paataLahinSolmu = -1;
            
            int paaSolmu = parasReitti[seuraavaPaa - 1];
            int i = 0;
            while(i < solmuja){  // Luuppi joka määrittää mikä solmu on pääsolmua lähimpänä.
                //verkko[paaSolmu][]
                if(!vierailtu.contains(i) && verkko[paaSolmu][i] < paataLahimmanEtaisyys){
                    paataLahimmanEtaisyys = verkko[paaSolmu][i];
                    paataLahinSolmu = i;
                }
                ++i;
            }
            
            
            // Häntää lähin ei-vierailtu solmu, eli mistä solmusta pienin etäisyys häntään.
            int hantaaLahimmanEtaisyys = Integer.MAX_VALUE;
            int hantaaLahinSolmu = -1;
            
            int hantaSolmu = parasReitti[seuraavaHanta + 1];
            i = 0;                                   
            while(i < solmuja){  // Luuppi joka määrittää mikä solmu on häntäsolmua lähimpänä.
                //verkko[paaSolmu][]
                if(!vierailtu.contains(i) && verkko[i][hantaSolmu] < hantaaLahimmanEtaisyys){
                    hantaaLahimmanEtaisyys = verkko[i][hantaSolmu];
                    hantaaLahinSolmu = i;
                }
                ++i;
            }
            
            
            // Valitaan päätä ja häntää lähimmistä solmuista lähempi ja lisätään se reittiin.
            if(paataLahimmanEtaisyys < hantaaLahimmanEtaisyys){
                parasReitti[seuraavaPaa] = paataLahinSolmu;
                ++seuraavaPaa;
            } else{
                parasReitti[seuraavaHanta] = hantaaLahinSolmu;
                --seuraavaHanta;
            }
        }
        
        return parasReitti;
    }
    
}
