package com.purejadeite.jadegreen;

import com.purejadeite.jadegreen.definition.Definition;

public class DefinitionException extends MappingException {

	private Definition<?> definition;

	public DefinitionException() {
	}

	public DefinitionException(Definition<?> definition, String message) {
		super(getErrorMessage(definition, message));
		this.definition = definition;
	}

	public DefinitionException(Definition<?> definition, String message, Throwable cause) {
		super(getErrorMessage(definition, message), cause);
		this.definition = definition;
	}

	public DefinitionException(String message) {
		super(message);
	}

	public DefinitionException(Throwable cause) {
		super(cause);
	}

	public DefinitionException(String message, Throwable cause) {
		super(message, cause);
	}

	public DefinitionException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	private static String getErrorMessage(Definition<?> definition, String baseMessage) {
		return "key=" + definition.getFullId() + ":" + baseMessage;
	}

	public Definition<?> getDefinition() {
		return definition;
	}

}
