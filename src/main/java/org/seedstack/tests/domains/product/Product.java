/**
 * Copyright (c) 2013-2015 by The SeedStack authors. All rights reserved.
 *
 * This file is part of SeedStack, An enterprise-oriented full development stack.
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package org.seedstack.tests.domains.product;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

import org.seedstack.business.domain.BaseAggregateRoot;

/**
 * Product aggregate root.
 * @author thierry.bouvet@mpsa.com
 */
@Entity
public class Product extends BaseAggregateRoot<Long> {

    @Id
    private Long entityId;
    private String designation;
    @Column(length=500)
    private String summary;
    @Column(length=10000)
    private String details;
    private String picture;
    private Double price;

    /**
     * Constructor are in visibility package because only Factories can create
     * aggregates and entities.
     * <p/>
     * Factories are in the same package so he can access package visibility.
     */
    protected Product() {
    }
    
    public Product(Long entityId) {
        super();
        this.entityId = entityId;
    }


    @Override
    public Long getEntityId() {
        return entityId;
    }


	/**
	 * Gets the designation.
	 * 
	 * @return the designation
	 */
	public String getDesignation() {
		return designation;
	}

	/**
	 * Sets the designation.
	 * 
	 * @param designation the designation to set
	 */
	public void setDesignation(String designation) {
		this.designation = designation;
	}

	/**
	 * Gets the summary.
	 * 
	 * @return the summary
	 */
	public String getSummary() {
		return summary;
	}

	/**
	 * Sets the summary.
	 * 
	 * @param summary the summary to set
	 */
	public void setSummary(String summary) {
		this.summary = summary;
	}

	/**
	 * Gets the details.
	 * 
	 * @return the details
	 */
	public String getDetails() {
		return details;
	}

	/**
	 * Sets the details.
	 * 
	 * @param details the details to set
	 */
	public void setDetails(String details) {
		this.details = details;
	}

	/**
	 * Gets the picture.
	 * 
	 * @return the picture
	 */
	public String getPicture() {
		return picture;
	}

	/**
	 * Sets the picture.
	 * 
	 * @param picture the picture to set
	 */
	public void setPicture(String picture) {
		this.picture = picture;
	}

	/**
	 * Gets the price.
	 * 
	 * @return the price
	 */
	public Double getPrice() {
		return price;
	}

	/**
	 * Sets the price.
	 * 
	 * @param price the price to set
	 */
	public void setPrice(Double price) {
		this.price = price;
	}

}
