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
    

    private RechenService _rechenService;

    
    /**
     * Konstruktor für Dependency Injection.
     */
    @Autowired
    public DemoDatenLader( RechenService rechenService ) {

        _rechenService = rechenService;
    }
    
    
    /**
     * Methode lädt nach Start der Anwendung einige Demodaten in den ggT-Cache. 
     */
    @Override
    public void run( ApplicationArguments args ) throws Exception {

        _rechenService.ggtSpeichern( 1, 2, 1 );
        _rechenService.ggtSpeichern( 2, 4, 2 );
        
        LOG.info( "Demo-Daten in Cache geladen." );
    }
    
}
