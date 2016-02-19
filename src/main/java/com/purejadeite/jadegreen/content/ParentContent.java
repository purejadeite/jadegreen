package com.purejadeite.jadegreen.content;

import java.util.List;
import java.util.Map;

import com.purejadeite.jadegreen.definition.Definition;

/**
 * Excelファイルから取得した値
 * @author mitsuhiroseino
 */
public interface ParentContent<P extends ParentContent<?, ?, ?>, C extends Content<?, ?>,D extends Definition<?>> extends Content<P, D> {
	public List<C> getChildren();
	public Map<String, Content<?, ?>> getCells();
	public void addChild(C content);
}
