package com.purejadeite.jadegreen.definition.cell.option;

import java.io.File;
import java.io.IOException;
import java.util.Map;

import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.purejadeite.jadegreen.RoughlyMapUtils;

/**
 * 文字列を定義されたマップに応じたファイルを読み込むクラス
 * @author mitsuhiroseino
 *
 */
public class Read extends AbstractCellConverter {

	private static final Logger LOGGER = LoggerFactory.getLogger(Read.class);

	/**
	 * 必須項目名称
	 */
	private static final String[] CONFIG = {"default"};

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
	public Read(Map<String, Object> config) {
		super();
		this.validateConfig(config, CONFIG);
		this.file = RoughlyMapUtils.getString(config, "default");
		this.map = RoughlyMapUtils.getMap(config, "map");
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
