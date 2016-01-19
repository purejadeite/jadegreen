package com.purejadeite.jadegreen.definition;

import static com.purejadeite.util.collection.RoughlyMapUtils.*;

import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;

import com.purejadeite.jadegreen.definition.cell.CellDefinition;
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
public class WorksheetDefinition extends AbstractParentDefinition<WorkbookDefinition, Definition<?>> {

	private static final long serialVersionUID = -1303967958765873003L;
	/**
	 * 取得条件・シート名
	 */
	public static final String CFG_NAME = "name";

	/**
	 * Sheet配下の全Cellの定義
	 */
	public static final String CFG_CELLS = "cells";

	/**
	 * コンフィグ：target
	 */
	private static final String CFG_TARGET = "target";

	/**
	 * コンフィグ：target・シート名
	 */
	private static final String CFG_TARGET_NAME = "target.name";

	/**
	 * コンフィグ：target・Cell・Row
	 */
	private static final String CFG_TARGET_CELL_ROW = "target.cell.row";

	/**
	 * コンフィグ：target・Cell・Column
	 */
	private static final String CFG_TARGET_CELL_COLUMN = "target.cell.column";

	/**
	 * コンフィグ：target・Cell・Value
	 */
	private static final String CFG_TARGET_CELL_VALUE = "target.cell.value";

	/**
	 * コンフィグ：join・相手先シートID
	 */
	private static final String CFG_JOIN_SHEET_ID = "join.sheetId";

	/**
	 * コンフィグ：join・相手先キーID
	 */
	private static final String CFG_JOIN_KEY_ID = "join.keyId";

	/**
	 * コンフィグ：join・自身のキーID
	 */
	private static final String CFG_JOIN_MY_KEY_ID = "join.myKeyId";

	/**
	 * 出力
	 */
	private static final String CFG_OUTPUT = "output";

	/**
	 * 必須項目名称
	 */
	private static final String[] CONFIG = { CFG_TARGET };

	/**
	 * 対象シート条件・シート名
	 */
	private String name;

	/**
	 * 対象シート条件・シート名
	 */
	private String targetName;

	/**
	 * 対象シート条件・Cell行番号
	 */
	private int targetCellRow;

	/**
	 * 対象シート条件・Cell列番号
	 */
	private int targetCellColumn;

	/**
	 * 対象シート条件・Cell値
	 */
	private String targetCellValue;

	/**
	 * 定義上の最少行番号
	 */
	private int minRow = Integer.MAX_VALUE;

	/**
	 * 定義上の最大行番号
	 */
	private int maxRow = 0;

	/**
	 * 定義上の最少列番号
	 */
	private int minCol = Integer.MAX_VALUE;

	/**
	 * 定義上の最大列番号
	 */
	private int maxCol = 0;

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
	 * <pre>
	 * データの出力を行うか
	 * true: 行う
	 * false: 行わない
	 * </pre>
	 */
	protected boolean output = false;

	/**
	 * コンストラクタ
	 *
	 * @param parent
	 *            Book読み込み定義
	 * @param config
	 *            コンフィグ
	 */
	public WorksheetDefinition(WorkbookDefinition parent, Map<String, Object> config) {
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
		this.output = getBooleanValue(cfg, CFG_OUTPUT);
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
	public void addChild(Definition<?> child) {
		super.addChild(child);
		this.addCell(child);
	}

	/**
	 * Cell読み込み定義を追加します
	 *
	 * @param child
	 *            Cell読み込み定義
	 */
	private void addCell(Definition<?> child) {
		if (child != null) {
			if (child instanceof CellDefinition) {
				// 単独Cellの場合
				CellDefinition<?> cell = (CellDefinition<?>) child;
				int i;
				// 最少行番号
				i = cell.getMinRow();
				if (i != CellDefinition.NO_ADDRESS) {
					minRow = Math.min(i, minRow);
					maxRow = Math.max(i, maxRow);
				}
				// 最大行番号
				i = cell.getMaxRow();
				if (i != CellDefinition.NO_ADDRESS) {
					minRow = Math.min(i, minRow);
					maxRow = Math.max(i, maxRow);
				}
				// 最少列番号
				i = cell.getMinCol();
				if (i != CellDefinition.NO_ADDRESS) {
					minCol = Math.min(i, minCol);
					maxCol = Math.max(i, maxCol);
				}
				// 最大列番号
				i = cell.getMaxCol();
				if (i != CellDefinition.NO_ADDRESS) {
					minCol = Math.min(i, minCol);
					maxCol = Math.max(i, maxCol);
				}
			} else if (child instanceof ParentDefinition) {
				// Tableの場合は子要素のCellをばらして追加
				ParentDefinition<?, ?> pmd = (ParentDefinition<?, ?>) child;
				for (Definition<?> c : pmd.getChildren()) {
					addCell(c);
				}
			}
		}
	}

	/**
	 * 定義上の最少行番号を取得します
	 *
	 * @return 定義上の最少行番号
	 */
	public int getMinRow() {
		return minRow;
	}

	/**
	 * 定義上の最大行番号を取得します
	 *
	 * @return 定義上の最大行番号
	 */
	public int getMaxRow() {
		return maxRow;
	}

	/**
	 * 定義上の最少列番号を取得します
	 *
	 * @return 定義上の最少列番号
	 */
	public int getMinCol() {
		return minCol;
	}

	/**
	 * 定義上の最大列番号を取得します
	 *
	 * @return 定義上の最大列番号
	 */
	public int getMaxCol() {
		return maxCol;
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
	public Map<String, Object> toMap() {
		Map<String, Object> map = super.toMap();
		map.put("name", name);
		map.put("minRow", minRow);
		map.put("maxRow", maxRow);
		map.put("minCol", minCol);
		map.put("maxCol", maxCol);
		map.put("output", output);
		return map;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected void buildOptions(String id, List<Map<String, Object>> options) {
		// 現在はworksheet用のOptionsは無し
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Object applyOptions(Object value) {
		return value;
	}

	public boolean match(String name, Table<String> table) {
		if (targetName != null) {
			if (!SimpleComparison.compare(targetName, name)) {
				return false;
			};
		}
		if (targetCellRow != -1 && targetCellColumn != -1) {
			String value = table.get(targetCellRow, targetCellColumn);
			if (!StringUtils.equals(targetCellValue, value)) {
				return false;
			}
		}
		return true;
	}

}
