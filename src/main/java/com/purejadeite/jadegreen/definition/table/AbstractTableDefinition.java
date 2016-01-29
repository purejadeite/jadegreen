package com.purejadeite.jadegreen.definition.table;

import static com.purejadeite.util.collection.RoughlyMapUtils.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.purejadeite.jadegreen.definition.AbstractParentDefinition;
import com.purejadeite.jadegreen.definition.Definition;
import com.purejadeite.jadegreen.definition.SheetDefinition;
import com.purejadeite.jadegreen.definition.cell.TableCellDefinition;
import com.purejadeite.jadegreen.definition.option.table.TableOptionManager;

/**
 * テーブル形式の範囲の情報を保持するクラスの抽象クラスです
 * @author mitsuhiroseino
 */
abstract public class AbstractTableDefinition<C extends TableCellDefinition<?>> extends AbstractParentDefinition<SheetDefinition, C> implements TableDefinition<C> {

	private static final long serialVersionUID = -6138799003034104152L;

	/**
	 * 開始位置
	 */
	protected int begin = 0;

	/**
	 * 終了位置
	 */
	protected int end = 0;

	/**
	 * 配下のセル読み込み情報
	 */
	protected List<C> cells = new ArrayList<>();

	/**
	 * 開始キー項目
	 * ※現在未使用
	 */
	protected String beginKeyId = null;

	/**
	 * 開始キー値
	 * ※現在未使用
	 */
	protected String beginValue = null;

	/**
	 * 終了キー項目
	 */
	protected String breakId = null;

	/**
	 * 終了キー値
	 */
	protected List<String> breakValues = null;

	/**
	 * レコード数
	 */
	protected int size = 0;

	/**
	 * コンストラクタ
	 *
	 * @param parent
	 *            親定義
	 * @param config
	 *            コンフィグ
	 */
	protected AbstractTableDefinition(SheetDefinition parent, Map<String, Object> config) {
		super(parent, config);
		this.begin = getIntValue(config, getBeginDefinitionKey());
		this.end = getIntValue(config, getEndConfigDefinitionKey(), Integer.MAX_VALUE);
		this.breakId = getString(config, CFG_BREAK_ID);
		List<String> breakValue = getList(config, CFG_BREAK_VALUE);
		if (breakValue == null) {
			this.breakValues = new ArrayList<>();
			this.breakValues.add(getString(config, CFG_BREAK_VALUE));
		} else {
			this.breakValues = breakValue;
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected void buildOptions(Definition<?> definition, List<Map<String, Object>> options) {
		this.options = TableOptionManager.build(definition, options);
	}

	/**
	 * 開始定義のキー名を取得します
	 * @return 開始定義のキー
	 */
	abstract protected String getBeginDefinitionKey();

	/**
	 * 終了定義のキー名を取得します
	 * @return 終了定義のキー
	 */
	abstract protected String getEndConfigDefinitionKey();

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String getBreakId() {
		return breakId;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void setBreakId(String breakId) {
		this.breakId = breakId;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<String> getBreakValues() {
		return breakValues;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void setBreakValues(List<String> breakValues) {
		this.breakValues = breakValues;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public int getBegin() {
		return begin;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public int getEnd() {
		return end;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<C> getChildren() {
		return this.cells;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void addChild(C child) {
		if (child.getId().equals(breakId)) {
			// 終了条件に指定されている場合
			child.setBreakId(true);
			child.setBreakValues(breakValues);
		} else if (cells.isEmpty()) {
			// 先頭セルの場合
			if (end == Integer.MAX_VALUE && breakId == null) {
				// 終了条件が設定されていない場合に、項目がnullになった時に終了
				breakId = child.getId();
				child.setBreakId(true);
				child.setBreakValues(breakValues);
			}
		}
		cells.add(child);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void addChildren(List<C> children) {
		for (C child : children) {
			addChild(child);
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Map<String, Object> toMap() {
		Map<String, Object> map = super.toMap();
		map.put("begin", begin);
		map.put("end", end);
		map.put("beginKeyId", beginKeyId);
		map.put("beginValue", beginValue);
		map.put("endKeyId", breakId);
		map.put("endValue", breakValues);
		map.put("size", size);
		List<Map<String, Object>> cellMaps = new ArrayList<>();
		for(C cell: cells) {
			cellMaps.add(cell.toMap());
		}
		map.put("cells", cellMaps);
		return map;
	}
}
