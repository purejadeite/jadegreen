package com.purejadeite.jadegreen.content;

import java.util.List;



/**
 * コンテンツのリンクインターフェイス
 * @author mitsuhiroseino
 */
public interface LinkContent extends Content {

	public List<Content> getSheetKeyContents(Content book);

	public Content getMySheetKeyContent(Content book);

	public Content getTargetSheet(Content mySheetKeyContent, List<Content> sheetKeyContents);

	public Content getValueContent(Content targetSheet, List<Content> valueContents);
}
