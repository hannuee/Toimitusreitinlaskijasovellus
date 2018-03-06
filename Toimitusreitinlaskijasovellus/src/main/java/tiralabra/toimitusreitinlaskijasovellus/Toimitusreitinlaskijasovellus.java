/**
 *@author Hannu Erälaukko
 */
package tiralabra.toimitusreitinlaskijasovellus;

import java.util.HashMap;  
import java.util.Set;
import java.util.ArrayList;

import spark.ModelAndView;
import static spark.Spark.*;
import spark.template.thymeleaf.ThymeleafTemplateEngine;

import tiralabra.algoritmit.*;
import tiralabra.karttapalvelut.GoogleMaps;

public class Toimitusreitinlaskijasovellus {
    
    public static void main(String[] args) throws Exception {
        
        // Pääsivu
        get("/", (req, res) -> {
            int[] numerot = {1,2,3};
            
            HashMap map = new HashMap<>();
            map.put("numerot", numerot);
            
            return new ModelAndView(map, "index");
        }, new ThymeleafTemplateEngine());
        
        
        post("/naytaSijainnitKartalla", (req, res) -> {
            //req.queryParams("poistettavaRaakaAine")
            
            Set<String> setti = req.queryParams();
            
            for(String mjono : setti){
                System.out.println(mjono);
            }

            System.out.println("" + req.queryParams("autocompletePiilossa1"));
            
            res.redirect("/");
            return "";
        });
        
        
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