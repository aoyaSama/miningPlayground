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
	private double miningPower;
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
			if (currentHead == null && block.getHeight() > 5) {
				currentHead = block;
			} else if (block != null && block.getHeight() > currentHead.getHeight() + 5 && majority) {
				this.currentHead = block;
			}
		}
	}

	@Override
	public void networkUpdate(NetworkStatistics statistics) {
        // get the current mining power
		miningPower = getHashRate() / statistics.getTotalHashRate();
        // if current mining power is under 51 set majority as false
		if (miningPower < 0.51)
			majority = false;
		else
			majority = true;
	}

}
