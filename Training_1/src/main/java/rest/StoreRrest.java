package rest;

import dto.ProductDto;
import entity.Product;
import facade.StoreRestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Component
@Path("/Product")
public class StoreRrest {

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
    public Response addProduct(Product product) {
    	storeRestService.addProduct(product);
        return Response.status(Response.Status.CREATED).build();
    }

}