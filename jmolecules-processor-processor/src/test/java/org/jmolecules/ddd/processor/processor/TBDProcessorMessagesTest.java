package org.jmolecules.ddd.processor.processor;

import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.Test;

/**
 * Unit test for {@link TBDProcessorMessages}.
 *
 * TODO: replace the example testcases with your own testcases
 *
 */
public class TBDProcessorMessagesTest {

    @Test
    public void test_enum() {

        MatcherAssert.assertThat(TBDProcessorMessages.ERROR_ILLEGAL_REFERENCE.getCode(), Matchers.startsWith("TBD"));

    }


}
