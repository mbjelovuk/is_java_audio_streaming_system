/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package podsistem_2;

import entiteti2.Korisnik;
import entiteti2.Audiosnimak;
import entiteti2.Kategorija;
import javax.annotation.Resource;
import javax.jms.*;
import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonReader;
import javax.json.JsonArrayBuilder;
import java.io.StringReader;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Milica Bjelovuk
 */

public class Main2 {
    @Resource(lookup="myConnFactory")
    private static ConnectionFactory connectionFactory;

    @Resource(lookup="podsistem2q")
    private static Queue queue;
    
    @Resource(lookup="responseq")
    private static Queue rqueue;

    public static void main(String[] args) {
        JMSContext context = connectionFactory.createContext();
        JMSConsumer consumer = context.createConsumer(queue);
        JMSProducer producer = context.createProducer();

        InterfejsBaze2 baza = new InterfejsBaze2();
        
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
                    case "CREATE_KATEGORIJA":
                        JsonObject kat = jo.getJsonObject("podaci");
                        Kategorija k = new Kategorija();
                        k.setNaziv(kat.getString("naziv"));
                        boolean okK = (baza.createKategorija(k)!=null);
                        response = Json.createObjectBuilder()
                                .add("zahtev", zahtev)
                                .add("status", okK ? 200 : 400)
                                .add("idKategorija", k.getIdKategorija() != null ? k.getIdKategorija() : -1)
                                .build();
                        break;
                    case "GET_ALL_KATEGORIJE":
                        List<Kategorija> kategorije = baza.getAllKategorije();
                        JsonArrayBuilder katArr = Json.createArrayBuilder();
                        for (Kategorija kk : kategorije) {
                            katArr.add(Json.createObjectBuilder()
                                    .add("id", kk.getIdKategorija())
                                    .add("naziv", kk.getNaziv()));
                        }
                        response = Json.createObjectBuilder()
                                .add("zahtev", zahtev)
                                .add("status", 200)
                                .add("kategorije", katArr)
                                .build();
                        break;
                    case "CREATE_AUDIOSNIMAK":
                        JsonObject snimak = jo.getJsonObject("podaci");
                        Audiosnimak a = new Audiosnimak();
                        a.setNaziv(snimak.getString("naziv"));
                        a.setTrajanje(snimak.getInt("trajanje"));
                        String datumVreme = snimak.getString("datetime postavljanja");
                        
                        SimpleDateFormat sdfDatum = new SimpleDateFormat("yyyy-MM-dd");
                        SimpleDateFormat sdfVreme = new SimpleDateFormat("HH:mm:ss");
                        SimpleDateFormat sdfFull = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                        
                        try {
                            Date ceodatum = sdfFull.parse(datumVreme);
                            Date datum = sdfDatum.parse(sdfDatum.format(ceodatum));
                            Date vreme = sdfVreme.parse(sdfVreme.format(ceodatum));

                            a.setDatumPostavljanja(datum);
                            a.setVremePostavljanja(vreme);
                            
                            Integer vlasnikId = snimak.getInt("vlasnikId");
                            boolean okA = (baza.createAudiosnimak(a, vlasnikId)!=null);
                            
                            response = Json.createObjectBuilder()
                                .add("zahtev", zahtev)
                                .add("status", okA ? 200 : 400)
                                .add("idAudiosnimak", a.getIdAudiosnimak() != null ? a.getIdAudiosnimak() : -1)
                                .build();
                        } catch (ParseException e) {
                            response = Json.createObjectBuilder()
                                .add("zahtev", zahtev)
                                .add("status", 400)
                                .add("poruka", "greska u parsiranju")
                                .build();
                            e.printStackTrace();
                        }
                        break;
                    case "CHANGE_NAZIV_AUDIOSNIMAK":
                        JsonObject pnaziv = jo.getJsonObject("podaci");
                        Integer idSnimak = pnaziv.getInt("idAudiosnimak");
                        String noviNaziv = pnaziv.getString("noviNaziv");
                        boolean okNaziv = (baza.changeNazivAudiosnimak(idSnimak, noviNaziv)!=null);
                        response = Json.createObjectBuilder()
                                .add("zahtev", zahtev)
                                .add("status", okNaziv ? 200 : 404)
                                .add("idAudiosnimak", idSnimak)
                                .add("noviNaziv", noviNaziv)
                                .build();
                        break;
                    case "ADD_KATEGORIJA":
                        JsonObject dodajkat = jo.getJsonObject("podaci");
                        Integer idAudiosnimak = dodajkat.getInt("idAudiosnimak");
                        Integer idKategorija = dodajkat.getInt("idKategorija");
                        boolean okDodaj = (baza.addKategorijaAudiosnimku(idAudiosnimak, idKategorija)!=null);
                        response = Json.createObjectBuilder()
                                .add("zahtev", zahtev)
                                .add("status", okDodaj ? 200 : 400)
                                .build();
                        break;
                    case "DELETE_AUDIOSNIMAK":
                        JsonObject del = jo.getJsonObject("podaci");
                        Integer delSnimakId = del.getInt("idAudiosnimak");
                        Integer delKorisnikId = del.getInt("idKorisnik");
                        boolean deleted = baza.deleteAudiosnimak(delSnimakId, delKorisnikId);
                        response = Json.createObjectBuilder()
                                .add("zahtev", zahtev)
                                .add("status", deleted ? 200 : 404)
                                .build();
                        break;
                    case "GET_ALL_AUDIOSNIMCI":
                        List<Audiosnimak> snimci = baza.getAllAudiosnimci();
                        JsonArrayBuilder snArr = Json.createArrayBuilder();
                        for (Audiosnimak s : snimci) {
                            snArr.add(Json.createObjectBuilder()
                                    .add("id", s.getIdAudiosnimak())
                                    .add("naziv", s.getNaziv())
                                    .add("trajanje", s.getTrajanje()));
                        }
                        response = Json.createObjectBuilder()
                                .add("zahtev", zahtev)
                                .add("status", 200)
                                .add("audiosnimci", snArr)
                                .build();
                        break;
                    case "GET_KATEGORIJE_ZA_AUDIOSNIMAK":
                        JsonObject podaci = jo.getJsonObject("podaci");
                        if (podaci == null || !podaci.containsKey("idAudiosnimak")) {
                            response = Json.createObjectBuilder()
                                .add("status", 400)
                                .add("poruka", "fali idAudiosnimak")
                                .build();
                            break;
                        }
                        Integer snimakId = podaci.getInt("idAudiosnimak");
                        List<Kategorija> katList = baza.getKategorijeZaAudiosnimak(snimakId);
                        JsonArrayBuilder kArr = Json.createArrayBuilder();
                        for (Kategorija kk : katList) {
                            kArr.add(Json.createObjectBuilder()
                                    .add("id", kk.getIdKategorija())
                                    .add("naziv", kk.getNaziv()));
                        }
                        response = Json.createObjectBuilder()
                                .add("zahtev", zahtev)
                                .add("status", (katList!=null) ? 200 : 404)
                                .add("kategorije", kArr)
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
                Logger.getLogger(Main2.class.getName()).log(Level.SEVERE, null, ex);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }
}
