package com.purejadeite.jadegreen.definition.converter.cell;

import java.io.File;
import java.io.IOException;
import java.util.Map;

import org.apache.commons.collections.MapUtils;
import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 文字列を定義されたマップに応じたファイルを読み込むクラス
 * @author mitsuhiroseino
 *
 */
public class Read extends AbstractCellConverter {

	private static final Logger LOGGER = LoggerFactory.getLogger(Read.class);

	/**
	 * 読み込みファイルマップ
	 */
	private Map<String, String> map;

	/**
	 * デフォルトの読み込みファイル
	 */
	private String dflt;

	/**
	 * コンストラクタ
	 * @param cell 値の取得元Cell読み込み定義
	 * @param config コンバーターのコンフィグ
	 */
	@SuppressWarnings("unchecked")
	public Read(Map<String, Object> config) {
		super();
		this.map = (Map<String, String>) config.get("map");
		this.dflt = MapUtils.getString(config, "default");
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Object applyImpl(Object value) {
		String filePath = map.get(value);
		if (filePath == null) {
			filePath = dflt;
		}
		if (filePath != null) {
			try {
				return FileUtils.readFileToString(new File(filePath));
			} catch (IOException e) {
				LOGGER.error("ファイルがありません:" + filePath);
			}
		}
		return null;
	}

	public Map<String, Object> toMap() {
		Map<String, Object> map = super.toMap();
		map.put("map", this.map);
		map.put("dflt", this.dflt);
		return map;
	}
}
