package purejadeite.jadegreen.content.cell;

import purejadeite.jadegreen.content.BookContentImpl;
import purejadeite.jadegreen.content.Content;
import purejadeite.jadegreen.content.SheetContentImpl;
import purejadeite.jadegreen.content.SpecificValue;
import purejadeite.jadegreen.content.range.RangeContentImpl;
import purejadeite.jadegreen.definition.Definition;
import purejadeite.jadegreen.definition.cell.LinkRangeCellDefinitionImpl;
import purejadeite.jadegreen.reader.Status;

import static purejadeite.jadegreen.reader.Status.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;

/**
 * <pre>
 * 値の取得元セルの情報を保持する抽象クラス
 * </pre>
 * @author mitsuhiroseino
 */
public class LinkRangeCellContentImpl extends AbstractLinkCellContent<LinkRangeCellDefinitionImpl> implements RangeCellContent {

	public LinkRangeCellContentImpl(Content parent, LinkRangeCellDefinitionImpl definition) {
		super(parent, definition);
	}

	public Status addValue(int row, int col, Object value) {
		// 値を取得しない
		return NO;
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

//		// 全Contentから欲しい値のContentを取得
//		List<Content> valueContents = bookContent.searchContents(cell.getValueDefinition());
//		if (valueContents.isEmpty()) {
//			// 欲しい値が無いならば定義が不正なので例外
//			throw new RuntimeException();
//		} else if (valueContents.size() == 1 && StringUtils.isEmpty(cell.getSheetKeyId())
//				&& StringUtils.isEmpty(cell.getKeyId())) {
//			// 欲しい値が1つでキーが指定されていない場合はそれの値を返す
//			// 1ファイルに各シートが1つずつしかない場合を想定
//			return valueContents.get(0).getValues(ignore);
//		}

		// 全Contentから相手のシートのキーになるContentを取得
		List<Content> sheetKeyContents = getSheetKeyContents(book);

		// 自分の属するシートのキーを取得
		Content mySheetKeyContent = getMySheetKeyContent(book);

		// 値の取得元シートを取得
		Content sheetContent = getTargetSheet(mySheetKeyContent, sheetKeyContents);

		// 取得元のキーとなるレコードを取得
		Content keyContent = sheetContent.searchContents(definition.getKeyDefinition()).get(0);

		// 取得元のテーブル丸ごと取得
		Content rangeContent = keyContent.getUpperContent(RangeContentImpl.class);

		// 取得元のキーとなるレコードを取得
//		Content valueContent = sheetContent.searchContents(cell.getValueDefinition()).get(0);

		// 自分の属するsheetを取得
		Content mySheetContent = this.getUpperContent(SheetContentImpl.class);

		// 自分のキーとなるレコードを取得
		Content myKeyContent = mySheetContent.searchContents(definition.getMyKeyDefinition()).get(0);

		// valueのID
		String[] ids = StringUtils.split(definition.getValueId(), ".");
		String valueId = ids[ids.length - 1];

		// 相手のキーと自分のキーが一致したら相手の値を取得
		List<Map<String, Object>> rangeValues = (List<Map<String, Object>>) rangeContent.getValues();
		List<Object> myKeys = (List<Object>) myKeyContent.getValues();
		List<Object> myValues = new ArrayList<>();
		for (Object myKey : myKeys) {
			myValues.add(null);
			for (Map<String, Object> rangeValue : rangeValues) {
				if (rangeValue.get(keyContent.getId()).equals(myKey)) {
					myValues.set(myValues.size() - 1, rangeValue.get(valueId));
					continue;
				}
			}
		}
		return myValues;
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

	@Override
	public boolean isClosed() {
		return true;
	}

	@Override
	public void close(int size) {
	}

	@Override
	public int size() {
		return -1;
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