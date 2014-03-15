# Welcome to the DHT Project

This  project  plays  around  with  [distributed  hash  tables][dht].  The  core
priciples is  based on  [Kademlia][kademlia]. But  instead of  an 160  bit SHA-1
hash a 128 bit [UUID][uuid] is used as key/node id.

## Libraries

- The messages sent over the network are serialized with [Kryo][kryo].
- The command line options are parsed wiwth [JCommander][jcommander].
- Theinteracive shell uses [JLine][jline].

[dht]:          https://en.wikipedia.org/wiki/Distributed_hash_table
[kademlia]:     http://xlattice.sourceforge.net/components/protocol/kademlia/specs.html
[uuid]:         http://en.wikipedia.org/wiki/Universally_unique_identifier
[kryo]:         https://github.com/EsotericSoftware/kryo
[jcommander]:   http://jcommander.org/
[jline]:        https://github.com/jline/jline2
