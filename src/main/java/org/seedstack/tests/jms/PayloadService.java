package org.seedstack.tests.jms;

import org.seedstack.business.api.Service;
import org.seedstack.tests.domains.jms.Payload;

@Service
public interface PayloadService {

	void createPayload(Payload payload);
}
