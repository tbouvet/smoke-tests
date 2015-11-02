package org.seedstack.tests.jms;

import javax.inject.Inject;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;

import org.seedstack.business.api.domain.Repository;
import org.seedstack.business.api.interfaces.assembler.FluentAssembler;
import org.seedstack.business.api.interfaces.assembler.ModelMapper;
import org.seedstack.seed.jms.api.JmsMessageListener;
import org.seedstack.seed.persistence.jpa.api.Jpa;
import org.seedstack.tests.domains.jms.Payload;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.databind.ObjectMapper;

@JmsMessageListener(connection = "connection-2", destinationTypeStr = "QUEUE", destinationName = "${queue1.name}")
public class MessageReceiver implements MessageListener {

	private static final Logger LOGGER = LoggerFactory.getLogger(MessageReceiver.class);

	@Jpa
	@Inject
	Repository<Payload, Long> repository;

	@Inject
	FluentAssembler fluentAssembler;

	@Inject
	PayloadService payloadService;

	@Override
	public void onMessage(Message message) {
		if (message instanceof TextMessage) {
			TextMessage textMessage = (TextMessage) message;
			try {
				LOGGER.info("Received message: " + textMessage.getText());
				createPayload(textMessage.getText());
			} catch (JMSException e) {
				LOGGER.error("JMS Exception received: ", e);
			} catch (Exception e) {
				LOGGER.error("Exception received: ", e);
				throw new RuntimeException(e);
			}
		}
	}

	void createPayload(String payloadMessage) throws Exception {
		ObjectMapper mapper = new ObjectMapper();
		PayloadRepresentation payloadRepresentation = mapper.readValue(payloadMessage, PayloadRepresentation.class);
		Payload payload = fluentAssembler.merge(payloadRepresentation).with(ModelMapper.class).into(Payload.class)
		        .fromFactory();
		// createPayload(payload);
		payloadService.createPayload(payload);
	}

	// @Transactional
	// @JpaUnit("smoke-domain")
	// public void createPayload(Payload payload) {
	// LOGGER.info("Create a new payload #{}", payload.getEntityId());
	// try {
	// repository.persist(payload);
	// } catch (Exception e) {
	// throw SeedException.wrap(e,
	// ApplicationErrorCode.PAYLOAD_SAVE_ERROR).put("id", payload.getEntityId())
	// .put("detail", payload.getDetail());
	// }
	// }

}
