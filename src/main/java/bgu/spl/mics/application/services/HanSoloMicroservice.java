package bgu.spl.mics.application.services;


import bgu.spl.mics.Broadcast;
import bgu.spl.mics.Event;
import bgu.spl.mics.MicroService;
import bgu.spl.mics.application.messages.DeactivationEvent;
import bgu.spl.mics.application.messages.TerminateBroadcast;

/**
 * HanSoloMicroservices is in charge of the handling {@link AttackEvents}.
 * This class may not hold references for objects which it is not responsible for:
 * {@link AttackEvents}.
 *
 * You can add private fields and public methods to this class.
 * You MAY change constructor signatures and even add new public constructors.
 */
public class HanSoloMicroservice extends MicroService {

    private Class<? extends Event> AttackEvent;
    private Class<? extends Broadcast> TerminateBroadcast;

    public HanSoloMicroservice() {
        super("Han");
    }


    @Override
    protected void initialize() {
        subscribeEvent(AttackEvent, (Han)->handleAttack());
    }

    private void handleAttack() {
        //handle attack
        //send deactivation event?
        subscribeBroadcast(TerminateBroadcast, (Han)->terminate());
    }


}
