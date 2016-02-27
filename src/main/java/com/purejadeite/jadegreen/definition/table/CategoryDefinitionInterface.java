package com.purejadeite.jadegreen.definition.table;

import com.purejadeite.jadegreen.definition.DefinitionInterface;
import com.purejadeite.jadegreen.definition.ParentDefinitionInterface;

/**
 * 任意の集まりを保持するクラスのインターフェイスです
 * @author mitsuhiroseino
 */
public interface CategoryDefinitionInterface<C extends DefinitionInterface<?>> extends ParentDefinitionInterface<ParentDefinitionInterface<?, ?>, C> {
}
