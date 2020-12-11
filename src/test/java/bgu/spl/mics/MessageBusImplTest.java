package bgu.spl.mics;

import bgu.spl.mics.application.messages.AttackEvent;
import bgu.spl.mics.application.passiveObjects.Attack;
import bgu.spl.mics.application.services.C3POMicroservice;
import bgu.spl.mics.application.services.HanSoloMicroservice;
import bgu.spl.mics.application.services.LeiaMicroservice;
import bgu.spl.mics.application.services.testMicroservice;
import bgu.spl.mics.example.messages.ExampleBroadcast;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


import static org.junit.jupiter.api.Assertions.*;

class MessageBusImplTest {

    // We made a few changes in those tests compared to the tests that we submitted.
    private MessageBusImpl testBus;
    private MicroService Han;
    private MicroService C3PO;

    @BeforeEach
    void setUp() {
        Han = new HanSoloMicroservice();
        C3PO = new C3POMicroservice();
        testBus = MessageBusImpl.getInstance();
    }

    @Test
    void complete() {
        Han.subscribeEvent(AttackEvent.class, (callback)->{});
        AttackEvent e1 = new AttackEvent();
        Future f = C3PO.sendEvent(e1);
        Han.complete(e1, true);
        assertTrue(f.isDone());
    }

    @Test
    void sendBroadcast() {
        ExampleBroadcast e1 = new ExampleBroadcast("Hi");
        testBus.register(Han);
        Han.subscribeBroadcast(ExampleBroadcast.class, (callback)->{});
        C3PO.sendBroadcast(e1);
        try{
            ExampleBroadcast e2 = (ExampleBroadcast) testBus.awaitMessage(Han);
            assertEquals(e1, e2);
        }
        catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Test
    void sendEvent() {
        AttackEvent e1 = new AttackEvent();
        testBus.register(Han);
        testBus.register(C3PO);
        Han.subscribeEvent(AttackEvent.class, (callback)->{});
        C3PO.sendEvent(e1);
        try{
            Message e2 = testBus.awaitMessage(Han);
            assertEquals(e1,e2);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Test
    void awaitMessage() {
        testBus.register(Han);
        AttackEvent e1 = new AttackEvent();
        Han.subscribeEvent(AttackEvent.class, (callback)->{});
        testBus.sendEvent(e1);
        try{
            assertEquals(e1, testBus.awaitMessage(Han)); }
        catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}