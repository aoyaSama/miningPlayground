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
	private Block selfishHead;
	private boolean attack = false;

	public SelfishMiner(String id, int hashRate, int connectivity) {
		super(id, hashRate, connectivity);
	}

	@Override
	public Block currentlyMiningAt() {
		return this.selfishHead;
	}

	@Override
	public void blockMined(Block block, boolean isMinerMe) {
		if(isMinerMe) {
			if (block.getHeight() > selfishHead.getHeight()) {
				this.selfishHead = block;
				// start block withholding attack
				this.attack = true;
			}
        }
        else {
			int selfishAheadBy = selfishHead.getHeight() - block.getHeight();
			int aheadBy = block.getHeight() - currentHead.getHeight();

			// if another block is announced with an height just 1 below this
			// miner's block height then then announce this miner's secret branch
			// only if the announced block isn't the same height as public block
			// or if new block mined is same height as this miner's
			// unannounced block then set current head as the selfish head
			// and hope the network choose this miner's block
			if (this.attack &&
				((selfishAheadBy == 1 && aheadBy > 0) || selfishAheadBy == 0)){
				this.currentHead = this.selfishHead;
				// reset attack once block is announced
				this.attack = false;
			}
			// if new block is higher than reset attack
			else if (block.getHeight() > selfishHead.getHeight()) {
				this.currentHead = block;
				this.selfishHead = block;
				this.attack = false;
			}
        }
	}

    @Override
    public void initialize(Block genesis, NetworkStatistics networkStatistics) {
        this.currentHead = genesis;
		// also set gensis block as the selfish head
		this.selfishHead = genesis;
    }
}