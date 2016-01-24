package com.purejadeite.jadegreen;

import com.purejadeite.util.collection.LazyTable;
import com.purejadeite.util.collection.Table;

public class Worksheet extends LazyTable<String> {

	/**
	 * ブック名
	 */
	public String workbookName;

	/**
	 * シート名
	 */
	public String name;

	/**
	 * ブック名を取得します
	 * @return ブック名
	 */
	public String getWorkbookName() {
		return workbookName;
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
	public Worksheet(String workbookName, String name) {
		super();
		this.workbookName = workbookName;
		this.name = name;
	}

	/**
	 * コンストラクタ
	 */
	public Worksheet(String workbookName, String name, Table<String> table) {
		super(table);
		this.workbookName = workbookName;
		this.name = name;
	}


	/**
	 * コンストラクタ
	 */
	public Worksheet(String workbookName, String name, String[][] table) {
		super(table);
		this.workbookName = workbookName;
		this.name = name;
	}

}
