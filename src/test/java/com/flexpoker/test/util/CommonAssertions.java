package com.flexpoker.test.util;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;

import java.util.List;
import java.util.UUID;

import com.flexpoker.framework.event.Event;
import com.flexpoker.framework.event.EventType;

public class CommonAssertions {

    public static void verifyEventIdsAndVersionNumbers(UUID tableId,
            List<? extends Event<? extends EventType>> events) {
        int version = 0;
        for (Event<?> event : events) {
            assertEquals(tableId, event.getAggregateId());
            assertEquals(++version, event.getVersion());
        }
    }

    public static void verifyNumberOfEventsAndEntireOrderByType(
            List<? extends Event<? extends EventType>> events, EventType... eventTypes) {
        assertEquals(eventTypes.length, events.size());
        assertArrayEquals(eventTypes, events.stream().map(x -> x.getType()).toArray());
    }
}