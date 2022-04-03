package edu.nyu.crypto.miners;

import edu.nyu.crypto.blockchain.Block;
import edu.nyu.crypto.blockchain.NetworkStatistics;

/**
 * Fee Snipping Miner class
 * Forks the blockchain to try stealing  unsually valualbe blocks when profitable
 *
 * Student name: Takemitsu Yamanaka
 * Student ID:   757038
 */
public class FeeSnipingMiner extends CompliantMiner implements Miner {
    private Block snipeBlock;
    private boolean fork;
    private int blockHeightTotal = 0;
    private double successRate = 0.0;
    private double blockValueTotal = 0.0;
    private double averageReward = 0.0;

    public FeeSnipingMiner(String id, int hashRate, int connectivity) {
        super(id, hashRate, connectivity);
    }


    @Override
    public void blockMined(Block block, boolean isMinerMe) {
        if(isMinerMe) {
            if (block.getHeight() > currentHead.getHeight()) {
                this.currentHead = block;
                calculateAverage(block);
            }
        }
        else{
           if (block.getHeight() > snipeBlock.getHeight()) {

                this.snipeBlock = block;
                int advance = currentHead.getHeight() - snipeBlock.getHeight();

                // if this miner isn't on a new fork, check block reward to
                // decide whether to attack or not
                if (!fork) {

                    // if block reward is worth to mine based on success rate
                    if (block.getBlockValue() > this.averageReward/successRate) {
                        fork = true;
                    }
                    else {
                        fork = false;
                        this.currentHead = block;
                        calculateAverage(block);
                    }
                }
                else {
                    // if this miner is in a new fork, then check if we won
                    // the race to win the sniping test, if not, reset attack
                   if (advance < -1) {
                       fork = false;
                       currentHead = snipeBlock;
                       calculateAverage(block);
                   }
                }
            }
        }
    }


    /**
     * Calculates the average block reward.
     * This will run throughout the simulation, therefore if 100 iteration of
     * simulation is ran, then we'll have over 99 iterations worth of data
     *
     * @param block the new block to add
     */
    private void calculateAverage(Block block) {
        this.blockValueTotal += block.getBlockValue();
        this.blockHeightTotal += 1;
        this.averageReward = (double) this.blockValueTotal
                / this.blockHeightTotal;
    }


    @Override
    public void initialize(Block genesis, NetworkStatistics networkStatistics) {
        fork = false;
        currentHead = genesis;
        snipeBlock = genesis;
    }


    @Override
    public void networkUpdate(NetworkStatistics statistics) {
        // get the current mining power
		double miningPower = (double) this.getHashRate()
            / statistics.getTotalHashRate();

        // chance of succeeding if every other miner is honest
        this.successRate = Math.pow(miningPower/(1-miningPower), 2);
        // this.successRate = Math.pow(miningPower, 2);
    }
}