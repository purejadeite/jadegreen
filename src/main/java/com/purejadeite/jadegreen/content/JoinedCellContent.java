package com.purejadeite.jadegreen.content;

import com.purejadeite.jadegreen.definition.cell.JoinedCellDefinition;

/**
 * 他のセルしたCellのインターフェイス
 * @author mitsuhiroseino
 */
public interface JoinedCellContent<P extends Content<?, ?>, D extends JoinedCellDefinition<?>> extends CellContent<P, D> {
}
