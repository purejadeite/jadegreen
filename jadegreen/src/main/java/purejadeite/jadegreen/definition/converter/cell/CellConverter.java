package purejadeite.jadegreen.definition.converter.cell;


/**
 * Cellの値を変換する抽象クラス
 * @author mitsuhiroseino
 *
 */
public interface CellConverter {

	/**
	 * 値の変換を行います
	 * @param value 変換前の値
	 * @return 変換後の値
	 */
	public Object convert(Object value);

	public void chain(CellConverter converter);

	public String toJson();

}
