/**
 *@author Hannu Erälaukko
 */
package tiralabra.toimitusreitinlaskijasovellus;

import java.util.HashMap;

import spark.ModelAndView;
import static spark.Spark.*;
import spark.template.thymeleaf.ThymeleafTemplateEngine;

import tiralabra.algoritmit.*;
import tiralabra.karttapalvelut.GoogleMaps;

public class Toimitusreitinlaskijasovellus {
    
    static final int[] KENTTIEN_NUMEROT = {0,1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19}; 
    
    public static void main(String[] args) throws Exception {
        
        
        // Pääsivu.
        get("/", (req, res) -> {
            HashMap map = new HashMap<>();
            map.put("numerot", KENTTIEN_NUMEROT);
            map.put("naytaReitti", false);
            
            return new ModelAndView(map, "index");
        }, new ThymeleafTemplateEngine());
        
        
        // Käyttäjän lähettämän lomakkeen käsittely.
        post("/naytaSijainnitKartalla", (req, res) -> {
            
            // Lasketaan montako toimituskohdetta käyttäjä on syöttänyt.
            int montakoKenttaa = 0;
            for(int kentanNumero : KENTTIEN_NUMEROT){
                String kentanSisalto = req.queryParams("autocompletePiilossa" + kentanNumero);
                if(!kentanSisalto.isEmpty()){
                    ++montakoKenttaa;
                }
            }
            
            // Otetaan toimituskohteet talteen oikean kokoiseen taulukkoon.
            String[] toimituskohteet = new String[montakoKenttaa];
            int index = 0;
            for(int kentanNumero : KENTTIEN_NUMEROT){
                String kentanSisalto = req.queryParams("autocompletePiilossa" + kentanNumero);
                if(!kentanSisalto.isEmpty()){
                    toimituskohteet[index] = kentanSisalto;
                    ++index;
                }
            }
            
            // Pyydetään Google Distance Matrix API:lta etäisyys jokaisen toimituskohdeparin välille.
            int[][] verkko = GoogleMaps.annaEtaisyysmatriisi(toimituskohteet);

            
            // Selvitetään lyhin toimitusreitti 3 eri tavalla.
            // (BruteForcea ei käytetä jos toimituskohteita on enemmän kuin 15
            //  koska algoritmi käy suurilla aineistoilla niin raskaaksi.)
            // Selvitetään samalla hieman statistiikkaa algoritmien suoriutumisesta.
            int[] vastausBF = null;
            String kestoBF = "";
            if(toimituskohteet.length <= 15){
                long alkuBF = System.nanoTime();
                vastausBF = KauppamatkustajaBruteForce.ratkaise(verkko);
                long loppuBF = System.nanoTime();
                kestoBF = "BruteForce: " + ((loppuBF - alkuBF)/1000000000.0) + " sekuntia.";
            }
            
            long alkuDyn = System.nanoTime();
            int[] vastausDyn = KauppamatkustajaDynaaminen.ratkaise(verkko);
            long loppuDyn = System.nanoTime();
            String kestoDyn = "Dynaaminen: " + ((loppuDyn - alkuDyn)/1000000000.0) + " sekuntia.";
            
            long alkuHeur = System.nanoTime();
            int[] vastausHeur = KauppamatkustajaHeuristinen.ratkaise(verkko);
            long loppuHeur = System.nanoTime();
            String kestoHeur = "Heuristinen: " + ((loppuHeur - alkuHeur)/1000000000.0) + " sekuntia.";
            
            String BFvsDyn = "";
            String DynVsHeur = "";
            String DynVsHeurPituus = "";
            if(vastausBF != null){
                if(ReittienVertailija.ratkaise(vastausBF, vastausDyn)){
                    BFvsDyn = "BruteForce ja Dynaaminen antoivat odotetusti saman vastauksen.";
                    
                    if(ReittienVertailija.ratkaise(vastausDyn, vastausHeur)){
                        DynVsHeur = "Myös Heuristinen antoi saman vastauksen."; 
                    } else {
                        DynVsHeur = "Heuristinen antoi eri vastauksen.";
                        DynVsHeurPituus = "Heuristisen antaman reitin ajallinen pituus on " + 
                                (( (1.0*Reitinpituus.ratkaise(verkko, vastausHeur)) / (1.0*Reitinpituus.ratkaise(verkko, vastausDyn))) * 100.00) +
                                "% verrattuna BruteForcen ja Dynaamisen antaman reitin pituuteen.";
                    }
                } else {
                    BFvsDyn = "Jokin meni pieleen, BruteForce ja Dynaaminen antoivat eri vastaukset.";
                }
            } else {
                if(ReittienVertailija.ratkaise(vastausDyn, vastausHeur)){
                    DynVsHeur = "Heuristinen antoi saman vastauksen."; 
                } else {
                    DynVsHeur = "Heuristinen antoi eri vastauksen.";
                    DynVsHeurPituus = "Heuristisen antaman reitin ajallinen pituus on " + 
                            (( (1.0*Reitinpituus.ratkaise(verkko, vastausHeur)) / (1.0*Reitinpituus.ratkaise(verkko, vastausDyn))) * 100.00) +
                            "% verrattuna Dynaamisen antamaan reitin pituuteen.";
                }
            }
            
            String[] kaikkiVertailutYhdessa = {kestoBF, kestoDyn, kestoHeur, BFvsDyn, DynVsHeur, DynVsHeurPituus};
            
            
            
            // Näytetään dynaamisen vastaus:
            String[] waypoints = new String[vastausDyn.length - 2];
            int indexVastaus = 1;
            int indexWaypoints = 0;
            while(indexWaypoints < waypoints.length){
                waypoints[indexWaypoints] = toimituskohteet[vastausDyn[indexVastaus]];
                ++indexVastaus;
                ++indexWaypoints;
            }
            
            
            HashMap map = new HashMap<>();
            map.put("numerot", KENTTIEN_NUMEROT);
            map.put("naytaReitti", true);
            map.put("start", toimituskohteet[vastausDyn[0]]);
            map.put("waypoints", waypoints);
            map.put("end", toimituskohteet[vastausDyn[vastausDyn.length-1]]);
            map.put("statistiikat", kaikkiVertailutYhdessa);
            
            return new ModelAndView(map, "index");
        }, new ThymeleafTemplateEngine());
        
    }
 
}