package com.purejadeite.jadegreen.definition.cell;

import static com.purejadeite.jadegreen.definition.DefinitionKeys.*;

import java.util.List;
import java.util.Map;

import com.purejadeite.jadegreen.definition.JoinDefinition;
import com.purejadeite.jadegreen.definition.MappingDefinition;
import com.purejadeite.jadegreen.definition.WorkbookDefinition;
import com.purejadeite.jadegreen.definition.WorksheetDefinition;
import com.purejadeite.jadegreen.definition.table.TableDefinition;
import com.purejadeite.util.RoughlyMapUtils;

/**
 * Tableの構成要素となるCell結合定義
 *
 * @author mitsuhiroseino
 */
public class JoinedTableCellDefinitionImpl extends AbstractNoAdressTableCellDefinition<TableDefinition<?>> implements JoinedCellDefinition<TableDefinition<?>>, JoinDefinition<TableDefinition<?>> {

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
	 * @param table
	 * @param id
	 * @param options
	 * @param joinConfig
	 */
	public JoinedTableCellDefinitionImpl(WorkbookDefinition book, TableDefinition<?> table, String id, boolean noOutput,
			List<Map<String, Object>> options, Map<String, String> joinConfig) {
		super(table, id, noOutput, options);
		this.validateConfig(joinConfig, CONFIG);
		this.book = book;
		// 相手シートのID
		if (joinConfig.containsKey(CFG_SHEET_ID)) {
			sheetId = joinConfig.get(CFG_SHEET_ID);
		} else {
			sheetId = sheet.getJoinSheetId();
		}
		// 相手シートのキー項目のID
		if (joinConfig.containsKey(CFG_KEY_ID)) {
			keyId = joinConfig.get(CFG_KEY_ID);
		} else {
			keyId = sheet.getJoinKeyId();
		}
		// 自身のシートのキー項目のID
		if (joinConfig.containsKey(CFG_MY_KEY_ID)) {
			myKeyId = joinConfig.get(CFG_MY_KEY_ID);
		} else if (sheet.getJoinMyKeyId() != null) {
			myKeyId = sheet.getJoinMyKeyId();
		} else {
			myKeyId = keyId;
		}
		// 相手シートのテーブルのキー項目のID
		tableKeyId = joinConfig.get(CFG_TABLE_KEY_ID);
		// 自身のシートのテーブルのキー項目のID
		if (joinConfig.containsKey(CFG_MY_TABLE_KEY_ID)) {
			myTableKeyId = joinConfig.get(CFG_MY_TABLE_KEY_ID);
		} else {
			myTableKeyId = tableKeyId;
		}
		// 相手シートから取得する項目のID
		if (joinConfig.containsKey(CFG_VALUE_ID)) {
			valueId = joinConfig.get(CFG_VALUE_ID);
		} else {
			valueId = id;
		}
	}

	public static CellDefinition<TableDefinition<?>> newInstance(WorkbookDefinition book, TableDefinition<?> table, Map<String, Object> config) {
		String id = RoughlyMapUtils.getString(config, ID);
		boolean noOutput = RoughlyMapUtils.getBooleanValue(config, NO_OUTPUT);
		List<Map<String, Object>> options = RoughlyMapUtils.getList(config, OPTIONS);
		Map<String, String> join = RoughlyMapUtils.getMap(config, JOIN);
		return new JoinedTableCellDefinitionImpl(book, table, id, noOutput, options, join);
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
		return sheet.get(myTableKeyId);
	}

	/**
	 * {@inheritDoc}
	 */
	public MappingDefinition<?> getMyKeyDefinition() {
		return sheet.get(myKeyId);
	}

	/**
	 * {@inheritDoc}
	 */
	public MappingDefinition<?> getKeyDefinition() {
		WorksheetDefinition sheet = book.get(sheetId);
		return sheet.get(keyId);
	}

	/**
	 * {@inheritDoc}
	 */
	public MappingDefinition<?> getTableKeyDefinition() {
		WorksheetDefinition sheet = book.get(sheetId);
		return sheet.get(tableKeyId);
	}

	/**
	 * {@inheritDoc}
	 */
	public MappingDefinition<?> getValueDefinition() {
		WorksheetDefinition sheet = book.get(sheetId);
		return sheet.get(valueId);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Map<String, Object> toMap() {
		Map<String, Object> map = super.toMap();
		map.put("myKeyId", myKeyId);
		map.put("myTableKeyId", myTableKeyId);
		map.put("sheetId", sheetId);
		map.put("keyId", keyId);
		map.put("tableKeyId", tableKeyId);
		map.put("valueId", valueId);
		return map;
	}

}
