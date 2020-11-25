package bgu.spl.mics.application.services;

import bgu.spl.mics.Broadcast;
import bgu.spl.mics.MicroService;

public class testMicroservice extends MicroService {

    private boolean check = false;
    /**
     * @param name the micro-service name (used mainly for debugging purposes -
     *             does not have to be unique)
     */
    public testMicroservice(String name) {
        super(name);
    }

    @Override
    protected void initialize() {

    }



}
