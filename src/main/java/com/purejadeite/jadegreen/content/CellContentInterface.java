package com.purejadeite.jadegreen.content;

import com.purejadeite.jadegreen.definition.cell.CellDefinitionInterface;

/**
 * Cellの値取得インターフェイス
 * @author mitsuhiroseino
 */
public interface CellContentInterface<P extends ContentInterface<?, ?>, D extends CellDefinitionInterface<?, ?>> extends ContentInterface<P, D> {
}
