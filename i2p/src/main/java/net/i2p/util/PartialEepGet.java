package net.i2p.util;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Locale;

import net.i2p.I2PAppContext;

/**
 * Fetch exactly the first 'size' bytes into a stream
 * Anything less or more will throw an IOException
 * No retries, no min and max size options, no timeout option
 * If the server does not return a Content-Length header of the correct size,
 * the fetch will fail.
 *
 * Useful for checking .sud versions
 *
 * @since 0.7.12
 * @author zzz
 */
public class PartialEepGet extends EepGet {
    long _fetchSize;

    /**
     * Instantiate an EepGet that will fetch exactly size bytes when fetch() is called.
     *
     * @param proxyHost use null or "" for no proxy
     * @param proxyPort use 0 for no proxy
     * @param size fetch exactly this many bytes
     */
    public PartialEepGet(I2PAppContext ctx, String proxyHost, int proxyPort,
                         OutputStream outputStream,  String url, long size) {
        // we're using this constructor:
        // public EepGet(I2PAppContext ctx, boolean shouldProxy, String proxyHost, int proxyPort, int numRetries,
        //               long minSize, long maxSize, String outputFile, OutputStream outputStream, String url, boolean allowCaching, String etag, String postData) {
        super(ctx, proxyHost != null && proxyPort > 0, proxyHost, proxyPort, 0,
              size, size, null, outputStream, url, true, null, null);
        _fetchSize = size;
    }
   
    /**
     * PartialEepGet [-p 127.0.0.1:4444] [-l #bytes] url
     *
     */ 
    public static void main(String args[]) {
        String proxyHost = "127.0.0.1";
        int proxyPort = 4444;
        // 40 sig + 16 version for .suds
        long size = 56;
        String url = null;
        try {
            for (int i = 0; i < args.length; i++) {
                if (args[i].equals("-p")) {
                    proxyHost = args[i+1].substring(0, args[i+1].indexOf(':'));
                    String port = args[i+1].substring(args[i+1].indexOf(':')+1);
                    proxyPort = Integer.parseInt(port);
                    i++;
                } else if (args[i].equals("-l")) {
                    size = Long.parseLong(args[i+1]);
                    i++;
                } else if (args[i].startsWith("-")) {
                    usage();
                    return;
                } else {
                    url = args[i];
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            usage();
            return;
        }
        
        if (url == null) {
            usage();
            return;
        }

        String saveAs = suggestName(url);
        OutputStream out;
        try {
            // resume from a previous eepget won't work right doing it this way
            out = new FileOutputStream(saveAs);
        } catch (IOException ioe) {
            System.err.println("Failed to create output file " + saveAs);
            return;
        }

        EepGet get = new PartialEepGet(I2PAppContext.getGlobalContext(), proxyHost, proxyPort, out, url, size);
        get.addStatusListener(get.new CLIStatusListener(1024, 40));
        if (get.fetch(45*1000, -1, 60*1000)) {
            System.err.println("Last-Modified: " + get.getLastModified());
            System.err.println("Etag: " + get.getETag());
        } else {
            System.err.println("Failed " + url);
            System.exit(1);
        }
    }
    
    private static void usage() {
        System.err.println("PartialEepGet [-p 127.0.0.1:4444] [-l #bytes] url\n" +
                           "              (use -p :0 for no proxy)");
    }
    
    @Override
    protected String getRequest() throws IOException {
        StringBuilder buf = new StringBuilder(2048);
        URL url = new URL(_actualURL);
        String host = url.getHost();
        if (host == null || host.length() <= 0)
            throw new MalformedURLException("Bad URL, no host");
        int port = url.getPort();
        String path = url.getPath();
        String query = url.getQuery();
        if (_log.shouldLog(Log.DEBUG))
            _log.debug("Requesting " + _actualURL);
        // RFC 2616 sec 5.1.2 - full URL if proxied, absolute path only if not proxied
        String urlToSend;
        if (_shouldProxy) {
            urlToSend = _actualURL;
            if ((path == null || path.length()<= 0) &&
                (query == null || query.length()<= 0))
                urlToSend += "/";
        } else {
            urlToSend = path;
            if (urlToSend == null || urlToSend.length()<= 0)
                urlToSend = "/";
            if (query != null)
                urlToSend += '?' + query;
        }
        buf.append("GET ").append(urlToSend).append(" HTTP/1.1\r\n");
        // RFC 2616 sec 5.1.2 - host + port (NOT authority, which includes userinfo)
        buf.append("Host: ").append(host);
        if (port >= 0)
            buf.append(':').append(port);
        buf.append("\r\n");
        buf.append("Range: bytes=");
        buf.append(_alreadyTransferred);
        buf.append('-');
        buf.append(_fetchSize - 1);
        buf.append("\r\n");

        buf.append("Cache-control: no-cache\r\n" +
                   "Pragma: no-cache\r\n" +
                   "Accept-Encoding: \r\n" +
                   "Connection: close\r\n");
        boolean uaOverridden = false;
        if (_extraHeaders != null) {
            for (String hdr : _extraHeaders) {
                if (hdr.toLowerCase(Locale.US).startsWith("user-agent: "))
                    uaOverridden = true;
                buf.append(hdr).append("\r\n");
            }
        }
        // This will be replaced if we are going through I2PTunnelHTTPClient
        if(!uaOverridden)
            buf.append("User-Agent: " + USER_AGENT + "\r\n");
        buf.append("\r\n");

        if (_log.shouldLog(Log.DEBUG))
            _log.debug("Request: [" + buf.toString() + "]");
        return buf.toString();
    }
}
