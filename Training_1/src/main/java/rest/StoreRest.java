package rest;

import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.springframework.beans.factory.annotation.Autowired;

import dto.ProductDto;
import facade.StoreRestServiceFacade;

@Path("/product")
public class StoreRest {

    @Autowired
    private StoreRestServiceFacade storeRestServiceFacade;

    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public ProductDto getProduct(@PathParam("id") String productId) {
        return storeRestServiceFacade.getProduct(productId);
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<ProductDto> getAllProducts() {
        return storeRestServiceFacade.getAllProducts();
    }

    @DELETE
    @Path("/{id}")
    public Response deleteProduct(@PathParam("id") String productId) {
    	storeRestServiceFacade.deleteProduct(productId);
        return Response.status(Response.Status.OK).build();
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response addProduct(ProductDto productDto) {
    	Response.Status status = null;    	
    	if (storeRestServiceFacade.addProduct(productDto) == null){
    		status = Response.Status.UNAUTHORIZED;
    	} else {
    		status =Response.Status.ACCEPTED;
    	}
        return Response.status(status).build();
    }

}