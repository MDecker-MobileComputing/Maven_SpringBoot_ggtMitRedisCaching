package de.eldecker.spring.ggt;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;


/**
 * Einstiegspunkt der Programmausführung.
 */
@SpringBootApplication
@EnableCaching
public class GgtBerechnungApplication {

	public static void main( String[] args ) {

		SpringApplication.run( GgtBerechnungApplication.class, args );
	}

}
