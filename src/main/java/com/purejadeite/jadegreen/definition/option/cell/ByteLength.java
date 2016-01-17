package com.purejadeite.jadegreen.definition.option.cell;

import static com.purejadeite.util.collection.RoughlyMapUtils.*;

import java.util.Map;

import org.apache.commons.codec.binary.StringUtils;

/**
 * 文字列のバイト長を取得するクラス
 * @author mitsuhiroseino
 *
 */
public class ByteLength extends AbstractStringCellOption {

	private static final String CFG_ENCODE = "encode";

	/**
	 * エンコード
	 */
	private String encode;

	/**
	 * コンストラクタ
	 * @param cell 値の取得元Cell読み込み定義
	 * @param config コンバーターのコンフィグ
	 */
	public ByteLength(Map<String, Object> config) {
		super();
		this.encode = getString(config, CFG_ENCODE);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Object applyToString(String value) {
		return Integer.valueOf(StringUtils.getBytesUnchecked(value, encode).length);
	}

	public Map<String, Object> toMap() {
		Map<String, Object> map = super.toMap();
		map.put("encode", encode);
		return map;
	}

}
