package springapp.mvc.REST;

import org.springframework.stereotype.Component;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Component
@Path("/")
public class DefaultREST {
	
	@GET
	@Produces(MediaType.TEXT_HTML)
	public String getClient(){
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append("<html><title>Hello</title><body><h3>");
        stringBuffer.append("HI");
        stringBuffer.append("</body></h3></html>");
		
		return stringBuffer.toString();
}
}