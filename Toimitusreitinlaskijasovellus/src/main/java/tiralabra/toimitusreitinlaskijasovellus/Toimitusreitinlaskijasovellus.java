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
            int[] vastausBF = null;
            if(toimituskohteet.length <= 15){
                vastausBF = KauppamatkustajaBruteForce.ratkaise(verkko);
            }
            int[] vastausDyn = KauppamatkustajaDynaaminen.ratkaise(verkko);
            int[] vastausHeur = KauppamatkustajaHeuristinen.ratkaise(verkko);
            
            
            
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
            map.put("start", toimituskohteet[vastausDyn[0]]);
            map.put("waypoints", waypoints);
            map.put("end", toimituskohteet[vastausDyn[vastausDyn.length-1]]);
            
            return new ModelAndView(map, "kartta");
        }, new ThymeleafTemplateEngine());
        
        
//        String[] kierros = {
//        "Aleksanterinkatu 52, 00100 Helsinki",
//        "Kulosaarentie 40, 00570 Helsinki",
//        "Itäkatu 1-7, 00930 Helsinki",
//        "Kivikonlaita 5, 00940 Helsinki",
//        "Ratavallintie 1, 00720 Helsinki",
//        "Kantelettarentie 1, 00420 Helsinki",
//        "Tietokuja 1, 00330 Helsinki"
//        };
//        
//        int[][] verkko = GoogleMaps.annaEtaisyysmatriisi(kierros);
//        int[] vastaus1 = KauppamatkustajaBruteForce.ratkaise(verkko);
//        int[] vastaus2 = KauppamatkustajaDynaaminen.ratkaise(verkko);
//        int[] vastaus3 = KauppamatkustajaHeuristinen.ratkaise(verkko);
//        
//        System.out.println("Reitin alku");
//        int i = 0;
//        while(i < vastaus1.length){
//            System.out.println(kierros[vastaus1[i]]);
//            ++i;
//        }
//        System.out.println("Reitin loppu");
//        System.out.println("------------------");
//        System.out.println("Reitin alku");
//        i = 0;
//        while(i < vastaus2.length){
//            System.out.println(kierros[vastaus2[i]]);
//            ++i;
//        }
//        System.out.println("Reitin loppu");
//        System.out.println("------------------");
//        System.out.println("Reitin alku");
//        i = 0;
//        while(i < vastaus3.length){
//            System.out.println(kierros[vastaus3[i]]);
//            ++i;
//        }
//        System.out.println("Reitin loppu");
        
    }
 
}