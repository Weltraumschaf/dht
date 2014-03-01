package de.weltraumschaf.dht;

import de.weltraumschaf.commons.ApplicationException;
import de.weltraumschaf.commons.InvokableAdapter;
import de.weltraumschaf.commons.Version;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.commons.lang3.Validate;

public class Main extends InvokableAdapter {

    /**
     * JAR relative path to version property file.
     */
    private static final String VERSION_FILE = "/de/weltraumschaf/dht/version.properties";

    /**
     * Version of the application.
     */
    private final Version version = new Version(VERSION_FILE);

    /**
     * Dedicated constructor.
     *
     * @param args command line arguments provided by JVM
     * @throws ApplicationException if {@link #processor} can't be instantiated
     */
    public Main(final String[] args) throws ApplicationException {
        super(args);

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
        getIoStreams().println("hello " + version.getVersion());
    }

    /**
     * Prints version information on STDOUT.
     */
    void showVersionMessage() {
        getIoStreams().println(String.format("Version: %s", version.getVersion()));
    }
}
