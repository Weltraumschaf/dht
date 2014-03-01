package de.weltraumschaf.dht;

import java.util.UUID;
import org.apache.commons.codec.binary.Hex;
import org.apache.commons.codec.digest.DigestUtils;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import org.junit.Test;

/**
 * Tests for {@link Main}.
 */
public class MainTest {

    @Test
    public void testApp() {
        final String uuid = UUID.randomUUID().toString();
        final byte[] hash = DigestUtils.sha1(uuid);

        for (final byte b : hash) {
            System.out.println(Bits.formatByteToBitString(b));
        }
    }
}
