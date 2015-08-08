package purejadeite.jadegreen.content.cell;

import java.util.List;

import purejadeite.jadegreen.content.Content;



/**
 * Cell読み込み定義のインターフェイス
 * @author mitsuhiroseino
 */
public interface LinkCellContent extends CellContent {

	public List<Content> getSheetKeyContents(Content book);

	public Content getMySheetKeyContent(Content book);

	public Content getTargetSheet(Content mySheetKeyContent, List<Content> sheetKeyContents);

	public Content getValueContent(Content targetSheet, List<Content> valueContents);
}
