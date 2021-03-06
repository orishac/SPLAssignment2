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
        totalAttacks = new AtomicInteger();
        totalAttacks.set(0);
        HanSoloFinish = 0;
        C3POFinish = 0;
        R2D2Deactivate = 0;
        LeiaTerminate = 0;
        HanSoloTerminate = 0;
        C3POTerminate = 0;
        R2D2Terminate = 0;
        LandoTerminate = 0;
    }

    public static Diary getInstance() {
        return DiaryHolder.instance;
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

    public AtomicInteger getNumberOfAttacks() {
        return totalAttacks;
    }

    public long getC3POFinish() {
        return C3POFinish;
    }

    public long getHanSoloFinish() {
        return HanSoloFinish;
    }

    public long getR2D2Deactivate() {
        return R2D2Deactivate;
    }

    public long getHanSoloTerminate() {
        return HanSoloTerminate;
    }

    public long getC3POTerminate() {
        return C3POTerminate;
    }

    public long getLandoTerminate() {
        return LandoTerminate;
    }

    public long getR2D2Terminate() {
        return R2D2Terminate;
    }

    public void resetNumberAttacks() {
        totalAttacks.set(0);
    }


}
