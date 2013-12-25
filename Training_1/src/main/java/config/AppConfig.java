package config;

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

import facade.BuyerRestService;
import facade.StoreRestService;
import rest.BuyerRest;
import rest.ProfitableClientsRest;
import rest.StoreRest;

@Configuration
@ImportResource({"/WEB-INF/data.xml", "classpath:META-INF/cxf/cxf.xml", "classpath:META-INF/cxf/cxf-servlet.xml"})
@ComponentScan(basePackages = {"facade.impl", "service.impl", "entity", "rest", "repo"})
public class AppConfig {
	
	@Autowired
	private BuyerRest buyerRest;
	
	@Autowired
	private ProfitableClientsRest profitableClientsRest;
	
	@Autowired
	private StoreRest storeRest;	
	
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
    public Server jaxRsServer(ApplicationContext appContext) {
        JAXRSServerFactoryBean factory = RuntimeDelegate.getInstance().createEndpoint(jaxRsApiApplication(), JAXRSServerFactoryBean.class);
        factory.setServiceBean(buyerRest);
        factory.setServiceBean(profitableClientsRest);
        factory.setServiceBean(storeRest);
        factory.setAddress("/rest"+ factory.getAddress());
        factory.setProvider(jsonProvider());
        return factory.create();
    }		
		
}
