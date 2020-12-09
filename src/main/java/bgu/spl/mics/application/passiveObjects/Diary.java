package bgu.spl.mics.application.passiveObjects;


import java.util.concurrent.atomic.AtomicInteger;

/**
 * Passive data-object representing a Diary - in which the flow of the battle is recorded.
 * We are going to compare your recordings with the expected recordings, and make sure that your output makes sense.
 * <p>
 * Do not add to this class nothing but a single constructor, getters and setters.
 */
public class Diary {

    private AtomicInteger totalAttacks;
    private long HanSoloFinish;
    private long C3POFinish;
    private long R2D2Deactivate;
    private long LeiaTerminate;
    private long HanSoloTerminate;
    private long C3POTerminate;
    private long R2D2Terminate;
    private long LandoTerminate;


    private static class DiaryHolder {
        private static Diary instance = new Diary();
    }

    private Diary() {

    }

    public static Diary getInstance() {
        return Diary.DiaryHolder.instance;
    }

    public void setTotalAttacks() {
        totalAttacks.incrementAndGet();
    }

    public void setHanSoloFinish(long HanSoloFinish) {
        this.HanSoloFinish = HanSoloFinish;
    }

    public void setC3POFinish(long C3POFinish) {
        this.C3POFinish = C3POFinish;
    }

    public void setR2D2Deactivate(long R2D2Deactivate) {
        this.R2D2Deactivate = R2D2Deactivate;
    }

    public void setLeiaTerminate(long LeiaTerminate) {
        this.LeiaTerminate = LeiaTerminate;
    }

    public void setHanSoloTerminate(long HanSoloTerminate) {
        this.HanSoloTerminate = HanSoloTerminate;
    }

    public void setC3POTerminate(long C3POTerminate) {
        this.C3POTerminate = C3POTerminate;
    }

    public void setR2D2Terminate(long R2D2Terminate) {
        this.R2D2Terminate = R2D2Terminate;
    }

    public void setLandoTerminate(long LandoTerminate) {
        this.LandoTerminate = LandoTerminate;
    }

}
