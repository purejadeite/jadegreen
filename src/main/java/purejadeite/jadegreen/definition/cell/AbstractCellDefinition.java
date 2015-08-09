package purejadeite.jadegreen.definition.cell;

import purejadeite.jadegreen.definition.AbstractDefinition;
import purejadeite.jadegreen.definition.Definition;
import purejadeite.jadegreen.definition.converter.cell.CellConverter;
import purejadeite.jadegreen.definition.converter.cell.CellConverterFactory;

import java.util.List;
import java.util.Map;

/**
 * <pre>
 * 値の取得元セルの情報を保持する抽象クラス
 * </pre>
 * @author mitsuhiroseino
 */
public abstract class AbstractCellDefinition extends AbstractDefinition implements CellDefinition {

	/**
	 * コンバーターなどでラップしたCell読み込み定義
	 */
	protected CellConverter converter;

	/**
	 * コンストラクタ
	 * @param parent 親定義
	 * @param id 定義ID
	 * @param converters コンバーター
	 */
	protected AbstractCellDefinition(Definition parent, String id, boolean stuff, List<Map<String, String>> converters) {
		super(parent, id, stuff);
		this.converter = CellConverterFactory.build(converters);
	}

	/**
	 * サポートされていないオペレーションです
	 */
	@Override
	public List<Definition> getChildren() {
		throw new UnsupportedOperationException();
	}

	/**
	 * サポートされていないオペレーションです
	 */
	@Override
	public void addChild(Definition child) {
		throw new UnsupportedOperationException();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public CellConverter getConverter() {
		return converter;
	}

	@Override
	public Object convert(Object value) {
		if (converter == null) {
			return value;
		} else {
			return converter.convert(value);
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String toJson() {
		return super.toJson() + "," + getJson("converter", converter);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String toString() {
		return super.toString() + ", converter=" + converter;
	}

}
