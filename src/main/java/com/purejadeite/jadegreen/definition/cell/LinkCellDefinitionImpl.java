package com.purejadeite.jadegreen.definition.cell;

import java.util.Map;

import com.purejadeite.jadegreen.definition.WorkbookDefinitionImpl;
import com.purejadeite.jadegreen.definition.Definition;

/**
 * 単一セルのリンク定義です
 * @author mitsuhiroseino
 */
public class LinkCellDefinitionImpl extends AbstractLinkCellDefinition {

	/**
	 * コンストラクタ
	 * @param book ブック読み込み定義
	 * @param sheet シート読み込み定義
	 * @param id 定義ID
	 * @param options オプション
	 * @param config リンク設定
	 */
	private LinkCellDefinitionImpl(WorkbookDefinitionImpl book, Definition sheet, String id, boolean noOutput, Map<String, String> config) {
		super(book, sheet, id, noOutput, config);
	}

	/**
	 * インスタンスを取得します
	 * @param book ブック読み込み定義
	 * @param parent シート読み込み定義
	 * @param id 定義ID
	 * @param options オプション
	 * @param config リンク設定
	 * @return ラップされたCellリンク定義
	 */
	public static Definition getInstance(WorkbookDefinitionImpl book, Definition parent, String id, boolean noOutput,
			Map<String, String> config) {
		return new LinkCellDefinitionImpl(book, parent, id, noOutput, config);
	}

}
