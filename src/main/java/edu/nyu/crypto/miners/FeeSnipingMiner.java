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

    public FeeSnipingMiner(String id, int hashRate, int connectivity) {
        super(id, hashRate, connectivity);

    }

    @Override
	public void blockMined(Block block, boolean isMinerMe) {
        if(isMinerMe) {
            if (block.getHeight() > this.currentHead.getHeight()) {
                this.currentHead = block;
            }
        }
        else {
            if (block.getHeight() > currentHead.getHeight()) {
                // System.out.println(block.getBlockValue());
                // if new block mined check block value
                if(block.getBlockValue() < 16) {
                    this.currentHead = block;
                }
                // if block reward was unsually high, then announce preivous block
                else{
                    Block prevBlock = block.getPreviousBlock();
                    this.currentHead = prevBlock;
                }
            }
        }
	}
}
