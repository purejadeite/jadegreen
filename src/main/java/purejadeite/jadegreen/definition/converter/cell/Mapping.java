package purejadeite.jadegreen.definition.converter.cell;

import java.util.Map;

/**
 * 値を定義された変換マップの値に置き換えるクラス
 * @author mitsuhiroseino
 *
 */
public class Mapping extends AbstractCellConverter {

	/**
	 * 値変換マップ
	 */
	private Map<String, String> map;

	/**
	 * <pre>
	 * 変換先が無い場合の動作
	 * true: 元の値を返す
	 * false: nullを返す
	 * </pre>
	 */
	private Boolean lazy;

	/**
	 * コンストラクタ
	 * @param cell 値の取得元Cell読み込み定義
	 * @param config コンバーターのコンフィグ
	 */
	@SuppressWarnings("unchecked")
	public Mapping(Map<String, Object> config) {
		super();
		this.map = (Map<String, String>) config.get("map");
		this.lazy = (Boolean) config.get("lazy");
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Object convertImpl(Object value) {
		Object mappedValue = null;
		if (map.containsKey(value)) {
			mappedValue = map.get(value);
		} else {
			if (Boolean.TRUE.equals(this.lazy)) {
				mappedValue = value;
			}
		}
		return mappedValue;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String toJson() {
		return "{" + super.toJson() + "," + getJson("map", map) + "}";
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String toString() {
		return this.getClass().getSimpleName() + " [" + super.toString() + ", map=" + map + "]";
	}
}
