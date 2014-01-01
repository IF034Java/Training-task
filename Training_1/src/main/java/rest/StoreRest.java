package rest;

import java.util.List;
import java.util.Properties;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.sun.corba.se.impl.logging.ORBUtilSystemException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.mail.MailException;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import dto.ProductDto;
import facade.StoreRestService;

@Component
@Path("/product")
public class StoreRest {

    @Autowired
    private StoreRestService storeRestService;

    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public ProductDto getProduct(@PathParam("id") String productId) {
        return storeRestService.getProduct(productId);
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<ProductDto> getAllProducts() {
        return storeRestService.getAllProducts();
    }

    @DELETE
    @Path("/{id}")
    public Response deleteProduct(@PathParam("id") String productId) {
    	storeRestService.deleteProduct(productId);
        return Response.status(Response.Status.OK).build();
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response addProduct(ProductDto productDto) {
    	Response.Status status = null;    	
    	if (storeRestService.addProduct(productDto) == null){
    		status = Response.Status.UNAUTHORIZED;
    	} else {
    		status =Response.Status.ACCEPTED;
    	}
        return Response.status(status).build();
    }

    @Bean
    private boolean mailSender(){
        boolean isSent = true;
        JavaMailSenderImpl javaMailSender = new JavaMailSenderImpl();
        javaMailSender.setHost("smtp.mail.ru");
        javaMailSender.setPort(465);
        javaMailSender.setUsername("kvm84@mail.ru");
        javaMailSender.setPassword("password");
        javaMailSender.setProtocol("smtps");
        Properties properties = new Properties();
        properties.setProperty("mail.smtps.auth", "true");
        properties.setProperty("mail.smtps.starttls.enable", "true");
        javaMailSender.setJavaMailProperties(properties);
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo("kvm84@mail.ru");
        message.setFrom("kvm84@mail.ru");
        message.setSubject("Subject");
        message.setText("Hello! This is my first message over Spring Mail");
        try{
            javaMailSender.send(message);
            System.out.println("Mail sent successful");
        } catch (MailException exception) {
            System.out.println("Some errors was found" + exception.getStackTrace());
            isSent = false;
        }
        return isSent;
    }

}