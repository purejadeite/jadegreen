package com.purejadeite.jadegreen.definition.cell;

import java.util.List;
import java.util.Map;

import com.purejadeite.jadegreen.definition.AbstractDefinition;
import com.purejadeite.jadegreen.definition.Definition;
import com.purejadeite.jadegreen.definition.Options;
import com.purejadeite.jadegreen.definition.OptionsBuilder;

/**
 * <pre>
 * 値の取得元セルの情報を保持する抽象クラス
 * </pre>
 * @author mitsuhiroseino
 */
public abstract class AbstractCellDefinition extends AbstractDefinition implements CellDefinition {

	/**
	 * オプション
	 */
	protected Options options;

	/**
	 * コンストラクタ
	 * @param parent 親定義
	 * @param id 定義ID
	 * @param options オプション
	 */
	protected AbstractCellDefinition(Definition parent, String id, boolean noOutput, List<Map<String, String>> options) {
		super(parent, id, noOutput);
		this.options = OptionsBuilder.build(options);
	}

	/**
	 * サポートされていないオペレーションです
	 */
	@Override
	public List<Definition> getChildren() {
		throw new UnsupportedOperationException();
	}

	/**
	 * サポートされていないオペレーションです
	 */
	@Override
	public void addChild(Definition child) {
		throw new UnsupportedOperationException();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Options getOptions() {
		return options;
	}

	@Override
	public Object aplly(Object value) {
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
		map.put("options", options);
		return map;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String toString() {
		return super.toString() + ", options=" + options;
	}

}
