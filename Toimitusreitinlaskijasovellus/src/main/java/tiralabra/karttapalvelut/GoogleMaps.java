/**
 * @author Hannu Erälaukko
 */
package tiralabra.karttapalvelut;

import com.google.maps.*;
import com.google.maps.model.*;

public class GoogleMaps {
    
    private static int etaisyys(String mista, String mihin) throws Exception {
        // Google Distance Matrix API
        String[] origins = {mista};
        String[] destinations = {mihin};
        
        GeoApiContext con = new GeoApiContext.Builder()
                .apiKey("AIzaSyDhVYlGKXdpop9BB5nSrZ-cUNVf_SpNR50")
                .build();
        
        DistanceMatrix matrix = DistanceMatrixApi.newRequest(con)
                .origins(origins)
                .destinations(destinations)
                .mode(TravelMode.DRIVING)
                .language("fi")
                .units(Unit.METRIC)
                .await();
        
        return (int)matrix.rows[0].elements[0].duration.inSeconds;
    }
    
    public static int[][] annaEtaisyysmatriisi(String[] solmut) throws Exception {
        int solmuja = solmut.length;
        
        // Luodaan ja alustetaan etäisyysmatriisi.
        int[][] verkko = new int[solmuja][solmuja];
        for(int index = 0; index < solmuja; ++index){
            for(int index2 = 0; index2 < solmuja; ++index2){
                verkko[index][index2] = -1;
            }
        }
        
        int i = 0;
        while(i < solmuja){
            int i2 = 0;
            while(i2 < solmuja){
                if(i == i2){  // Koska etäisyys jostakin solmusta itseensä on 0.
                    verkko[i][i2] = 0;
                } else if(verkko[i][i2] == -1){
                    int et = etaisyys(solmut[i], solmut[i2]);
                    verkko[i][i2] = et;
                    verkko[i2][i] = et;
                }
                ++i2;
            }
            ++i;
        }
        
        return verkko;
    }
    
}
