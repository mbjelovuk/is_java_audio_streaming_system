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
import jmslogika.ProslZahteve;

/**
 *
 * @author Milica Bjelovuk
 */

//podsistem3

@Path("ocena")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class OcenaResource {
    @Inject
    private ProslZahteve jms;
    
    //zah14
    @POST
    public Response kreiraj(JsonObject body) {
        JsonObject odgovor = jms.proslediZahtev("CREATE_OCENA", body, 3);
        int status = odgovor.getInt("status", 404);
        return Response.status(status == 200 ? 201 : 404).entity(odgovor).build();
    }

    //zah15
    @PUT
    @Path("{idKorisnik}/{idAudiosnimak}")
    public Response promeni(@PathParam("idKorisnik") int idKorisnik, @PathParam("idAudiosnimak") int idAudiosnimak, JsonObject body) {
        JsonObject data = Json.createObjectBuilder()
            .add("idKorisnik", idKorisnik)
            .add("idAudiosnimak", idAudiosnimak)
            .add("novaOcena", body.getInt("novaOcena"))
            .build();

        JsonObject odgovor = jms.proslediZahtev("CHANGE_OCENA", data, 3);
        int status = odgovor.getInt("status", 404);
        return Response.status(status == 200 ? 200 : 404).entity(odgovor).build();
    }

    //zah16
    @DELETE
    @Path("{idKorisnik}/{idOcena}")
    public Response obrisi(@PathParam("idKorisnik") int idKorisnik, @PathParam("idOcena") int idOcena) {
        JsonObject data = Json.createObjectBuilder()
            .add("idKorisnik", idKorisnik)
            .add("idAudiosnimak", idOcena)
            .build();

        JsonObject odgovor = jms.proslediZahtev("DELETE_OCENA", data, 3);
        int status = odgovor.getInt("status", 404);
        return Response.status(status == 200 ? 204 : 404).entity(odgovor).build();
    }

    //zah26
    @GET
    @Path("audiosnimak/{id}")
    public Response oceneZaSnimak(@PathParam("id") int id) {
        JsonObject data = Json.createObjectBuilder()
                .add("idAudiosnimak", id)
                .build();
        JsonObject odgovor = jms.proslediZahtev("GET_OCENE_ZA_SNIMAK", data, 3);
        int status = odgovor.getInt("status", 404);
        return Response.status(status == 200 ? 200 : 404).entity(odgovor).build();
    }
    
}
