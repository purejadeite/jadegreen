package com.purejadeite.jadegreen.definition.table;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.purejadeite.jadegreen.definition.AbstractParentMappingDefinition;
import com.purejadeite.jadegreen.definition.Options;
import com.purejadeite.jadegreen.definition.WorksheetDefinition;
import com.purejadeite.jadegreen.definition.cell.TableCellDefinition;
import com.purejadeite.jadegreen.definition.option.table.TableOptionManager;

/**
 * テーブル形式の範囲の情報を保持するクラスの抽象クラスです
 * @author mitsuhiroseino
 */
abstract public class AbstractTableDefinition<C extends TableCellDefinition<?>> extends AbstractParentMappingDefinition<WorksheetDefinition, C> implements TableDefinition<C> {

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
	protected String breakValue = null;

	/**
	 * オプション
	 */
	protected Options options;

	/**
	 * レコード数
	 */
	protected int size = 0;

	/**
	 * コンストラクタ
	 * @param parent 親の読み込み情報
	 * @param id 定義ID
	 * @param begin 開始位置
	 * @param end 終了位置
	 * @param endKey 開始キー項目
	 * @param breakValue 終了キー値
	 * @param options オプション
	 */
	protected AbstractTableDefinition(WorksheetDefinition parent, String id, int begin,
			int end, String breakId, String breakValue, List<Map<String, Object>> options) {
		super(parent, id);
		this.begin = begin;
		this.end = end <= 0 ? Integer.MAX_VALUE : end;
		this.breakId = breakId;
		this.breakValue = breakValue;
		this.options = TableOptionManager.build(options);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Options getOptions() {
		return options;
	}

	@Override
	public Object apply(Object value) {
		if (options == null) {
			return value;
		} else {
			return options.apply(value);
		}
	}

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
	public String getBreakValue() {
		return breakValue;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void setBreakValue(String breakValue) {
		this.breakValue = breakValue;
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
		if (cells.isEmpty()) {
			if (end == Integer.MAX_VALUE && breakId == null) {
				// 終了条件が設定されていない場合は、先頭の項目がnullになった時に終了
				breakId = child.getId();
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
		map.put("endValue", breakValue);
		map.put("size", size);
		List<Map<String, Object>> cellMaps = new ArrayList<>();
		for(C cell: cells) {
			cellMaps.add(cell.toMap());
		}
		map.put("cells", cellMaps);
		if (options != null) {
			map.put("options", options.toMap());
		} else {
			map.put("options", null);
		}
		return map;
	}
}
