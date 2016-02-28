package com.purejadeite.jadegreen.definition.cell;

import static com.purejadeite.util.collection.RoughlyMapUtils.*;

import java.util.Map;

import org.apache.commons.lang3.ObjectUtils;

import com.purejadeite.jadegreen.definition.DefinitionInterface;
import com.purejadeite.jadegreen.definition.ParentDefinitionInterface;
import com.purejadeite.jadegreen.definition.SheetDefinition;
import com.purejadeite.util.SimpleValidator;
import com.purejadeite.util.collection.Table;

/**
 * 単一セルの結合定義です
 *
 * @author mitsuhiroseino
 */
public class JoinedCellDefinition extends AbstractNoAdressCellDefinition<ParentDefinitionInterface<?,?>, Object>
		implements JoinedCellDefinitionInterface<ParentDefinitionInterface<?,?>, Object> {

	private static final long serialVersionUID = -6688614988181481927L;

	/**
	 * 必須項目名称
	 */
	private static final String[] CONFIG = {};

	/**
	 * 自身のシートのキーになる項目のID
	 */
	protected String myKeyId;

	/**
	 * 結合先のシートのID
	 */
	protected String sheetId;

	/**
	 * 結合先のキーになる項目のID
	 */
	protected String keyId;

	/**
	 * 結合先の値を取得する対象の項目のID
	 */
	protected String valueId;

	/**
	 * コンストラクタ
	 *
	 * @param parent
	 *            親定義
	 * @param config
	 *            コンフィグ
	 */
	public JoinedCellDefinition(ParentDefinitionInterface<?,?> parent, Map<String, Object> config) {
		super(parent, config);
		Map<String, String> joinConfig = getMap(config, CFG_JOIN);
		SimpleValidator.containsKey(joinConfig, CONFIG);

		SheetDefinition sheet = parent.getSheet();
		// 相手シートのID
		sheetId = ObjectUtils.firstNonNull(joinConfig.get(CFG_SHEET_ID), sheet.getRelationSheetId());
		// 相手シートのキー項目のID
		keyId = ObjectUtils.firstNonNull(joinConfig.get(CFG_KEY_ID), sheet.getRelationKeyId());
		// 自身のシートのキー項目のID
		myKeyId = ObjectUtils.firstNonNull(joinConfig.get(CFG_MY_KEY_ID), sheet.getRelationMyKeyId(), keyId);
		// 相手シートから取得する項目のID
		valueId = ObjectUtils.firstNonNull(joinConfig.get(CFG_VALUE_ID), id);
	}

	public static boolean assess(Map<String, Object> config, ParentDefinitionInterface<?, ?> table) {
		return config.containsKey(CFG_JOIN);
	}

	public String getSheetId() {
		return keyId;
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
		map.put("sheetId", sheetId);
		map.put("keyId", keyId);
		map.put("valueId", valueId);
		return map;
	}

	@Override
	public Object capture(Table<String> table) {
		return null;
	}

}
