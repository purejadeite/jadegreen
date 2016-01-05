package com.purejadeite.jadegreen.definition.cell;

import static com.purejadeite.jadegreen.definition.DefinitionKeys.*;

import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.ObjectUtils;

import com.purejadeite.jadegreen.definition.JoinDefinition;
import com.purejadeite.jadegreen.definition.MappingDefinition;
import com.purejadeite.jadegreen.definition.WorkbookDefinition;
import com.purejadeite.jadegreen.definition.WorksheetDefinition;
import com.purejadeite.util.RoughlyMapUtils;

/**
 * 単一セルの結合定義です
 *
 * @author mitsuhiroseino
 */
public class JoinedCellDefinitionImpl extends AbstractNoAdressCellDefinition<WorksheetDefinition>
		implements JoinedCellDefinition<WorksheetDefinition>, JoinDefinition<WorksheetDefinition> {

	private static final long serialVersionUID = -6688614988181481927L;

	private static final String CFG_SHEET_ID = "sheetId";

	private static final String CFG_KEY_ID = "keyId";

	private static final String CFG_MY_KEY_ID = "myKeyId";

	private static final String CFG_VALUE_ID = "valueId";

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
	 * @param joinConfig
	 *            結合設定
	 */
	protected JoinedCellDefinitionImpl(WorkbookDefinition book, WorksheetDefinition sheet, String id,
			boolean noOutput, List<Map<String, Object>> options, Map<String, String> joinConfig) {
		super(sheet, id, noOutput, options);
		this.validateConfig(joinConfig, CONFIG);
		this.book = book;
		this.sheet = sheet;
		// 相手シートのID
		sheetId = ObjectUtils.firstNonNull(joinConfig.get(CFG_SHEET_ID), sheet.getJoinSheetId());
		// 相手シートのキー項目のID
		keyId = ObjectUtils.firstNonNull(joinConfig.get(CFG_KEY_ID), sheet.getJoinKeyId());
		// 自身のシートのキー項目のID
		myKeyId = ObjectUtils.firstNonNull(joinConfig.get(CFG_MY_KEY_ID), sheet.getJoinMyKeyId(), keyId);
		// 相手シートから取得する項目のID
		valueId = ObjectUtils.firstNonNull(joinConfig.get(CFG_VALUE_ID), id);
	}

	public static MappingDefinition<?> newInstance(WorkbookDefinition book, WorksheetDefinition sheet,
			Map<String, Object> config) {
		String id = RoughlyMapUtils.getString(config, ID);
		boolean noOutput = RoughlyMapUtils.getBooleanValue(config, NO_OUTPUT);
		List<Map<String, Object>> options = RoughlyMapUtils.getList(config, OPTIONS);
		Map<String, String> join = RoughlyMapUtils.getMap(config, JOIN);
		return new JoinedCellDefinitionImpl(book, sheet, id, noOutput, options, join);
	}

	public String getSheetId() {
		return keyId;
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
		map.put("mySheetKeyId", myKeyId);
		map.put("sheetKeyId", keyId);
		map.put("valueId", valueId);
		map.put("book", book.getFullId());
		return map;
	}

	public Object apply(Object value) {
		return value;
	}

}
