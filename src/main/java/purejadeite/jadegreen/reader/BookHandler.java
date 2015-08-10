package purejadeite.jadegreen.reader;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

/**
 * ブック用ハンドラ
 * @author mitsuhiroseino
 */
public class BookHandler extends DefaultHandler {

    private static final String TAG_SHEET = "sheet";

    private static final String NAME_SHEET_NAME = "name";

    private static final String NAME_RID = "r:id";

    private String matchSheetName = null;

    private MatchType type;

    private Map<String, String> sheetNames = new HashMap<>();

    /**
     * コンストラクタ
     * @param sheetName 取得対象のシート名
     */
    public BookHandler(String sheetName) {
        String str = sheetName;
        MatchType type = MatchType.PERFECT;

        if (StringUtils.endsWith(str, "*")) {
        	str = StringUtils.substring(str, 0, str.length() - 1);
        	// 前方一致
        	type = MatchType.FORWARD;
        }

        if (StringUtils.startsWith(str, "*")) {
        	str = StringUtils.substring(str, 1);
        	if (type == MatchType.PERFECT) {
        		// 後方一致
            	type = MatchType.BACK;
        	} else {
        		// 部分一致
            	type = MatchType.PART;
        	}
        }
        this.type = type;
        this.matchSheetName = str;
    }

    /**
     * シートIDとシート名のマッピング情報取得します。
     * @return シート名
     */
    public Map<String, String> getSheetNames() {
        return sheetNames;
    }

    /**
     * 開始タグを取得した際に動作するメソッド
     * {@inheritDoc}
     */
    @Override
    public void startElement(String uri, String localName, String name,
            Attributes attributes) throws SAXException {
        if (TAG_SHEET.equals(name)) {
        	// シートの開始タグならばシート名とr:IDを取得
            String sheetNameTemp = attributes.getValue(NAME_SHEET_NAME);
            String rIdTemp = attributes.getValue(NAME_RID);
            if (isTarget(sheetNameTemp)) {
                sheetNames.put(rIdTemp, sheetNameTemp);
            }
        }
    }

    /**
     * 取得対象のシートか？
     * @param name
     * @return
     */
    private boolean isTarget(String name) {
    	if (type == MatchType.FORWARD) {
    		return StringUtils.startsWith(name, this.matchSheetName);
    	} else if (type == MatchType.BACK) {
    		return StringUtils.endsWith(name, this.matchSheetName);
    	} else if (type == MatchType.PART) {
    		return StringUtils.contains(name, this.matchSheetName);
    	}
		return StringUtils.equals(name, this.matchSheetName);

    }

}
