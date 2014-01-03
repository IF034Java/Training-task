package config;

import java.util.Properties;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;
import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;
import javax.ws.rs.ext.RuntimeDelegate;

import org.apache.cxf.bus.spring.SpringBus;
import org.apache.cxf.endpoint.Server;
import org.apache.cxf.jaxrs.JAXRSServerFactoryBean;
import org.codehaus.jackson.jaxrs.JacksonJsonProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.context.annotation.ImportResource;
import org.springframework.dao.annotation.PersistenceExceptionTranslationPostProcessor;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.context.AbstractSecurityWebApplicationInitializer;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.annotation.TransactionManagementConfigurer;

import rest.BuyerRest;
import rest.ProfitableClientsRest;
import rest.StoreRest;

@Configuration
@ImportResource({"/WEB-INF/data.xml"})
@ComponentScan(basePackages = {"facade.impl", "service.impl", "entity",  "repo"})
//@EnableJpaRepositories("repo")
//@EnableTransactionManagement
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class AppConfig extends WebSecurityConfigurerAdapter{		
	
	@Override	
	protected void configure(HttpSecurity http) throws Exception {
		http.authorizeRequests().antMatchers("/rest/product/**").hasRole("USER")				
		.and().formLogin()
		.and().authorizeRequests().antMatchers("/rest/*").hasRole("MANAGER")		
		.and().formLogin();							 
	}
	
	@Override
    @Autowired
    public void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth
            .jdbcAuthentication()
                .dataSource(dataSource())
                .usersByUsernameQuery("select name,password,enabled from user where name = ?")
        
        .authoritiesByUsernameQuery("select username,authority from authorities where username = ?").rolePrefix("ROLE_");
    }
	
	@Bean
	public BuyerRest buyerRest(){
		return new BuyerRest();
	}
	
	@Bean
	public ProfitableClientsRest profitableClientsRest(){
		return new ProfitableClientsRest();
	}
	
	@Bean
	public StoreRest storeRest(){
		return new StoreRest();
	}		
	
	@ApplicationPath(value = "/")
    public class JaxRsApiApplication extends Application { }
	
	@Bean
    public JaxRsApiApplication jaxRsApiApplication() {
        return new JaxRsApiApplication();
    }
	
	@Bean
	JacksonJsonProvider jsonProvider(){		
		return (new JacksonJsonProvider());
	}
	
	@Bean
	AnnotationConfigApplicationContext annotationConfigApplicationContext(){
		return (new AnnotationConfigApplicationContext());
	}
	
	@Bean(destroyMethod = "shutdown")
    public SpringBus cxf() {
        return new SpringBus();
    }
	
	@Bean
	@DependsOn("cxf")
    public Server jaxRsServer() {
        JAXRSServerFactoryBean factory = RuntimeDelegate.getInstance().createEndpoint(jaxRsApiApplication(), JAXRSServerFactoryBean.class);
        factory.setServiceBean(buyerRest());
        factory.setServiceBean(profitableClientsRest());
        factory.setServiceBean(storeRest());
        factory.setAddress("/rest"+ factory.getAddress());
        factory.setProvider(jsonProvider());
        return factory.create();
    }
	
	@Bean
	public DataSource dataSource(){
      DriverManagerDataSource dataSource = new DriverManagerDataSource();
      dataSource.setDriverClassName("com.mysql.jdbc.Driver");
      dataSource.setUrl("jdbc:mysql://localhost:3306/training1");
      dataSource.setUsername( "training" );
      dataSource.setPassword( "training" );
      return dataSource;
	}
	
	/*@Bean
	public PersistenceExceptionTranslationPostProcessor exceptionTranslation(){
		return new PersistenceExceptionTranslationPostProcessor();
	}
	
	private Properties additionalProperties(){
		Properties properties = new Properties();
		properties.setProperty("hibernate.hbm2ddl.auto", "validate");
		properties.setProperty("hibernate.dialect", "org.hibernate.dialect.MySQL5InnoDBDialect");
		
		return properties;
	}
	
	@Bean
	   public LocalContainerEntityManagerFactoryBean entityManagerFactory() {
	      LocalContainerEntityManagerFactoryBean entityManagerFactoryBean = new LocalContainerEntityManagerFactoryBean();
	      entityManagerFactoryBean.setDataSource(dataSource());
	      entityManagerFactoryBean.setPackagesToScan("entity");
	      JpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
	      entityManagerFactoryBean.setJpaVendorAdapter(vendorAdapter);
	      entityManagerFactoryBean.setJpaProperties(additionalProperties());
	 
	      return entityManagerFactoryBean;
	   }
	
	@Bean
	   public PlatformTransactionManager transactionManager(EntityManagerFactory emf){
	      JpaTransactionManager transactionManager = new JpaTransactionManager();
	      transactionManager.setEntityManagerFactory(emf);
	 
	      return transactionManager;
	   }*/
	    		
}
