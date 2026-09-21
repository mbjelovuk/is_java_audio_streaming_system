/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package podsistem_1;

import entiteti1.Korisnik;
import entiteti1.Mesto;
import javax.annotation.Resource;
import javax.jms.*;
import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonReader;
import javax.json.JsonArrayBuilder;
import java.io.StringReader;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Milica Bjelovuk
 */

public class Main1 {
    @Resource(lookup="myConnFactory")
    private static ConnectionFactory connectionFactory;

    @Resource(lookup="podsistem1q")
    private static Queue queue;
    
    @Resource(lookup="responseq")
    private static Queue rqueue;

    public static void main(String[] args) {
        JMSContext context = connectionFactory.createContext();
        JMSConsumer consumer = context.createConsumer(queue);
        JMSProducer producer = context.createProducer();

        InterfejsBaze1 baza = new InterfejsBaze1();

        while (true) {
            try {
                TextMessage msg = (TextMessage) consumer.receive();
                if (msg == null) {
                    System.out.println("nevalidna poruka");
                    continue;
                }
                
                String body = ((TextMessage) msg).getText();
                JsonReader jr = Json.createReader(new StringReader(body));
                JsonObject jo = jr.readObject();
                jr.close();
                String zahtev = jo.getString("zahtev", "");
                JsonObject response;

                switch (zahtev) {
                    case "CREATE_MESTO":
                        JsonObject grad = jo.getJsonObject("podaci");
                        Mesto m = new Mesto();
                        m.setNaziv(grad.getString("naziv"));
                        boolean okM = (baza.createMesto(m)!=null);
                        response = Json.createObjectBuilder()
                                .add("zahtev", zahtev)
                                .add("status", okM ? 200 : 400)
                                .add("idMesto", m.getIdMesto() != null ? m.getIdMesto() : -1)
                                .build();
                        break;
                    case "GET_ALL_MESTA":
                        List<Mesto> mesta = baza.getAllMesta();
                        JsonArrayBuilder mArr = Json.createArrayBuilder();
                        for (Mesto mm : mesta) {
                            mArr.add(Json.createObjectBuilder()
                                    .add("id", mm.getIdMesto())
                                    .add("naziv", mm.getNaziv()));
                        }
                        response = Json.createObjectBuilder()
                                .add("zahtev", zahtev)
                                .add("status", 200)
                                .add("mesta", mArr)
                                .build();
                        break;
                    case "CREATE_KORISNIK":
                        JsonObject korisnik = jo.getJsonObject("podaci");
                        Korisnik k = new Korisnik();
                        k.setIme(korisnik.getString("ime"));
                        k.setEmail(korisnik.getString("email"));
                        k.setGodiste(korisnik.getInt("godiste"));
                        k.setPol(korisnik.getString("pol"));
                        Integer idMesto = null;
                        if (korisnik.containsKey("idMesto") && !korisnik.isNull("idMesto")) {
                            idMesto = korisnik.getInt("idMesto");
                        }
                        boolean okK = (baza.createKorisnik(k, idMesto)!=null);
                        response = Json.createObjectBuilder()
                                .add("zahtev", zahtev)
                                .add("status", okK ? 200 : 400)
                                .add("idKorisnik", k.getIdKorisnik() != null ? k.getIdKorisnik() : -1)
                                .build();
                        break;
                    case "GET_ALL_KORISNICI":
                        List<Korisnik> korisnici = baza.getAllKorisnici();
                        JsonArrayBuilder kArr = Json.createArrayBuilder();
                        for (Korisnik kk : korisnici) {
                            kArr.add(Json.createObjectBuilder()
                                    .add("id", kk.getIdKorisnik())
                                    .add("ime", kk.getIme())
                                    .add("email", kk.getEmail())
                                    .add("pol", kk.getPol())
                                    .add("godiste", kk.getGodiste()));
                        }
                        response = Json.createObjectBuilder()
                                .add("zahtev", zahtev)
                                .add("status", 200)
                                .add("korisnici", kArr)
                                .build();
                        break;
                    case "CHANGE_KORISNIK_EMAIL":
                        JsonObject mail = jo.getJsonObject("podaci");
                        Integer idKorisnik = mail.getInt("idKorisnik");
                        String noviEmail = mail.getString("noviEmail");
                        boolean okEmail = (baza.changeKorisnikEmail(idKorisnik, noviEmail) != null);
                        response = Json.createObjectBuilder()
                                .add("zahtev", zahtev)
                                .add("status", okEmail ? 200 : 404)
                                .add("idKorisnik", idKorisnik)
                                .add("noviEmail", noviEmail)
                                .build();
                        break;
                    case "CHANGE_KORISNIK_MESTO":
                        JsonObject novo = jo.getJsonObject("podaci");
                        Integer idKorisnikMesto = novo.getInt("idKorisnik");
                        Integer idMestoNovo = novo.getInt("idMestoNovo");
                        boolean okGrad = (baza.changeKorisnikMesto(idKorisnikMesto, idMestoNovo) != null);
                        response = Json.createObjectBuilder()
                                .add("zahtev", zahtev)
                                .add("status", okGrad ? 200 : 404)
                                .add("idKorisnik", idKorisnikMesto)
                                .add("idMestoNovo", idMestoNovo)
                                .build();
                        break;
                    default:
                        response = Json.createObjectBuilder()
                                .add("zahtev", zahtev)
                                .add("status", 400)
                                .add("poruka", "Nepoznat zahtev")
                                .build();
                        System.err.println("nepoznat zahtev: " + zahtev);
                }
                producer.send(rqueue, response.toString());
            } catch (JMSException ex) {
                Logger.getLogger(Main1.class.getName()).log(Level.SEVERE, null, ex);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }
    
}
