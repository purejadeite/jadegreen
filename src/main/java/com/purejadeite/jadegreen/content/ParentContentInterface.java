package com.purejadeite.jadegreen.content;

import java.util.List;
import java.util.Map;

import com.purejadeite.jadegreen.definition.ParentDefinitionInterface;

/**
 * Excelファイルから取得した値
 * @author mitsuhiroseino
 */
public interface ParentContentInterface<P extends ParentContentInterface<?, ?, ?>, C extends ContentInterface<?, ?>,D extends ParentDefinitionInterface<?, ?>> extends ContentInterface<P, D> {
	public List<C> getChildren();
	public Map<String, ContentInterface<?, ?>> getCells();
	public void addChild(C content);
}
