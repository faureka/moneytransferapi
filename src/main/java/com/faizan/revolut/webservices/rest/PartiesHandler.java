package com.faizan.revolut.webservices.rest;

import com.faizan.revolut.exceptions.InvalidPartyException;
import com.faizan.revolut.server.logic.PartiesLogicHelper;
import io.swagger.annotations.Api;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Response;

@Api("/parties")
@Path("/parties")
public class PartiesHandler {
    private static final Logger logger = LoggerFactory.getLogger(PartiesHandler.class);

    @GET
    @Path("{id}")
    public Response getById(@PathParam("id") Long id) {
        try {
            return Response.status(200).entity(PartiesLogicHelper.getById(id)).build();
        } catch (InvalidPartyException e) {
            logger.error("Error ::", e);
            return Response.status(e.status()).entity(e.json()).build();
        }
    }
}
