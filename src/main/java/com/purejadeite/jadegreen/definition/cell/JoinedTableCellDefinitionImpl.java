package com.purejadeite.jadegreen.definition.cell;

import static com.purejadeite.jadegreen.definition.DefinitionKeys.*;

import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.ObjectUtils;

import com.purejadeite.jadegreen.definition.Definition;
import com.purejadeite.jadegreen.definition.DefinitionManager;
import com.purejadeite.jadegreen.definition.JoinDefinition;
import com.purejadeite.jadegreen.definition.WorksheetDefinition;
import com.purejadeite.jadegreen.definition.table.TableDefinition;
import com.purejadeite.util.SimpleValidator;
import com.purejadeite.util.collection.RoughlyMapUtils;

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
	private static final String[] CONFIG = {CFG_TABLE_KEY_ID};

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
	 *
	 * @param book
	 * @param table
	 * @param id
	 * @param options
	 * @param joinConfig
	 */
	public JoinedTableCellDefinitionImpl(WorksheetDefinition sheet, TableDefinition<?> table, String id,
			List<Map<String, Object>> options, Map<String, String> joinConfig) {
		super(table, id, options);
		SimpleValidator.containsKey(joinConfig, CONFIG);
		this.sheet = sheet;
		// 相手シートのID
		sheetId = ObjectUtils.firstNonNull(joinConfig.get(CFG_SHEET_ID), sheet.getJoinSheetId());
		// 相手シートのキー項目のID
		keyId = ObjectUtils.firstNonNull(joinConfig.get(CFG_KEY_ID), sheet.getJoinKeyId());
		// 自身のシートのキー項目のID
		myKeyId = ObjectUtils.firstNonNull(joinConfig.get(CFG_MY_KEY_ID), sheet.getJoinMyKeyId(), keyId);
		// 相手シートのテーブルのキー項目のID
		tableKeyId = joinConfig.get(CFG_TABLE_KEY_ID);
		// 自身のシートのテーブルのキー項目のID
		myTableKeyId = ObjectUtils.firstNonNull(joinConfig.get(CFG_MY_TABLE_KEY_ID), tableKeyId);
		// 相手シートから取得する項目のID
		valueId = ObjectUtils.firstNonNull(joinConfig.get(CFG_VALUE_ID), id);
	}

	public static CellDefinition<TableDefinition<?>> newInstance(WorksheetDefinition sheet, TableDefinition<?> table, Map<String, Object> config) {
		String id = RoughlyMapUtils.getString(config, ID);
		List<Map<String, Object>> options = RoughlyMapUtils.getList(config, OPTIONS);
		Map<String, String> join = RoughlyMapUtils.getMap(config, JOIN);
		return new JoinedTableCellDefinitionImpl(sheet, table, id, options, join);
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
	public Definition<?> getMyTableKeyDefinition() {
		return DefinitionManager.get(sheet, myTableKeyId);
	}

	/**
	 * {@inheritDoc}
	 */
	public Definition<?> getMyKeyDefinition() {
		return DefinitionManager.get(sheet, myKeyId);
	}

	/**
	 * {@inheritDoc}
	 */
	public Definition<?> getKeyDefinition() {
		return DefinitionManager.get(sheetId, keyId);
	}

	/**
	 * {@inheritDoc}
	 */
	public Definition<?> getTableKeyDefinition() {
		return DefinitionManager.get(sheetId, tableKeyId);
	}

	/**
	 * {@inheritDoc}
	 */
	public Definition<?> getValueDefinition() {
		return DefinitionManager.get(sheetId, valueId);
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
