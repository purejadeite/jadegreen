package purejadeite.jadegreen.content.cell;

import purejadeite.jadegreen.content.AbstractContent;
import purejadeite.jadegreen.content.Content;
import purejadeite.jadegreen.content.SheetContentImpl;
import purejadeite.jadegreen.content.SpecificValue;
import purejadeite.jadegreen.content.Status;
import purejadeite.jadegreen.definition.Definition;
import purejadeite.jadegreen.definition.cell.LinkCellDefinition;

import static purejadeite.jadegreen.content.Status.*;

import java.util.ArrayList;
import java.util.List;

/**
 * <pre>
 * 値の取得元セルの情報を保持する抽象クラス
 * </pre>
 * @author mitsuhiroseino
 */
public abstract class AbstractLinkCellContent<D extends LinkCellDefinition> extends AbstractContent<D> implements LinkCellContent {

	public AbstractLinkCellContent(Content parent, D definition) {
		super(parent, definition);
	}

	public Status addValue(int row, int col, Object value) {
		// 値を取得しない
		return END;
	}

	@Override
	public Object getRawValuesImpl(Definition... ignore) {
		// 値は無視してもらう
		return SpecificValue.IGNORE;
	}

	public List<Content> getSheetKeyContents(Content book) {
		List<Content> sheetKeyContents = book.searchContents(definition.getSheetKeyDefinition());
		if (sheetKeyContents.isEmpty()) {
			// 相手のキーが無いならば定義が不正なので例外
			throw new RuntimeException();
		}
		return sheetKeyContents;
	}

	public Content getMySheetKeyContent(Content book) {
		Content sheet = this.getUpperContent(SheetContentImpl.class);
		List<Content> mySheetKeyContents = sheet.searchContents(definition.getMySheetKeyDefinition());
		if (mySheetKeyContents.size() != 1) {
			// キーが一意でないならば例外
			throw new RuntimeException();
		}
		return mySheetKeyContents.get(0);
	}

	public Content getTargetSheet(Content mySheetKeyContent, List<Content> sheetKeyContents) {
		Object mySheetKeyValues = mySheetKeyContent.getValues();
		for (Content sheetKeyContent : sheetKeyContents) {
			if (mySheetKeyValues.equals(sheetKeyContent.getValues())) {
				return sheetKeyContent.getUpperContent(SheetContentImpl.class);
			}
		}
		return null;
	}

	public Content getValueContent(Content targetSheet, List<Content> valueContents) {
		for (Content valueContent : valueContents) {
			// そのキーと同じシートにある値を探す
			if (targetSheet == valueContent.getUpperContent(SheetContentImpl.class)) {
				return valueContent;
			}
		}
		return null;
	}

	@Override
	public String getId() {
		return definition.getId();
	}

	@Override
	public Definition getDefinition() {
		return definition;
	}

	@Override
	public List<Content> searchContents(Definition key) {
		List<Content> contents = new ArrayList<>();
		if (definition == key) {
			contents.add(this);
		}
		return contents;
	}

}
