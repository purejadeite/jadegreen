package com.purejadeite.jadegreen.definition.cell;

import static com.purejadeite.jadegreen.definition.DefinitionKeys.*;

import java.util.List;
import java.util.Map;

import com.purejadeite.jadegreen.RoughlyMapUtils;
import com.purejadeite.jadegreen.definition.Definition;
import com.purejadeite.jadegreen.definition.LinkDefinition;
import com.purejadeite.jadegreen.definition.WorkbookDefinition;
import com.purejadeite.jadegreen.definition.WorksheetDefinition;

/**
 * 単一セルのリンク定義です
 *
 * @author mitsuhiroseino
 */
public class LinkCellDefinitionImpl extends AbstractNoAdressCellDefinition<WorksheetDefinition>
		implements LinkCellDefinition<WorksheetDefinition>, LinkDefinition<WorksheetDefinition> {

	private static final String CFG_MY_SHEET_KEY_ID = "mySheetKeyId";

	private static final String CFG_SHEET_KEY_ID = "sheetKeyId";

	private static final String CFG_VALUE_ID = "valueId";

	/**
	 * 必須項目名称
	 */
	private static final String[] CONFIG = { CFG_MY_SHEET_KEY_ID, CFG_SHEET_KEY_ID, CFG_VALUE_ID };

	/**
	 * 自身のシートのキーになる項目のID
	 */
	protected String mySheetKeyId;

	/**
	 * リンク先のキーになる項目のID
	 */
	protected String sheetKeyId;

	/**
	 * リンク先の値を取得する対象の項目のID
	 */
	protected String valueId;

	/**
	 * Book読み込み定義
	 */
	protected WorkbookDefinition book;

	/**
	 * コンストラクタ
	 *
	 * @param book
	 *            ブック読み込み定義
	 * @param parent
	 *            シート読み込み定義
	 * @param id
	 *            定義ID
	 * @param options
	 *            オプション
	 * @param linkConfig
	 *            リンク設定
	 */
	protected LinkCellDefinitionImpl(WorkbookDefinition book, WorksheetDefinition parent, String id,
			boolean noOutput, List<Map<String, Object>> options, Map<String, String> linkConfig) {
		super(parent, id, noOutput, options);
		this.validateConfig(linkConfig, CONFIG);
		this.book = book;
		this.mySheetKeyId = linkConfig.get(CFG_MY_SHEET_KEY_ID);
		this.sheetKeyId = linkConfig.get(CFG_SHEET_KEY_ID);
		this.valueId = linkConfig.get(CFG_VALUE_ID);
	}

	public static Definition<?> newInstance(WorkbookDefinition book, WorksheetDefinition parent,
			Map<String, Object> config) {
		String id = RoughlyMapUtils.getString(config, ID);
		boolean noOutput = RoughlyMapUtils.getBooleanValue(config, NO_OUTPUT);
		List<Map<String, Object>> options = RoughlyMapUtils.getList(config, OPTIONS);
		Map<String, String> link = RoughlyMapUtils.getMap(config, LINK);
		return new LinkCellDefinitionImpl(book, parent, id, noOutput, options, link);
	}

	public String getSheetKeyId() {
		return sheetKeyId;
	}

	/**
	 * {@inheritDoc}
	 */
	public Definition<?> getMySheetKeyDefinition() {
		return book.get(mySheetKeyId);
	}

	/**
	 * {@inheritDoc}
	 */
	public Definition<?> getSheetKeyDefinition() {
		return book.get(sheetKeyId);
	}

	/**
	 * {@inheritDoc}
	 */
	public Definition<?> getValueDefinition() {
		return book.get(valueId);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Map<String, Object> toMap() {
		Map<String, Object> map = super.toMap();
		map.put("mySheetKeyId", mySheetKeyId);
		map.put("sheetKeyId", sheetKeyId);
		map.put("valueId", valueId);
		map.put("book", book.getFullId());
		return map;
	}

	public Object apply(Object value) {
		return value;
	}

}
