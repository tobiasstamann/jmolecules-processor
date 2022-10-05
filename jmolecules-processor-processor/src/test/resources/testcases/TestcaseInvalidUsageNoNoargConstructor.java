package org.jmolecules.ddd.processor.processor.tests;

import org.jmolecules.ddd.processor.api.TBD;

@TBD("Xyz")
public class TestcaseInvalidUsageNoNoargConstructor {

    private String field;

    public  TestcaseInvalidUsageNoNoargConstructor (String arg) {
    }

    public String getField() {
        return field;
    }

    public void setField(String field) {
        this.field = field;
    }
}