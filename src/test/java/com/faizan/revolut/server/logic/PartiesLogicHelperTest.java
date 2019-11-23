package com.faizan.revolut.server.logic;

import com.faizan.revolut.exceptions.InvalidPartyException;
import com.faizan.revolut.models.Person;
import com.faizan.revolut.models.incoming.Party;
import org.junit.*;
import org.junit.rules.ExpectedException;

public class PartiesLogicHelperTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Before
    public void setup() {
        Party party = new Party();
        party.setName("Faizan");
        PartiesLogicHelper.add(party);
        thrown = ExpectedException.none();
    }

    @After
    public void tearDown() {
        thrown = ExpectedException.none();
    }

    @Test
    public void setAndGetPerson() throws InvalidPartyException {
        Party party = new Party();
        party.setName("Revolut");
        long idx = PartiesLogicHelper.add(party);
        Assert.assertEquals(PartiesLogicHelper.getById(idx).getName(), "Revolut");
    }

    @Test
    public void invalidPartyId() throws InvalidPartyException {
        thrown.expect(InvalidPartyException.class);
        thrown.expectMessage("Invalid party id provided");
        PartiesLogicHelper.getById(3L);
    }

    @Test
    public void validPartyId() throws InvalidPartyException {
        Person person = PartiesLogicHelper.getById(1L);
        Assert.assertNotNull(person);
        Assert.assertNotNull(person.getName());
        Assert.assertEquals(person.getName(), "Faizan");
    }
}
