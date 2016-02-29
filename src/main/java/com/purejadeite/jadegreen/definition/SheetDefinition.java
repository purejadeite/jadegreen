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
	 * コンフィグ：シート間の関連
	 */
	protected static final String CFG_RELATION = "relation";

	/**
	 * コンフィグ：シート間の関連・相手先シートID
	 */
	protected static final String CFG_RELATION_SHEET_ID = "sheetId";

	/**
	 * コンフィグ：シート間の関連・相手先キーID
	 */
	protected static final String CFG_RELATION_KEY_ID = "keyId";

	/**
	 * コンフィグ：シート間の関連・自身のキーID
	 */
	protected static final String CFG_RELATION_MY_KEY_ID = "myKeyId";

	/**
	 * 集約
	 */
	protected static final String CFG_UNION = "union";

	/**
	 * ルート
	 */
	protected static final String CFG_ROOT_ID = "rootId";

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
	 * 関連するシートのID
	 */
	protected String relationSheetId;

	/**
	 * 関連するシートのキーになる項目のID
	 */
	protected String relationKeyId;

	/**
	 * 自身のシートのキーになる項目のID
	 */
	protected String relationMyKeyId;

	/**
	 * データの統合を行うか
	 */
	protected boolean union;

	/**
	 * ルートになる項目のID
	 */
	protected String rootId;

	/**
	 * <pre>
	 * データの出力を行うか
	 * true: 行う
	 * false: 行わない
	 * </pre>
	 */
	protected boolean output = false;

	/**
	 * 配下の全セル
	 */
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
		this.targetCellRow = getIntValue(cfg, CFG_TARGET_CELL_ROW);
		this.targetCellColumn = getIntValue(cfg, CFG_TARGET_CELL_COLUMN);
		this.targetCellValue = getString(cfg, CFG_TARGET_CELL_VALUE);

		Map<String, Object> relationCfg = getMap(config, CFG_RELATION);
		if (relationCfg != null) {
			this.relationSheetId = getString(relationCfg, CFG_RELATION_SHEET_ID);
			this.relationKeyId = getString(relationCfg, CFG_RELATION_KEY_ID);
			this.relationMyKeyId = getString(relationCfg, CFG_RELATION_MY_KEY_ID, relationKeyId);
		}

		this.union = getBooleanValue(cfg, CFG_UNION);
		this.output = getBooleanValue(cfg, CFG_OUTPUT);
		this.keyIds = getAsList(cfg, CFG_KEY_IDS);
		this.rootId = getString(cfg, CFG_ROOT_ID);
	}

	public String getRelationSheetId() {
		return relationSheetId;
	}

	public String getRelationKeyId() {
		return relationKeyId;
	}

	public String getRelationMyKeyId() {
		return relationMyKeyId;
	}

	public String getRootId() {
		return rootId;
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
		if (targetCellRow != 0 && targetCellColumn != 0) {
			String value = table.get(targetCellRow - 1, targetCellColumn - 1);
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
