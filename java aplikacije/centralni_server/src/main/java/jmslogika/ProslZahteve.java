/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jmslogika;

import javax.annotation.Resource;
import javax.jms.*;
import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonReader;
import java.io.StringReader;

/**
 *
 * @author Milica Bjelovuk
 */

public class ProslZahteve {
    @Resource(lookup = "myConnFactory")
    private ConnectionFactory connectionFactory;

    @Resource(lookup = "podsistem1q")
    private Queue q1;

    @Resource(lookup = "podsistem2q")
    private Queue q2;

    @Resource(lookup = "podsistem3q")
    private Queue q3;

    @Resource(lookup = "responseq")
    private Queue rq;
    
    public JsonObject proslediZahtev(String zahtev, JsonObject podaci, int podsistem) {
        try (
            JMSContext context = connectionFactory.createContext()) {
            JMSProducer producer = context.createProducer();
            JMSConsumer consumer = context.createConsumer(rq);

            JsonObject poruka = Json.createObjectBuilder()
                    .add("zahtev", zahtev)
                    .add("podaci", podaci != null ? podaci : Json.createObjectBuilder().build())
                    .build();

            String p = poruka.toString();
            TextMessage msg = context.createTextMessage(p);

            switch (podsistem) {
                case 1: 
                    producer.send(q1, msg); break;
                case 2: 
                    producer.send(q2, msg); break;
                case 3: 
                    producer.send(q3, msg); break;
            }

            TextMessage odg = (TextMessage) consumer.receive(5000);
            if (odg == null) {
                return Json.createObjectBuilder()
                        .add("status", 500)
                        .add("poruka", "podsistem zakazao")
                        .build();
            }

            try (
                JsonReader jr = Json.createReader(new StringReader(odg.getText()))) {
                return jr.readObject();
            }

        } catch (Exception ex) {
            ex.printStackTrace();
            return Json.createObjectBuilder()
                    .add("status", 500)
                    .add("poruka", "greska u jms: " + ex.getMessage())
                    .build();
        }
    }
    
}
