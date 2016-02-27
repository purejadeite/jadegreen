package com.purejadeite.jadegreen.content;

import com.purejadeite.jadegreen.definition.table.TableDefinitionInterface;

/**
 * テーブル形式の範囲の情報を保持するクラスのインターフェイスです
 * @author mitsuhiroseino
 */
public interface TableContentInterface extends ParentContentInterface<ParentContentInterface<?, ?, ?>, TableCellContentInterface<?>, TableDefinitionInterface<?>> {
	public int size();
}
