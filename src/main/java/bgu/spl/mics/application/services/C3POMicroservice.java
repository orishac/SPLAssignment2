package bgu.spl.mics.application.services;

import bgu.spl.mics.Broadcast;
import bgu.spl.mics.Event;
import bgu.spl.mics.MicroService;
import bgu.spl.mics.application.messages.AttackEvent;


/**
 * C3POMicroservices is in charge of the handling {@link AttackEvents}.
 * This class may not hold references for objects which it is not responsible for:
 * {@link AttackEvents}.
 *
 * You can add private fields and public methods to this class.
 * You MAY change constructor signatures and even add new public constructors.
 */
public class C3POMicroservice extends MicroService {

    private Class<? extends Event> AttackEvent;
    private Class<? extends Broadcast> TerminateBroadcast;

    public C3POMicroservice() {
        super("C3PO");
    }

    @Override
    protected void initialize() {
        subscribeEvent(AttackEvent, (C3PO)->handleHattack());
    }

    private void handleHattack() {
        //attack handling
        //send deactivation event?
        subscribeBroadcast(TerminateBroadcast, (C3PO)->terminate());
    }



}
