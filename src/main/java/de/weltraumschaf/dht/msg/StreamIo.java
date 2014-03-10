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
package de.weltraumschaf.dht.msg;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

/**
 *
 * @author Sven Strittmatter <weltraumschaf@googlemail.com>
 */
public final class StreamIo {

    private StreamIo() {
        super();
    }

    public static void write(final DataOutputStream output, final RawMessage raw) throws IOException {
        output.writeInt(raw.getType());
        final byte[] data = raw.getData();
        output.writeInt(data.length);
        output.write(data, 0, data.length);
    }

    public static RawMessage read(final DataInputStream input) throws IOException {
        final int type = input.readInt();
        final int length = input.readInt();
        final byte[] data = new byte[length];

        input.readFully(data);

        return new RawMessage(type, data);
    }
}
