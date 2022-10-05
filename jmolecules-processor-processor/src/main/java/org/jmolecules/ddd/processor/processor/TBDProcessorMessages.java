package org.jmolecules.ddd.processor.processor;


import io.toolisticon.aptk.tools.corematcher.ValidationMessage;

/**
 * Messages used by annotation processors.
 */
public enum TBDProcessorMessages implements ValidationMessage {

    ERROR_ILLEGAL_REFERENCE("TBD_ERROR_001", "Class '${0}' annotated with '${1}' is not allowed to use class '${2}' annotated with '${3}'");


    /**
     * the message code.
     */
    private final String code;
    /**
     * the message text.
     */
    private final String message;

    /**
     * Constructor.
     *
     * @param code    the message code
     * @param message the message text
     */
        TBDProcessorMessages(String code, String message) {
        this.code = code;
        this.message = message;
    }

    /**
     * Gets the code of the message.
     *
     * @return the message code
     */
    public String getCode() {
        return this.code;
    }

    /**
     * Gets the message text.
     *
     * @return the message text
     */
    public String getMessage() {
        return message;
    }


}
