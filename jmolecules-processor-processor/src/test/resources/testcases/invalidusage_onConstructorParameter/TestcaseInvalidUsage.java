package org.jmolecules.ddd.processor.processor.tests.domain;

import org.jmolecules.ddd.annotation.ValueObject;
import org.jmolecules.ddd.processor.processor.tests.application.ServiceClass;

@ValueObject
public class TestcaseInvalidUsage {

    private TestcaseInvalidUsage(ServiceClass field) {
        // ...
    }

}