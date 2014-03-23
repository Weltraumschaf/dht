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
import de.weltraumschaf.dht.CliOptions;
import de.weltraumschaf.dht.Contact;
import de.weltraumschaf.dht.id.NodeId;
import de.weltraumschaf.dht.msg.Message;
import de.weltraumschaf.dht.msg.MessageType;
import de.weltraumschaf.dht.msg.Messaging;
import de.weltraumschaf.dht.server.PortValidator;
import de.weltraumschaf.dht.shell.CommandArgumentExcpetion;
import de.weltraumschaf.dht.shell.CommandMainType;
import static de.weltraumschaf.dht.shell.CommandMainType.BOOTSTRAP;
import de.weltraumschaf.dht.shell.CommandRuntimeException;
import java.io.IOException;
import java.util.List;
import org.apache.commons.lang3.Validate;

/**
 * Executes `bootstrap` command.
 *
 * @author Sven Strittmatter <weltraumschaf@googlemail.com>
 */
public class Bootstrap extends BaseCommand {

    @Override
    public Descriptor getDescriptor() {
        return new BaseDescriptor() {

            @Override
            public CommandMainType getCommand() {
                return BOOTSTRAP;
            }

            @Override
            public String getUsage() {
                return String.format("%s <nodeId> <host> [ <port> ]", BOOTSTRAP.toString());
            }

            @Override
            public String getHelpDescription() {
                return String.format(
                    "Bootstraps the network. If optional port is not given, then %d is used.",
                    CliOptions.DEFAULT_PORT);
            }
        };
    }

    @Override
    public void execute() {
        println("Start bootstrapping...");

        try {
            bootstrap();
        } catch (IOException ex) {
            throw new CommandRuntimeException("Can't bootstrap!", ex);
        }

        println("Bootstrapping done.");
    }

    private void bootstrap() throws IOException {
        final Arguments args = validateArguments();
        final Contact bootsrapNode = new Contact(args.getNodeId(), newAddress(args.getHost(), args.getPort()));
        final Contact self = new Contact(getApplicationContext().getNodeId(), newLocalAddress());

        findNode(bootsrapNode, self);
        insertInBucket(bootsrapNode);
    }

    private Arguments validateArguments() throws CommandArgumentExcpetion {
        final List<Token> args = getArguments();

        @SuppressWarnings("unchecked")
        final Token<String> nodeIdToken = args.get(0);

        if (nodeIdToken.getType() != TokenType.LITERAL) {
            throw new CommandArgumentExcpetion("NodeId must be a literal!");
        }

        final NodeId nodeId = NodeId.valueOf(nodeIdToken.getValue());

        @SuppressWarnings("unchecked")
        final Token<String> hostToken = args.get(1);

        if (hostToken.getType() != TokenType.LITERAL) {
            throw new CommandArgumentExcpetion("Host must be a literal!");
        }

        if (args.size() == 1) {
            return new Arguments(nodeId, hostToken.getValue(), CliOptions.DEFAULT_PORT);
        }

        @SuppressWarnings("unchecked")
        final Token<Integer> portToken = args.get(2);

        if (portToken.getType() != TokenType.NUMBER) {
            throw new CommandArgumentExcpetion("Port must be a number!");
        }

        final Integer port = portToken.getValue();

        if (!PortValidator.isValid(port)) {
            throw new CommandArgumentExcpetion(
                    String.format("Parameter >port< must be in range %s!", PortValidator.range()));
        }

        return new Arguments(nodeId, hostToken.getValue(), port);
    }

    private void findNode(final Contact bootsrapNode, final Contact self) throws IOException {
        final Message message = Messaging.newProtocollMessage(
            MessageType.FIND_NODE,
            self.getNetworkAddress(), bootsrapNode.getNetworkAddress(),
            null);
        Messaging.newSender().send(message);
        getApplicationContext().getOutbox().put(message);
    }

    private void insertInBucket(final Contact bootsrapNode) {
        getApplicationContext().getkBucket().add(bootsrapNode);
    }

    /**
     * Container for the command arguments.
     */
    private static final class Arguments {
        private final NodeId nodeId;
        /**
         * Host to send the message.
         */
        private final String host;
        /**
         * Port to send the message.
         */
        private final int port;
        /**
         * Dedicated constructor.
         *
         * @param nodeId must not be {@code null} or empty
         * @param host must not be {@code null} or empty
         * @param port must be in {@link PortValidator#range() specified range}
         */
        public Arguments(final NodeId nodeId, final String host, final int port) {
            super();
            this.nodeId = Validate.notNull(nodeId, "Parameter >nodeId< must not be null!");
            this.host = Validate.notEmpty(host, "Parameter >host< must not be null or empty!");
            Validate.isTrue(PortValidator.isValid(port),
                    String.format("Parameter >port< must be in range %s! Given >%d<.", PortValidator.range(), port));
            this.port = port;
        }

        public NodeId getNodeId() {
            return nodeId;
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
    }

}
