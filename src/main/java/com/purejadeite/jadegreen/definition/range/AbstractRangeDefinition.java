package com.purejadeite.jadegreen.definition.range;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.purejadeite.jadegreen.definition.AbstractParentDefinition;
import com.purejadeite.jadegreen.definition.Options;
import com.purejadeite.jadegreen.definition.OptionsBuilder;
import com.purejadeite.jadegreen.definition.WorksheetDefinitionImpl;
import com.purejadeite.jadegreen.definition.cell.RangeCellDefinition;

/**
 * テーブル形式の範囲の情報を保持するクラスの抽象クラスです
 * @author mitsuhiroseino
 */
abstract public class AbstractRangeDefinition<C extends RangeCellDefinition<?>> extends AbstractParentDefinition<WorksheetDefinitionImpl, C> implements RangeDefinition<C> {

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
	protected String endKeyId = null;

	/**
	 * 終了キー値
	 */
	protected String endValue = null;

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
	 * @param noOutput データの読み込みのみ行うか
	 * @param begin 開始位置
	 * @param end 終了位置
	 * @param endKey 開始キー項目
	 * @param endValue 終了キー値
	 * @param options オプション
	 */
	protected AbstractRangeDefinition(WorksheetDefinitionImpl parent, String id, boolean noOutput, int begin,
			int end, String endKeyId, String endValue, List<Map<String, Object>> options) {
		super(parent, id, noOutput);
		this.begin = begin;
		this.end = end <= 0 ? Integer.MAX_VALUE : end;
		this.endKeyId = endKeyId;
		this.endValue = endValue;
		this.options = OptionsBuilder.build(options);
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
	public String getEndKeyId() {
		return endKeyId;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void setEndKeyId(String endKeyId) {
		this.endKeyId = endKeyId;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String getEndValue() {
		return endValue;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void setEndValue(String endValue) {
		this.endValue = endValue;
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
		map.put("endKeyId", endKeyId);
		map.put("endValue", endValue);
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
