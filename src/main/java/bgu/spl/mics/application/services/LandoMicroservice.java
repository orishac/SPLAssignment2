package bgu.spl.mics.application.services;

import bgu.spl.mics.Broadcast;
import bgu.spl.mics.Event;
import bgu.spl.mics.MicroService;
import bgu.spl.mics.application.messages.BombDestroyerEvent;
import bgu.spl.mics.application.messages.TerminateBroadcast;

/**
 * LandoMicroservice
 * You can add private fields and public methods to this class.
 * You MAY change constructor signatures and even add new public constructors.
 */
public class LandoMicroservice  extends MicroService {

    private Class<Event> BombDestroyerEvent;
    private Class<? extends Broadcast> TerminateBroadcast;
    private long duration;

    public LandoMicroservice(long duration) {
        super("Lando");
        this.duration = duration;
    }

    @Override
    protected void initialize() {
       subscribeEvent(BombDestroyerEvent, Lando->handleBombDestroyer());
    }

    private void handleBombDestroyer() {
        //handle the bomb destroyer event
        subscribeBroadcast(TerminateBroadcast, (Lando)->terminate());
        sendBroadcast(new TerminateBroadcast());
    }


}
