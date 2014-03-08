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
package de.weltraumschaf.dht.cmd;

import de.weltraumschaf.commons.shell.Token;
import de.weltraumschaf.commons.shell.TokenType;
import de.weltraumschaf.dht.msg.Message;
import de.weltraumschaf.dht.msg.Messaging;
import de.weltraumschaf.dht.server.PortValidator;
import de.weltraumschaf.dht.shell.CommandRuntimeException;
import de.weltraumschaf.dht.shell.CommandArgumentExcpetion;
import de.weltraumschaf.dht.shell.CommandMainType;
import static de.weltraumschaf.dht.shell.CommandMainType.SEND;
import java.io.IOException;
import java.util.List;
import org.apache.commons.lang3.Validate;

/**
 * Executes `send` command.
 *
 * See http://www.javaworld.com/article/2077322/core-java/sockets-programming-in-java-a-tutorial.html
 *
 * @author Sven Strittmatter <weltraumschaf@googlemail.com>
 */
final class Send extends BaseCommand {

    @Override
    public Descriptor getDescriptor() {
        return new BaseDescriptor() {

            @Override
            public CommandMainType getCommand() {
                return SEND;
            }

            @Override
            public String getUsage() {
                return "send <host> <port> <message>";
            }

            @Override
            public String getHelpDescription() {
                return String.format("Sends <message> to <host:port>. The message must be encapsulated in quotes if it "
                        + "has more than one word. Port must be in range of %s.", PortValidator.range());
            }
        };
    }

    @Override
    public void execute() {
        final Arguments args = validateArguments();
        final Message message = Messaging.newMessage(
                newLocalAddress(),
                newAddress(args.getHost(), args.getPort()),
                args.getMessage());

        try {
            Messaging.newSender().send(message);
            println(String.format("Message sent to %s:%d.", args.getHost(), args.getPort()));
        } catch (final IOException ex) {
            throw new CommandRuntimeException(
                    String.format("Can't open client conection to %s:%s!", args.getHost(), args.getPort()), ex);
        }
    }

    private Arguments validateArguments() throws CommandArgumentExcpetion {
        final List<Token> args = getArguments();

        @SuppressWarnings("unchecked")
        final Token<String> hostToken = args.get(0);

        if (hostToken.getType() != TokenType.LITERAL) {
            throw new CommandArgumentExcpetion("Host must be a literal!");
        }

        @SuppressWarnings("unchecked")
        final Token<Integer> portToken = args.get(1);

        if (portToken.getType() != TokenType.NUMBER) {
            throw new CommandArgumentExcpetion("Port must be a number!");
        }

        final Integer port = portToken.getValue();

        if (!PortValidator.isValid(port)) {
            throw new CommandArgumentExcpetion(
                    String.format("Parameter >port< must be in range %s!", PortValidator.range()));
        }

        @SuppressWarnings("unchecked")
        final Token<String> messageToken = args.get(2);

        if (messageToken.getType() != TokenType.STRING && messageToken.getType() != TokenType.LITERAL) {
            throw new CommandArgumentExcpetion("Message must be a string or literal!");
        }

        return new Arguments(hostToken.getValue(), port, messageToken.getValue());
    }

    /**
     * Container for the command arguments.
     */
    private static final class Arguments {

        /**
         * Host to send the message.
         */
        private final String host;
        /**
         * Port to send the message.
         */
        private final int port;
        /**
         * Message to send.
         */
        private final String message;

        /**
         * Dedicated constructor.
         *
         * @param host must not be {@code null} or empty
         * @param port must be in {@link PortValidator#range() specified range}
         * @param message must not be {@code null} or empty
         */
        public Arguments(final String host, final int port, final String message) {
            super();
            this.host = Validate.notEmpty(host, "Parameter >host< must not be null or empty!");
            Validate.isTrue(PortValidator.isValid(port),
                    String.format("Parameter >port< must be in range %s! Given >%d<.", PortValidator.range(), port));
            this.port = port;
            this.message = Validate.notEmpty(message, "Parameter >message< must not be null or empty!");
        }

        /**
         * Get the host.
         *
         * @return never {@code null} or empty
         */
        public String getHost() {
            return host;
        }

        /**
         * Get the port.
         *
         * @return {@link PortValidator#range() range}
         */
        public int getPort() {
            return port;
        }

        /**
         * Get the message.
         *
         * @return never {@code null} or empty
         */
        public String getMessage() {
            return message;
        }

    }
}
