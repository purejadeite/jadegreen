package com.purejadeite.jadegreen.definition;

import static com.purejadeite.util.collection.RoughlyMapUtils.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;

import com.purejadeite.jadegreen.DefinitionException;
import com.purejadeite.jadegreen.option.sheet.SheetOptionManager;
import com.purejadeite.util.SimpleComparison;
import com.purejadeite.util.SimpleValidator;
import com.purejadeite.util.collection.StringKeyNestedMap;
import com.purejadeite.util.collection.Table;

/**
 * Sheet読み込み定義
 *
 * @author mitsuhiroseino
 *
 */
public class SheetDefinition extends AbstractParentDefinition<BookDefinition, DefinitionInterface<?>> {

	private static final long serialVersionUID = -1303967958765873003L;
	/**
	 * 取得条件・シート名
	 */
	protected static final String CFG_NAME = "name";

	/**
	 * Sheet配下の全Cellの定義
	 */
	protected static final String CFG_CELLS = "cells";

	/**
	 * コンフィグ：target
	 */
	protected static final String CFG_TARGET = "target";

	/**
	 * コンフィグ：target・シート名
	 */
	protected static final String CFG_TARGET_NAME = "target.name";

	/**
	 * コンフィグ：target・Cell・Row
	 */
	protected static final String CFG_TARGET_CELL_ROW = "target.cell.row";

	/**
	 * コンフィグ：target・Cell・Column
	 */
	protected static final String CFG_TARGET_CELL_COLUMN = "target.cell.column";

	/**
	 * コンフィグ：target・Cell・Value
	 */
	protected static final String CFG_TARGET_CELL_VALUE = "target.cell.value";

	/**
	 * コンフィグ：シートの主キー
	 */
	protected static final String[] CFG_KEY_IDS = { "keyId", "keyIds" };

	/**
	 * コンフィグ：join・相手先シートID
	 */
	protected static final String CFG_JOIN_SHEET_ID = "join.sheetId";

	/**
	 * コンフィグ：join・相手先キーID
	 */
	protected static final String CFG_JOIN_KEY_ID = "join.keyId";

	/**
	 * コンフィグ：join・自身のキーID
	 */
	protected static final String CFG_JOIN_MY_KEY_ID = "join.myKeyId";

	/**
	 * 集約
	 */
	protected static final String CFG_UNION = "union";

	/**
	 * 出力
	 */
	protected static final String CFG_OUTPUT = "output";

	/**
	 * 必須項目名称
	 */
	private static final String[] CONFIG = { CFG_TARGET };

	/**
	 * 対象シート条件・シート名
	 */
	protected String name;

	/**
	 * 対象シート条件・シート名
	 */
	protected String targetName;

	/**
	 * 対象シート条件・Cell行番号
	 */
	protected int targetCellRow;

	/**
	 * 対象シート条件・Cell列番号
	 */
	protected int targetCellColumn;

	/**
	 * 対象シート条件・Cell値
	 */
	protected String targetCellValue;

	/**
	 * シートの主キー
	 */
	protected List<String> keyIds;

	/**
	 * 結合先のシートのID
	 */
	protected String joinSheetId;

	/**
	 * 結合先のキーになる項目のID
	 */
	protected String joinKeyId;

	/**
	 * 自身のシートのキーになる項目のID
	 */
	protected String joinMyKeyId;

	/**
	 * データの統合を行うか
	 */
	protected boolean union;

	/**
	 * <pre>
	 * データの出力を行うか
	 * true: 行う
	 * false: 行わない
	 * </pre>
	 */
	protected boolean output = false;

	protected Map<String, DefinitionInterface<?>> cells = new HashMap<>();

	/**
	 * コンストラクタ
	 *
	 * @param parent
	 *            Book読み込み定義
	 * @param config
	 *            コンフィグ
	 */
	public SheetDefinition(BookDefinition parent, Map<String, Object> config) {
		super(parent, config);
		SimpleValidator.containsKey(config, CONFIG);
		Map<String, Object> cfg = new StringKeyNestedMap(config);
		this.targetName = getString(cfg, CFG_TARGET_NAME);
		this.targetCellRow = getIntValue(cfg, CFG_TARGET_CELL_ROW) - 1;
		this.targetCellColumn = getIntValue(cfg, CFG_TARGET_CELL_COLUMN) - 1;
		this.targetCellValue = getString(cfg, CFG_TARGET_CELL_VALUE);
		this.joinSheetId = getString(cfg, CFG_JOIN_SHEET_ID);
		this.joinKeyId = getString(cfg, CFG_JOIN_KEY_ID);
		this.joinMyKeyId = getString(cfg, CFG_JOIN_MY_KEY_ID);
		this.union = getBooleanValue(cfg, CFG_UNION);
		this.output = getBooleanValue(cfg, CFG_OUTPUT);
		this.keyIds = getAsList(cfg, CFG_KEY_IDS);
	}

	public String getJoinSheetId() {
		return joinSheetId;
	}

	public String getJoinKeyId() {
		return joinKeyId;
	}

	public String getJoinMyKeyId() {
		return joinMyKeyId;
	}

	/**
	 * UNIONを行うか
	 */
	public boolean isUnion() {
		return union;
	}

	/**
	 * Mapへの出力を行うか
	 */
	public boolean isOutput() {
		return output;
	}

	public void setOutput(boolean output) {
		this.output = output;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void addChild(DefinitionInterface<?> child) {
		super.addChild(child);
		this.addCell(child);
	}

	/**
	 * Cell読み込み定義を追加します
	 *
	 * @param child
	 *            Cell読み込み定義
	 */
	protected void addCell(DefinitionInterface<?> child) {
		if (child != null) {
			if (cells.put(child.getId(), child) != null) {
				throw new DefinitionException("idはsheet配下で一意になるように設定してください:" + child.getFullId());
			}
			if (child instanceof ParentDefinitionInterface) {
				// 親の場合は子要素もばらして追加
				ParentDefinitionInterface<?, ?> parent = (ParentDefinitionInterface<?, ?>) child;
				for (DefinitionInterface<?> c : parent.getChildren()) {
					addCell(c);
				}
			}
		}
	}

	/**
	 * シート名を取得します
	 *
	 * @return シート名
	 */
	public String getName() {
		return name;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public SheetDefinition getSheet() {
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Map<String, Object> toMap() {
		Map<String, Object> map = super.toMap();
		map.put("name", name);
		map.put("output", output);
		return map;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected void buildOptions(DefinitionInterface<?> definition, List<Map<String, Object>> options) {
		this.options = SheetOptionManager.build(definition, options);
	}

	/**
	 * テーブルが取得対象か判定します
	 * @param name シート名
	 * @param table
	 * @return
	 */
	public boolean match(String name, Table<String> table) {
		if (targetName != null) {
			if (!SimpleComparison.compare(targetName, name)) {
				return false;
			}
		}
		if (targetCellRow != -1 && targetCellColumn != -1) {
			String value = table.get(targetCellRow, targetCellColumn);
			if (!StringUtils.equals(targetCellValue, value)) {
				return false;
			}
		}
		return true;
	}

	/**
	 * 配下のセルを全て取得します
	 * @return 配下のセル
	 */
	public Map<String, DefinitionInterface<?>> getCells() {
		return cells;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public DefinitionInterface<?> getCell(String id) {
		return cells.get(id);
	}

	public static boolean assess(Map<String, Object> config, ParentDefinitionInterface<?, ?> table) {
		return config.containsKey(CFG_TARGET) && !config.containsKey(CFG_UNION);
	}

}
