package purejadeite.jadegreen.definition.converter.range;



/**
 * Cellの値を変換する抽象クラス
 * @author mitsuhiroseino
 *
 */
public interface RangeConverter {

	/**
	 * 値の変換を行います
	 * @param value 変換前の値
	 * @return 変換後の値
	 */
	public Object convert(Object values);

	public void chain(RangeConverter converter);


}