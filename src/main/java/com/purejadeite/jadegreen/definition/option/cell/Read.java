package com.purejadeite.jadegreen.definition.option.cell;

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
	 * デフォルトの読み込みファイル
	 */
	private String file;

	/**
	 * 読み込みファイルマップ
	 */
	private Map<String, String> map;

	/**
	 * コンストラクタ
	 * @param cell 値の取得元Cell読み込み定義
	 * @param config コンバーターのコンフィグ
	 */
	@SuppressWarnings("unchecked")
	public Read(Map<String, Object> config) {
		super();
		this.file = MapUtils.getString(config, "default");
		this.map = (Map<String, String>) config.get("map");
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Object applyImpl(Object value) {
		String filePath = null;
		if (map != null) {
			filePath = map.get(value);
		}
		if (filePath == null) {
			filePath = file;
		}
		if (filePath != null) {
			try {
				return FileUtils.readFileToString(new File(filePath));
			} catch (IOException e) {
				LOGGER.error("ファイルがありません:" + filePath);
				throw new IllegalArgumentException(e);
			}
		}
		return null;
	}

	public Map<String, Object> toMap() {
		Map<String, Object> map = super.toMap();
		map.put("file", this.file);
		map.put("map", this.map);
		return map;
	}
}
