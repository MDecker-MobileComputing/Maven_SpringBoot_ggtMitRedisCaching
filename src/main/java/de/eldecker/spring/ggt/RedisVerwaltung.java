package de.eldecker.spring.ggt;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.stereotype.Component;


/**
 * Bean-Klasse für Verwaltung des Redis-Caches (löschen, vorbereitete Werte speichern).
 */
@Component
public class RedisVerwaltung {

    private final static Logger LOG = LoggerFactory.getLogger( RedisVerwaltung.class );
    
    
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
