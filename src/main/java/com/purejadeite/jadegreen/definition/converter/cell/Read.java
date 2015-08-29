package com.purejadeite.jadegreen.definition.converter.cell;

import java.io.File;
import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

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
	 * <pre>
	 * 対象が無い場合の動作
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
	public Read(Map<String, Object> config) {
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
			try {
				return FileUtils.readFileToString(new File((String) mappedValue));
			} catch (IOException e) {
				LOGGER.error("ファイルがありません:" + mappedValue);
				return null;
			}

		} else {
			if (Boolean.TRUE.equals(this.lazy)) {
				mappedValue = value;
			}
		}
		return mappedValue;
	}

	public List<Map<String, Object>> toList() {
		Map<String, Object> map = new LinkedHashMap<>();
		map.put("name", this.getClass().getSimpleName());
		map.put("map", this.map);
		List<Map<String, Object>> list = super.toList();
		list.add(map);
		return list;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String toString() {
		return this.getClass().getSimpleName() + " [" + super.toString() + ", map=" + map + "]";
	}
}
