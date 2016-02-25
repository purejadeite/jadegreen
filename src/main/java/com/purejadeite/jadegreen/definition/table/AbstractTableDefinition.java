package com.purejadeite.jadegreen.definition.table;

import static com.purejadeite.util.collection.RoughlyMapUtils.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.purejadeite.jadegreen.definition.AbstractParentDefinition;
import com.purejadeite.jadegreen.definition.Definition;
import com.purejadeite.jadegreen.definition.ParentDefinition;
import com.purejadeite.jadegreen.definition.table.cell.TableCellDefinition;
import com.purejadeite.jadegreen.option.table.TableOptionManager;

/**
 * テーブル形式の範囲の情報を保持するクラスの抽象クラスです
 * @author mitsuhiroseino
 */
abstract public class AbstractTableDefinition<C extends TableCellDefinition<?>> extends AbstractParentDefinition<ParentDefinition<?,?>, C> implements TableDefinition<C> {

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
	protected AbstractTableDefinition(ParentDefinition<?,?> parent, Map<String, Object> config) {
		super(parent, config);
		this.begin = getIntValue(config, getBeginId());
		this.end = getIntValue(config, getEndId(), TableDefinition.UNLIMITED);
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
	abstract protected String getBeginId();

	/**
	 * 終了定義のキー名を取得します
	 * @return 終了定義のキー
	 */
	abstract protected String getEndId();

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
	public void addChild(C child) {
		if (child.getId().equals(breakId)) {
			// 終了条件に指定されている場合
			child.setBreakKey(true);
			child.setBreakValues(breakValues);
		} else if (children.isEmpty()) {
			// 先頭セルの場合
			if (end == TableDefinition.UNLIMITED && breakId == null) {
				// 終了条件が設定されていない場合に、項目がnullになった時に終了
				breakId = child.getId();
				child.setBreakKey(true);
				child.setBreakValues(breakValues);
			}
		}
		super.addChild(child);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public TableDefinition<?> getTable() {
		return this;
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
		map.put("breakId", breakId);
		map.put("breakValues", breakValues);
		map.put("size", size);
		return map;
	}
}
