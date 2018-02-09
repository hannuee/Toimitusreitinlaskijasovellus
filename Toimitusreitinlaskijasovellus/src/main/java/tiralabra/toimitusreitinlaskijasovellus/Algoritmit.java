/**
 *@author Hannu Erälaukko
 */
package tiralabra.toimitusreitinlaskijasovellus;

import java.util.HashSet;

public class Algoritmit {
    
    /**
     * Tämä metodi on apumetodi metodille kauppamatkustajaBruteForce.
     * Tämä metodi käy läpi kaikki reittivaihtoehdot rekursiivisesti
     * (paitsi ei niitä reittejä joista tulee varmasti pitempiä kuin siihenastisesta löydetystä lyhimmästä reitistä)
     * ja laittaa muistiin lyhimmän löytämänsä reitin.
     * @param pituus Tähänastisen reitin pituus
     * @param vierailtu Missä solmuissa on jo käyty ja milloin.
     * @param nykyinen Missä solmussa ollaan nyt.
     * @param monessako Monessako solmussa on jo vierailtu.
     * @param paras Lyhimmän reitin pituus.
     * @param parasReitti Lyhimmän löydetyn reitin vierailtu-taulukko.
     * @param solmuja Solmujen määrä verkossa.
     * @param verkko Verkko matriisimuodossa.
     * @see #kauppamatkustajaBruteForce(int[][] verkko)
     */
    private static void kmBFrekursio(int pituus, int[] vierailtu, int nykyinen, int monessako, 
                                     int[] paras, int[] parasReitti, final int solmuja, int[][] verkko){
        if(monessako == solmuja){  // JOS ollaan vierailtu kaikissa solmuissa NIIN tämä rekursiohaara loppuu. 
            if(pituus + verkko[nykyinen][0] < paras[0]){  // JA JOS reitti on tähänastisista reiteistä lyhin huomioiden matka solmuun 1
                paras[0] = pituus + verkko[nykyinen][0];  // NIIN laitetaan reitti muistiin.
                for(int i = 0; i < solmuja; i++){  // Kopioidaan paras tähänastinen reitti muistiin:
                    parasReitti[i] = vierailtu[i];
                }
            }
            return;
        }
        
        // Luuppi joka haarauttaa rekursion.
        for(int i = 1; i < solmuja; i++){
            // Nykyisestä solmusta(nykyinen) mennään jokaiseen ei-vierailtuun solmuun 
            // kunhan reitti ei silloin olisi jo lyhintä reittiä pitempi.
            if(vierailtu[i] == 0 && pituus + verkko[nykyinen][i] < paras[0]){    
                int[] vierailtu2 = vierailtu.clone();  // Kopioidaan vierailtu-taulukko uutta rekursiohaaraa varten.
                vierailtu2[i] = monessako + 1;  // Merkitään seuraava vierailtava solmu vierailluksi vierailujärjestysnumerolla. 
                kmBFrekursio(pituus + verkko[nykyinen][i], vierailtu2, i, monessako + 1, 
                             paras, parasReitti, solmuja, verkko);
            }
        }
    }
    
    // Pitkälti TIRA kalvon 390 mukaan, mutta muokattu ominaisuus joka muistaa lyhimmän reitin pituuden lisäki myös itse reitin.
    // Toteutettu tämä pitämällä kirjaa vierailluista solmuista vierailtu-taulukon avulla joka muutettu booleanista intiksi,
    // ja jossa pidetään kirjaa milloin mihinkin solmuun on menty.
    // Kun läpikäyty reitti huomataan lyhimmäksi siihenastiseksi reitiksi niin kopioidaan vierailtu-taulukon sisältö parasReitti-taulukkoon.
    /**
     * Palautta lyhimmän reitin joka käy verkon kaikki solmut läpi, alkaen solmusta 0 ja loppuen solmuun 0.
     * Lyhin reitti lasketaan käymällä läpi kaikki reittimahdollisuudet jotka alkavat solmusta 0 ja loppuvat solmuun 0.
     * Poikkeuksena reitit joista tulee varmasti pitempiä kuin siihenastisesta löydetystä lyhimmästä reitistä.
     * @param verkko Verkko matriisimuodossa. 
     * (Ulomman taulukon indexi kertoo mistä solmusta kaari lähtee, 
     * sisemmän taulukon indexi kertoo mihin solmuun kaari menee,
     * sisemmän taulukon indexiä vastaava arvo kertoo kuinka pitkä kyseinen suunnattu kaari on.)
     * @return Taulukko jossa ensimmäinen alkio kertoo lähtösolmun ja loput taulukon alkiot
     * kertovat solmun mihin taulukon edellisen alkion viittaamasta solmusta tulee edetä.
     */
    // Palauttaa toistaiseksi testausta varten vain parhaan reitin pituuden.
    public static int kauppamatkustajaBruteForce(int[][] verkko){
        final int solmuja = verkko.length;  // Solmujen määrä verkossa.

        int[] paras = {Integer.MAX_VALUE};  // Lyhimmän reitin pituus.
        int[] parasReitti = new int[solmuja];  // Lyhimmän löydetyn reitin vierailtu-taulukko.

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
        kmBFrekursio(0, vierailtu, 0, 1, 
                     paras, parasReitti, solmuja, verkko);
        
        // Luodaan reittiOhjetaulukko parasReitti-taulukon avulla.
        int[] reittiOhjeet = new int[solmuja + 1];
        int i = 0;
        while(i < solmuja){
            reittiOhjeet[parasReitti[i] - 1] = i;
            ++i;
        }
        reittiOhjeet[solmuja] = 0;  // Lisätään loppuun vielä ohje lähtösolmuun paluusta.
        
        return paras[0];
    }
    
    
    // Otettu mallia:
    // https://www.quora.com/Are-there-any-good-examples-of-the-Held-Karp-algorithm-in-C++-Hard-to-find-example-code-to-solve-the-traveling-salesman-problem-Everyone-wants-to-just-talk-about-theory-and-not-show-how-to-actually-do-it-What-is-the-big-secret
    // Palauttaa toistaiseksi testausta varten vain parhaan reitin pituuden.
    public static int kauppamatkustajaDynaaminen(int[][] verkko){
        final int solmuja = verkko.length;  // Solmujen määrä verkossa.
        
        int[][] paras = new int[1<<(solmuja - 1)][solmuja];
        int i = 0;
        while(i < (1<<(solmuja - 1))){
            int i2 = 0;
            while(i2 < solmuja){
                paras[i][i2] = Integer.MAX_VALUE;
                ++i2;
            }
            ++i;
        }
        
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
        
        int vastaus = Integer.MAX_VALUE;
        for(int viimeinen = 0; viimeinen < solmuja - 1; ++viimeinen){
            vastaus = Math.min(vastaus, verkko[viimeinen][solmuja - 1] + paras[(1<<(solmuja - 1)) - 1][viimeinen]);
        }
        
        return vastaus;  
    }
    
    
    public static int[] kauppamatkustajaHeuristinen(int[][] verkko){    // IDEA: wikistä, lähin naapuri taktiikka. IMPLEMENTAATIO: itse.
        final int solmuja = verkko.length;  // Solmujen määrä verkossa.
        
        int[] reittiOhjeet = new int[solmuja + 1];  // Heuristisesti lyhimmän reitin kulkemisohjeet, somusta 0 solmuun 0.
        reittiOhjeet[0] = 0;
        reittiOhjeet[solmuja] = 0;
        int seuraavaPaa = 1;
        int seuraavaHanta = solmuja - 1;
        
        HashSet<Integer> vierailtu = new HashSet<>(solmuja);
        vierailtu.add(0);
        
        // Luuppi joka lisää solmuja reitin päähän tai häntään, kunnes kaikki solmut on lisätty reittiin.
        while(seuraavaPaa <= seuraavaHanta){
            
            
            // Päätä lähin ei-vierailtu solmu.
            int paataLahimmanEtaisyys = Integer.MAX_VALUE;
            int paataLahinSolmu = -1;
            
            int paaSolmu = reittiOhjeet[seuraavaPaa - 1];
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
            
            int hantaSolmu = reittiOhjeet[seuraavaHanta + 1];
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
                reittiOhjeet[seuraavaPaa] = paataLahinSolmu;
                ++seuraavaPaa;
            } else{
                reittiOhjeet[seuraavaHanta] = hantaaLahinSolmu;
                --seuraavaHanta;
            }
        }
        
        return reittiOhjeet;
    }
    
}
