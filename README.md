# miningPlayground
Class assignment to study Bitcoin mining strategies in a simple simulator.

Students should modify the classes MajorityMiner, SelfishMiner and FeeSnipingMiner, overriding methods as necessary to implement the desired functionality.

1. MajorityMiner : that performs a 51% attack if it is capable.

2. SelfishMiner that performs a temporary block withholding attack if profitable.

3. FeeSnipingMiner that forks to try stealing unusually valuable blocks when profitable. That is, when a block with an unusually large transaction fee is mined by a competitor, this miner should temporarily reject that block and try to re-mine a longer fork where it keeps the large transaction fee for itself.


# Author
MajorityMiner.java, FeeSnipingMiner.java, and SelfishMiner.java
Takemitsu Yamanaka #757038

Code forked from @jcb82

This assignment was originally developed by Joseph Bonneau and Benedikt Bünz at Stanford, with later development by Joseph Bonneau, Assimakis Kattis and Kevin Choi at NYU.