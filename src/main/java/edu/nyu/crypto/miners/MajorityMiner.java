package edu.nyu.crypto.miners;

import edu.nyu.crypto.blockchain.Block;
import edu.nyu.crypto.blockchain.NetworkStatistics;

/**
 * Majority Miner Class
 * Perform a 51% attack if it is capable
 *
 * Student name: Takemitsu Yamanaka
 * Student ID:   757038
 */
public class MajorityMiner extends CompliantMiner implements Miner {
	private boolean majority = false;

    public MajorityMiner(String id, int hashRate, int connectivity) {
        super(id, hashRate, connectivity);
    }

	@Override
	public void blockMined(Block block, boolean isMinerMe) {
		if (isMinerMe) {
			if (block.getHeight() > currentHead.getHeight()) {
				this.currentHead = block;
			}
		} else {
			// if this miner isn't the majority then announce new block as recongised
			// otherwise do nothing
			if (!this.majority && block.getHeight() > currentHead.getHeight()) {
				this.currentHead = block;
			}
		}
	}

	@Override
	public void networkUpdate(NetworkStatistics statistics) {
        // get the current mining power
		double miningPower = (double) this.getHashRate() / statistics.getTotalHashRate();
        // if current mining power is under 51 set majority as
		if (miningPower < 0.51)
			this.majority = false;
		else
			this.majority = true;
	}
}
