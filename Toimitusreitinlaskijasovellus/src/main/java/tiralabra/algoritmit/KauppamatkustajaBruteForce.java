/**
 * @author Hannu Erälaukko
 */
package tiralabra.algoritmit;

public class KauppamatkustajaBruteForce {
    
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
    public static int[] ratkaise(int[][] verkko){
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
        
        return reittiOhjeet;
    }
    
}
