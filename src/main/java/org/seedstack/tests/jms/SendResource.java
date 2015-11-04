package org.seedstack.tests.jms;

import javax.inject.Inject;
import javax.jms.Destination;
import javax.jms.MessageProducer;
import javax.jms.Session;
import javax.jms.TextMessage;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.seedstack.seed.core.api.Configuration;
import org.seedstack.seed.jms.api.JmsConnection;
import org.seedstack.seed.transaction.api.Transactional;
import org.seedstack.tests.domains.jms.Payload;

import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * Resource to send informations with JMS.
 * 
 * @author thierry.bouvet@mpsa.com
 *
 */
@Path("informations")
public class SendResource {

    @Configuration("queue1.name")
    private String queueName;

    @Inject
    private Session session;

    /**
     * Send a JMS message to create a {@link Payload}.
     * 
     * @param pr
     *            payload to create
     * @return ok if message sent
     * @throws Exception
     *             if an error occurred (JMS communication error)
     */
    @POST
    @Path("send")
    @Produces(MediaType.TEXT_PLAIN)
    @Consumes(MediaType.APPLICATION_JSON)
    public String createPayload(PayloadRepresentation pr) throws Exception {
        sendMessage(pr);
        return "Message sent";
    }

    @Transactional
    @JmsConnection("connection-1")
    void sendMessage(PayloadRepresentation pr) throws Exception {
        ObjectMapper mapper = new ObjectMapper();

        Destination queue = session.createQueue(this.queueName);
        TextMessage message = session.createTextMessage();
        message.setText(mapper.writeValueAsString(pr));

        MessageProducer producer = session.createProducer(queue);
        producer.send(message);
    }

}
