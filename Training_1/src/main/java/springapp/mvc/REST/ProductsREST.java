package springapp.mvc.REST;

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
import org.springframework.stereotype.Component;

import springapp.mvc.entity.Products;
import springapp.mvc.service.ProductsService;

@Component
@Path("/Products")
public class ProductsREST {

    @Autowired
    private ProductsService productsService;

    @GET
    @Path("/getOne/{id}")
    @Produces(MediaType.TEXT_HTML)
    public String getClient(@PathParam("id") String productId) {
        Products product = productsService.getProduct(Integer.valueOf(productId));
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append("<html><title>Hello</title><body><h3>");
        stringBuffer.append(product.getId());
        stringBuffer.append(" ");
        stringBuffer.append(product.getName());
        stringBuffer.append(" ");
        stringBuffer.append(product.getExpirationDate());
        stringBuffer.append(" ");
        stringBuffer.append(product.getPrice());
        stringBuffer.append("</body></h3></html>");

        return stringBuffer.toString();
    }

    @GET
    @Path("/getAll")
    @Produces(MediaType.TEXT_HTML)
    public String getAllClients() {
        List<Products> allProducts = productsService.getAllProducts();
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append("<html><title>Hello</title><body><h3>");
        for (Products products : allProducts) {
            stringBuffer.append(products.getId());
            stringBuffer.append(" ");
            stringBuffer.append(products.getName());
            stringBuffer.append(" ");
            stringBuffer.append(products.getExpirationDate());
            stringBuffer.append(" ");
            stringBuffer.append(products.getPrice());
            stringBuffer.append(" ");
            stringBuffer.append("<p>");
        }
        stringBuffer.append("</body></h3></html>");

        return stringBuffer.toString();
    }

    @DELETE
    @Path("/delete/{id}")
    @Produces(MediaType.TEXT_HTML)
    public String deleteClient(@PathParam("id") String productId){
        productsService.deleteProduct(Integer.valueOf(productId));
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append("<html><title>Hello</title><body><h3>");
        stringBuffer.append(" ");
        stringBuffer.append("Delete success");
        stringBuffer.append("</body></h3></html>");

        return stringBuffer.toString();
    }

    @POST
    @Path("/add")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response addClient(Products product){

        productsService.addProduct(product);
        String result = "Product saved : " + product.getName();
        return Response.status(201).entity(result).build();
    }

}