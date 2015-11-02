package org.seedstack.tests.jms;

import javax.inject.Inject;

import org.seedstack.business.api.domain.Repository;
import org.seedstack.seed.core.api.SeedException;
import org.seedstack.seed.persistence.jpa.api.Jpa;
import org.seedstack.seed.persistence.jpa.api.JpaUnit;
import org.seedstack.seed.transaction.api.Transactional;
import org.seedstack.tests.domains.jms.Payload;
import org.seedstack.tests.errors.ApplicationErrorCode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PayloadServiceImpl implements PayloadService {

	private static final Logger LOGGER = LoggerFactory.getLogger(PayloadServiceImpl.class);

	@Jpa
	@Inject
	Repository<Payload, Long> repository;

	@Transactional
	@JpaUnit("smoke-domain")
	@Override
	public void createPayload(Payload payload) {
		LOGGER.info("Create a new payload #{}", payload.getEntityId());
		try {
			repository.persist(payload);
		} catch (Exception e) {
			throw SeedException.wrap(e, ApplicationErrorCode.PAYLOAD_SAVE_ERROR).put("id", payload.getEntityId())
			        .put("detail", payload.getDetail());
		}
	}

}
