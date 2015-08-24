package com.purejadeite.jadegreen.definition.cell;

import java.util.Map;

import com.purejadeite.jadegreen.definition.BookDefinitionImpl;
import com.purejadeite.jadegreen.definition.Definition;

/**
 * 単一セルのリンク定義です
 * @author mitsuhiroseino
 */
public class LinkCellDefinitionImpl extends AbstractLinkCellDefinition {

	private static final long serialVersionUID = 5847566126062846404L;

	/**
	 * コンストラクタ
	 * @param book ブック読み込み定義
	 * @param sheet シート読み込み定義
	 * @param id 定義ID
	 * @param converters コンバーター
	 * @param config リンク設定
	 */
	private LinkCellDefinitionImpl(BookDefinitionImpl book, Definition sheet, String id, boolean noOutput, Map<String, String> config) {
		super(book, sheet, id, noOutput, config);
	}

	/**
	 * インスタンスを取得します
	 * @param book ブック読み込み定義
	 * @param parent シート読み込み定義
	 * @param id 定義ID
	 * @param converters コンバーター
	 * @param config リンク設定
	 * @return ラップされたCellリンク定義
	 */
	public static Definition getInstance(BookDefinitionImpl book, Definition parent, String id, boolean noOutput,
			Map<String, String> config) {
		return new LinkCellDefinitionImpl(book, parent, id, noOutput, config);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String toJson() {
		return "{" + super.toJson() + "}";
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String toString() {
		return this.getClass().getSimpleName() + " [" + super.toString() + "]";
	}

}
