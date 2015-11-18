/**
 * Copyright (c) 2013-2015 by The SeedStack authors. All rights reserved.
 *
 * This file is part of SeedStack, An enterprise-oriented full development stack.
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
/*
 * Creation : 30 juil. 2013
 */
package org.seedstack.tests.domains;

import javax.inject.Inject;

import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.seedstack.business.domain.Factory;
import org.seedstack.business.domain.Repository;
import org.seedstack.seed.Logging;
import org.seedstack.seed.it.SeedITRunner;
import org.seedstack.jpa.Jpa;
import org.seedstack.jpa.JpaUnit;
import org.seedstack.seed.transaction.Transactional;
import org.seedstack.tests.domains.client.Client;
import org.slf4j.Logger;

@RunWith(SeedITRunner.class)
public class ClientJpaRepositoryIT {
	@Jpa
	@Inject
	private Repository<Client, Long> clientRepository;
	@Inject
	private Factory<Client> clientFactory;

	@Logging
	private static Logger logger;

	private Client createClient() {
		final long id = 1L;
		Client client = clientFactory.create(id);
		Assertions.assertThat(client.getEntityId()).isEqualTo(id);
		clientRepository.persist(client);
		;
		Assertions.assertThat(client.getEntityId()).isEqualTo(id);
		return client;
	}

	@Test
	@Transactional
	@JpaUnit("smoke-domain")
	public void checkProductResource() {
		Client client = createClient();
		clientRepository.delete(client);

		client = clientRepository.load(client.getEntityId());
		Assertions.assertThat(client).isNull();
	}
}
