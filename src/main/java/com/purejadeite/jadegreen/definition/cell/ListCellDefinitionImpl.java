package com.purejadeite.jadegreen.definition.cell;

import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;

import com.purejadeite.jadegreen.definition.Definition;

/**
 * 単一セル内で一覧になっているセルの読み込み定義です
 *
 * @author mitsuhiroseino
 */
public class ListCellDefinitionImpl extends CellDefinitionImpl {

	private String splitter;

	/**
	 * コンストラクタ
	 *
	 * @param parent
	 *            シート読み込み定義
	 * @param id
	 *            定義ID
	 * @param row
	 *            取得対象行
	 * @param col
	 *            取得対象列
	 * @param converters
	 *            コンバーター
	 */
	private ListCellDefinitionImpl(Definition parent, String id, boolean noOutput, int row, int col, String splitter,
			List<Map<String, String>> converters) {
		super(parent, id, noOutput, row, col, converters);
		this.splitter = splitter;
	}

	/**
	 * インスタンスを取得します
	 *
	 * @param parent
	 *            シート読み込み定義
	 * @param id
	 *            定義ID
	 * @param row
	 *            取得対象行
	 * @param col
	 *            取得対象列
	 * @param converters
	 *            コンバーター
	 * @return ラップされたCell読み込み定義
	 */
	public static CellDefinition getInstance(Definition parent, String id, boolean noOutput, int row, int col,
			String splitter, List<Map<String, String>> converters) {
		CellDefinition cell = new ListCellDefinitionImpl(parent, id, noOutput, row, col, splitter, converters);
		return cell;
	}

	@Override
	public Object convert(Object value) {
		Object val = super.convert(value);
		if (val == null) {
			return val;
		} else {
			String strVal = val.toString();
			return StringUtils.split(strVal, splitter);
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Map<String, Object> toMap() {
		Map<String, Object> map = super.toMap();
		map.put("splitter", splitter);
		return map;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String toString() {
		return this.getClass().getSimpleName() + " [" + super.toString() + ", row=" + row + ", col=" + col + "]";
	}

}
