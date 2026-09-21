/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.klijent;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonReader;

/**
 *
 * @author Milica Bjelovuk
 */

public class Klijent{

    private static final String CSERVER_URL = "http://localhost:8080/centralni_server/resources/";

    private JsonObject posaljiZahtev(String putanja, RESTMetoda metoda, JsonObject podaci) {
        HttpURLConnection konekcija = null;
        try {
            URL url = new URL(CSERVER_URL + putanja);
            konekcija = (HttpURLConnection) url.openConnection();
            konekcija.setRequestMethod(metoda.name());
            konekcija.setRequestProperty("Content-Type", "application/json");

            if (podaci != null) {
                konekcija.setDoOutput(true);
                try (OutputStream izlaz = konekcija.getOutputStream()) {
                    izlaz.write(podaci.toString().getBytes("UTF-8"));
                }
            }

            //za odgovor servera
            int status = konekcija.getResponseCode();
            InputStream is = (status >= 400) ? konekcija.getErrorStream() : konekcija.getInputStream();
            
            BufferedReader reader = new BufferedReader(new InputStreamReader(is, "UTF-8"));
            StringBuilder odgovor = new StringBuilder();
            String linija;
            while ((linija = reader.readLine()) != null) {
                odgovor.append(linija);
            }
            reader.close();
            
            if (odgovor.length() == 0) {
                return Json.createObjectBuilder()
                           .add("status", status)
                           .build();
            }

            try (JsonReader jr = Json.createReader(new StringReader(odgovor.toString()))) {
                return jr.readObject();
            }

        } catch (Exception e) {
            e.printStackTrace();
            return Json.createObjectBuilder()
                .add("status", "error")
                .add("poruka", e.getMessage())
                .build();
        } finally {
            if (konekcija != null) {
                konekcija.disconnect();
            }
        }
    }
    
    public JsonObject createMesto(JsonObject podaci) {
        return posaljiZahtev("mesto", RESTMetoda.POST, podaci);
    }

    public JsonObject createKorisnik(JsonObject podaci) {
        return posaljiZahtev("korisnik", RESTMetoda.POST, podaci);
    }

    public JsonObject changeKorisnikEmail(int id, JsonObject podaci) {
        return posaljiZahtev("korisnik/" + id + "/email", RESTMetoda.PUT, podaci);
    }

    public JsonObject changeKorisnikMesto(int id, JsonObject podaci) {
        return posaljiZahtev("korisnik/" + id + "/mesto", RESTMetoda.PUT, podaci);
    }

    public JsonObject createKategorija(JsonObject podaci) {
        return posaljiZahtev("kategorija", RESTMetoda.POST, podaci);
    }

    public JsonObject createAudiosnimak(JsonObject podaci) {
        return posaljiZahtev("audiosnimak", RESTMetoda.POST, podaci);
    }

    public JsonObject changeNazivAudiosnimak(int id, JsonObject podaci) {
        return posaljiZahtev("audiosnimak/" + id + "/naziv", RESTMetoda.PUT, podaci);
    }

    public JsonObject addKategorija(int id, JsonObject podaci) {
        return posaljiZahtev("audiosnimak/" + id + "/kategorija", RESTMetoda.POST, podaci);
    }

    public JsonObject createPaket(JsonObject podaci) {
        return posaljiZahtev("paket", RESTMetoda.POST, podaci);
    }

    public JsonObject changeCenaPaketa(int id, JsonObject podaci) {
        return posaljiZahtev("paket/" + id + "/cena", RESTMetoda.PUT, podaci);
    }

    public JsonObject createPretplata(JsonObject podaci) {
        return posaljiZahtev("pretplata", RESTMetoda.POST, podaci);
    }

    public JsonObject createSlusanje(JsonObject podaci) {
        return posaljiZahtev("slusanje", RESTMetoda.POST, podaci);
    }

    public JsonObject addOmiljeni(int idAudiosnimak, JsonObject podaci) {
        return posaljiZahtev("audiosnimak/" + idAudiosnimak + "/omiljeni", RESTMetoda.POST, podaci);
    }

    public JsonObject createOcena(JsonObject podaci) {
        return posaljiZahtev("ocena", RESTMetoda.POST, podaci);
    }

    public JsonObject changeOcena(int idKorisnik, int idAudiosnimak, int novaOcena) {
        JsonObject podaci = Json.createObjectBuilder()
                             .add("novaOcena", novaOcena)
                             .build();
        return posaljiZahtev("ocena/" + idKorisnik + "/" + idAudiosnimak, RESTMetoda.PUT, podaci);
    }

    public JsonObject deleteOcena(int idKorisnik, int idOcena) {
        return posaljiZahtev("ocena/" + idKorisnik + "/" + idOcena, RESTMetoda.DELETE, null);
    }

    public JsonObject deleteAudiosnimak(int id, int idKorisnik) {
        return posaljiZahtev("audiosnimak/" + id + "?idKorisnik=" + idKorisnik, RESTMetoda.DELETE, null);
    }

    public JsonObject getAllMesta() {
        return posaljiZahtev("mesto", RESTMetoda.GET, null);
    }

    public JsonObject getAllKorisnici() {
        return posaljiZahtev("korisnik", RESTMetoda.GET, null);
    }

    public JsonObject getAllKategorije() {
        return posaljiZahtev("kategorija", RESTMetoda.GET, null);
    }

    public JsonObject getAllAudiosnimci() {
        return posaljiZahtev("audiosnimak", RESTMetoda.GET, null);
    }

    public JsonObject getKategorijeZaAudiosnimak(int id) {
        return posaljiZahtev("audiosnimak/" + id + "/kategorije", RESTMetoda.GET, null);
    }

    public JsonObject getAllPaketi() {
        return posaljiZahtev("paket", RESTMetoda.GET, null);
    }

    public JsonObject getPretplateZaKorisnika(int id) {
        return posaljiZahtev("pretplata/korisnik/" + id, RESTMetoda.GET, null);
    }

    public JsonObject getSlusanjaZaSnimak(int id) {
        return posaljiZahtev("slusanje/audiosnimak/" + id, RESTMetoda.GET, null);
    }

    public JsonObject getOceneZaSnimak(int id) {
        return posaljiZahtev("ocena/audiosnimak/" + id, RESTMetoda.GET, null);
    }

    public JsonObject getOmiljeni(int korisnikId) {
        return posaljiZahtev("omiljeni/korisnik/" + korisnikId, RESTMetoda.GET, null);
    }
    
}
