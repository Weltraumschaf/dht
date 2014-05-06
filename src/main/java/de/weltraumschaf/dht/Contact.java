/*
 *  LICENSE
 *
 * "THE BEER-WARE LICENSE" (Revision 43):
 * "Sven Strittmatter" <weltraumschaf@googlemail.com> wrote this file.
 * As long as you retain this notice you can do whatever you want with
 * this stuff. If we meet some day, and you think this stuff is worth it,
 * you can buy me a non alcohol-free beer in return.
 *
 * Copyright (C) 2012 "Sven Strittmatter" <weltraumschaf@googlemail.com>
 */

package de.weltraumschaf.dht;

import de.weltraumschaf.commons.guava.Objects;
import de.weltraumschaf.dht.data.KBucketKey;
import de.weltraumschaf.dht.id.NodeId;
import de.weltraumschaf.dht.net.NetworkAddress;
import org.apache.commons.lang3.Validate;

/**
 * The data how a node is reachable.
 * <p>
 * This entity is immutable and will be stored in the DHT.
 * </p>
 *
 * @author Sven Strittmatter <weltraumschaf@googlemail.com>
 */
public final class Contact implements KBucketKey {

    /**
     * Global unique user id.
     */
    private final NodeId nodeId;
    /**
     * Host on which the users client listen.
     */
    private final NetworkAddress networkAddress;

    public Contact(final NetworkAddress networkAddress) {
        this(NodeId.NULL, networkAddress);
    }

    /**
     * Dedicated constructor.
     *
     * @param userId must not be {@code null}
     * @param networkAddress must not be {@code null}
     */
    public Contact(final NodeId userId, final NetworkAddress networkAddress) {
        super();
        this.nodeId = Validate.notNull(userId, "Parameter >userId< must not be null!");
        this.networkAddress = Validate.notNull(networkAddress, "Parameter >networkAddress< must not be null!");
    }

    /**
     * Unique id of node.
     *
     * @return never {@code null}
     */
    public NodeId getNodeId() {
        return nodeId;
    }

    /**
     * Get the network address.
     *
     * @return never {@code null}
     */
    public NetworkAddress getNetworkAddress() {
        return networkAddress;
    }

    @Override
    public String toString() {
        return Objects.toStringHelper(this).add("nodeId", nodeId).add("networkAddress", networkAddress).toString();
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(nodeId, networkAddress);
    }

    @Override
    public boolean equals(final Object obj) {
        if (!(obj instanceof Contact)) {
            return false;
        }

        final Contact other = (Contact) obj;
        return Objects.equal(nodeId, other.getNodeId())
            && Objects.equal(networkAddress, other.getNetworkAddress());
    }

    @Override
    public byte[] data() {
        return nodeId.data();
    }



}
