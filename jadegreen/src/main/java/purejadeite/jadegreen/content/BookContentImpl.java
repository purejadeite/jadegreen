package purejadeite.jadegreen.content;

import purejadeite.jadegreen.definition.BookDefinitionImpl;
import purejadeite.jadegreen.definition.Definition;
import purejadeite.jadegreen.reader.Status;

import java.util.ArrayList;
import java.util.List;

/**
 * Sheet読み込み定義
 * @author mitsuhiroseino
 *
 */
public class BookContentImpl extends AbstractContent<BookDefinitionImpl> {

	private String name;

	private List<SheetContentImpl> sheets = new ArrayList<>();

	public BookContentImpl(BookDefinitionImpl definition, String name) {
		super(null, definition);
		this.name = name;
	}

	/**
	 * {@inheritDoc}
	 */
	public void addSheet(SheetContentImpl sheet) {
		sheets.add(sheet);
	}

	public Status addValue(int row, int col, Object value) {
		throw new UnsupportedOperationException();
	}

	/**
	 * {@inheritDoc}
	 */
	public Object getRawValuesImpl(Definition... ignore) {
		List<Object> values = new ArrayList<>();
		for (Content sheet : sheets) {
			Object vals = sheet.getRawValues(ignore);
			if (vals != SpecificValue.IGNORE) {
				values.add(vals);
			}
		}
		return values;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean isClosed() {
		if (closed) {
			return true;
		}
		for (Content sheet : sheets) {
			if (!sheet.isClosed()) {
				return false;
			}
		}
		close();
		return true;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Object getValuesImpl(Definition... ignore) {
		List<Object> values = new ArrayList<>();
		for (Content sheet : sheets) {
			Object vals = sheet.getValues(ignore);
			if (vals != SpecificValue.STUFF && vals != SpecificValue.IGNORE) {
				values.add(vals);
			}
		}
		return values;
	}

	@Override
	public List<Content> searchContents(Definition key) {
		List<Content> cntnts = new ArrayList<>();
		if (definition == key) {
			cntnts.add(this);
		}
		for (Content sheet : sheets) {
			cntnts.addAll(sheet.searchContents(key));
		}
		return cntnts;
	}

	public Content getUpperContent(Class<? extends Content> contentClazz) {
		return null;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String toJson() {
		return "" + getJson(null, sheets);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String toString() {
		return this.getClass().getSimpleName() + " [" + super.toString() + ", name=" + name + ", " + ", sheets="
				+ sheets + "]";
	}

}
