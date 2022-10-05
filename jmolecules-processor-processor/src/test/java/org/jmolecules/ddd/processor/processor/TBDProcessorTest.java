package org.jmolecules.ddd.processor.processor;

import io.toolisticon.aptk.tools.MessagerUtils;
import io.toolisticon.aptk.tools.corematcher.CoreMatcherValidationMessages;
import io.toolisticon.cute.CompileTestBuilder;
import io.toolisticon.cute.JavaFileObjectUtils;
import org.junit.Before;
import org.junit.Test;

import javax.tools.JavaFileObject;
import javax.tools.StandardLocation;


/**
 * Tests of {@link org.jmolecules.ddd.processor.processor.TBDProcessor}.
 *

 */

public class TBDProcessorTest {


    CompileTestBuilder.CompilationTestBuilder compileTestBuilder;

    @Before
    public void init() {
        MessagerUtils.setPrintMessageCodes(true);

        compileTestBuilder = CompileTestBuilder
                .compilationTest()
                .addProcessors(TBDProcessor.class);
    }


    @Test
    public void test_valid_usage() {

        compileTestBuilder
                .addSources(JavaFileObjectUtils.readFromResource("testcases/validusage/TestcaseValidUsage.java"))
                .compilationShouldSucceed()
                .executeTest();
    }

    @Test
    public void test_invalid_usage_on_field() {

        compileTestBuilder
                .addSources("testcases/invalidusage_onField/TestcaseInvalidUsage.java",
                        "testcases/invalidusage_onField/ServiceClass.java")
                .compilationShouldFail()
                .expectErrorMessageThatContains(TBDProcessorMessages.ERROR_ILLEGAL_REFERENCE.getCode())
                .executeTest();
    }

    @Test
    public void test_invalid_usage_on_constructor_parameter() {

        compileTestBuilder
                .addSources("testcases/invalidusage_onConstructorParameter/TestcaseInvalidUsage.java",
                        "testcases/invalidusage_onConstructorParameter/ServiceClass.java")
                .compilationShouldFail()
                .expectErrorMessageThatContains(TBDProcessorMessages.ERROR_ILLEGAL_REFERENCE.getCode())
                .executeTest();
    }

    @Test
    public void test_invalid_usage_on_method_parameter() {

        compileTestBuilder
                .addSources("testcases/invalidusage_onMethodParameter/TestcaseInvalidUsage.java",
                        "testcases/invalidusage_onMethodParameter/ServiceClass.java")
                .compilationShouldFail()
                .expectErrorMessageThatContains(TBDProcessorMessages.ERROR_ILLEGAL_REFERENCE.getCode())
                .executeTest();

    }

    @Test
    public void test_invalid_usage_on_method_return_type() {

        compileTestBuilder
                .addSources("testcases/invalidusage_onMethodReturnType/TestcaseInvalidUsage.java",
                        "testcases/invalidusage_onMethodReturnType/ServiceClass.java")
                .compilationShouldFail()
                .expectErrorMessageThatContains(TBDProcessorMessages.ERROR_ILLEGAL_REFERENCE.getCode())
                .executeTest();

    }

    @Test
    public void test_invalid_usage_on_extends() {

        compileTestBuilder
                .addSources("testcases/invalidusage_onExtends/TestcaseInvalidUsage.java",
                        "testcases/invalidusage_onExtends/ServiceClass.java")
                .compilationShouldFail()
                .expectErrorMessageThatContains(TBDProcessorMessages.ERROR_ILLEGAL_REFERENCE.getCode())
                .executeTest();

    }

    @Test
    public void test_invalid_usage_on_implements() {

        compileTestBuilder
                .addSources("testcases/invalidusage_onImplements/TestcaseInvalidUsage.java",
                        "testcases/invalidusage_onImplements/ServiceClass.java")
                .compilationShouldFail()
                .expectErrorMessageThatContains(TBDProcessorMessages.ERROR_ILLEGAL_REFERENCE.getCode())
                .executeTest();

    }

}