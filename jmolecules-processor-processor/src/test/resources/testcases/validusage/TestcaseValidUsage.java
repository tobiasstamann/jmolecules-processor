package org.jmolecules.ddd.processor.processor.tests;

import org.jmolecules.ddd.annotation.ValueObject;

@ValueObject()
public class TestcaseValidUsage {

    private String field;

    public String getField() {
        return field;
    }

    public void setField(String field) {
        this.field = field;
    }
}