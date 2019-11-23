package com.faizan.revolut.webservices.rest;

import com.faizan.revolut.exceptions.InvalidAccountException;
import com.faizan.revolut.server.logic.AccountsLogicHelper;
import io.swagger.annotations.Api;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Api("/accounts")
@Path("/accounts")
public class AccountHandler {
    private static final Logger logger = LoggerFactory.getLogger(AccountHandler.class);

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllAccounts() {
        return Response.status(200).entity(AccountsLogicHelper.getAll()).build();
    }

    @GET
    @Path("/{accountId}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAccountById(@PathParam("accountId") Long accountId) {
        try {
            return Response.status(200).entity(AccountsLogicHelper.getById(accountId)).build();
        } catch (InvalidAccountException e) {
            logger.error("Error ::", e);
            return Response.status(e.status()).entity(e.json()).build();
        }
    }

    @GET
    @Path("/{accountId}/balance")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAccountBalance(@PathParam("accountId") Long accountId) {
        try {
            return Response.status(200).entity(AccountsLogicHelper.getBalanceById(accountId)).build();
        } catch (InvalidAccountException e) {
            logger.error("Error ::", e);
            return Response.status(e.status()).entity(e.json()).build();
        }
    }

}
