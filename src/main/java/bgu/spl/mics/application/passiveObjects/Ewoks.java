package bgu.spl.mics.application.passiveObjects;


import java.util.LinkedList;
import java.util.List;
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


    public void release(int ewok) {
        for (Ewok e : ewokList)
            if (e.getSerialNumber()==ewok) {
                e.release();
            }
    }

    public void acquire(List<Integer> serials) {
        sortList(serials);
        for (Integer serial : serials) {
            for (Ewok ewok : ewokList) {
                if (ewok.getSerialNumber() == serial) {
                    ewok.acquire();
                }
            }
        }
    }

    private void sortList(List<Integer> serials) {
        int i=1;
        while (i<serials.size()) {
            int j=i;
            while (j>0 & serials.get(j-1) > serials.get(j)) {
                int temp = serials.get(j);
                serials.set(j, serials.get(j - 1));
                serials.set(j - 1, temp);
                j = j - 1;
            }
            i=i+1;
        }
    }


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
