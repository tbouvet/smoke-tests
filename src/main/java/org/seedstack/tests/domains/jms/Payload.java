package org.seedstack.tests.domains.jms;

import javax.persistence.Entity;
import javax.persistence.Id;

import org.seedstack.business.domain.BaseAggregateRoot;

/**
 * Payload used for JMS communication.
 * 
 * @author thierry.bouvet
 *
 */
@Entity
public class Payload extends BaseAggregateRoot<Long> {

    @Id
    private Long id;

    private String detail;

    protected Payload() {
    }

    public Payload(Long id) {
        super();
        this.id = id;
    }

    public Payload(Long id, String detail) {
        super();
        this.id = id;
        this.detail = detail;
    }

    @Override
    public Long getEntityId() {
        return this.id;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

}
