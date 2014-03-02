package de.weltraumschaf.dht;

import com.beust.jcommander.JCommander;
import de.weltraumschaf.commons.ApplicationException;
import de.weltraumschaf.commons.InvokableAdapter;
import de.weltraumschaf.commons.Version;
import de.weltraumschaf.dht.shell.InteractiveShell;
import java.io.IOException;
import org.apache.commons.lang3.Validate;

public final class Main extends InvokableAdapter {

    public static final String NAME = "dht";
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

    /**
     * Dedicated constructor.
     *
     * @param args command line arguments provided by JVM
     * @throws ApplicationException if {@link #processor} can't be instantiated
     */
    public Main(final String[] args) throws ApplicationException {
        super(args);
        cliOptionsParser.setProgramName(NAME);
        cliOptionsParser.addObject(options);
        cliOptionsParser.parse(args);

        try {
            version.load();
        } catch (final IOException ex) {
            throw new ApplicationException(ExitCodeImpl.FATAL, "Can't load version file!", ex);
        }
    }

    /**
     * Main entry point of application invoked by JVM.
     *
     * @param args command line arguments provided by JVM
     * @throws ApplicationException if errors occurs while creating report processor
     */
    public static void main(final String[] args) throws ApplicationException {
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
        cliOptionsParser.usage(buffer);
        getIoStreams().print(buffer.toString());
    }

    private void run() throws IOException {
        final InteractiveShell shell = new InteractiveShell(getIoStreams());
        shell.start();
    }

}
