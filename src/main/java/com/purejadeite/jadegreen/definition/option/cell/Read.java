package com.purejadeite.jadegreen.definition.option.cell;

import java.io.File;
import java.io.IOException;
import java.util.Map;

import org.apache.commons.io.FileUtils;

import com.purejadeite.jadegreen.DefinitionException;
import com.purejadeite.util.RoughlyMapUtils;

/**
 * 文字列を定義されたマップに応じたファイルを読み込むクラス
 *
 * @author mitsuhiroseino
 *
 */
public class Read extends AbstractCellConverter {

	private static final long serialVersionUID = 6328364128215865166L;

	private static final String CFG_DEFAULT = "default";

	private static final String CFG_MAP = "map";

	/**
	 * 必須項目名称
	 */
	private static final String[] CONFIG = { CFG_DEFAULT };

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
	 *
	 * @param cell
	 *            値の取得元Cell読み込み定義
	 * @param config
	 *            コンバーターのコンフィグ
	 */
	public Read(Map<String, Object> config) {
		super();
		this.validateConfig(config, CONFIG);
		this.file = RoughlyMapUtils.getString(config, CFG_DEFAULT);
		this.map = RoughlyMapUtils.getMap(config, CFG_MAP);
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
				throw new DefinitionException("filePath=" + filePath + ":ファイルを取得できません", e);
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
