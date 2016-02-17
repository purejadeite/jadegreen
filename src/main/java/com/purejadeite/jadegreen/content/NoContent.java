package com.purejadeite.jadegreen.content;

import com.purejadeite.jadegreen.definition.NoDefinition;

/**
 * なにも無い定義のインターフェイス
 * @author mitsuhiroseino
 */
public interface NoContent<P extends NoContent<P>> extends ParentContent<NoContent<?>, Content<?, ?>, NoDefinition> {
}
