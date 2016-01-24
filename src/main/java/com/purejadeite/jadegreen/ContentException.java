package com.purejadeite.jadegreen;

import java.util.List;

import com.purejadeite.jadegreen.content.Content;

/**
 * コンテンツ不正時の例外
 * @author mitsuhiroseino
 *
 */
public class ContentException extends JadegreenException {

	private static final long serialVersionUID = 767495151046226836L;

	/**
	 * コンテンツ
	 */
	private Content<?, ?>[] contents;

	/**
	 * コンストラクタ
	 */
	public ContentException() {
	}

	/**
	 * コンストラクタ
	 *
	 * @param message
	 *            詳細
	 * @param contents
	 *            コンテンツ
	 */
	public ContentException(String message, Content<?, ?>... contents) {
		super(getErrorMessage(contents, message));
		this.contents = contents;
	}

	/**
	 * コンストラクタ
	 *
	 * @param message
	 *            詳細
	 * @param contents
	 *            コンテンツ
	 */
	public ContentException(String message, List<Content<?, ?>> contents) {
		super(getErrorMessage(contents, message));
		this.contents = contents.toArray(new Content<?, ?>[0]);
	}

	/**
	 * コンストラクタ
	 *
	 * @param message
	 *            詳細
	 * @param cause
	 *            原因
	 * @param contents
	 *            コンテンツ
	 */
	public ContentException(String message, Throwable cause,
			Content<?, ?>... contents) {
		super(getErrorMessage(contents, message), cause);
		this.contents = contents;
	}

	/**
	 * コンストラクタ
	 *
	 * @param message
	 *            詳細
	 * @param cause
	 *            原因
	 * @param contents
	 *            コンテンツ
	 */
	public ContentException(String message, Throwable cause,
			List<Content<?, ?>> contents) {
		super(getErrorMessage(contents, message), cause);
		this.contents = contents.toArray(new Content<?, ?>[0]);
	}

	/**
	 * コンストラクタ
	 *
	 * @param message
	 *            詳細
	 */
	public ContentException(String message) {
		super(message);
	}

	/**
	 * コンストラクタ
	 *
	 * @param cause
	 *            原因
	 */
	public ContentException(Throwable cause) {
		super(cause);
	}

	/**
	 * コンストラクタ
	 *
	 * @param message
	 *            詳細
	 * @param cause
	 *            原因
	 */
	public ContentException(String message, Throwable cause) {
		super(message, cause);
	}

	/**
	 * コンストラクタ
	 *
	 * @param message
	 *            詳細
	 * @param cause
	 *            原因
	 * @param enableSuppression
	 *            抑制を有効化するか、それとも無効化するか
	 * @param writableStackTrace
	 *            スタックトレースを書き込み可能にするかどうか
	 */
	public ContentException(String message, Throwable cause,
			boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	/**
	 * エラーメッセージを構築します
	 *
	 * @param contents
	 *            コンテンツ
	 * @param baseMessage
	 *            元になるメッセージ
	 * @return エラーメッセージ
	 */
	private static String getErrorMessage(List<Content<?, ?>> contents,
			String baseMessage) {
		return getErrorMessage(contents.toArray(new Content<?, ?>[0]), baseMessage);
	}

	/**
	 * エラーメッセージを構築します
	 *
	 * @param contents
	 *            コンテンツ
	 * @param baseMessage
	 *            元になるメッセージ
	 * @return エラーメッセージ
	 */
	private static String getErrorMessage(Content<?, ?>[] contents,
			String baseMessage) {
		String message = "[";
		int size = 0;
		for (Content<?, ?> content : contents) {
			if (0 < size) {
				message += ",";
			}
			message += "key=" + content.getFullId() + "&value="
					+ content.getRawValues();
			size++;
		}
		return message + "]:" + baseMessage;
	}

	/**
	 * コンテンツを取得します
	 *
	 * @return コンテンツ
	 */
	public Content<?, ?>[] getContents() {
		return contents;
	}

}
