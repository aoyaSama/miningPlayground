# miningPlayground
Class assignment to study Bitcoin mining strategies in a simple simulator.

Students should modify the classes MajorityMiner, SelfishMiner and FeeSnipingMiner, overriding methods as necessary to implement the desired functionality.

1. MajorityMiner : that performs a 51% attack if it is capable.
    - Attack becoms feasible when:
        - Malicious party/miner/mining pool gas majority of the hash power, α > 0.5
        - Mining on a fork that's not 6 blocks behind the longest chain

2. SelfishMiner that performs a temporary block withholding attack if profitable.
    - When the attacker finds a block before the rest of the network,
        rather than announcing this block, it'll keep it a secret
    - Attacker announce their branch only when:
      - The public branch is one block behind the attacker's secret branch.
      - The public branch and attacker's branch are the same height, then both are announced to the network. Letting the network decide which branch to work on.

1. FeeSnipingMiner that forks to try stealing unusually valuable blocks when profitable. That is, when a block with an unusually large transaction fee is mined by a competitor, this miner should temporarily reject that block and try to re-mine a longer fork where it keeps the large transaction fee for itself.

# References

- [Selfish Mining](https://decentralizedthoughts.github.io/2020-02-26-selfish-mining)

# Author
MajorityMiner.java, FeeSnipingMiner.java, and SelfishMiner.java
Takemitsu Yamanaka #757038

Code forked from @jcb82

This assignment was originally developed by Joseph Bonneau and Benedikt Bünz at Stanford, with later development by Joseph Bonneau, Assimakis Kattis and Kevin Choi at NYU.