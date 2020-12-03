package bgu.spl.mics;

import bgu.spl.mics.application.messages.AttackEvent;
import bgu.spl.mics.application.passiveObjects.Attack;
import bgu.spl.mics.application.services.testMicroservice;
import bgu.spl.mics.example.messages.ExampleBroadcast;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


import static org.junit.jupiter.api.Assertions.*;

class MessageBusImplTest {

    private MessageBusImpl testBus;
    private testMicroservice m1;
    private testMicroservice m2;

    @BeforeEach
    void setUp() {

        m1 = new testMicroservice("m1");
        m2 = new testMicroservice("m2");
    }

    @Test
    void complete() {
        Future<AttackEvent> f = new Future<>();
        AttackEvent e1 = new AttackEvent();
        testBus.complete(e1, true);
        assertTrue(f.isDone());
    }

    @Test
    void sendBroadcast() {
        ExampleBroadcast e1 = new ExampleBroadcast("Hi");
        m2.subscribeBroadcast(ExampleBroadcast.class, (callback)->{});
        m1.sendBroadcast(e1);
        ExampleBroadcast e2 = (ExampleBroadcast) m2.awaitMessage();
        assertEquals(e1, e2);

    }

    @Test
    void sendEvent() {
        AttackEvent e1 = new AttackEvent();
        m2.subscribeEvent(AttackEvent.class, (callback)->{});
        m1.sendEvent(e1);
        AttackEvent e2 = (AttackEvent) m2.awaitMessage();
        assertEquals(e1,e2);
    }

    @Test
    void awaitMessage() {
        testBus.register(m1);
        AttackEvent e1 = new AttackEvent();
        m1.subscribeEvent(AttackEvent.class, (callback)->{});
        testBus.sendEvent(e1);
        assertEquals(e1, m1.awaitMessage());
    }
}