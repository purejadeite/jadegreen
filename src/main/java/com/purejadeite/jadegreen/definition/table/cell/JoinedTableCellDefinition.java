package com.purejadeite.jadegreen.definition.table.cell;

import static com.purejadeite.util.collection.RoughlyMapUtils.*;

import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.ObjectUtils;

import com.purejadeite.jadegreen.definition.DefinitionInterface;
import com.purejadeite.jadegreen.definition.ParentDefinitionInterface;
import com.purejadeite.jadegreen.definition.SheetDefinition;
import com.purejadeite.jadegreen.definition.cell.JoinedCellDefinitionInterface;
import com.purejadeite.jadegreen.definition.table.TableDefinitionInterface;
import com.purejadeite.util.SimpleValidator;
import com.purejadeite.util.collection.Table;

/**
 * Tableの構成要素となるCell結合定義
 *
 * @author mitsuhiroseino
 */
public class JoinedTableCellDefinition extends AbstractNoAdressTableCellDefinition<TableDefinitionInterface<?>, List<Object>> implements JoinedCellDefinitionInterface<TableDefinitionInterface<?>, List<Object>> {

	private static final long serialVersionUID = 2442986614257910095L;

	public static final String CFG_TABLE_KEY_ID = "tableKeyId";

	public static final String CFG_MY_TABLE_KEY_ID = "myTableKeyId";

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
	 * コンストラクタ
	 *
	 * @param sheet
	 *            シート定義
	 * @param parent
	 *            親定義
	 * @param config
	 *            コンフィグ
	 */
	public JoinedTableCellDefinition(SheetDefinition sheet, TableDefinitionInterface<?> parent, Map<String, Object> config) {
		super(parent, config);
		Map<String, String> joinConfig = getMap(config, CFG_JOIN);
		SimpleValidator.containsKey(joinConfig, CONFIG);
		// 相手シートのID
		sheetId = ObjectUtils.firstNonNull(joinConfig.get(CFG_SHEET_ID), sheet.getRelationSheetId());
		// 相手シートのキー項目のID
		keyId = ObjectUtils.firstNonNull(joinConfig.get(CFG_KEY_ID), sheet.getRelationKeyId());
		// 自身のシートのキー項目のID
		myKeyId = ObjectUtils.firstNonNull(joinConfig.get(CFG_MY_KEY_ID), sheet.getRelationMyKeyId(), keyId);
		// 相手シートのテーブルのキー項目のID
		tableKeyId = joinConfig.get(CFG_TABLE_KEY_ID);
		// 自身のシートのテーブルのキー項目のID
		myTableKeyId = ObjectUtils.firstNonNull(joinConfig.get(CFG_MY_TABLE_KEY_ID), tableKeyId);
		// 相手シートから取得する項目のID
		valueId = ObjectUtils.firstNonNull(joinConfig.get(CFG_VALUE_ID), id);
	}

	public static boolean assess(Map<String, Object> config, ParentDefinitionInterface<?, ?> table) {
		return table != null && config.containsKey(CFG_JOIN);
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
	public DefinitionInterface<?> getMyTableKeyDefinition() {
		return this.getCell(myTableKeyId);
	}

	/**
	 * {@inheritDoc}
	 */
	public DefinitionInterface<?> getMyKeyDefinition() {
		return this.getCell(myKeyId);
	}

	/**
	 * {@inheritDoc}
	 */
	public DefinitionInterface<?> getKeyDefinition() {
		return this.getCell(sheetId, keyId);
	}

	/**
	 * {@inheritDoc}
	 */
	public DefinitionInterface<?> getTableKeyDefinition() {
		return this.getCell(sheetId, tableKeyId);
	}

	/**
	 * {@inheritDoc}
	 */
	public DefinitionInterface<?> getValueDefinition() {
		return this.getCell(sheetId, valueId);
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

	@Override
	public List<Object> capture(Table<String> table) {
		return null;
	}

	@Override
	public List<Object> capture(Table<String> table, int size) {
		return null;
	}

}
