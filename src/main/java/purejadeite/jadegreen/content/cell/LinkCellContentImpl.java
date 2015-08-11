package purejadeite.jadegreen.content.cell;

import static purejadeite.jadegreen.content.Status.*;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import purejadeite.jadegreen.content.BookContentImpl;
import purejadeite.jadegreen.content.Content;
import purejadeite.jadegreen.content.SpecificValue;
import purejadeite.jadegreen.content.Status;
import purejadeite.jadegreen.definition.Definition;
import purejadeite.jadegreen.definition.cell.LinkCellDefinitionImpl;

/**
 * <pre>
 * 値の取得元セルの情報を保持する抽象クラス
 * </pre>
 * @author mitsuhiroseino
 */
public class LinkCellContentImpl extends AbstractLinkCellContent<LinkCellDefinitionImpl> {

	private static final long serialVersionUID = -6716986220851296963L;

	private static final Logger LOGGER = LoggerFactory.getLogger(LinkCellContentImpl.class);

	public LinkCellContentImpl(Content parentContent, LinkCellDefinitionImpl definition) {
		super(parentContent, definition);
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

	@Override
	public Object getValuesImpl(Definition... ignore) {
		// Contentのルートを取得
		Content book = this.getUpperContent(BookContentImpl.class);

		// 全Contentから欲しい値のContentを取得
		List<Content> valueContents = book.searchContents(definition.getValueDefinition());
		if (valueContents.isEmpty()) {
			// 欲しい値が無いならば定義が不正なので例外
			throw new RuntimeException();
		} else if (valueContents.size() == 1 && StringUtils.isEmpty(definition.getSheetKeyId())) {
			// 欲しい値が1つでキーが指定されていない場合はそれの値を返す
			// 1ファイルに各シートが1つずつしかない場合を想定
			return valueContents.get(0).getValues(ignore);
		}

		// 全Contentから相手のシートのキーになるContentを取得
		List<Content> sheetKeyContents = getSheetKeyContents(book);

		// 自分の属するシートのキーを取得
		Content mySheetKeyContent = getMySheetKeyContent(book);

		// 値の取得元シートを取得
		Content targetSheet = getTargetSheet(mySheetKeyContent, sheetKeyContents);

		// 所得元の値のセルを取得
		Content valueContent = getValueContent(targetSheet, valueContents);
		if (valueContent != null) {
			return valueContent.getValues(ignore);
		} else {
			// 値が見つからないならば定義不正で例外
			throw new RuntimeException();
		}
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
	public boolean isClosed() {
		return true;
	}

	@Override
	public List<Content> searchContents(Definition key) {
		List<Content> contents = new ArrayList<>();
		if (definition == key) {
			contents.add(this);
		}
		return contents;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String toJson() {
		return "{" + super.toJson() + "}";
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String toString() {
		return this.getClass().getSimpleName() + " [" + super.toString() + "]";
	}
}
