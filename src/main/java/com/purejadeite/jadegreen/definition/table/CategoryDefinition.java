package com.purejadeite.jadegreen.definition.table;

import com.purejadeite.jadegreen.definition.Definition;
import com.purejadeite.jadegreen.definition.ParentDefinition;

/**
 * 任意の集まりを保持するクラスのインターフェイスです
 * @author mitsuhiroseino
 */
public interface CategoryDefinition<C extends Definition<?>> extends ParentDefinition<ParentDefinition<?, ?>, C> {
}
