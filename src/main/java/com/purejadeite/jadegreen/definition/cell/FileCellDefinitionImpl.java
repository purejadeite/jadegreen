package com.purejadeite.jadegreen.definition.cell;

import static com.purejadeite.util.collection.RoughlyMapUtils.*;

import java.io.File;
import java.io.IOException;
import java.util.Map;

import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.purejadeite.jadegreen.content.Content;
import com.purejadeite.jadegreen.definition.ParentDefinition;

/**
 * ファイル読み込みの定義です
 *
 * @author mitsuhiroseino
 */
public class FileCellDefinitionImpl<P extends ParentDefinition<?, ?>> extends AbstractCellDefinition<P> {

	private static final long serialVersionUID = -7466869542966847881L;

	private static final Logger LOGGER = LoggerFactory.getLogger(FileCellDefinitionImpl.class);

	/**
	 * 外部ファイルのパス
	 */
	public static final String CFG_FILE = "file";

	private String filePath;

	/**
	 * コンストラクタ
	 *
	 * @param parent
	 *            親定義
	 * @param config
	 *            コンフィグ
	 */
	private FileCellDefinitionImpl(P parent, Map<String, Object> config) {
		super(parent, config);
		this.filePath = getString(config, CFG_FILE);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean isIncluded(int row, int col) {
		return false;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Map<String, Object> toMap() {
		Map<String, Object> map = super.toMap();
		map.put("filePath", filePath);
		return map;
	}

	@Override
	public int getMinRow() {
		return NO_ADDRESS;
	}

	@Override
	public int getMaxRow() {
		return NO_ADDRESS;
	}

	@Override
	public int getMaxCol() {
		return NO_ADDRESS;
	}

	@Override
	public int getMinCol() {
		return NO_ADDRESS;
	}

	@Override
	public Object applyOptions(Object value, Content<?, ?> content) {
		String fileText = null;
		try {
			fileText = FileUtils.readFileToString(new File(filePath));
		} catch (IOException e) {
			LOGGER.error("ファイルを開くことができません:" + filePath);
			return null;
		}
		if (options == null) {
			return fileText;
		} else {
			return options.apply(fileText, content);
		}
	}

}
