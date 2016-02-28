package com.purejadeite.jadegreen.definition.table.cell;

import static com.purejadeite.util.collection.RoughlyMapUtils.*;

import java.util.Arrays;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;

import com.purejadeite.jadegreen.definition.table.TableDefinitionInterface;

/**
 * 行方向の繰り返しを持つテーブル配下のCellをList形式で読み込む定義
 * @author mitsuhiroseino
 */
abstract public class AbstractTableListCellDefinition extends AbstractTableCellDefinition<TableDefinitionInterface<?>> {

	protected static final String CFG_SPLITTER = "splitter";

	/**
	 * 分割文字列
	 */
	protected String splitter;

	/**
	 * コンストラクタ
	 *
	 * @param parent
	 *            親定義
	 * @param config
	 *            コンフィグ
	 */
	public AbstractTableListCellDefinition(TableDefinitionInterface<?> parent, Map<String, Object> config) {
		super(parent, config);
		this.splitter = getString(config, CFG_SPLITTER, "\n");
	}

	/**
	 * 値の取得
	 * @param value
	 * @return
	 */
	protected Object getValue(String value) {
		if (value == null) {
			return null;
		}
		String[] valueArray = StringUtils.split(value, splitter);
		return Arrays.asList(valueArray);
	}

	/**
	 * 終了判定
	 * @param value
	 * @return
	 */
	protected boolean isBreaked(String value) {
		if (breakValues != null) {
			// 終了判定
			if (value == null) {
				// 値がnullだった場合
				if (breakValues.contains(value)) {
					// 終了条件値の場合
					return true;
				}
			} else {
				String[] valueArray = StringUtils.split(value, splitter);
				for (String item : valueArray) {
					if (breakValues.contains(item)) {
						// 終了条件値の場合
						return true;
					}
				}
			}
		}
		return false;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Map<String, Object> toMap() {
		Map<String, Object> map = super.toMap();
		map.put("splitter", splitter);
		return map;
	}

}
