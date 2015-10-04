package com.purejadeite.jadegreen;

import java.util.List;

import com.purejadeite.jadegreen.content.Content;

public class ContentException extends MappingException {

	private Content<?> content;

	public ContentException() {
	}

	public ContentException(Content<?> content, String message) {
		super(getErrorMessage(content, message));
		this.content = content;
	}

	public ContentException(List<Content<?>> contents, String message) {
		super(getErrorMessage(contents, message));
		this.content = contents.get(0);
	}

	public ContentException(Content<?> content, String message, Throwable cause) {
		super(getErrorMessage(content, message), cause);
		this.content = content;
	}

	public ContentException(String message) {
		super(message);
	}

	public ContentException(Throwable cause) {
		super(cause);
	}

	public ContentException(String message, Throwable cause) {
		super(message, cause);
	}

	public ContentException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	private static String getErrorMessage(Content<?> content, String baseMessage) {
		return "key=" + content.getFullId() + "&value=" + content.getRawValues() + ":" + baseMessage;
	}

	private static String getErrorMessage(List<Content<?>> contents, String baseMessage) {
		String message = "[";
		int size = 0;
		for(Content<?> content : contents) {
			if (0 < size) {
				message += ",";
			}
			message += "key=" + content.getFullId() + "&value=" + content.getRawValues();
			size++;
		}
		return message + "]:" + baseMessage;
	}

	public Content<?> getContent() {
		return content;
	}

}
