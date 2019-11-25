package com.faizan.revolut;

import com.faizan.revolut.enums.TransactionState;
import com.faizan.revolut.fixtures.TransferDetailsFixture;
import com.faizan.revolut.models.AccountDetails;
import com.faizan.revolut.models.Balance;
import com.faizan.revolut.models.MoneyTransaction;
import com.faizan.revolut.models.config.RevolutConfig;
import io.dropwizard.testing.ResourceHelpers;
import io.dropwizard.testing.junit.DropwizardAppRule;
import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
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
    @ClassRule
    public static final DropwizardAppRule<RevolutConfig> RULE = new DropwizardAppRule<>(MoneyTransfer.class,
            ResourceHelpers.resourceFilePath("config.yml"));
    private Client client;

    @Before
    public void setup() {
        client = RULE.client();
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
        Assert.assertEquals(TransactionState.COMPLETED, transaction.getState());
        Assert.assertEquals(MediaType.APPLICATION_JSON, response.getMediaType().toString());
    }

    @Test
    public void getByInvalidId() {
        Response response = client.target("http://localhost:8080/revolut/transaction/1").request().get();
        Assert.assertNotNull(response);
        Assert.assertEquals(400, response.getStatus());
        Assert.assertEquals(MediaType.APPLICATION_JSON, response.getMediaType().toString());
    }

    @Test
    public void getByInvalidAccountId() {
        Response response = client.target("http://localhost:8080/revolut/transaction/account/1").request().get();
        Assert.assertNotNull(response);
        Assert.assertEquals(400, response.getStatus());
        Assert.assertEquals(MediaType.APPLICATION_JSON, response.getMediaType().toString());
    }

    @Test
    public void getAllAccounts() {
        Response response = client.target("http://localhost:8080/revolut/accounts").request().get();
        Assert.assertNotNull(response);
        Assert.assertEquals(200, response.getStatus());
        List<AccountDetails> accounts = response.readEntity(new GenericType<List<AccountDetails>>() {
        });
        Assert.assertEquals(2, accounts.size());
        Assert.assertEquals(new Long(865930402721L), accounts.get(0).getNumber());
        Assert.assertEquals(MediaType.APPLICATION_JSON, response.getMediaType().toString());
    }

    @Test
    public void getAccountById() {
        Response response = client.target("http://localhost:8080/revolut/accounts/865930402721").request().get();
        Assert.assertNotNull(response);
        Assert.assertEquals(200, response.getStatus());
        AccountDetails account = response.readEntity(AccountDetails.class);
        Assert.assertEquals(new Long(865930402721L), account.getNumber());
        Assert.assertEquals(MediaType.APPLICATION_JSON, response.getMediaType().toString());
    }

    @Test
    public void getAccountBalance() {
        Response response = client.target("http://localhost:8080/revolut/accounts/865930402721/balance").request()
                .get();
        Assert.assertNotNull(response);
        Assert.assertEquals(200, response.getStatus());
        Balance balance = response.readEntity(Balance.class);
        Assert.assertEquals(BigDecimal.valueOf(100_000_000.00d), balance.getBalance());
        Assert.assertEquals(MediaType.APPLICATION_JSON, response.getMediaType().toString());
    }
}
