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
import org.seedstack.jpa.JpaUnit;
import org.seedstack.seed.transaction.Transactional;
import org.seedstack.tests.domains.product.Product;
import org.slf4j.Logger;

@RunWith(SeedITRunner.class)
public class ProductJpaRepositoryIT {
    @Inject
    private Repository<Product, Long> productRepository;
    @Inject
    private Factory<Product> productFactory;

    @Logging
    private static Logger logger;

    private Product createProduct() {
        final long id = 1L;
        Product product = productFactory.create(id);
        Assertions.assertThat(product.getEntityId()).isEqualTo(id);
        productRepository.persist(product);;
        Assertions.assertThat(product.getEntityId()).isEqualTo(id);
        return product;
    }

    @Test
    @Transactional
    @JpaUnit("product-domain")
    public void checkProductResource() {
        Product product = createProduct();
        productRepository.delete(product);;
        product = productRepository.load(product.getEntityId());
        Assertions.assertThat(product).isNull();
    }
}
