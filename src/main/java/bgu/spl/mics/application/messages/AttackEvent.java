package bgu.spl.mics.application.messages;
import bgu.spl.mics.Event;
import bgu.spl.mics.application.passiveObjects.Attack;

import java.util.LinkedList;
import java.util.List;

public class AttackEvent implements Event<Boolean> {

    private List<Integer> serials;
    private int duration;
    private boolean isDone;

    public AttackEvent() {
    }

    public AttackEvent(Attack attack) {
        this.serials = attack.getSerials();
        this.duration = attack.getDuration();
        isDone=false;
    }

    public AttackEvent(AttackEvent attackEvent) {
        this.serials = attackEvent.getSerials();
        this.duration = attackEvent.getDuration();
        isDone=true;
    }

    public List<Integer> getSerials() {
        return serials;
    }

    public int getDuration() {
        return duration;
    }

    public void finished() {
        isDone = true;
    }
}
