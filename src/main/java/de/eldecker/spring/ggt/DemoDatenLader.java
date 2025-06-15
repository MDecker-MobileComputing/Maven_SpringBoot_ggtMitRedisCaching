package de.eldecker.spring.ggt;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;


/**
 * Diese Bean lädt beim Programmstart die Ergebnisse einiger 
 * ggT-Berechnungen in den Cache.
 */
@Component
public class DemoDatenLader implements ApplicationRunner {

    private final static Logger LOG = LoggerFactory.getLogger( DemoDatenLader.class );
    
    /** Bean für Steuerung von Redis-Cache. */
    private RedisVerwaltung _redisVerwaltung;

    
    /**
     * Konstruktor für Dependency Injection.
     */
    @Autowired
    public DemoDatenLader( RedisVerwaltung redisVerwaltung ) {

        _redisVerwaltung = redisVerwaltung;
    }
    
    
    /**
     * Methode lädt nach Start der Anwendung einige Demodaten in den ggT-Cache. 
     * 
     * @param args Wird nicht ausgewertet
     */
    @Override
    public void run( ApplicationArguments args ) throws Exception {

        _redisVerwaltung.ggtSpeichern( 1, 2, 1 );
        _redisVerwaltung.ggtSpeichern( 2, 4, 2 );
        
        LOG.info( "Demo-Daten in Cache geladen." );
    }
    
}
