package com.purejadeite.jadegreen.content;

import com.purejadeite.jadegreen.definition.cell.CellDefinition;

/**
 * Cellの値取得インターフェイス
 * @author mitsuhiroseino
 */
public interface CellContent<P extends Content<?, ?>, D extends CellDefinition<?>> extends Content<P, D> {
}
