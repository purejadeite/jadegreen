package com.purejadeite.jadegreen.definition;

import static com.purejadeite.util.collection.RoughlyMapUtils.*;

import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.ObjectUtils;

import com.purejadeite.util.SimpleValidator;

/**
 * リンク定義です
 *
 * @author mitsuhiroseino
 */
public class LinkedDefinition extends AbstractDefinition<ParentDefinitionInterface<?, ?>> {

	/**
	 * セルの結合
	 */
	public static final String CFG_LINK = "link";

	public static final String CFG_SHEET_ID = "sheetId";

	public static final String CFG_KEY_ID = "keyId";

	public static final String CFG_VALUE_ID = "valueId";

	public static final String CFG_MY_KEY_ID = "myKeyId";

	/**
	 * 必須項目名称
	 */
	private static final String[] CONFIG = { CFG_LINK };

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
	public LinkedDefinition(ParentDefinitionInterface<?, ?> parent, Map<String, Object> config) {
		super(parent, config);
		SimpleValidator.containsKey(config, CONFIG);

		SheetDefinition sheet = parent.getSheet();
		boolean link = getBooleanValue(config, CFG_LINK);
		if (link) {
			sheetId = sheet.getRelationSheetId();
			// 相手シートのキー項目のID
			keyId = sheet.getRelationKeyId();
			// 自身のシートのキー項目のID
			myKeyId = ObjectUtils.firstNonNull(sheet.getRelationMyKeyId(), keyId);
			// 相手シートから取得する項目のID
			valueId = id;
		} else {
			Map<String, String> linkConfig = getMap(config, CFG_LINK);
			// 相手シートのID
			sheetId = ObjectUtils.firstNonNull(linkConfig.get(CFG_SHEET_ID), sheet.getRelationSheetId());
			// 相手シートのキー項目のID
			keyId = ObjectUtils.firstNonNull(linkConfig.get(CFG_KEY_ID), sheet.getRelationKeyId());
			// 自身のシートのキー項目のID
			myKeyId = ObjectUtils.firstNonNull(linkConfig.get(CFG_MY_KEY_ID), sheet.getRelationMyKeyId(), keyId);
			// 相手シートから取得する項目のID
			valueId = ObjectUtils.firstNonNull(linkConfig.get(CFG_VALUE_ID), id);

		}
	}

	public static boolean assess(Map<String, Object> config, ParentDefinitionInterface<?, ?> table) {
		return config.containsKey(CFG_LINK);
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
	protected void buildOptions(DefinitionInterface<?> definition, List<Map<String, Object>> options) {
	}

}
