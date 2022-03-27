package edu.nyu.crypto.miners;

import java.util.ArrayList;

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
    private int advance = 0;
    private double successRate = 0.0;
    private int blockHeightTotal = 0;
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
                // advance = currentHead.getHeight() - otherHead.getHeight();
            }
        }
        else{
           if (block.getHeight() > snipeBlock.getHeight()) {
                snipeBlock = block;
                advance = currentHead.getHeight() - snipeBlock.getHeight();
                
                if (!fork) {
                    // if (fork2Reward > 1.5) {
                    if (block.getBlockValue() > this.averageReward/successRate) {
                        // Should we fork longer?
                        // 1 is ignoring potential rewards from not forking, so we need to choose something higher
                        // To simplify we give up if we are more than one block behind.
                        // Optimal behaviour would be to adapt that to the reward.
                        fork = true;
                    }
                    else {
                        fork = false;
                        this.currentHead = block;
                        calculateAverage(block);
                    }
                }
                else {
                   if (advance < -1) {
                       fork = false;
                       currentHead = snipeBlock;
                       calculateAverage(block);
                   }
                }

            }
        }
    }


    private void calculateAverage(Block block) {
        this.blockValueTotal += block.getBlockValue();
        this.blockHeightTotal += 1;
        this.averageReward = (double) this.blockValueTotal
                / this.blockHeightTotal;
        if(this.averageReward > block.getHeight()){

            // System.out.println("ave" + this.averageReward + " value " + block.getBlockValue());
            // this.averageReward = 1;
            // this.averageReward = block.getHeight();
        }
    }


    @Override
    public void initialize(Block genesis, NetworkStatistics networkStatistics) {
        fork = false;
        advance = 0;
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