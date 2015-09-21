package com.purejadeite.jadegreen.definition.cell;

import static com.purejadeite.jadegreen.definition.DefinitionKeys.*;

import java.util.Map;

import com.purejadeite.jadegreen.RoughlyMapUtils;
import com.purejadeite.jadegreen.definition.Definition;
import com.purejadeite.jadegreen.definition.WorkbookDefinitionImpl;

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
	 * @param link リンク設定
	 */
	private LinkCellDefinitionImpl(WorkbookDefinitionImpl book, Definition sheet, String id, boolean noOutput, Map<String, String> link) {
		super(book, sheet, id, noOutput, link);
	}

	public static Definition newInstance(WorkbookDefinitionImpl book, Definition parent, Map<String, Object> config) {
		String id = RoughlyMapUtils.getString(config, ID);
		boolean noOutput = RoughlyMapUtils.getBooleanValue(config, NO_OUTPUT);
		Map<String, String> link = RoughlyMapUtils.getMap(config, LINK);
		return new LinkCellDefinitionImpl(book, parent, id, noOutput, link);
	}

}
