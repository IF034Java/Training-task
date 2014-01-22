package utils;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

import liquibase.Liquibase;
import liquibase.database.DatabaseConnection;
import liquibase.database.jvm.JdbcConnection;
import liquibase.exception.LiquibaseException;
import liquibase.resource.FileSystemResourceAccessor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DbCreator {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(DbCreator.class);
	
	private static Liquibase liquibase = null;	
	
	private static DatabaseConnection initialDb(){
		Properties properties = new Properties();
		Connection connection = null;
		
		try {
			properties.load(DbCreator.class.getClassLoader().getResourceAsStream("jdbc-test.properties"));
		} catch (IOException e1) {			
			LOGGER.error("Problem with reading jdbc-test.properties"+e1.getMessage());					
		}		
		try {						
			connection = DriverManager.getConnection(properties.getProperty("dbUrl"), properties.getProperty("login"), properties.getProperty("password"));
		} catch (SQLException e) { 
			LOGGER.error("Problem with connecting to database"+e.getMessage());
		}		
		return new JdbcConnection(connection);									
	}
	
	public static void restoreDb(){
		try {
			liquibase = new Liquibase("dump.changelog.xml", new FileSystemResourceAccessor(), initialDb());			
			liquibase.dropAll();		
			liquibase.update("dev");					
		} catch (LiquibaseException e1) {			
			LOGGER.error("Liquibase exception"+e1.getMessage());
		}
	}
	
	public static void createSchemaDb(){
		try {
			liquibase = new Liquibase("schema.changelog.xml", new FileSystemResourceAccessor(), initialDb());			
			liquibase.dropAll();		
			liquibase.update("dev");					
		} catch (LiquibaseException e1) {			
			LOGGER.error("Liquibase exception"+e1.getMessage());
		}
	}
}

