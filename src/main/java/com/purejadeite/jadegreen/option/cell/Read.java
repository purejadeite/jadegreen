package com.purejadeite.jadegreen.option.cell;

import static com.purejadeite.util.collection.RoughlyMapUtils.*;

import java.io.File;
import java.io.IOException;
import java.util.Map;

import org.apache.commons.io.FileUtils;

import com.purejadeite.jadegreen.DefinitionException;
import com.purejadeite.jadegreen.content.ContentInterface;
import com.purejadeite.jadegreen.definition.DefinitionInterface;
import com.purejadeite.util.SimpleValidator;

/**
 * 文字列を定義されたマップに応じたファイルを読み込むクラス
 *
 * @author mitsuhiroseino
 *
 */
public class Read extends AbstractCellOption {

	private static final long serialVersionUID = 6328364128215865166L;

	protected static final String CFG_DEFAULT = "default";

	protected static final String CFG_MAP = "map";

	/**
	 * 必須項目名称
	 */
	private static final String[] CONFIG = { CFG_DEFAULT };

	/**
	 * デフォルトの読み込みファイル
	 */
	protected String defaultFilePath;

	/**
	 * 読み込みファイルマップ
	 */
	protected Map<String, String> map;

	/**
	 * コンストラクタ
	 *
	 * @param cell
	 *            値の取得元Cell読み込み定義
	 * @param config
	 *            コンバーターのコンフィグ
	 */
	public Read(DefinitionInterface<?> definition, Map<String, Object> config) {
		super(definition);
		SimpleValidator.containsKey(config, CONFIG);
		this.defaultFilePath = getString(config, CFG_DEFAULT);
		this.map = getMap(config, CFG_MAP);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Object applyImpl(Object value, ContentInterface<?, ?> content) {
		String filePath = null;
		if (map != null) {
			filePath = map.get(value);
		}
		if (filePath == null) {
			filePath = defaultFilePath;
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
		map.put("file", this.defaultFilePath);
		map.put("map", this.map);
		return map;
	}
}
