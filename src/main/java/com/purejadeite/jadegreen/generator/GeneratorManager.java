package com.purejadeite.jadegreen.generator;

import static com.purejadeite.util.collection.RoughlyMapUtils.*;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;

import com.purejadeite.jadegreen.DefinitionException;
import com.purejadeite.jadegreen.JadegreenException;
import com.purejadeite.jadegreen.definition.Definition;
import com.purejadeite.jadegreen.option.Option;

public class GeneratorManager {

	/**
	 * ジェネレーター
	 */
	protected static Map<String, Class<? extends Generator>> GENERATORS = new HashMap<>();

	static {
		register(Fixed.class);
		register(Index.class);
		register(Now.class);
		register(Uuid.class);
	}

	public static void register(Class<? extends Generator> clazz) {
		GENERATORS.put(clazz.getSimpleName().toLowerCase(), clazz);
	}

	public static void registerAll(@SuppressWarnings("unchecked") Class<? extends Generator>... classes) {
		for (Class<? extends Generator> clazz : classes) {
			register(clazz);
		}
	}

	public static Generator build(Definition<?> definition, Map<String, Object> generatorCfg) {
		if (generatorCfg == null) {
			return null;
		}
		Generator generator = null;
		String type = getString(generatorCfg, Option.CFG_TYPE);
		generator = build(definition, type, generatorCfg);
		if (generator == null) {
			throw new DefinitionException("type=" + type + ":generatorのclassが取得できません");
		} else {
			return generator;
		}
	}

	/**
	 * コンバーターのインスタンスを生成します
	 * @param config コンバーターのコンフィグ
	 * @return コンバーター
	 */
	public static Generator build(Definition<?> definition, String type, Map<String, Object> config) {
		// クラスを取得
		Class<? extends Generator> clazz = GENERATORS.get(type.toLowerCase());
		if (clazz == null) {
			return null;
		}
		// コンストラクタを取得
		Constructor<? extends Generator> constructor;
		try {
			constructor = clazz.getConstructor(Definition.class, Map.class);
		} catch (NoSuchMethodException | SecurityException e) {
			throw new JadegreenException("generator id=" + definition + ",type = " + type + ":generatorのclassからコンストラクターを取得できません", e);
		}

		// インスタンスを取得
		Generator generator = null;
		try {
			generator = constructor.newInstance(definition, config);
		} catch (InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
			throw new JadegreenException("generator id=" + definition + ",type = " + type + ": " + e.getCause().getMessage() ,e);
		}
		return generator;
	}
}
