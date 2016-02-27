package com.purejadeite.jadegreen.content;

import com.purejadeite.jadegreen.definition.NoDefinitionInterface;

/**
 * なにも無い定義のインターフェイス
 * @author mitsuhiroseino
 */
public interface NoContentInterface<P extends NoContentInterface<P>> extends ParentContentInterface<NoContentInterface<?>, ContentInterface<?, ?>, NoDefinitionInterface> {
}
