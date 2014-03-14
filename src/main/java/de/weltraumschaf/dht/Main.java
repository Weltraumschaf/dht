package de.weltraumschaf.dht;

import de.weltraumschaf.dht.id.NodeId;
import com.beust.jcommander.JCommander;
import de.weltraumschaf.commons.ApplicationException;
import de.weltraumschaf.commons.InvokableAdapter;
import de.weltraumschaf.commons.Version;
import de.weltraumschaf.dht.log.Log;
import de.weltraumschaf.dht.log.Logger;
import de.weltraumschaf.dht.msg.MessageBox;
import de.weltraumschaf.dht.msg.Messaging;
import de.weltraumschaf.dht.server.Server;
import de.weltraumschaf.dht.shell.InteractiveShell;
import java.io.IOException;
import org.apache.commons.lang3.Validate;

public final class Main extends InvokableAdapter implements Application {

    /**
     * Logging facility.
     */
    private static final Logger LOG = Log.getLogger(Main.class);
    /**
     * JAR relative path to version property file.
     */
    private static final String VERSION_FILE = "/de/weltraumschaf/dht/version.properties";

    /**
     * Version of the application.
     */
    private final Version version = new Version(VERSION_FILE);

    private final CliOptions options = new CliOptions();
    private final JCommander cliOptionsParser = new JCommander();
    private final InteractiveShell shell = new InteractiveShell(this);
    private final MessageBox inbox = Messaging.newMessageBox();
    private final MessageBox outbox = Messaging.newMessageBox();
    private final NodeId nodeId = NodeId.newRandom();
    private final Clock clock = new Clock().start();
    private Server server;

    /**
     * Dedicated constructor.
     *
     * @param args command line arguments provided by JVM
     * @throws ApplicationException if {@link #processor} can't be instantiated
     * @throws ClassNotFoundException if command classes can't be found
     */
    public Main(final String[] args) throws ApplicationException, ClassNotFoundException {
        super(args);
    }

    public void initEnvironment() throws ApplicationException {
        cliOptionsParser.setProgramName(NAME);
        cliOptionsParser.addObject(options);
        cliOptionsParser.parse(getArgs());

        try {
            version.load();
        } catch (final IOException ex) {
            throw new ApplicationException(ExitCodeImpl.FATAL, "Can't load version file!", ex);
        }

        server = new Server(getIoStreams(), inbox);
        server.setHost(options.getHost());
        server.setPort(options.getPort());

        registerShutdownHook(new Runnable() {

            @Override
            public void run() {
                if (server.isRunning()) {
                    try {
                        server.stop();
                    } catch (final IOException | InterruptedException ex) {
                        getIoStreams().println("Error: " + ex);

                        if (options.isDebug()) {
                            getIoStreams().printStackTrace(ex);
                        }

                        LOG.error(ex.getMessage(), ex);
                    }
                }
            }
        });
    }

    /**
     * Main entry point of application invoked by JVM.
     *
     * @param args command line arguments provided by JVM
     * @throws ApplicationException if errors occurs while creating report processor
     */
    public static void main(final String[] args) throws ApplicationException, ClassNotFoundException {
        main(new Main(args));
    }

    /**
     * Setup and invokes the application.
     *
     * @param app invoked application, must not be {@code}
     * @throws ApplicationException if errors occurs while creating report processor
     */
    static void main(final Main app) throws ApplicationException {
        Validate.notNull(app);
        InvokableAdapter.main(app);
    }

    @Override
    public Version getVersion() {
        return version;
    }

    @Override
    public CliOptions getOptions() {
        return options;
    }


    @Override
    public Server getServer() {
        return server;
    }

    @Override
    public MessageBox getInbox() {
        return inbox;
    }

    @Override
    public MessageBox getOutbox() {
        return outbox;
    }

    @Override
    public NodeId getNodeId() {
        return nodeId;
    }

    @Override
    public Clock getClock() {
        return clock;
    }

    @Override
    public void execute() throws Exception {
        initEnvironment();

        if (options.isHelp()) {
            showHelpMessage();
            return;
        }

        if (options.isVersion()) {
            showVersionMessage();
            return;
        }

        run();
    }

    /**
     * Prints version information on STDOUT.
     */
    void showVersionMessage() {
        getIoStreams().println(String.format("Version: %s", version.getVersion()));
    }

    private void showHelpMessage() {
        final StringBuilder buffer = new StringBuilder();
        buffer.append("DHT is an interactive shell to play around with distributed hash table technology.")
              .append(NL).append(NL);
        cliOptionsParser.usage(buffer);
        buffer.append(NL);
        buffer.append("Developed by Sven Strittmatter <ich@weltraumschaf.de>").append(NL);
        buffer.append("https://github.com/Weltraumschaf/dht").append(NL).append(NL);
        getIoStreams().print(buffer.toString());
    }

    private void run() {
        try {
            shell.start();
        } catch (final Exception ex) {
            getIoStreams().errorln(ex.getMessage());

            if (options.isDebug()) {
                getIoStreams().printStackTrace(ex);
            }

            LOG.error(ex.getMessage(), ex);
        }
    }

}
