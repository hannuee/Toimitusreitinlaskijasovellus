/**
 *@author Hannu Erälaukko
 */
package tiralabra.toimitusreitinlaskijasovellus;

import com.google.maps.*;
import com.google.maps.model.*;

import tiralabra.algoritmit.*;

public class Toimitusreitinlaskijasovellus {
    
    public static void main(String[] args) throws Exception{
        
        // Todella suppea demo, karttademo tulossa ensiviikolla.
        
        int[] vastaus = KauppamatkustajaDynaaminen.ratkaise(
            new int[][]{        
            {0, 96, 1, 5, 69},
            {96, 0, 47, 1, 11},
            {1, 47, 0, 77, 3},
            {5, 1, 77, 0, 99},
            {69, 11, 3, 99, 0}});
        
        System.out.println("Reitin alku");
        for(int solmu : vastaus){
            System.out.println("" + solmu);
        }
        System.out.println("Reitin loppu");
    }
    
//            // Google Distance Matrix API
//        String[] origins = {"Tampere, Helsinki, Turku"};
//        String[] destinations = {"Tampere, Helsinki, Turku"};
//        
//        GeoApiContext con = new GeoApiContext.Builder()
//                .apiKey("AIzaSyDhVYlGKXdpop9BB5nSrZ-cUNVf_SpNR50")
//                .build();
//        
//        DistanceMatrix matrix = DistanceMatrixApi.newRequest(con)
//                .origins(origins)
//                .destinations(destinations)
//                .mode(TravelMode.DRIVING)
//                .language("fi")
//                .units(Unit.METRIC)
//                .await();
//        
//        // Käydään tulos läpi ja tulostetaan.
//        for(DistanceMatrixRow row : matrix.rows){
//            for(DistanceMatrixElement element : row.elements){
//                System.out.println("Aika: " + element.duration);
//                System.out.println("Aika: " + element.distance);
//            }
//        }
    
}