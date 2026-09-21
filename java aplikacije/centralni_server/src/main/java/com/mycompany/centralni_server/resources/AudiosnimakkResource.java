/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.centralni_server.resources;

import javax.ws.rs.Path;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.DELETE;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.Consumes;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.inject.Inject;
import javax.json.Json;
import javax.json.JsonObject;
import javax.ws.rs.QueryParam;
import jmslogika.ProslZahteve;

/**
 *
 * @author Milica Bjelovuk
 */

//podsistem2 - zah 6,7,8,17,21
//podsistem3 - zah 13,27

@Path("audiosnimak")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class AudiosnimakkResource {
    @Inject
    private ProslZahteve jms;

    //audiosnimak
    
    //zah6
    @POST
    public Response kreiraj(JsonObject body) {
        JsonObject odgovor = jms.proslediZahtev("CREATE_AUDIOSNIMAK", body, 2);
        int status = odgovor.getInt("status", 404);
        return Response.status(status == 200 ? 201 : 404).entity(odgovor).build();
    }
    
    //zah7
    @PUT
    @Path("{id}/naziv")
    public Response promeniNaziv(@PathParam("id") int id, JsonObject body) {
        JsonObject data = Json.createObjectBuilder()
                .add("idAudiosnimak", id)
                .add("noviNaziv", body.getString("noviNaziv"))
                .build();
        JsonObject odgovor = jms.proslediZahtev("CHANGE_NAZIV_AUDIOSNIMAK", data, 2);
        int status = odgovor.getInt("status", 404);
        return Response.status(status == 200 ? 200 : 404).entity(odgovor).build();
    }

    //zah8
    @POST
    @Path("{id}/kategorija")
    public Response dodajKategoriju(@PathParam("id") int id, JsonObject body) {
        JsonObject data = Json.createObjectBuilder()
                .add("idAudiosnimak", id)
                .add("idKategorija", body.getInt("idKategorija"))
                .build();
        JsonObject odgovor = jms.proslediZahtev("ADD_KATEGORIJA", data, 2);
        int status = odgovor.getInt("status", 404);
        return Response.status(status == 200 ? 200 : 404).entity(odgovor).build();
    }

    //zah17
    @DELETE
    @Path("{id}")
    public Response obrisi(@PathParam("id") int id, @QueryParam("idKorisnik") int idKorisnik) {
        JsonObject data = Json.createObjectBuilder()
                .add("idAudiosnimak", id)
                .add("idKorisnik", idKorisnik)
                .build();
        JsonObject odgovor = jms.proslediZahtev("DELETE_AUDIOSNIMAK", data, 2);
        int status = odgovor.getInt("status", 404);
        return Response.status(status == 200 ? 204 : 404).entity(odgovor).build();
    }
    
    //zah21
    @GET
    public Response svi() {
        JsonObject odgovor = jms.proslediZahtev("GET_ALL_AUDIOSNIMCI", null, 2);
        int status = odgovor.getInt("status", 404);
        return Response.status(status == 200 ? 200 : 404).entity(odgovor).build();
    }
    
    //zah22
    @GET
    @Path("{id}/kategorije")
    public Response kategorijeZaSnimak(@PathParam("id") int id) {
        JsonObject data = Json.createObjectBuilder()
                .add("idAudiosnimak", id)
                .build();
        JsonObject odgovor = jms.proslediZahtev("GET_KATEGORIJE_ZA_AUDIOSNIMAK", data, 2);
        int status = odgovor.getInt("status", 404);
        return Response.status(status == 200 ? 200 : 404).entity(odgovor).build();
    }
    
    //omiljeni
    
    //zah13
    @POST
    @Path("{id}/omiljeni")
    public Response dodajOmiljeni(@PathParam("id") int id, JsonObject body) {
        JsonObject data = Json.createObjectBuilder()
                .add("idKorisnik", body.getInt("idKorisnik"))
                .add("idAudiosnimak", id)
                .build();
        JsonObject odgovor = jms.proslediZahtev("ADD_OMILJENI", data, 3);
        int status = odgovor.getInt("status", 404);
        return Response.status(status == 200 ? 200 : 404).entity(odgovor).build();
    }

    //zah27
    @GET
    @Path("omiljeni/korisnik/{id}")
    public Response omiljeniKorisnika(@PathParam("id") int id) {
        JsonObject data = Json.createObjectBuilder()
                .add("idKorisnik", id)
                .build();
        JsonObject odgovor = jms.proslediZahtev("GET_OMILJENI", data, 3);
        int status = odgovor.getInt("status", 404);
        return Response.status(status == 200 ? 200 : 404).entity(odgovor).build();
    }
    
}
