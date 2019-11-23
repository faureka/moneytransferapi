package com.faizan.revolut.server.logic;

import com.faizan.revolut.exceptions.InvalidPartyException;
import com.faizan.revolut.models.Person;
import com.faizan.revolut.models.incoming.Party;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.atomic.AtomicLong;

public class PartiesLogicHelper {
    private static final AtomicLong counter = new AtomicLong(0L);
    private static final ConcurrentMap<Long, Person> parties = new ConcurrentHashMap<>();

    static long add(Party party) {
        long idx = counter.incrementAndGet();
        parties.put(idx, new Person(party.getName()));
        return idx;
    }

    public static Person getById(Long id) throws InvalidPartyException {
        if (!parties.containsKey(id)) {
            throw new InvalidPartyException();
        }
        return parties.get(id);
    }
}
