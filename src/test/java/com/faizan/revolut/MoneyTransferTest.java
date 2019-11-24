package com.faizan.revolut;

import com.faizan.revolut.fixtures.TransferDetailsFixture;
import com.faizan.revolut.models.AccountDetails;
import com.faizan.revolut.models.MoneyTransaction;
import com.faizan.revolut.models.config.RevolutConfig;
import io.dropwizard.testing.ResourceHelpers;
import io.dropwizard.testing.junit.DropwizardAppRule;
import org.glassfish.jersey.client.JerseyClientBuilder;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.math.BigDecimal;
import java.util.List;

public class MoneyTransferTest {
    private static final Logger logger = LoggerFactory.getLogger(MoneyTransferTest.class);
    @Rule
    public final DropwizardAppRule<RevolutConfig> RULE = new DropwizardAppRule<>(MoneyTransfer.class,
            ResourceHelpers.resourceFilePath("config.yml"));
    private Client client;

    @Before
    public void setup() {
        client = new JerseyClientBuilder().build();
    }

    @Test
    public void runServerTest() {
        List<AccountDetails> accounts = client.target("http://localhost:8080/revolut/accounts").request()
                .get(new GenericType<List<AccountDetails>>() {
                });
        logger.info(Integer.toString(accounts.size()));
        Assert.assertEquals(accounts.size(), 2);
    }

    @Test
    public void transfer() {
        Entity<?> entity = Entity.entity(TransferDetailsFixture.getActualDetails(), MediaType.APPLICATION_JSON);
        Response response = client.target("http://localhost:8080/revolut/transaction/transfer").request().post(entity);
        Assert.assertNotNull(response);
        Assert.assertEquals(201, response.getStatus());
        MoneyTransaction transaction = response.readEntity(MoneyTransaction.class);
        Assert.assertEquals(new Long(1L), transaction.getId());
        Assert.assertEquals(BigDecimal.TEN, transaction.getAmount());
    }

    @Test
    public void getByInvalidId() {
        Response response = client.target("http://localhost:8080/revolut/transaction/1").request().get();
        Assert.assertNotNull(response);
        Assert.assertEquals(400, response.getStatus());
    }

    @Test
    public void getByInvalidAccountId() {
        Response response = client.target("http://localhost:8080/revolut/transaction/account/1").request().get();
        Assert.assertNotNull(response);
        Assert.assertEquals(400, response.getStatus());
    }
}