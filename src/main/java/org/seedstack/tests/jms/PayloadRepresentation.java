package org.seedstack.tests.jms;

import org.seedstack.business.api.interfaces.assembler.DtoOf;
import org.seedstack.business.api.interfaces.assembler.MatchingEntityId;
import org.seedstack.business.api.interfaces.assembler.MatchingFactoryParameter;
import org.seedstack.tests.domains.jms.Payload;

@DtoOf(value = Payload.class)
public class PayloadRepresentation {

	private Long id;

	private String detail;

	@MatchingEntityId
	@MatchingFactoryParameter(index = 0)
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@MatchingFactoryParameter(index = 1)
	public String getDetail() {
		return detail;
	}

	public void setDetail(String detail) {
		this.detail = detail;
	}

}
