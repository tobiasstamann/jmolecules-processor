package org.jmolecules.ddd.processor.example.domain;


import org.jmolecules.ddd.annotation.ValueObject;
import org.jmolecules.ddd.processor.example.application.TestService;

@ValueObject
public class TestValueObject {

    // Uncomment to break things ;)
    //private TestService testService;

    private final Long longField;
    private final String stringField;

    public TestValueObject(Long longField, String stringField) {
        this.longField = longField;
        this.stringField = stringField;
    }

    public Long getLongField() {
        return longField;
    }

    public String getStringField() {
        return stringField;
    }
}
