package com.purejadeite.jadegreen.content;

import com.purejadeite.jadegreen.definition.table.CategoryDefinition;

/**
 * 範囲の情報を保持するクラスのインターフェイスです
 * @author mitsuhiroseino
 */
public interface CategoryContent extends ParentContent<ParentContent<?, ?, ?>, Content<?, ?>, CategoryDefinition<?>> {
}
