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
    private Block check;
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
                this.snipeBlock = block;
                if(snipeBlock.getHeight() - block.getHeight() == 0){

                }
                // snipeBlock = block;
                calculateAverage(block);

                if(this.currentHead.getHeight() == this.snipeBlock.getHeight()){
                    System.out.println("Gottem, block h " + block.getHeight());
                }

                // if( block.getBlockValue() > 500){
                //     System.out.println("ave" + this.averageReward + " value " + block.getBlockValue());
                //     System.out.println("current height " + block.getHeight());
                // }
            }
        }
        else {
            if (block.getHeight() > currentHead.getHeight()) {
                // if( this.currentHead.getHeight() == this.snipeHead.getHeight()){
                //     System.out.println("did not Gottem");
                // }
                // if( block.getBlockValue() > 25 && block.getHeight() < 50){
                //     System.out.println("ave" + this.averageReward + " value " + block.getBlockValue());
                //     System.out.println("current height " + block.getHeight());
                // }
                // if new block mined check block value
                // if(block.getBlockValue() < 1){
                //     System.out.println("ave" + this.averageReward + " value " + block.getBlockValue());
                // }
                int aheadBy = block.getHeight() - this.snipeBlock.getHeight();
                // if(aheadBy > 5){
                //     System.out.println("did not Gottem");
                // }
                if(block.getHeight() == this.snipeBlock.getHeight() ||
                    block.getBlockValue() < this.averageReward/successRate) {
                // if(block.getBlockValue()  < 22) {
                    this.currentHead = block;
                    calculateAverage(block);
                }
                // if block reward was unsually high, then announce preivous block
                else{
                    System.out.println("Snipe, block h " + block.getHeight());
                    snipeBlock = block;
                    Block prevBlock = block.getPreviousBlock();
                    this.currentHead = prevBlock;
                }
                /**
                 * Check if network accepts the previous, if it did, then
                 * work from new, if didn't, start from old block
                 */
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
        if(this.averageReward > block.getHeight()){
            this.averageReward = block.getHeight();
        }
        // else{
        //     this.averageReward = (double) this.blockValueTotal
        //         / this.currentHead.getHeight();
        // }

        // if( this.averageReward > 25){
        //     System.out.println("ave" + this.averageReward + " value " + block.getBlockValue());
        //     System.out.println("current height " + block.getHeight());
        // }
    }


    @Override
    public void initialize(Block genesis, NetworkStatistics networkStatistics) {
        this.currentHead = genesis;
        this.snipeBlock = genesis;

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
