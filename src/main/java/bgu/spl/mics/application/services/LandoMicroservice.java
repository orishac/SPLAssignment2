package bgu.spl.mics.application.services;

import bgu.spl.mics.Broadcast;
import bgu.spl.mics.Event;
import bgu.spl.mics.MicroService;
import bgu.spl.mics.application.messages.BombDestroyerEvent;
import bgu.spl.mics.application.messages.TerminateBroadcast;
import bgu.spl.mics.application.passiveObjects.Diary;

/**
 * LandoMicroservice
 * You can add private fields and public methods to this class.
 * You MAY change constructor signatures and even add new public constructors.
 */
public class LandoMicroservice  extends MicroService {

    private Diary diary;
    private Class<Event> BombDestroyerEvent;
    private Class<? extends Broadcast> TerminateBroadcast;
    private BombDestroyerEvent bomb;
    private long duration;

    public LandoMicroservice(long duration) {
        super("Lando");
        this.duration = duration;
        bomb = new BombDestroyerEvent();
    }

    @Override
    protected void initialize() {
        diary = Diary.getInstance();
        subscribeEvent(bomb.getClass(), Lando-> {
           try {
               handleBombDestroyer(bomb);
           } catch (InterruptedException e) {
               e.printStackTrace();
           }
       });
        l1countDown();
    }

    private void handleBombDestroyer(BombDestroyerEvent bomb) throws InterruptedException {
        //handle the bomb destroyer event
        this.bomb = bomb;
        Thread.sleep(duration);
        //write to diary
        subscribeBroadcast(TerminateBroadcast, (Lando)->terminate());
        sendBroadcast(new TerminateBroadcast());
    }

    private void writeDiary() {
        diary.setLandoTerminate(System.currentTimeMillis());
    }


}
