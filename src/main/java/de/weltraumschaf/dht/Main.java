package de.weltraumschaf.dht;

import de.weltraumschaf.dht.id.NodeId;
import com.beust.jcommander.JCommander;
import de.weltraumschaf.commons.ApplicationException;
import de.weltraumschaf.commons.InvokableAdapter;
import de.weltraumschaf.commons.Version;
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
        context.setIo(getIoStreams());
        context.setInbox(Messaging.newMessageBox());
        context.setOutbox(Messaging.newMessageBox());
        context.setNodeId(NodeId.newRandom());
        context.setClock(new Clock().start());

        cliOptionsParser.setProgramName(ApplicationContext.NAME);
        final CliOptions options = new CliOptions();
        cliOptionsParser.addObject(options);
        cliOptionsParser.parse(getArgs());
        context.setOptions(options);

        try {
            final Version version = new Version(VERSION_FILE);
            version.load();
        } catch (final IOException ex) {
            throw new ApplicationException(ExitCodeImpl.FATAL, "Can't load version file!", ex);
        }

        final Server server = new Server(getIoStreams(), context.getInbox());
        server.setHost(options.getHost());
        server.setPort(options.getPort());
        context.setServer(server);

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
    public void execute() throws Exception {
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
        getIoStreams().println(String.format("Version: %s", context.getVersion()));
    }

    private void showHelpMessage() {
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
