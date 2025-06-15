package de.eldecker.spring.ggt;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
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
     * 
     * @param zahl1 Zahl 1
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
    
    
    /**
     * Methode um Demo-Daten/vorberechntete ggT-Werte in den Cache zu speichern.
     * <br><br>
     * 
     * Beispielwert für Cache-Key: 
     * <pre>
     * meine_app::ggtCache::1-2
     * </pre>
     * <br>
     * Das Prefix {@code meine_app} ist in der Datei {@code application.properties} 
     * mit dem Parameter {@code spring.cache.redis.key-prefix} definiert.
     * 
     * @param zahl1 Zahl 1
     * 
     * @param zahl2 Zahl 2
     * 
     * @return ggT von {@code zahl1} und {@code zahl2}
     */
    @CachePut( value = "ggtCache", key = "#zahl1 + '-' + #zahl2" )
    public int ggtSpeichern( int zahl1, int zahl2, int ggt ) {
        
        return ggt;
    }
    
    
    /**
     * Alle Einträge im Cache löschen.
     */
    @CacheEvict( value = "ggtCache", allEntries = true )
    public void cacheLoeschen() {

        LOG.info( "Cache für ggT-Werte geleert." );
    }
    
}
