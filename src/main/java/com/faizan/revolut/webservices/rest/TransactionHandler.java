package com.faizan.revolut.webservices.rest;

import com.faizan.revolut.exceptions.InvalidAccountException;
import com.faizan.revolut.exceptions.InvalidTransactionException;
import com.faizan.revolut.models.incoming.TransferDetails;
import com.faizan.revolut.server.logic.TransactionLogicHelper;
import io.swagger.annotations.Api;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Api("transaction")
@Path("transaction")
public class TransactionHandler {
    private static final Logger logger = LoggerFactory.getLogger(TransactionHandler.class);

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAll() {
        return Response.status(200).entity(TransactionLogicHelper.getAll()).build();
    }

    @POST
    @Path("/transfer")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response transfer(TransferDetails details) {
        try {
            return Response.status(201).entity(TransactionLogicHelper.transfer(details)).build();
        } catch (InvalidTransactionException | InvalidAccountException e) {
            logger.error("Error ::", e);
            return Response.status(e.status()).entity(e.json()).build();
        }
    }

    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getById(@PathParam("id") Long id) {
        try {
            return Response.status(200).entity(TransactionLogicHelper.getById(id)).build();
        } catch (InvalidTransactionException e) {
            logger.error("Error ::", e);
            return Response.status(e.status()).entity(e.json()).build();
        }
    }

    @GET
    @Path("/account/{accountId}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getByAccountId(@PathParam("accountId") Long accountId) {
        try {
            return Response.status(200).entity(TransactionLogicHelper.getByAccountId(accountId)).build();
        } catch (InvalidAccountException e) {
            logger.error("Error ::", e);
            return Response.status(e.status()).entity(e.json()).build();
        }
    }
}
