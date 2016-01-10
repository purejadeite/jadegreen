package com.purejadeite.jadegreen.definition.cell;

import java.util.List;
import java.util.Map;

import com.purejadeite.jadegreen.definition.AbstractMappingDefinition;
import com.purejadeite.jadegreen.definition.Options;
import com.purejadeite.jadegreen.definition.ParentMappingDefinition;
import com.purejadeite.jadegreen.definition.WorksheetDefinition;
import com.purejadeite.jadegreen.definition.option.cell.CellOptionManager;

/**
 * <pre>
 * 値の取得元セルの情報を保持する抽象クラス
 * </pre>
 *
 * @author mitsuhiroseino
 */
abstract public class AbstractCellDefinition<P extends ParentMappingDefinition<?, ?>> extends AbstractMappingDefinition<P> implements CellDefinition<P> {

	private static final long serialVersionUID = -1364121835461648806L;

	/**
	 * シート
	 */
	protected WorksheetDefinition sheet;

	/**
	 * オプション
	 */
	protected Options options;

	/**
	 * コンストラクタ
	 *
	 * @param parent
	 *            親定義
	 * @param id
	 *            定義ID
	 * @param options
	 *            オプション
	 */
	protected AbstractCellDefinition(P parent, String id,
			List<Map<String, Object>> options) {
		super(parent, id);
		this.options = CellOptionManager.build(options);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Options getOptions() {
		return options;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void setSheet(WorksheetDefinition sheet) {
		this.sheet = sheet;
	}

	@Override
	public Object apply(Object value) {
		if (options == null) {
			return value;
		} else {
			return options.apply(value);
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Map<String, Object> toMap() {
		Map<String, Object> map = super.toMap();
		if (options != null) {
			map.put("options", options.toMap());
		} else {
			map.put("options", null);
		}
		return map;
	}

}
