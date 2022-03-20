package edu.nyu.crypto.miners;

import edu.nyu.crypto.blockchain.Block;
import edu.nyu.crypto.blockchain.NetworkStatistics;

/**
 * Selfish Miner class
 * Performs a temporary block withholding attack if profitable
 *
 * Student name: Takemitsu Yamanaka
 * Student ID:   757038
 */
public class SelfishMiner extends CompliantMiner implements Miner {

	public SelfishMiner(String id, int hashRate, int connectivity) {
		super(id, hashRate, connectivity);
	}


	@Override
	public void blockMined(Block block, boolean isMinerMe) {

	}

	@Override
	public void networkUpdate(NetworkStatistics statistics) {

	}
}
