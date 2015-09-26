package com.purejadeite.jadegreen.definition.cell;

import static com.purejadeite.jadegreen.definition.DefinitionKeys.*;

import java.util.List;
import java.util.Map;

import com.purejadeite.jadegreen.RoughlyMapUtils;
import com.purejadeite.jadegreen.definition.Definition;
import com.purejadeite.jadegreen.definition.LinkDefinition;
import com.purejadeite.jadegreen.definition.WorkbookDefinition;
import com.purejadeite.jadegreen.definition.range.RangeDefinition;

/**
 * Rangeの構成要素となるCellリンク定義
 *
 * @author mitsuhiroseino
 */
public class LinkRangeCellDefinitionImpl extends AbstractNoAdressRangeCellDefinition<RangeDefinition<?>> implements LinkCellDefinition<RangeDefinition<?>>, LinkDefinition<RangeDefinition<?>> {

	private static final String CFG_MY_SHEET_KEY_ID = "mySheetKeyId";
	private static final String CFG_MY_KEY_ID = "myKeyId";
	private static final String CFG_SHEET_KEY_ID = "sheetKeyId";
	private static final String CFG_KEY_ID = "keyId";
	private static final String CFG_VALUE_ID = "valueId";

	/**
	 * 必須項目名称
	 */
	private static final String[] CONFIG = { CFG_MY_SHEET_KEY_ID, CFG_MY_KEY_ID, CFG_SHEET_KEY_ID, CFG_KEY_ID,
			CFG_VALUE_ID };

	/**
	 * 取得対象のRangeの定義ID
	 */
	private String mySheetKeyId;

	/**
	 * 自分側のキーとなる項目の定義ID
	 */
	private String myKeyId;

	/**
	 * 取得対象のRangeの定義ID
	 */
	private String sheetKeyId;

	/**
	 * 取得対象のキーとなる項目の定義ID
	 */
	private String keyId;

	/**
	 * 取得対象の値の定義ID
	 */
	private String valueId;

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
		this.mySheetKeyId = linkConfig.get(CFG_MY_SHEET_KEY_ID);
		this.myKeyId = linkConfig.get(CFG_MY_KEY_ID);
		this.sheetKeyId = linkConfig.get(CFG_SHEET_KEY_ID);
		this.keyId = linkConfig.get(CFG_KEY_ID);
		this.valueId = linkConfig.get(CFG_VALUE_ID);
	}

	public static CellDefinition<RangeDefinition<?>> newInstance(WorkbookDefinition book, RangeDefinition<?> range, Map<String, Object> config) {
		String id = RoughlyMapUtils.getString(config, ID);
		boolean noOutput = RoughlyMapUtils.getBooleanValue(config, NO_OUTPUT);
		List<Map<String, Object>> options = RoughlyMapUtils.getList(config, OPTIONS);
		Map<String, String> link = RoughlyMapUtils.getMap(config, LINK);
		return new LinkRangeCellDefinitionImpl(book, range, id, noOutput, options, link);
	}

	public String getSheetKeyId() {
		return sheetKeyId;
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
	public Definition<?> getMyKeyDefinition() {
		return book.get(myKeyId);
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
	public Definition<?> getKeyDefinition() {
		return book.get(keyId);
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
		map.put("myKeyId", myKeyId);
		map.put("sheetKeyId", sheetKeyId);
		map.put("keyId", keyId);
		map.put("valueId", valueId);
		return map;
	}

}
