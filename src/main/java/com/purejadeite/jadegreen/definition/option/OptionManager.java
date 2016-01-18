package com.purejadeite.jadegreen.definition.option;

import static com.purejadeite.util.collection.RoughlyMapUtils.*;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.purejadeite.jadegreen.DefinitionException;
import com.purejadeite.jadegreen.JadegreenException;

/**
 * コンバーターインスタンスを生成するクラスです
 * @author mitsuhiroseino
 *
 */
public class OptionManager<O extends Option> {

	/**
	 * コンバーター
	 */
	protected Map<String, Class<? extends O>> OPTIONS = new HashMap<>();

	public void registerAll(@SuppressWarnings("unchecked") Class<? extends O>... classes) {
		for (Class<? extends O> clazz : classes) {
			register(clazz);
		}
	}

	public void register(Class<? extends O> clazz) {
		OPTIONS.put(clazz.getSimpleName().toLowerCase(), clazz);
	}

	public Options build(String id, List<Map<String, Object>> opts) {
		if (opts == null || opts.size() == 0) {
			return null;
		}
		List<Option> options = new ArrayList<>();
		Option option = null;
		for (Map<String, Object> opt : opts) {
			String type = getString(opt, Option.CFG_TYPE);
			option = build(id, type, opt);
			if (option == null) {
				throw new DefinitionException("type=" + type + ":optionsのclassが取得できません");
			} else {
				options.add(option);
			}
		}
		return new Options(id, options);
	}

	/**
	 * コンバーターのインスタンスを生成します
	 * @param config コンバーターのコンフィグ
	 * @return コンバーター
	 */
	public Option build(String id, String type, Map<String, Object> config) {
		// クラスを取得
		Class<? extends O> clazz = OPTIONS.get(type.toLowerCase());
		if (clazz == null) {
			return null;
		}
		// コンストラクタを取得
		Constructor<? extends O> constructor;
		try {
			constructor = clazz.getConstructor(String.class, Map.class);
		} catch (NoSuchMethodException | SecurityException e) {
			throw new JadegreenException("option id=" + id + ",type = " + type + ":optionsのclassからコンストラクターを取得できません", e);
		}

		// インスタンスを取得
		O option = null;
		try {
			option = constructor.newInstance(id, config);
		} catch (InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
			throw new JadegreenException("option id=" + id + ",type = " + type + ": " + e.getCause().getMessage() ,e);
		}
		return option;
	}

}
