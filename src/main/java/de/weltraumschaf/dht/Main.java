package de.weltraumschaf.dht;

import de.weltraumschaf.dht.id.NodeId;
import com.beust.jcommander.JCommander;
import com.beust.jcommander.ParameterException;
import de.weltraumschaf.commons.application.ApplicationException;
import de.weltraumschaf.commons.application.InvokableAdapter;
import de.weltraumschaf.commons.application.Version;
import de.weltraumschaf.dht.data.DataStructures;
import de.weltraumschaf.dht.log.Log;
import de.weltraumschaf.dht.log.Logger;
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

    private final ApplicationContext context = new ApplicationContext();
    private final JCommander cliOptionsParser = new JCommander();
    private final InteractiveShell shell = new InteractiveShell(context);

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
        LOG.debug("Initialize environment.");
        LOG.debug("Add I/O streams to context.");
        context.setIo(getIoStreams());
        LOG.debug("Add inbox to context.");
        context.setInbox(Messaging.newMessageBox());
        LOG.debug("Add outbox to context.");
        context.setOutbox(Messaging.newMessageBox());
        LOG.debug("Add node ID to context.");
        context.setNodeId(NodeId.newRandom());
        LOG.debug("Add clock context.");
        context.setClock(new Clock().start());

        cliOptionsParser.setProgramName(ApplicationContext.NAME);
        final CliOptions options = new CliOptions();
        cliOptionsParser.addObject(options);

        try {
            cliOptionsParser.parse(getArgs());
        } catch (final ParameterException ex) {
            throw new ApplicationException(ExitCodeImpl.FATAL, "Bad options!", ex);
        }

        LOG.debug("Add CLI options to context.");
        context.setOptions(options);

        try {
            final Version version = new Version(VERSION_FILE);
            version.load();
            LOG.debug("Add version to context.");
            context.setVersion(version);
        } catch (final IOException ex) {
            throw new ApplicationException(ExitCodeImpl.FATAL, "Can't load version file!", ex);
        }

        final Server server = new Server(getIoStreams(), context.getInbox());
        server.setHost(options.getHost());
        server.setPort(options.getPort());
        LOG.debug("Add server to context.");
        context.setServer(server);

        LOG.debug("Add K-Bucket to context.");
        context.setKBucket(DataStructures.<Contact>newBucketSet(0, 256, 20, DataStructures.<Contact>newBucketTrimmer()));

        LOG.debug("Register shutdown hook.");
        registerShutdownHook(new Runnable() {

            @Override
            public void run() {
                LOG.debug("Shutdown hook called.");

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
    public void execute() throws Exception {
        LOG.debug("Start DHT ...");
        initEnvironment();

        if (context.getOptions().isHelp()) {
            showHelpMessage();
            return;
        }

        if (context.getOptions().isVersion()) {
            showVersionMessage();
            return;
        }

        run();
    }

    /**
     * Prints version information on STDOUT.
     */
    void showVersionMessage() {
        LOG.debug("Show version message.");
        getIoStreams().println(String.format("Version: %s", context.getVersion()));
    }

    private void showHelpMessage() {
        LOG.debug("Show help message.");
        final StringBuilder buffer = new StringBuilder();
        buffer.append("DHT is an interactive shell to play around with distributed hash table technology.")
              .append(ApplicationContext.NL).append(ApplicationContext.NL);
        cliOptionsParser.usage(buffer);
        buffer.append(ApplicationContext.NL);
        buffer.append("Developed by Sven Strittmatter <ich@weltraumschaf.de>").append(ApplicationContext.NL);
        buffer.append("https://github.com/Weltraumschaf/dht").append(ApplicationContext.NL).append(ApplicationContext.NL);
        getIoStreams().print(buffer.toString());
    }

    private void run() {
        try {
            LOG.debug("Start interactive shell.");
            shell.start();
        } catch (final Exception ex) {
            getIoStreams().errorln(ex.getMessage());

            if (context.getOptions().isDebug()) {
                getIoStreams().printStackTrace(ex);
            }

            LOG.error(ex.getMessage(), ex);
        }
    }

    @Override
    public ApplicationContext getContext() {
        return context;
    }

}
