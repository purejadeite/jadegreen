package com.purejadeite.jadegreen.option.table;

import static com.purejadeite.util.collection.RoughlyMapUtils.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.purejadeite.jadegreen.content.ContentInterface;
import com.purejadeite.jadegreen.definition.DefinitionInterface;
import com.purejadeite.util.SimpleValidator;

/**
 * 行のChainコンバーター
 * chainIdの値と他の行のkeyIdの値が等しい場合、
 * chainIdの値を他の行そのものと置き換えます
 *
 * @author mitsuhiroseino
 *
 */
public class Chain extends AbstractTableOption {

	protected static final String CFG_KEY_ID = "keyId";

	protected static final String CFG_CHAIN_ID = "chainId";

	/**
	 * 必須項目名称
	 */
	private static final String[] CONFIG = { CFG_KEY_ID, CFG_CHAIN_ID };

	/**
	 * キー項目の定義ID
	 */
	protected String keyId;

	/**
	 * 接続先項目の定義ID
	 */
	protected String chainId;

	/**
	 * コンストラクタ
	 *
	 * @param config
	 *            コンバーターのコンフィグ
	 */
	public Chain(DefinitionInterface<?> definition, Map<String, Object> config) {
		super(definition);
		SimpleValidator.containsKey(config, CONFIG);
		this.keyId = getString(config, CFG_KEY_ID);
		this.chainId = getString(config, CFG_CHAIN_ID);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected Object applyImpl(List<Map<String, Object>> values, ContentInterface<?, ?> content) {
		// まだchainされていないlineを保持するMap
		// キーになるIDでレコードの取得ができるMapを作る
		Map<Object, Map<String, Object>> keyMap = new HashMap<>();
		for (Map<String, Object> line: values) {
			keyMap.put(line.get(keyId), line);
		}
		
		Map<Object, Map<String, Object>> newValues = new LinkedHashMap<>();
		for (int i = 0; i < values.size(); i++) {
			Map<String, Object> line0 = values.get(i);
			Object keyValue = line0.get(keyId);
			if (keyMap.containsKey(keyValue)) {
				// まだどこかにリンクされて無いものはルートに追加
				newValues.put(keyValue, line0);
			} else {
				// すでにどこかにリンクされているものはルートから外す
				if (newValues.containsKey(keyValue)) {
					newValues.remove(keyValue);
				}
			}

			// つなぎ先を取得
			List<String> chainValues = getList(line0, chainId);
			if (chainValues == null) {
				Object chainValue = line0.get(chainId);
				if (chainValue == null) {
					continue;
				} else {
					// つなぎ先が一つの場合
					Map<String, Object> line1 = keyMap.get(chainValue);
					if (line1 != null && !line0.equals(line1)) {
						// keyMapにあるならば置き換え
						line0.put(chainId, line1);
						keyMap.remove(line1.get(keyId));
					}
				}
			} else {
				// つなぎ先が複数ある場合
				List<Object> newLinkValues = new ArrayList<>();
				for (int j = 0; j < chainValues.size(); j++) {
					Object chainValue = chainValues.get(j);
					if (chainValue != null) {
						// つなぎ先が一つの場合
						Map<String, Object> line1 = keyMap.get(chainValue);
						if (line1 != null && !line0.equals(line1)) {
							// keyMapにあるならば置き換え
							newLinkValues.add(line1);
							keyMap.remove(line1.get(keyId));
							continue;
						}
					}
					newLinkValues.add(chainValues.get(j));
				}
				line0.put(chainId, newLinkValues);
			}
		}
		return new ArrayList<>(newValues.values());
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Map<String, Object> toMap() {
		Map<String, Object> map = super.toMap();
		map.put("keyId", keyId);
		map.put("chainId", chainId);
		return map;
	}
}