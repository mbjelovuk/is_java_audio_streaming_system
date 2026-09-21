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

@Path("paket")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class PaketResource {
    @Inject
    private ProslZahteve jms;

    //zah9
    @POST
    public Response kreiraj(JsonObject body) {
        JsonObject odgovor = jms.proslediZahtev("CREATE_PAKET", body, 3);
        int status = odgovor.getInt("status", 404);
        return Response.status(status == 200 ? 201 : 404).entity(odgovor).build();
    }

    //zah10
    @PUT
    @Path("{id}/cena")
    public Response promeniCenu(@PathParam("id") int id, JsonObject body) {
        JsonObject data = Json.createObjectBuilder()
                .add("idPaket", id)
                .add("novaCena", body.getInt("novaCena"))
                .build();
        JsonObject odgovor = jms.proslediZahtev("CHANGE_CENA_PAKETA", data, 3);
        int status = odgovor.getInt("status", 404);
        return Response.status(status == 200 ? 200 : 404).entity(odgovor).build();
    }

    //zah23
    @GET
    public Response svi() {
        JsonObject odgovor = jms.proslediZahtev("GET_ALL_PAKETI", null, 3);
        int status = odgovor.getInt("status", 404);
        return Response.status(status == 200 ? 200 : 404).entity(odgovor).build();
    }
    
}
