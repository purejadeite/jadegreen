package com.purejadeite.util;

/**
 * 2つの値の関係を評価するユーティリティです
 *
 * @author mitsuhiroseino
 *
 */
public class EvaluationUtils {

	/**
	 * ==
	 */
	public static String EQUAL_TO = "==";

	/**
	 * !=
	 */
	public static String NOT_EQUAL_TO = "!=";

	/**
	 * >
	 */
	public static String GREATER_THAN = ">";

	/**
	 * >=
	 */
	public static String GREATER_THAN_OR_EQUAL_TO = ">=";

	/**
	 * <
	 */
	public static String LESS_THAN = "<";

	/**
	 * <=
	 */
	public static String LESS_THAN_OR_EQUAL_TO = "<=";

	/**
	 * 比較演算子
	 *
	 * @author mitsuhiroseino
	 *
	 */
	public static enum Operator {

		/**
		 * ==
		 */
		EQUAL_TO(EvaluationUtils.EQUAL_TO),

		/**
		 * !=
		 */
		NOT_EQUAL_TO(EvaluationUtils.NOT_EQUAL_TO),

		/**
		 * >
		 */
		GREATER_THAN(EvaluationUtils.GREATER_THAN),

		/**
		 * >=
		 */
		GREATER_THAN_OR_EQUAL_TO(EvaluationUtils.GREATER_THAN_OR_EQUAL_TO),

		/**
		 * <
		 */
		LESS_THAN(EvaluationUtils.LESS_THAN),

		/**
		 * <=
		 */
		LESS_THAN_OR_EQUAL_TO(EvaluationUtils.LESS_THAN_OR_EQUAL_TO);

		/**
		 * 比較演算子文字列
		 */
		private String operator;

		/**
		 * コンストラクタ
		 *
		 * @param operator
		 *            比較演算子文字列
		 */
		private Operator(String operator) {
			this.operator = operator;
		}

		/**
		 * 比較演算子文字列を取得する
		 *
		 * @return 比較演算子
		 */
		public String getOperator() {
			return operator;
		}

		public Operator toOperatorEnum(String operator) {
			for(Operator ope : values()){
				if(ope.getOperator().equals(operator)) {
					return ope;
				}
			}
			return null;
		}
	}

	private EvaluationUtils() {
	}

	/**
	 * 値の大小を評価します
	 *
	 * @param a
	 *            値A
	 * @param operator
	 *            比較演算子
	 * @param b
	 *            値B
	 * @return
	 */
	public static <T extends Comparable<T>> boolean evaluate(Object a, Operator operator, Object b) {
		return evaluate(a, operator.getOperator(), b);
	}

	/**
	 * 値の大小を評価します
	 *
	 * @param a
	 *            値A
	 * @param operator
	 *            比較演算子
	 * @param b
	 *            値B
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static <T extends Comparable<T>> boolean evaluate(Object a, String operator, Object b) {
		String message = null;
		T ac = null;
		T bc = null;
		if (a == null || a instanceof Comparable) {
			ac = (T) a;
			if (b == null || b instanceof Comparable) {
				bc = (T) b;
				return evaluate(ac, operator, bc);
			} else {
				message = "b->" + b.getClass().getName();
			}
		} else {
			message = "a->" + a.getClass().getName();
		}
		throw new IllegalArgumentException("比較できるのはComparableのインスタンスのみです:" + message);
	}

	/**
	 * 値の大小を評価します
	 *
	 * @param a
	 *            値A
	 * @param operator
	 *            比較演算子
	 * @param b
	 *            値B
	 * @return
	 */
	public static <T extends Comparable<T>> boolean evaluate(T a, Operator operator, T b) {
		return evaluate(a, operator.getOperator(), b);
	}

	/**
	 * 値の大小を評価します
	 *
	 * @param a
	 *            値A
	 * @param operator
	 *            比較演算子
	 * @param b
	 *            値B
	 * @return
	 */
	public static <T extends Comparable<T>> boolean evaluate(T a, String operator, T b) {
		if (EQUAL_TO.equals(operator)) {
			return equalTo(a, b);
		} else if (NOT_EQUAL_TO.equals(operator)) {
			return notEqualTo(a, b);
		} else if (GREATER_THAN.equals(operator)) {
			return greaterThan(a, b);
		} else if (GREATER_THAN_OR_EQUAL_TO.equals(operator)) {
			return greaterThanOrEqualTo(a, b);
		} else if (LESS_THAN.equals(operator)) {
			return lessThan(a, b);
		} else if (LESS_THAN_OR_EQUAL_TO.equals(operator)) {
			return lessThanOrEqualTo(a, b);
		} else {
			throw new IllegalArgumentException("「" + operator + "」は正しい比較演算子ではありません");
		}
	}

	/**
	 * <pre>
	 * 下記の式を検証します
	 * a == b
	 * </pre>
	 *
	 * @param a
	 *            値A
	 * @param b
	 *            値B
	 * @return 比較結果
	 */
	public static boolean equalTo(Object a, Object b) {
		if (a == b) {
			return true;
		} else if (a == null || b == null) {
			return false;
		} else {
			return a.equals(b);
		}
	}

	/**
	 * <pre>
	 * 下記の式を検証します
	 * a != b
	 * </pre>
	 *
	 * @param a
	 *            値A
	 * @param b
	 *            値B
	 * @return 比較結果
	 */
	public static boolean notEqualTo(Object a, Object b) {
		return !equalTo(a, b);
	}

	/**
	 * <pre>
	 * 下記の式を検証します
	 * a > b
	 * </pre>
	 *
	 * @param a
	 *            値A
	 * @param b
	 *            値B
	 * @return 比較結果
	 */
	public static <T extends Comparable<T>> boolean greaterThan(T a, T b) {
		if (a == b) {
			return false;
		} else if (a == null) {
			return false;
		} else if (b == null) {
			return true;
		} else {
			return a.compareTo(b) > 0;
		}
	}

	/**
	 * <pre>
	 * 下記の式を検証します
	 * a >= b
	 * </pre>
	 *
	 * @param a
	 *            値A
	 * @param b
	 *            値B
	 * @return 比較結果
	 */
	public static <T extends Comparable<T>> boolean greaterThanOrEqualTo(T a, T b) {
		return equalTo(a, b) || greaterThan(a, b);
	}

	/**
	 * <pre>
	 * 下記の式を検証します
	 * a < b
	 * </pre>
	 *
	 * @param a
	 *            値A
	 * @param b
	 *            値B
	 * @return 比較結果
	 */
	public static <T extends Comparable<T>> boolean lessThan(T a, T b) {
		if (a == b) {
			return false;
		} else if (a == null) {
			return true;
		} else if (b == null) {
			return false;
		} else {
			return a.compareTo(b) < 0;
		}
	}

	/**
	 * <pre>
	 * 下記の式を検証します
	 * a <= b
	 * </pre>
	 *
	 * @param a
	 *            値A
	 * @param b
	 *            値B
	 * @return 比較結果
	 */
	public static <T extends Comparable<T>> boolean lessThanOrEqualTo(T a, T b) {
		return equalTo(a, b) || lessThan(a, b);
	}

}
