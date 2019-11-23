package com.faizan.revolut.webservices.rest;

import com.faizan.revolut.exceptions.InvalidPartyException;
import com.faizan.revolut.models.Person;
import com.faizan.revolut.server.logic.PartiesLogicHelper;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import javax.ws.rs.core.Response;

@RunWith(PowerMockRunner.class)
@PrepareForTest(PartiesLogicHelper.class)
public class PartiesHandlerTest {

    private PartiesHandler handler;

    @Before
    public void setup() throws InvalidPartyException {
        handler = new PartiesHandler();
        PowerMockito.mockStatic(PartiesLogicHelper.class);
        PowerMockito.when(PartiesLogicHelper.getById(1L)).thenReturn(new Person("Faizan"));
        PowerMockito.when(PartiesLogicHelper.getById(0L)).thenThrow(InvalidPartyException.class);
    }

    @Test
    public void getPartyById() {
        Response response = handler.getById(1L);
        Assert.assertNotNull(response);
        Assert.assertEquals(response.getStatus(), 200);
        Person p = (Person) response.getEntity();
        boolean contains = p.getName().contains("Faizan");
        Assert.assertEquals(contains, Boolean.TRUE);
    }

    @Test
    public void getInvalidPartyById() {
        Response response = handler.getById(0L);
        Assert.assertNotNull(response);
        Assert.assertEquals(response.getStatus(), 400);
        String e = (String) response.getEntity();
        boolean contains = e.contains("Invalid party id provided");
        Assert.assertEquals(contains, Boolean.TRUE);
    }
}
