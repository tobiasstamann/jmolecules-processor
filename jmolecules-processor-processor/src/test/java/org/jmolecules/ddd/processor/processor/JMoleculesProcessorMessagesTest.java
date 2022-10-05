package org.jmolecules.ddd.processor.processor;

import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.Test;

/**
 * Unit test for {@link JMoleculesProcessorMessages}.
 *
 * TODO: replace the example testcases with your own testcases
 *
 */
public class JMoleculesProcessorMessagesTest {

    @Test
    public void test_enum() {

        MatcherAssert.assertThat(JMoleculesProcessorMessages.ERROR_ILLEGAL_REFERENCE.getCode(), Matchers.startsWith("TBD"));

    }


}
