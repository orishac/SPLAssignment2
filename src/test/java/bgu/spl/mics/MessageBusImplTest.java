package bgu.spl.mics;

import bgu.spl.mics.application.messages.AttackEvent;
import bgu.spl.mics.application.passiveObjects.Attack;
import bgu.spl.mics.application.services.C3POMicroservice;
import bgu.spl.mics.application.services.HanSoloMicroservice;
import bgu.spl.mics.application.services.LeiaMicroservice;
import bgu.spl.mics.application.services.testMicroservice;
import bgu.spl.mics.example.messages.ExampleBroadcast;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class MessageBusImplTest {

    private MessageBusImpl testBus;
    private MicroService m1;
    private MicroService m2;
    private AttackEvent attack;
    private ExampleBroadcast broadcast;


    @Before
    public void setUp() throws Exception {
        testBus = new MessageBusImpl();
        m1 = new testMicroservice("m1");
        m2 = new testMicroservice("m2");
        attack = new AttackEvent();
        broadcast = new ExampleBroadcast("hi");
    }

    @Test
    public void subscribeEvent() {
        m1.subscribeEvent(attack.getClass());
        m2.subscribeEvent();
        testBus.subscribeEvent();

    }

    @Test
    public void subscribeBroadcast() {
        m1.subscribeBroadcast();
        m2.subscribeBroadcast();
        Assert.assertEquals();
    }

    @Test
    public void complete() {
        m1.complete();
        m2.complete();
        testBus.complete();
    }

    @Test
    public void sendBroadcast() {
        m1.sendBroadcast(broadcast);
        m2.sendBroadcast(broadcast);
        testBus.sendBroadcast(broadcast);

    }

    @Test
    public void sendEvent() {
        m1.sendEvent();
        m2.sendEvent();
        testBus.sendEvent();

    }

    @Test
    public void register() throws InterruptedException {
        testBus.register(m1);
        Assert.assertEquals(new AttackEvent(), testBus.awaitMessage(m1));

    }

    @Test
    public void unregister() throws InterruptedException {
        testBus.register(m1);
        testBus.unregister(m1);
        Assert.assertEquals(null, testBus.awaitMessage(m1));
    }

    @Test
    public void awaitMessage() throws InterruptedException {
        testBus.register(m1);
        Assert.assertEquals(new AttackEvent(), testBus.awaitMessage(m1));
    }
}