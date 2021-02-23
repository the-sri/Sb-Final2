package com.sri.eGameScoreAPI;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@ComponentScan("com.sri.eGameScoreAPI")
@SpringBootApplication
public class App 
{
    public static void main( String[] args )
    {		
    	SpringApplication.run(App.class, args);	
    	//Uses eGameDB as the database, as developed by Sri
    	System.out.println( "--------------------------------------------------------- ---------------------------------" );
    	System.out.println( "-----------------Successfully Launched the eGameScoreAPI ! ---------------------------------" );
    	System.out.println( "--------------------------------------------------------- ---------------------------------" );

    }
}
