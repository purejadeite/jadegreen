package purejadeite.jadegreen.definition.converter.range;

import java.util.List;
import java.util.Map;

import purejadeite.core.AbstractToJson;

/**
 * Rangeの値を変換する抽象クラス
 * @author mitsuhiroseino
 */
public abstract class AbstractRangeConverter extends AbstractToJson implements RangeConverter {

	protected RangeConverter converter;

	/**
	 * コンストラクタ
	 * @param range 値の取得元Range読み込み定義
	 */
	public AbstractRangeConverter() {
	}

	@Override
	public Object convert(Object values) {
		return convertImpl((List<Map<String, Object>>) values);
	}

	/**
	 * テーブルの変換を行います
	 * @param values 変換前のテーブル
	 * @return 変換後のテーブル
	 */
	abstract protected Object convertImpl(List<Map<String, Object>> values);

	@Override
	public void chain(RangeConverter converter) {
		if (this.converter != null) {
			this.converter.chain(converter);
		} else {
			this.converter = converter;
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String toJson() {
		return getJson("converter", converter);

	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String toString() {
		return "converter=" + converter;
	}
}
