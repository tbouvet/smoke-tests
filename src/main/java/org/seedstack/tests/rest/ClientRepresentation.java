/**
 * Copyright (c) 2013-2015 by The SeedStack authors. All rights reserved.
 *
 * This file is part of SeedStack, An enterprise-oriented full development stack.
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package org.seedstack.tests.rest;

import org.seedstack.business.assembler.DtoOf;
import org.seedstack.business.assembler.MatchingEntityId;
import org.seedstack.business.assembler.MatchingFactoryParameter;
import org.seedstack.tests.domains.client.Client;

/**
 * Representation for a {@link Client}.
 * @author thierry.bouvet@mpsa.com
 */
@DtoOf(value=Client.class)
public class ClientRepresentation {
    private Long id;
    private String name;

    @MatchingEntityId
    @MatchingFactoryParameter(index = 0)
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @MatchingFactoryParameter(index = 1)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
