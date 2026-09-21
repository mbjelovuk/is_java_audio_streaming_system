/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package podsistem_3;

import entiteti3.Paket;
import entiteti3.Pretplata;
import entiteti3.Slusa;
import entiteti3.Ocena;
import entiteti3.Audiosnimak;
import javax.annotation.Resource;
import javax.jms.*;
import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonReader;
import java.io.StringReader;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.json.JsonArrayBuilder;

/**
 *
 * @author Milica Bjelovuk
 */

public class Main3 {
    @Resource(lookup = "myConnFactory")
    private static ConnectionFactory connectionFactory;

    @Resource(lookup = "podsistem3q")
    private static Queue queue;
    
    @Resource(lookup = "responseq")
    private static Queue rqueue;

    public static void main(String[] args) {
        JMSContext context = connectionFactory.createContext();
        JMSConsumer consumer = context.createConsumer(queue);
        JMSProducer producer = context.createProducer();

        InterfejsBaze3 baza = new InterfejsBaze3();

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
                    case "CREATE_PAKET":
                        JsonObject pak = jo.getJsonObject("podaci");
                        Paket p = new Paket();
                        p.setNaziv(pak.getString("naziv"));
                        p.setTrenutnaCena(pak.getInt("cena"));
                        boolean okP = (baza.createPaket(p)!=null);
                        response = Json.createObjectBuilder()
                                .add("zahtev", zahtev)
                                .add("status", okP ? 200 : 400)
                                .add("idPaket", p.getIdPaket() != null ? p.getIdPaket() : -1)
                                .build();
                        break;
                    case "CHANGE_CENA_PAKETA":
                        JsonObject novacena = jo.getJsonObject("podaci");
                        Integer idPak = novacena.getInt("idPaket");
                        int novaCena = novacena.getInt("novaCena");
                        boolean okCena = (baza.changeCenaPaketa(idPak, novaCena)!=null);
                        response = Json.createObjectBuilder()
                                .add("zahtev", zahtev)
                                .add("status", okCena ? 200 : 404)
                                .add("idPaket", idPak)
                                .add("novaCena", novaCena)
                                .build();
                        break;
                    case "GET_ALL_PAKETI":
                        List<Paket> paketi = baza.getAllPaketi();
                        JsonArrayBuilder arr = Json.createArrayBuilder();
                        for (Paket pp : paketi) {
                            arr.add(Json.createObjectBuilder()
                                    .add("id", pp.getIdPaket())
                                    .add("naziv", pp.getNaziv())
                                    .add("cena", pp.getTrenutnaCena()));
                        }
                        response = Json.createObjectBuilder()
                                .add("zahtev", zahtev)
                                .add("status", 200)
                                .add("paketi", arr)
                                .build();
                        break;
                    case "CREATE_PRETPLATA":
                        JsonObject pr = jo.getJsonObject("podaci");
                        Integer idKorisnik = pr.getInt("idKorisnik");
                        Integer idPaket = pr.getInt("idPaket");
                        Pretplata pretplata = new Pretplata();
                        String datumVremee = pr.getString("datetimePocetka");
                        
                        SimpleDateFormat sdfDatumm = new SimpleDateFormat("yyyy-MM-dd");
                        SimpleDateFormat sdfVremee = new SimpleDateFormat("HH:mm:ss");
                        SimpleDateFormat sdfFulll = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                        
                        try {
                            Date ceodatum = sdfFulll.parse(datumVremee);
                            Date datumm = sdfDatumm.parse(sdfDatumm.format(ceodatum));
                            Date vremee = sdfVremee.parse(sdfVremee.format(ceodatum));

                            pretplata.setDatumPocetka(datumm);
                            pretplata.setVremePocetka(vremee);                            

                            boolean napravljena = (baza.createPretplata(pretplata, idKorisnik, idPaket)!=null);
                            response = Json.createObjectBuilder()
                                .add("zahtev", zahtev)
                                .add("status", napravljena ? 201 : 400)
                                .add("idKorisnik", idKorisnik)
                                .add("idPaket", idPaket)
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
                    case "GET_PRETPLATE_ZA_KORISNIKA":
                        JsonObject podaci = jo.getJsonObject("podaci");
                        if (podaci == null || !podaci.containsKey("idKorisnik")) {
                            response = Json.createObjectBuilder()
                                .add("status", 400)
                                .add("poruka", "fali idKorisnik")
                                .build();
                            break;
                        }
                        Integer korisnikId = podaci.getInt("idKorisnik");
                        List<Pretplata> pretplate = baza.getPretplateZaKorisnika(korisnikId);
                        JsonArrayBuilder pretArr = Json.createArrayBuilder();
                        for (Pretplata prt : pretplate) {
                            pretArr.add(Json.createObjectBuilder()
                                    .add("idPretplata", prt.getIdPretplata())
                                    .add("cena", prt.getCena()));
                        }
                        response = Json.createObjectBuilder()
                                .add("zahtev", zahtev)
                                .add("status", 200)
                                .add("pretplate", pretArr)
                                .build();
                        break;
                    case "CREATE_SLUSANJE":
                        JsonObject slus = jo.getJsonObject("podaci");
                        int idKorSl = slus.getInt("idKorisnik");
                        int idSnimakSl = slus.getInt("idAudiosnimak");
                        String datumVreme = slus.getString("datetime pocslusanja");
                        Slusa s = new Slusa();
                        
                        SimpleDateFormat sdfDatum = new SimpleDateFormat("yyyy-MM-dd");
                        SimpleDateFormat sdfVreme = new SimpleDateFormat("HH:mm:ss");
                        SimpleDateFormat sdfFull = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                        
                        try {
                            Date ceodatum = sdfFull.parse(datumVreme);
                            Date datum = sdfDatum.parse(sdfDatum.format(ceodatum));
                            Date vreme = sdfVreme.parse(sdfVreme.format(ceodatum));

                            s.setDatumPoc(datum);
                            s.setVremePoc(vreme);
                            s.setPocetniSekund(slus.getInt("pocetniSekund"));
                            s.setOdslusanoSekundi(slus.getInt("odslusanoSekundi"));

                            boolean okS = (baza.createSlusanje(s, idKorSl, idSnimakSl) != null);
                            response = Json.createObjectBuilder()
                                    .add("zahtev", zahtev)
                                    .add("status", okS ? 200 : 400)
                                    .add("idKorisnik", idKorSl)
                                    .add("idAudiosnimak", idSnimakSl)
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
                    case "GET_SLUSANJA_ZA_SNIMAK":
                        JsonObject podaci2 = jo.getJsonObject("podaci");
                        if (podaci2 == null || !podaci2.containsKey("idAudiosnimak")) {
                            response = Json.createObjectBuilder()
                                .add("status", 400)
                                .add("poruka", "fali idAudiosnimak")
                                .build();
                            break;
                        }
                        int idSnimak = podaci2.getInt("idAudiosnimak");
                        List<Slusa> slusanja = baza.getSlusanjaZaSnimak(idSnimak);
                        JsonArrayBuilder slusArr = Json.createArrayBuilder();
                        for (Slusa sl : slusanja) {
                            slusArr.add(Json.createObjectBuilder()
                                    .add("idSlusanje", sl.getIdSlusa())
                                    .add("odslusanoSekundi", sl.getOdslusanoSekundi()));
                        }
                        response = Json.createObjectBuilder()
                                .add("zahtev", zahtev)
                                .add("status", 200)
                                .add("slusanja", slusArr)
                                .build();
                        break;
                    case "ADD_OMILJENI":
                        JsonObject om = jo.getJsonObject("podaci");
                        int idKorOm = om.getInt("idKorisnik");
                        int idSnOm = om.getInt("idAudiosnimak");
                        boolean okOm = baza.addOmiljeni(idKorOm, idSnOm);
                        response = Json.createObjectBuilder()
                                .add("zahtev", zahtev)
                                .add("status", okOm ? 200 : 400)
                                .build();
                        break;
                    case "GET_OMILJENI":
                        JsonObject podacii = jo.getJsonObject("podaci");
                        if (podacii == null || !podacii.containsKey("idKorisnik")) {
                            response = Json.createObjectBuilder()
                                .add("status", 400)
                                .add("poruka", "fali idKorisnik")
                                .build();
                            break;
                        }
                        int idkor = podacii.getInt("idKorisnik");
                        List<Audiosnimak> omiljeni = baza.getOmiljeniZaKorisnika(idkor);
                        JsonArrayBuilder omArr = Json.createArrayBuilder();
                        for (Audiosnimak a : omiljeni) {
                            omArr.add(Json.createObjectBuilder()
                                    .add("id", a.getIdAudiosnimak())
                                    .add("naziv", a.getNaziv()));
                        }
                        response = Json.createObjectBuilder()
                                .add("zahtev", zahtev)
                                .add("status", 200)
                                .add("omiljeni", omArr)
                                .build();
                        break;
                    case "CREATE_OCENA":
                        JsonObject oc = jo.getJsonObject("podaci");
                        int idKorOc = oc.getInt("idKorisnik");
                        int idSnOc = oc.getInt("idAudiosnimak");
                        int vred = oc.getInt("ocena");
                        boolean okoloko=true;
                        if(vred<1 || vred>5) okoloko=false;
                        Ocena o = new Ocena();
                        
                        String datumVrem = oc.getString("datetimeOcenjivanja");
                        
                        SimpleDateFormat sdfDat = new SimpleDateFormat("yyyy-MM-dd");
                        SimpleDateFormat sdfVrem = new SimpleDateFormat("HH:mm:ss");
                        SimpleDateFormat sdfFul = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                        
                        try {
                            Date ceodatum = sdfFul.parse(datumVrem);
                            Date datum = sdfDat.parse(sdfDat.format(ceodatum));
                            Date vreme = sdfVrem.parse(sdfVrem.format(ceodatum));

                            o.setDatum(datum);
                            o.setVreme(vreme);
                            o.setOcena(vred);
                            
                            boolean okOc = (baza.createOcena(o, idKorOc, idSnOc)!=null);
                            response = Json.createObjectBuilder()
                                    .add("zahtev", zahtev)
                                    .add("status", okOc&&okoloko ? 200 : 400) //409 ako vec postoji
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
                    case "CHANGE_OCENA":
                        JsonObject ch = jo.getJsonObject("podaci");
                        int idKoc = ch.getInt("idKorisnik");
                        int idAs = ch.getInt("idAudiosnimak");
                        int nova = ch.getInt("novaOcena");
                        boolean okCh = (baza.changeOcena(idKoc, idAs, nova)!=null);
                        response = Json.createObjectBuilder()
                                .add("zahtev", zahtev)
                                .add("status", okCh ? 200 : 404)
                                .build();
                        break;
                    case "DELETE_OCENA":
                        JsonObject pod = jo.getJsonObject("podaci");
                        int idKDel = pod.getInt("idKorisnik");
                        int idOcDel = pod.getInt("idOcena");
                        boolean us = baza.deleteOcena(idOcDel, idKDel);
                        response = Json.createObjectBuilder()
                                .add("zahtev", zahtev)
                                .add("status", us ? 200 : 404)
                                .build();
                        break;
                    case "GET_OCENE_ZA_SNIMAK":
                        JsonObject podaci3 = jo.getJsonObject("podaci");
                        if (podaci3 == null || !podaci3.containsKey("idAudiosnimak")) {
                            response = Json.createObjectBuilder()
                                .add("status", 400)
                                .add("poruka", "fali idAudiosnimak")
                                .build();
                            break;
                        }
                        int ids = podaci3.getInt("idAudiosnimak");
                        List<Ocena> ocene = baza.getOceneZaSnimak(ids);
                        JsonArrayBuilder ocArr = Json.createArrayBuilder();
                        for (Ocena ocena : ocene) {
                            ocArr.add(Json.createObjectBuilder()
                                    .add("idOcena", ocena.getIdOcena())
                                    .add("ocena", ocena.getOcena()));
                        }
                        response = Json.createObjectBuilder()
                                .add("zahtev", zahtev)
                                .add("status", 200)
                                .add("ocene", ocArr)
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
                Logger.getLogger(Main3.class.getName()).log(Level.SEVERE, null, ex);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }
}
