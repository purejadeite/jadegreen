package com.purejadeite.jadegreen;

import com.purejadeite.jadegreen.definition.Definition;

/**
 * 定義不正時の例外
 * @author mitsuhiroseino
 *
 */
public class DefinitionException extends MappingException {

	private static final long serialVersionUID = -5511748214074563660L;

	/**
	 * 定義
	 */
	private Definition<?> definition;

	/**
	 * コンストラクタ
	 */
	public DefinitionException() {
	}

	/**
	 * コンストラクタ
	 * @param message 詳細
	 * @param definition 定義
	 */
	public DefinitionException(String message, Definition<?> definition) {
		super(getErrorMessage(definition, message));
		this.definition = definition;
	}

	/**
	 * コンストラクタ
	 * @param message 詳細
	 * @param cause 原因
	 * @param definition 定義
	 */
	public DefinitionException(String message, Throwable cause, Definition<?> definition) {
		super(getErrorMessage(definition, message), cause);
		this.definition = definition;
	}

	/**
	 * コンストラクタ
	 * @param message 詳細
	 */
	public DefinitionException(String message) {
		super(message);
	}

	/**
	 * コンストラクタ
	 * @param cause 原因
	 */
	public DefinitionException(Throwable cause) {
		super(cause);
	}

	/**
	 * コンストラクタ
	 * @param message 詳細
	 * @param cause 原因
	 */
	public DefinitionException(String message, Throwable cause) {
		super(message, cause);
	}

	/**
	 * コンストラクタ
	 * @param message 詳細
	 * @param cause 原因
	 * @param enableSuppression 抑制を有効化するか、それとも無効化するか
	 * @param writableStackTrace スタックトレースを書き込み可能にするかどうか
	 */
	public DefinitionException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	/**
	 * エラーメッセージを構築します
	 * 
	 * @param definition
	 *            定義
	 * @param baseMessage
	 *            元になるメッセージ
	 * @return エラーメッセージ
	 */
	private static String getErrorMessage(Definition<?> definition, String baseMessage) {
		return "key=" + definition.getFullId() + ":" + baseMessage;
	}

	/**
	 * 定義を主tくします
	 * @return 定義
	 */
	public Definition<?> getDefinition() {
		return definition;
	}

}
