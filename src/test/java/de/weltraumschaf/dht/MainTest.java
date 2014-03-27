package de.weltraumschaf.dht;

import org.apache.commons.lang3.StringUtils;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import org.junit.Ignore;
import org.junit.Test;

/**
 * Tests for {@link Main}.
 */
public class MainTest {

    @Test
    @Ignore("Play around with XOR")
    public void testApp() {
        final int end = 0x4;
        System.out.println("    a          b         xor   ");
        System.out.println("---------  ---------  ---------");

        for (int a = 0x0; a < end; ++a) {
            for (int b = 0x0; b < end; ++b) {
                final int xor = a ^ b;
                System.out.println(String.format("%4s (%02d)  %4s (%02d)  %4s (%02d)",
                    StringUtils.leftPad(Integer.toBinaryString(a), 4, "0"), a,
                    StringUtils.leftPad(Integer.toBinaryString(b), 4, "0"), b,
                    StringUtils.leftPad(Integer.toBinaryString(xor), 4, "0"), xor));
            }
        }
    }

}
