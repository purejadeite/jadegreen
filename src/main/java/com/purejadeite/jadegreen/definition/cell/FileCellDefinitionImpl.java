package com.purejadeite.jadegreen.definition.cell;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;

import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.purejadeite.jadegreen.definition.Definition;

/**
 * ファイル読み込みの定義です
 *
 * @author mitsuhiroseino
 */
public class FileCellDefinitionImpl extends AbstractCellDefinition {

	private static final Logger LOGGER = LoggerFactory.getLogger(FileCellDefinitionImpl.class);

	private String filePath;

	/**
	 * コンストラクタ
	 *
	 * @param parent
	 *            シート読み込み定義
	 * @param id
	 *            定義ID
	 * @param noOutput
	 *            出力可否
	 * @param row
	 *            取得対象行
	 * @param col
	 *            取得対象列
	 * @param options
	 *            コンバーター
	 */
	private FileCellDefinitionImpl(Definition parent, String id, boolean noOutput, String filePath, List<Map<String, String>> options) {
		super(parent, id, noOutput, options);
		this.filePath = filePath;
	}

	/**
	 * インスタンスを取得します
	 *
	 * @param parent
	 *            シート読み込み定義
	 * @param id
	 *            定義ID
	 * @param noOutput
	 *            出力可否
	 * @param row
	 *            取得対象行
	 * @param col
	 *            取得対象列
	 * @param options
	 *            コンバーター
	 * @return ラップされたCell読み込み定義
	 */
	public static CellDefinition getInstance(Definition parent, String id, boolean noOutput, String filePath,List<Map<String, String>> options) {
		CellDefinition cell = new FileCellDefinitionImpl(parent, id, noOutput, filePath, options);
		return cell;
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
	public Object aplly(Object value) {
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
			return options.apply(fileText);
		}
	}

}