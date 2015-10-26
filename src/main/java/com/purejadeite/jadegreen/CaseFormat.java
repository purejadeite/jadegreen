package com.purejadeite.jadegreen;

/**
 * Guavaに倣いつつも泥臭い方法で変換をする、ケースフォーマット変換クラス
 * @author mitsuhiroseino
 *
 */
public enum CaseFormat {

	LOWER_CAMEL() {
		protected String normalize(String word) {
			return camelToUpperHyphen(word);
		}

		@Override
		public String convert(String from) {
			StringBuilder to = new StringBuilder();
			boolean upper = false;
			for (int i = 0; i < from.length(); i++) {
				char c = from.charAt(i);
				if (c == '-') {
					upper = true;
				} else if (upper) {
					to.append(Character.toUpperCase(c));
					upper = false;
				}else{
					to.append(Character.toLowerCase(c));
				}
			}
			return to.toString();
		}

		@Override
		public boolean match(String word) {
			for (int i = 0; i < word.length(); i++) {
				char c = word.charAt(i);
				if (i == 0 && !('a' <= c && c <= 'z')) {
					// 先頭は小文字以外不可
					return false;
				} else if (c == '-' || c == '_') {
					return false;
				}
			}
			return true;
		}
	},
	UPPER_CAMEL() {
		protected String normalize(String word) {
			return camelToUpperHyphen(word);
		}

		@Override
		public String convert(String from) {
			StringBuilder to = new StringBuilder();
			boolean upper = false;
			for (int i = 0; i < from.length(); i++) {
				char c = from.charAt(i);
				if (c == '-') {
					upper = true;
				} else if (upper || i == 0) {
					to.append(Character.toUpperCase(c));
					upper = false;
				}else{
					to.append(Character.toLowerCase(c));
				}
			}
			return to.toString();
		}

		@Override
		public boolean match(String word) {
			for (int i = 0; i < word.length(); i++) {
				char c = word.charAt(i);
				if (i == 0 && !('A' <= c && c <= 'Z')) {
					// 先頭は大文字以外不可
					return false;
				} else if (c == '-' || c == '_') {
					return false;
				}
			}
			return true;
		}
	},
	LOWER_HYPHEN() {
		protected String normalize(String word) {
			return word.toUpperCase();
		}

		@Override
		public String convert(String from) {
			return from.toLowerCase();
		}

		@Override
		public boolean match(String word) {
			for (int i = 0; i < word.length(); i++) {
				char c = word.charAt(i);
				if (!(('a' <= c && c <= 'z') || ('0' <= c && c <= '9') || c =='-')) {
					// 小文字と数字とハイフン以外不可
					return false;
				}
			}
			return true;
		}
	},
	UPPER_HYPHEN() {
		protected String normalize(String word) {
			return word;
		}

		@Override
		public String convert(String from) {
			return from;
		}

		@Override
		public boolean match(String word) {
			for (int i = 0; i < word.length(); i++) {
				char c = word.charAt(i);
				if (!(('A' <= c && c <= 'Z') || ('0' <= c && c <= '9') || c =='-')) {
					// 大文字と数字とハイフン以外不可
					return false;
				}
			}
			return true;
		}
	},
	LOWER_UNDERSCORE() {
		protected String normalize(String word) {
			return underscoreToUpperHyphen(word);
		}

		@Override
		public String convert(String from) {
			StringBuilder to = new StringBuilder();
			for (int i = 0; i < from.length(); i++) {
				char c = from.charAt(i);
				if (c == '-') {
					to.append('_');
				}else{
					to.append(Character.toLowerCase(c));
				}
			}
			return to.toString();
		}

		@Override
		public boolean match(String word) {
			for (int i = 0; i < word.length(); i++) {
				char c = word.charAt(i);
				if (!(('a' <= c && c <= 'z') || ('0' <= c && c <= '9') || c =='_')) {
					// 小文字と数字とアンダースコア以外不可
					return false;
				}
			}
			return true;
		}
	},
	UPPER_UNDERSCORE() {
		protected String normalize(String word) {
			return underscoreToUpperHyphen(word);
		}

		@Override
		public String convert(String from) {
			StringBuilder to = new StringBuilder();
			for (int i = 0; i < from.length(); i++) {
				char c = from.charAt(i);
				if (c == '-') {
					to.append('_');
				}else{
					to.append(c);
				}
			}
			return to.toString();
		}

		@Override
		public boolean match(String word) {
			for (int i = 0; i < word.length(); i++) {
				char c = word.charAt(i);
				if (!(('A' <= c && c <= 'Z') || ('0' <= c && c <= '9') || c =='_')) {
					// 大文字と数字とアンダースコア以外不可
					return false;
				}
			}
			return true;
		}
	};

	private CaseFormat() {
	}

	/**
	 *
	 * @param word
	 * @return
	 */
	abstract protected String normalize(String word);

	abstract public String convert(String from);

	abstract public boolean match(String word);

	public String to(CaseFormat to, String word) {
		if (word == null || word.isEmpty()) {
			return "";
		}
		return to.convert(normalize(word));
	}

	private static String camelToUpperHyphen(String word) {
		StringBuilder normalized = new StringBuilder();
		for (int i = 0; i < word.length(); i++) {
			char c = word.charAt(i);
			if ('A' <= c && c <= 'Z') {
				if (i != 0) {
					normalized.append('-');
				}
			}
			normalized.append(Character.toUpperCase(c));
		}
		return normalized.toString();
	}

	private static String underscoreToUpperHyphen(String word) {
		StringBuilder normalized = new StringBuilder();
		for (int i = 0; i < word.length(); i++) {
			char c = word.charAt(i);
			if (c == '_') {
				normalized.append('-');
			} else {
				normalized.append(Character.toUpperCase(c));
			}
		}
		return normalized.toString();
	}

}
