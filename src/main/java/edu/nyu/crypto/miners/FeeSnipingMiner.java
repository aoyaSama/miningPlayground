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
            if (block.getHeight() > currentHead.getHeight()) {
                this.currentHead = block;
            }
        }
        else {
            if (block.getHeight() > currentHead.getHeight()) {
                this.currentHead = block;
            }
        }
	}

	@Override
	public void networkUpdate(NetworkStatistics statistics) {

	}


}
