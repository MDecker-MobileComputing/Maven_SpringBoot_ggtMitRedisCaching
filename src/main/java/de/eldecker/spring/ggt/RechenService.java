package de.eldecker.spring.ggt;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;


/**
 * Bean mit Geschäftslogik zur Berechnung des größten gemeinsamen Teilers (ggT) zweier Zahlen.
 */
@Service
public class RechenService {

    private final static Logger LOG = LoggerFactory.getLogger( RechenService.class );
    
    
    /**
     * Methode berechnet größten gemeinsamen Teiler (ggT) zweier Zahlen mit dem
     * <a href="https://studyflix.de/mathematik/euklidischer-algorithmus-4923">Euklidischen Algorithmus</a>.
     * <br><br>
     * 
     * Beispiel: ggT( 132, 28 ) = 4.
     * <br><br>
     * 
     * Diese Methode wird gecached (siehe Annotation {@code @Cacheable("ggtCache")}), deshalb ist auch
     * {@code @EnableCaching} in der Klasse {@code de.eldecker.spring.ggt.Application} notwendig.
     * <br><br>
     * 
     * Die erste der beiden als Argumente übergebenen Zahlen muss kleiner als die zweite Zahl sein,
     * damit im Cache keine symmetrischen Einträge angelegt werden wie z.B. {@code ggT( 2, 4 )} und 
     * {@code ggT( 4, 2 )}.
     * 
     * @param zahl1 Zahl 1 (muss kleiner als {@code zahl2} sein)
     * 
     * @param zahl2 Zahl 2
     * 
     * @return ggT von {@code zahl1} und {@code zahl2}
     */
    @Cacheable( value = "ggtCache", key = "#zahl1 + '-' + #zahl2" )
    public int berechneGGT( int zahl1, int zahl2 ) {
        
        // ggf negative Vorzeichen entfernen
        int a = Math.abs( zahl1 );
        int b = Math.abs( zahl2 );
        
        int anzIterationen = 0; 
        while ( b != 0 ) {
            
            int rest = a % b;
            a = b;
            b = rest;
            
            anzIterationen++;
        }
        
        LOG.info( "ggT von {} und {} berechnet: {} (Anzahl Iterationen: {})", 
                  zahl1, zahl2, a, anzIterationen );
        
        return a;
    }
    

    
}
