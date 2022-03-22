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
    private double blockValueTotal = 0.0;
    private double averageReward = 0.0;
    private double successRate = 0.0;

    public FeeSnipingMiner(String id, int hashRate, int connectivity) {
        super(id, hashRate, connectivity);
    }

    @Override
	public void blockMined(Block block, boolean isMinerMe) {
        if(isMinerMe) {
            if (block.getHeight() > this.currentHead.getHeight()) {
                this.currentHead = block;
                calculateAverage(block);
                // System.out.println(this.averageReward);
            }
        }
        else {
            if (block.getHeight() > currentHead.getHeight()) {
                // if new block mined check block value
                // if(block.getBlockValue() < 1){
                //     System.out.println("ave" + this.averageReward + " value " + block.getBlockValue());
                // }
                if(block.getBlockValue() < this.averageReward/successRate) {
                // if(block.getBlockValue()  < 32) {
                    this.currentHead = block;
                    calculateAverage(block);
                }
                // if block reward was unsually high, then announce preivous block
                else{
                    Block prevBlock = block.getPreviousBlock();
                    this.currentHead = prevBlock;
                }
            }
        }
	}

    private void calculateAverage(Block block) {
        // if(this.currentHead.getBlockValue() > this.averageReward){
        //     this.blockValueTotal += this.averageReward;
        // }
        // else{
        //     this.blockValueTotal += this.currentHead.getBlockValue();
        // }
        // this.blockValueTotal += this.currentHead.getBlockValue() * this.miningPower;
        // if(currentHead.getHeight() > this.previousHeight){
        //     this.blockValueTotal += block.getBlockValue();
        //     this.averageReward = (double) this.blockValueTotal
        //         / this.currentHead.getHeight();
        //     this.previousHeight = currentHead.getHeight();
        // }
        this.blockValueTotal += block.getBlockValue();
        this.averageReward = (double) this.blockValueTotal
            / this.currentHead.getHeight();
        // if( this.averageReward > 25){
        //     System.out.println("ave" + this.averageReward + " value " + block.getBlockValue());
        //     System.out.println("current height " + block.getHeight());
        // }
    }


    @Override
    public void initialize(Block genesis, NetworkStatistics networkStatistics) {
        this.currentHead = genesis;

        // reset per simulation
        this.blockValueTotal = 0.0;
        this.averageReward = 0.0;
        this.successRate = 0.0;
    }


    @Override
	public void networkUpdate(NetworkStatistics statistics) {
        // get the current mining power
		double miningPower = (double) this.getHashRate()
            / statistics.getTotalHashRate();

        // chance of succeeding if every other miner is honest
        successRate = Math.pow(miningPower/(1-miningPower), 2);
	}
}
