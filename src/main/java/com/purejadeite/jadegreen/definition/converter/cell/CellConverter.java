package com.purejadeite.jadegreen.definition.converter.cell;

import java.util.List;
import java.util.Map;

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

	/**
	 * インスタンスの内容をJSON形式で取得します
	 * @return JSON
	 */
	public String toJson();

	public List<Map<String,Object>> toList();

}
