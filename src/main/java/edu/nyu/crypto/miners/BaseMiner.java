package edu.nyu.crypto.miners;

import java.text.MessageFormat;


public abstract class BaseMiner implements Miner {
    private int connectivity;
    private int originalConnectivity;
    private int hashRate;
    private int originalHashRate;
    private final String id;

    protected BaseMiner(String id, int hashRate, int connectivity) {
        this.id = id;
        this.originalHashRate = this.hashRate = hashRate;
        this.originalConnectivity = this.connectivity = connectivity;
    }

    @Override
    public final int getConnectivity() {
        return connectivity;
    }

    @Override
    public final void setConnectivity(int connectivity) {
        this.connectivity = connectivity;
    }

    @Override
    public final void resetConnectivity() {
        this.connectivity = this.originalConnectivity;
    }


    @Override
    public final void setHashRate(int hashRate) {
        this.hashRate = hashRate;
    }

    @Override
    public final void resetHashRate() {
        this.hashRate = this.originalHashRate;
    }


    @Override
    public final int getHashRate() {
        return hashRate;
    }

    @Override
    public String getId() {
        return id;
    }

    @Override
    public String toString() {
        return MessageFormat.format("Miner[id={0},hashRate={1},connectivity={2}]", id, hashRate, connectivity);
    }

}
