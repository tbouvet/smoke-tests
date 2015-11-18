package org.seedstack.tests.domains.client;

import javax.persistence.Entity;
import javax.persistence.Id;

import org.seedstack.business.domain.BaseAggregateRoot;

/**
 * Created on 02/07/2015.
 * @author thierry.bouvet@mpsa.com
 */
@Entity
public class Client extends BaseAggregateRoot<Long> {

    @Id
    private Long clientId;

    private String name;

    protected Client() {
    }

    public Client(Long clientId) {
        this.clientId = clientId;
    }

    @Override
    public Long getEntityId() {
        return clientId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


}
