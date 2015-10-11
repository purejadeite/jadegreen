package com.purejadeite.jadegreen;

/**
 * マッピング処理の例外
 * @author mitsuhiroseino
 */
public class MappingException extends RuntimeException {

	private static final long serialVersionUID = -3742418545961834551L;

	/**
	 * コンストラクタ
	 */
	public MappingException() {
	}

	/**
	 * コンストラクタ
	 * @param message 詳細
	 */
	public MappingException(String message) {
		super(message);
	}

	/**
	 * コンストラクタ
	 * @param cause 原因
	 */
	public MappingException(Throwable cause) {
		super(cause);
	}

	/**
	 * コンストラクタ
	 * @param message 詳細
	 * @param cause 原因
	 */
	public MappingException(String message, Throwable cause) {
		super(message, cause);
	}

	/**
	 * コンストラクタ
	 * @param message 詳細
	 * @param cause 原因
	 * @param enableSuppression 抑制を有効化するか、それとも無効化するか
	 * @param writableStackTrace スタックトレースを書き込み可能にするかどうか
	 */
	public MappingException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

}
