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

package de.weltraumschaf.dht.id;

import java.util.UUID;
import org.apache.commons.codec.digest.DigestUtils;

/**
 *
 * @author Sven Strittmatter <weltraumschaf@googlemail.com>
 */
public final class NodeId extends BaseIdentity {

    public NodeId(byte[] id) {
        super(id);
    }

    public static NodeId create() {
        final String uuid = UUID.randomUUID().toString();
        return new NodeId(DigestUtils.sha1(uuid));
    }
}
