package org.seedstack.tests.rest;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.seedstack.business.api.domain.Factory;
import org.seedstack.business.api.domain.Repository;
import org.seedstack.business.api.interfaces.assembler.FluentAssembler;
import org.seedstack.business.api.interfaces.assembler.ModelMapper;
import org.seedstack.seed.persistence.jpa.api.Jpa;
import org.seedstack.seed.persistence.jpa.api.JpaUnit;
import org.seedstack.seed.transaction.api.Transactional;
import org.seedstack.tests.domains.client.Client;
import org.seedstack.tests.domains.product.Product;

/**
 * REST Resource to create and load a {@link Client}.
 * 
 * @author thierry.bouvet@mpsa.com
 */
@Path("client")
public class ClientResource {

	@Jpa
	@Inject
	Repository<Client, Long> repository;

	@Inject
	Factory<Client> factory;

	@Inject
	FluentAssembler fluentAssembler;

	/**
	 * Initialize products.
	 * 
	 * @return String content
	 */
	@GET
	@Path("init")
	@Transactional
	@JpaUnit("smoke-domain")
	@Produces(MediaType.TEXT_PLAIN)
	public String init() {
		final Long clients = 10L;
		for (Long i = 0L; i < clients; i++) {
			repository.persist(factory.create(i));
		}
		return "Clients created";
	}

	/**
	 * Load a product.
	 * 
	 * @param id
	 *            the {@link Product} id to load.
	 * @return the {@link ClientRepresentation} loaded.
	 */
	@GET
	@Path("load/{id}")
	@Transactional
	@JpaUnit("smoke-domain")
	@Produces(MediaType.APPLICATION_JSON)
	public ClientRepresentation load(@PathParam("id") Long id) {
		return fluentAssembler.assemble(repository.load(id)).with(ModelMapper.class).to(ClientRepresentation.class);
	}

}
