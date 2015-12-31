package org.exoplatform.crud.service;


import org.exoplatform.common.http.HTTPStatus;
import org.exoplatform.crud.entity.Product;
import org.exoplatform.services.rest.resource.ResourceContainer;
import org.json.JSONArray;
import org.json.JSONObject;

import javax.annotation.security.RolesAllowed;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;
import java.text.ParseException;
import java.util.List;

/**
 * @author <a href="mailto:obouras@exoplatform.com">Omar Bouras</a>
 * @version ${Revision}
 * @date 08/09/15
 */
@Path("/productdata")
public class ProductsRest implements ResourceContainer {
    GenericDAO genericDAO;
    public ProductsRest(){
        genericDAO=new GenericDAO<Product>(Product.class);

    }

    @RolesAllowed("users")
    @POST
    @Path("submit")
    @Consumes({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public Response editProduct(Product product)   {
        Product productBean = null;
        if(product.getId() != 0){
             productBean = (Product) genericDAO.findById(product.getId());
            if (productBean != null) {
                productBean.setCategory(product.getCategory());
                productBean.setPrice(product.getPrice());
                productBean.setLabel(product.getLabel());
                productBean.setCompany(product.getCompany());

            }
        }

        if( productBean== null && product != null){
            genericDAO.persist(product);
        }
        else if( productBean != null ){
            genericDAO.merge(productBean);
        }
        return getProducts();
    }

    @RolesAllowed("users")
    @POST
    @Path("delete")
    @Consumes({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public Response deleteProduct(String productId)   {
        if (productId != null){
            Product product = (Product) genericDAO.findById(Integer.valueOf(productId));
            genericDAO.delete(product);
        }

        return getProducts();
    }



    @RolesAllowed("users")
    @GET
    @Path("productlist")
    @Produces("application/json")
    public Response getProducts(/*@Context SecurityContext sc*/) {
        JSONObject jsonGlobal = new JSONObject();
        JSONArray jsonArray = new JSONArray();
        try {
            //mockFillProducts();
            List<Product> products=genericDAO.findAll();
            for(Product product:products){
                JSONObject json = new JSONObject();
                json.put("id",product.getId());
                json.put("category",product.getCategory());
                json.put("company",product.getCompany());
                json.put("label",product.getLabel());
                json.put("price",product.getPrice());
                jsonArray.put(json);
            }
            //String userId = sc.getUserPrincipal().getName();
            return Response.ok(jsonArray.toString(), MediaType.APPLICATION_JSON).build();
        } catch(Exception e) {
            return Response.status(HTTPStatus.INTERNAL_ERROR).build();
        }
    }



    public void mockFillProducts(){
        for(int i=0;i<10;i++){
            Product product=new Product();
            product.setLabel("Product "+i);
            product.setCompany("Company "+i);
            product.setCategory("Category "+i);
            product.setPrice(65.2);
            genericDAO.persist(product);
        }
    }


}
