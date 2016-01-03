package com.purejadeite.jadegreen.definition.cell;

import static com.purejadeite.jadegreen.definition.DefinitionKeys.*;

import java.util.List;
import java.util.Map;

import com.purejadeite.jadegreen.definition.LinkDefinition;
import com.purejadeite.jadegreen.definition.MappingDefinition;
import com.purejadeite.jadegreen.definition.WorkbookDefinition;
import com.purejadeite.jadegreen.definition.WorksheetDefinition;
import com.purejadeite.jadegreen.definition.range.RangeDefinition;
import com.purejadeite.util.RoughlyMapUtils;

/**
 * Rangeの構成要素となるCellリンク定義
 *
 * @author mitsuhiroseino
 */
public class LinkRangeCellDefinitionImpl extends AbstractNoAdressRangeCellDefinition<RangeDefinition<?>> implements LinkCellDefinition<RangeDefinition<?>>, LinkDefinition<RangeDefinition<?>> {

	private static final long serialVersionUID = 2442986614257910095L;

	private static final String CFG_SHEET_ID = "sheetId";

	private static final String CFG_KEY_ID = "keyId";

	private static final String CFG_TABLE_KEY_ID = "tableKeyId";

	private static final String CFG_VALUE_ID = "valueId";

	private static final String CFG_MY_KEY_ID = "myKeyId";

	private static final String CFG_MY_TABLE_KEY_ID = "myTableKeyId";

	/**
	 * 必須項目名称
	 */
	private static final String[] CONFIG = { CFG_SHEET_ID, CFG_KEY_ID, CFG_TABLE_KEY_ID};

	/**
	 * 取得対象のシートの定義ID
	 */
	private String sheetId;

	/**
	 * 取得対象のシートのキーとなる項目の定義ID
	 */
	private String keyId;

	/**
	 * 取得対象のテーブルのキーとなる項目の定義ID
	 */
	private String tableKeyId;

	/**
	 * 取得対象の値の定義ID
	 */
	private String valueId;

	/**
	 * 自分側のシートのキーとなるの定義ID
	 */
	private String myKeyId;

	/**
	 * 自分側のテーブルのキーとなる項目の定義ID
	 */
	private String myTableKeyId;

	/**
	 * Book読み込み定義
	 */
	private WorkbookDefinition book;

	/**
	 *
	 * @param book
	 * @param range
	 * @param id
	 * @param options
	 * @param linkConfig
	 */
	public LinkRangeCellDefinitionImpl(WorkbookDefinition book, RangeDefinition<?> range, String id, boolean noOutput,
			List<Map<String, Object>> options, Map<String, String> linkConfig) {
		super(range, id, noOutput, options);
		this.validateConfig(linkConfig, CONFIG);
		this.book = book;
		// 相手シートのID
		if (linkConfig.containsKey(CFG_SHEET_ID)) {
			sheetId = linkConfig.get(CFG_SHEET_ID);
		} else {
			sheetId = sheet.getJoinSheetId();
		}
		// 相手シートのキー項目のID
		if (linkConfig.containsKey(CFG_KEY_ID)) {
			keyId = linkConfig.get(CFG_KEY_ID);
		} else {
			keyId = sheet.getJoinKeyId();
		}
		// 自身のシートのキー項目のID
		if (linkConfig.containsKey(CFG_MY_KEY_ID)) {
			myKeyId = linkConfig.get(CFG_MY_KEY_ID);
		} else if (sheet.getJoinMyKeyId() != null) {
			myKeyId = sheet.getJoinMyKeyId();
		} else {
			myKeyId = keyId;
		}
		// 相手シートのテーブルのキー項目のID
		tableKeyId = linkConfig.get(CFG_TABLE_KEY_ID);
		// 自身のシートのテーブルのキー項目のID
		if (linkConfig.containsKey(CFG_MY_TABLE_KEY_ID)) {
			myTableKeyId = linkConfig.get(CFG_MY_TABLE_KEY_ID);
		} else {
			myTableKeyId = tableKeyId;
		}
		// 相手シートから取得する項目のID
		if (linkConfig.containsKey(CFG_VALUE_ID)) {
			valueId = linkConfig.get(CFG_VALUE_ID);
		} else {
			valueId = id;
		}
	}

	public static CellDefinition<RangeDefinition<?>> newInstance(WorkbookDefinition book, RangeDefinition<?> range, Map<String, Object> config) {
		String id = RoughlyMapUtils.getString(config, ID);
		boolean noOutput = RoughlyMapUtils.getBooleanValue(config, NO_OUTPUT);
		List<Map<String, Object>> options = RoughlyMapUtils.getList(config, OPTIONS);
		Map<String, String> link = RoughlyMapUtils.getMap(config, LINK);
		return new LinkRangeCellDefinitionImpl(book, range, id, noOutput, options, link);
	}

	public String getSheetId() {
		return sheetId;
	}

	public String getKeyId() {
		return keyId;
	}

	public String getValueId() {
		return valueId;
	}

	/**
	 * {@inheritDoc}
	 */
	public MappingDefinition<?> getMyTableKeyDefinition() {
		return sheet.getDefinition(myTableKeyId);
	}

	/**
	 * {@inheritDoc}
	 */
	public MappingDefinition<?> getMyKeyDefinition() {
		return sheet.getDefinition(myKeyId);
	}

	/**
	 * {@inheritDoc}
	 */
	public MappingDefinition<?> getKeyDefinition() {
		WorksheetDefinition sheet = book.getSheet(sheetId);
		return sheet.getDefinition(keyId);
	}

	/**
	 * {@inheritDoc}
	 */
	public MappingDefinition<?> getTableKeyDefinition() {
		WorksheetDefinition sheet = book.getSheet(sheetId);
		return sheet.getDefinition(tableKeyId);
	}

	/**
	 * {@inheritDoc}
	 */
	public MappingDefinition<?> getValueDefinition() {
		WorksheetDefinition sheet = book.getSheet(sheetId);
		return sheet.getDefinition(valueId);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Map<String, Object> toMap() {
		Map<String, Object> map = super.toMap();
		map.put("mySheetKeyId", myKeyId);
		map.put("myKeyId", myTableKeyId);
		map.put("sheetKeyId", sheetId);
		map.put("keyId", keyId);
		map.put("valueId", valueId);
		return map;
	}

}
