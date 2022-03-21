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
	private boolean profitable = false;
	private double miningPower;

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
				// this.selfishHead = block;
				int aheadBy = block.getHeight() - selfishHead.getHeight();
				// attack when profitable and ahead of the announced block
				if(this.profitable){
					this.selfishHead = block;
				}
				// else{
				// 	this.selfishHead = block;
				// 	this.currentHead = block;
				// }
				// announce mined block if not profitable to attack
				// // announce when ahead by 2 blocks and is attacking
				// // announce mined block if not profitable to attack

				// announce when ahead by 2 blocks and is attacking
			}
        }
        else {
			int aheadBySelfish = selfishHead.getHeight() - currentHead.getHeight();
			int aheadByCurrent = block.getHeight() - currentHead.getHeight();

			// if another block is announced when this miner is withholding
			// 2 or more blocks then announce this miner's block
			if (aheadByCurrent == 1){
				// System.out.println("420");
				this.currentHead = this.selfishHead;
			}
			// if new block mined is same height as this miner's
			// unannounced block then set current head as the selfish head
			// and hope the network choose this miner's block
			if(block.getHeight() == selfishHead.getHeight()){
				this.currentHead = this.selfishHead;
			}

			// if new block is higher than reset attack
			if (block.getHeight() > selfishHead.getHeight()) {
					this.currentHead = block;
					this.selfishHead = block;
			}
			//reset attack when blocks are announced
			// this.attack = false;
        }
	}

    @Override
    public void initialize(Block genesis, NetworkStatistics networkStatistics) {
        this.currentHead = genesis;
		// also set gensis block as the selfish head
		this.selfishHead = genesis;
    }

	@Override
	public void networkUpdate(NetworkStatistics statistics) {
		// Selfish mining still profitable if a > 33.3%
		// Only start selfish attack when profitable
		miningPower = (double) getHashRate() / statistics.getTotalHashRate();
		if (miningPower > 0.333)
			this.profitable = true;
		else
			this.profitable = false;
	}
}