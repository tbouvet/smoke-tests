package org.seedstack.tests.rest;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.seedstack.business.api.domain.Repository;
import org.seedstack.business.api.interfaces.assembler.FluentAssembler;
import org.seedstack.business.api.interfaces.assembler.ModelMapper;
import org.seedstack.seed.persistence.jpa.api.Jpa;
import org.seedstack.seed.persistence.jpa.api.JpaUnit;
import org.seedstack.seed.transaction.api.Transactional;
import org.seedstack.tests.domains.client.Client;
import org.seedstack.tests.domains.jms.Payload;
import org.seedstack.tests.jms.PayloadRepresentation;

/**
 * REST Resource to create and load a {@link Client}.
 * 
 * @author thierry.bouvet@mpsa.com
 */
@Path("payload")
public class PayloadResource {

	@Jpa
	@Inject
	Repository<Payload, Long> repository;

	@Inject
	FluentAssembler fluentAssembler;

	/**
	 * Load a payload.
	 * 
	 * @param id
	 *            the {@link Payload} id to load.
	 * @return the {@link PayloadRepresentation} loaded.
	 */
	@GET
	@Path("load/{id}")
	@Transactional
	@JpaUnit("jms-domain")
	@Produces(MediaType.APPLICATION_JSON)
	public PayloadRepresentation load(@PathParam("id") Long id) {
		return fluentAssembler.assemble(repository.load(id)).with(ModelMapper.class).to(PayloadRepresentation.class);
	}

}
