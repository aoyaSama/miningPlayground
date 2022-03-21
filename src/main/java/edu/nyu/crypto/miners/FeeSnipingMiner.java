package edu.nyu.crypto.miners;

import java.util.ArrayList;
import java.util.List;

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
    private List<Double> previousRewards = new ArrayList<>();
    private double blockValueTotal = 0.0;
    private double averageReward = 0.0;
    private double miningPower = 0.0;

    public FeeSnipingMiner(String id, int hashRate, int connectivity) {
        super(id, hashRate, connectivity);

    }

    @Override
	public void blockMined(Block block, boolean isMinerMe) {
        if(isMinerMe) {
            if (block.getHeight() > this.currentHead.getHeight()) {
                this.currentHead = block;
                calculateAverage();
                // System.out.println(this.averageReward);
            }
        }
        else {
            if (block.getHeight() > currentHead.getHeight()) {

                // if new block mined check block value
                // if(block.getBlockValue() > 4000){
                //     System.out.println("420");
                //     System.out.println(this.averageReward);
                //     System.out.println(block.getHeight());
                //     System.out.println(currentHead.getHeight());
                // }
                // if(block.getBlockValue() < this.averageReward) {
                if(block.getBlockValue() < 32) {
                    this.currentHead = block;
                    calculateAverage();
                }
                // if block reward was unsually high, then announce preivous block
                else{
                    Block prevBlock = block.getPreviousBlock();
                    this.currentHead = prevBlock;
                }
            }
        }
	}

    private void calculateAverage(){
        this.blockValueTotal += this.currentHead.getBlockValue();
        this.averageReward = (double) this.blockValueTotal
            / this.currentHead.getHeight();
    }


    @Override
	public void networkUpdate(NetworkStatistics statistics) {
        // get the current mining power
		miningPower = (double) this.getHashRate() / statistics.getTotalHashRate();
	}
}
