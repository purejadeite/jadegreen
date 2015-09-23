package com.purejadeite.jadegreen.definition.cell;

import java.util.List;
import java.util.Map;

import com.purejadeite.jadegreen.definition.AbstractDefinition;
import com.purejadeite.jadegreen.definition.Options;
import com.purejadeite.jadegreen.definition.OptionsBuilder;
import com.purejadeite.jadegreen.definition.ParentDefinition;

/**
 * <pre>
 * 値の取得元セルの情報を保持する抽象クラス
 * </pre>
 *
 * @author mitsuhiroseino
 */
abstract public class AbstractCellDefinition<P extends ParentDefinition<?, ?>> extends AbstractDefinition<P> implements CellDefinition<P> {

	/**
	 * オプション
	 */
	protected Options options;

	/**
	 * コンストラクタ
	 *
	 * @param parent
	 *            親定義
	 * @param id
	 *            定義ID
	 * @param options
	 *            オプション
	 */
	protected AbstractCellDefinition(P parent, String id, boolean noOutput,
			List<Map<String, Object>> options) {
		super(parent, id, noOutput);
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
	public Map<String, Object> toMap() {
		Map<String, Object> map = super.toMap();
		if (options != null) {
			map.put("options", options.toMap());
		} else {
			map.put("options", null);
		}
		return map;
	}

}
