package bgu.spl.mics.application.passiveObjects;


import java.util.concurrent.ConcurrentLinkedDeque;
import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * Passive object representing the resource manager.
 * <p>
 * This class must be implemented as a thread-safe singleton.
 * You must not alter any of the given public methods of this class.
 * <p>
 * You can add ONLY private methods and fields to this class.
 */
public class Ewoks {

    private ConcurrentLinkedDeque<Ewok> ewokList;


    private static class EwoksHolder {
        private static Ewoks instance = new Ewoks();
    }

    private Ewoks() {
        ewokList = new ConcurrentLinkedDeque<>();
    }

    public static Ewoks getInstance() {
        return EwoksHolder.instance;
    }

    public void addEwok(Ewok ewok) {
        ewokList.add(ewok);
    }

    public Ewok getEwok(int serial) throws InterruptedException {
        for (Ewok ewok : ewokList) {
            if (ewok.getSerialNumber() == serial) {
                while (!ewok.isAvailable()) {
                    wait();
                }
                return ewok;
            }
        }
        return null;
    }


}
