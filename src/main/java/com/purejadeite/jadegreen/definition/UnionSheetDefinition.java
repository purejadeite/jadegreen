package com.purejadeite.jadegreen.definition;

import static com.purejadeite.util.collection.RoughlyMapUtils.*;

import java.util.Map;

import com.purejadeite.util.SimpleValidator;

/**
 * Sheet読み込み定義
 *
 * @author mitsuhiroseino
 *
 */
public class UnionSheetDefinition extends SheetDefinition {

	/**
	 * 必須項目名称
	 */
	private static final String[] CONFIG = { CFG_UNION };

	/**
	 * データの統合を行うか
	 */
	protected boolean union;

	/**
	 * コンストラクタ
	 *
	 * @param parent
	 *            Book読み込み定義
	 * @param config
	 *            コンフィグ
	 */
	public UnionSheetDefinition(BookDefinition parent, Map<String, Object> config) {
		super(parent, config);
		SimpleValidator.containsKey(config, CONFIG);
		this.union = getBooleanValue(config, CFG_UNION);
	}

	/**
	 * UNIONを行うか
	 */
	public boolean isUnion() {
		return union;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Map<String, Object> toMap() {
		Map<String, Object> map = super.toMap();
		map.put("union", union);
		return map;
	}

	public static boolean assess(Map<String, Object> config, ParentDefinitionInterface<?, ?> table) {
		return config.containsKey(CFG_TARGET) && config.containsKey(CFG_UNION);
	}

}
