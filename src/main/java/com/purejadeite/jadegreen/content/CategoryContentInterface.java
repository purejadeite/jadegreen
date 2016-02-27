package com.purejadeite.jadegreen.content;

import com.purejadeite.jadegreen.definition.table.CategoryDefinitionInterface;

/**
 * 範囲の情報を保持するクラスのインターフェイスです
 * @author mitsuhiroseino
 */
public interface CategoryContentInterface extends ParentContentInterface<ParentContentInterface<?, ?, ?>, ContentInterface<?, ?>, CategoryDefinitionInterface<?>> {
}
