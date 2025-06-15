package de.eldecker.spring.ggt;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;


/**
 * Controller für Thymeleaf-Templates.
 */
@Controller
@RequestMapping( "/app/v1" )
public class ThymeleafController {

    private final static Logger LOG = LoggerFactory.getLogger( ThymeleafController.class );
    
    /** Bean mit Geschäftslogik für Berechnung. */
    private RechenService _rechenService;

    /** Bean für Steuerung von Redis-Cache. */
    private RedisVerwaltung _redisVerwaltung;
    
    
    /**
     * Konstruktor für Dependency Injection.
     */
    @Autowired
    public ThymeleafController( RechenService rechenService,
                                RedisVerwaltung redisVerwaltung ) {

        _rechenService   = rechenService;
        _redisVerwaltung = redisVerwaltung;
    }
    
    
    /**
     * Berechnung ggT (größter gemeinsamer Teiler) durchführen.
     * 
     * @param model Model-Objekt, das die Platzhalterwerte für das Thymeleaf-Template enthält
     * 
     * @param zahl1 Erste Zahl
     * 
     * @param zahl2 Zweite Zahl
     * 
     * @return Name des Thymeleaf-Templates, das die Ergebnisse anzeigt
     */
    @GetMapping( "/ggt-berechnen" )
    public String ggtBerechnen( Model model,
                                @RequestParam( value = "zahl_1", required = true ) int zahl1,
                                @RequestParam( value = "zahl_2", required = true ) int zahl2 
                              ) {
              
        if ( zahl1 > zahl2 ) {

            int temp = zahl1;
            zahl1    = zahl2;
            zahl2    = temp;
        }
        
        final int ggt = _rechenService.berechneGGT( zahl1, zahl2 );
        
        model.addAttribute( "zahl_1", zahl1 )
             .addAttribute( "zahl_2", zahl2 )
             .addAttribute( "ggt"   , ggt   );
        
        LOG.info( "Anfrage fuer ggT von {} und {} beantwortet mit {}.", zahl1, zahl2, ggt );
        
        return "ergebnis";
    }
    
    
    /**
     * Alle Einträge in Cache löschen.
     * 
     * @return Name des Thymeleaf-Templates für Erfolgsmeldung bei Löschen.
     */
    @GetMapping( "/cache-loeschen" )
    public String cacheLoeschen() {
        
        _redisVerwaltung.cacheLoeschen();

        return "geloescht";
    }
    
}
