package com.purejadeite.jadegreen.input.sxssf;

import com.purejadeite.util.collection.LazyTable;
import com.purejadeite.util.collection.Table;

/**
 * Excelから読み込んだシートの値のみを持つクラス
 * @author mitsuhiroseino
 */
public class Sheet extends LazyTable<String> {

	/**
	 * ブック名
	 */
	public String bookName;

	/**
	 * シート名
	 */
	public String name;

	/**
	 * ブック名を取得します
	 * @return ブック名
	 */
	public String getBookName() {
		return bookName;
	}

	/**
	 * シート名を取得します
	 * @return シート名
	 */
	public String getName() {
		return name;
	}

	/**
	 * コンストラクタ
	 */
	public Sheet(String bookName, String name) {
		super();
		this.bookName = bookName;
		this.name = name;
	}

	/**
	 * コンストラクタ
	 */
	public Sheet(String bookName, String name, Table<String> table) {
		super(table);
		this.bookName = bookName;
		this.name = name;
	}


	/**
	 * コンストラクタ
	 */
	public Sheet(String bookName, String name, String[][] table) {
		super(table);
		this.bookName = bookName;
		this.name = name;
	}

}
