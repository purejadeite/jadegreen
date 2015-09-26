package com.purejadeite.jadegreen.definition.cell;

import static com.purejadeite.jadegreen.definition.DefinitionKeys.*;

import java.util.List;
import java.util.Map;

import com.purejadeite.jadegreen.RoughlyMapUtils;
import com.purejadeite.jadegreen.definition.ParentDefinition;
import com.purejadeite.jadegreen.definition.option.generator.ValueGenerator;
import com.purejadeite.jadegreen.definition.option.generator.ValueGeneratorManager;

/**
 * 固定値の定義です
 *
 * @author mitsuhiroseino
 */
public class ValueDefinitionImpl<P extends ParentDefinition<?, ?>> extends AbstractNoAdressCellDefinition<P> {

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
	private ValueDefinitionImpl(P parent, String id, boolean noOutput, Map<String, Object> generator, List<Map<String, Object>> options) {
		super(parent, id, noOutput, options);
		if (generator != null) {
			this.generator = ValueGeneratorManager.build(generator);
		}
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
	public static <P extends ParentDefinition<?, ?>> CellDefinition<P> newInstance(P parent, Map<String, Object> config) {
		String id = RoughlyMapUtils.getString(config, ID);
		boolean noOutput = RoughlyMapUtils.getBooleanValue(config, NO_OUTPUT);
		Map<String, Object> generator = RoughlyMapUtils.getMap(config, GENERATOR);
		List<Map<String, Object>> options = RoughlyMapUtils.getList(config, OPTIONS);
		return new ValueDefinitionImpl<P>(parent, id, noOutput, generator, options);
	}

	@Override
	public Object apply(Object value) {
		Object val = value;
		if (generator != null) {
			val = generator.apply(val);
		}
		return super.apply(val);
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
