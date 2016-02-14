package com.purejadeite.jadegreen.option.cell;

import static com.purejadeite.util.collection.RoughlyMapUtils.*;

import java.util.Map;

import com.purejadeite.jadegreen.content.Content;
import com.purejadeite.jadegreen.definition.Definition;
import com.purejadeite.util.SimpleValidator;

/**
 * 文字列を定義されたマップに応じた値に置き換えるクラス
 * @author mitsuhiroseino
 *
 */
public class Mapping extends AbstractCellOption {

	private static final long serialVersionUID = -2189657683860067037L;

	protected static final String CFG_MAP = "map";

	protected static final String CFG_LAZY = "lazy";

	/**
	 * 必須項目名称
	 */
	private static final String[] CONFIG = {CFG_MAP};

	/**
	 * 値変換マップ
	 */
	protected Map<String, String> map;

	/**
	 * <pre>
	 * 変換先が無い場合の動作
	 * true: 元の値を返す
	 * false: nullを返す
	 * </pre>
	 */
	protected boolean lazy;

	/**
	 * コンストラクタ
	 * @param cell 値の取得元Cell読み込み定義
	 * @param config コンバーターのコンフィグ
	 */
	public Mapping(Definition<?> definition, Map<String, Object> config) {
		super(definition);
		SimpleValidator.containsKey(config, CONFIG);
		this.map = getMap(config, CFG_MAP);
		this.lazy = getBooleanValue(config, CFG_LAZY);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Object applyImpl(Object value, Content<?, ?> content) {
		Object mappedValue = null;
		if (map.containsKey(value)) {
			mappedValue = map.get(value);
		} else {
			if (this.lazy) {
				mappedValue = value;
			}
		}
		return mappedValue;
	}

	public Map<String, Object> toMap() {
		Map<String, Object> map = super.toMap();
		map.put("map", this.map);
		map.put("lazy", this.lazy);
		return map;
	}
}