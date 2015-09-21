package com.purejadeite.jadegreen.definition.cell;

import java.util.List;
import java.util.Map;

import com.purejadeite.jadegreen.definition.WorksheetDefinitionImpl;
import com.purejadeite.jadegreen.definition.generator.ValueGenerator;
import com.purejadeite.jadegreen.definition.generator.ValueGeneratorManager;

/**
 * 固定値の定義です
 *
 * @author mitsuhiroseino
 */
public class ValueDefinitionImpl extends AbstractCellDefinition<WorksheetDefinitionImpl> {

	private ValueGenerator generator;

	/**
	 * コンストラクタ
	 *
	 * @param parent
	 *            シート読み込み定義
	 * @param id
	 *            定義ID
	 * @param noOutput
	 *            出力可否
	 * @param row
	 *            取得対象行
	 * @param col
	 *            取得対象列
	 * @param options
	 *            コンバーター
	 */
	private ValueDefinitionImpl(WorksheetDefinitionImpl parent, String id, boolean noOutput, Map<String, Object> generator, List<Map<String, Object>> options) {
		super(parent, id, noOutput, options);
		this.generator = ValueGeneratorManager.build(generator);
	}

	/**
	 * インスタンスを取得します
	 *
	 * @param parent
	 *            シート読み込み定義
	 * @param id
	 *            定義ID
	 * @param noOutput
	 *            出力可否
	 * @param row
	 *            取得対象行
	 * @param col
	 *            取得対象列
	 * @param options
	 *            コンバーター
	 * @return ラップされたCell読み込み定義
	 */
	public static CellDefinition<WorksheetDefinitionImpl> getInstance(WorksheetDefinitionImpl parent, String id, boolean noOutput, Map<String, Object> generator, List<Map<String, Object>> options) {
		return new ValueDefinitionImpl(parent, id, noOutput, generator, options);
	}

	@Override
	public Object apply(Object value) {
		return super.apply(generator.apply(value));
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean isIncluded(int row, int col) {
		return false;
	}

	@Override
	public int getMinRow() {
		return NO_ADDRESS;
	}

	@Override
	public int getMaxRow() {
		return NO_ADDRESS;
	}

	@Override
	public int getMaxCol() {
		return NO_ADDRESS;
	}

	@Override
	public int getMinCol() {
		return NO_ADDRESS;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Map<String, Object> toMap() {
		Map<String, Object> map = super.toMap();
		map.put("generator", generator);
		return map;
	}

}
