package com.purejadeite.jadegreen.option.cell;

import static com.purejadeite.util.collection.RoughlyMapUtils.*;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import com.purejadeite.jadegreen.content.ContentInterface;
import com.purejadeite.jadegreen.content.SheetContent;
import com.purejadeite.jadegreen.content.SpecificValue;
import com.purejadeite.jadegreen.definition.DefinitionInterface;
import com.purejadeite.jadegreen.option.AbstactSwitch;
import com.purejadeite.jadegreen.option.Options;
import com.purejadeite.util.SimpleValidator;

/**
 * オプション実行条件オプション
 *
 * @author mitsuhiroseino
 *
 */
public class Switch extends AbstactSwitch implements CellOptionInterface, Serializable {

	protected static final String CFG_CELL_ID = "cellId";

	/**
	 * 必須項目名称
	 */
	private static final String[] CONFIG = {};

	protected String cellId;

	/**
	 * コンストラクタ
	 *
	 * @param config
	 *            コンバーターのコンフィグ
	 */
	public Switch(DefinitionInterface<?> definition, Map<String, Object> config) {
		super(definition, config);
		SimpleValidator.containsKey(config, CONFIG);
		this.cellId = getString(config, CFG_CELL_ID, definition.getId());
	}

	@Override
	public Object apply(Object value, ContentInterface<?, ?> content) {
		if (value == SpecificValue.UNDEFINED) {
			return value;
		} else {
			return applyImpl(value, content);
		}
	}

	protected Object applyImpl(Object cellValue, ContentInterface<?, ?> content) {
		Object value = null;
		if (cellId.equals(definition.getId())) {
			// 判定対象の値が自分自身だった場合
			value = cellValue;
		} else {
			// 判定対象の値を自シート内から取得
			SheetContent sheet = content.getSheet();
			ContentInterface<?, ?> cellContent = sheet.getCell(cellId);
			value = cellContent.getValues();
		}
		if (value instanceof List) {
			// 判定対象がTableCellかListCellの場合
			@SuppressWarnings("unchecked")
			List<Object> valueList = (List<Object>) value;
			for (Object v : valueList) {
				Options options = getOptions(v);
				if (options != null) {
					cellValue = options.apply(cellValue, content);
				}
			}
			return cellValue;
		} else {
			// 判定対象がCellの場合
			Options options = getOptions(value);
			if (options == null) {
				return cellValue;
			}
			return options.apply(cellValue, content);
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Map<String, Object> toMap() {
		Map<String, Object> map = super.toMap();
		return map;
	}

	@Override
	protected Options buildOptions(DefinitionInterface<?> definition, List<Map<String, Object>> options) {
		return CellOptionManager.build(definition, options);
	}

}