package org.seedstack.tests.rest;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.seedstack.business.domain.Factory;
import org.seedstack.business.domain.Repository;
import org.seedstack.business.assembler.FluentAssembler;
import org.seedstack.business.assembler.ModelMapper;
import org.seedstack.jpa.Jpa;
import org.seedstack.jpa.JpaUnit;
import org.seedstack.seed.transaction.Transactional;
import org.seedstack.tests.domains.product.Product;

/**
 * REST Resource to create and load a {@link Product}.
 * @author thierry.bouvet@mpsa.com
 */
@Path("product")
public class ProductResource {

    @Jpa
    @Inject
    Repository<Product, Long> repository;
    
    @Inject
    Factory<Product> factory;

    @Inject
    FluentAssembler fluentAssembler ;
    
    /**
     * Initialize products.
     * @return String content
     */
    @GET
    @Path("init")
    @Transactional
    @JpaUnit("product-domain")
    @Produces(MediaType.TEXT_PLAIN)
    public String init() {
        final Long products = 10L;
        for (Long i = 0L; i < products; i++) {
            repository.persist(factory.create(i));
        }
        return "Products created";
    }
    
    /**
     * Load a product.
     * @param id the {@link Product} id to load.
     * @return the {@link ProductRepresentation} loaded.
     */
    @GET
    @Path("load/{id}")
    @Transactional
    @JpaUnit("product-domain")
    @Produces(MediaType.APPLICATION_JSON)
    public ProductRepresentation load(@PathParam("id") Long id) {
        return fluentAssembler.assemble(repository.load(id)).with(ModelMapper.class).to(ProductRepresentation.class);
    }

}
