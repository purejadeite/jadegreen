package com.purejadeite.jadegreen.content;

import com.purejadeite.jadegreen.definition.cell.JoinedCellDefinitionInterface;

/**
 * 他のセルしたCellのインターフェイス
 * @author mitsuhiroseino
 */
public interface JoinedCellContentInterface<P extends ContentInterface<?, ?>, D extends JoinedCellDefinitionInterface<?>> extends CellContentInterface<P, D> {
}
