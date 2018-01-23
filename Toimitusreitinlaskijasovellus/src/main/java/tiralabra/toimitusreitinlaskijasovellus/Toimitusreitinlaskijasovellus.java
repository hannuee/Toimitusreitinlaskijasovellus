/**
 *
 * @author Hannu
 */
package tiralabra.toimitusreitinlaskijasovellus;

import com.google.maps.*;
import com.google.maps.model.*;

public class Toimitusreitinlaskijasovellus {
    
    public static void main(String[] args) throws Exception{
        
        // Matrix API
        
        String[] origins = {"Hämeenkatu 1, Tampere"};
        String[] destinations = {"Meiramikatu, Tampere"};
        
        GeoApiContext con = new GeoApiContext.Builder()
                .apiKey("AIzaSyCuVksfwxjzJnb7uDzNPwTCIY4NZZDv_T0")
                .build();
        
        DistanceMatrix matrix = DistanceMatrixApi.newRequest(con)
                .origins(origins)
                .destinations(destinations)
                .mode(TravelMode.DRIVING)
                .language("fi")
                .units(Unit.METRIC)
                .await();
        
        // Käydään tulos läpi ja tulostetaan.
        for(DistanceMatrixRow row : matrix.rows){
            for(DistanceMatrixElement element : row.elements){
                System.out.println("Aika: " + element.duration);
                System.out.println("Aika: " + element.distance);
            }
        }
        
    }
    
}
