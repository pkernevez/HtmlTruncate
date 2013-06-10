package net.kernevez.html;

import org.junit.Test;
import static org.junit.Assert.*;

public class StringUtilTest {

	public final static String SIMPLE_TEXT = "Du texte ici";

	@Test
	public void testTruncateHtmlWords_NoChar() throws Exception {
		assertEquals("", StringUtil.truncateHtmlWords(SIMPLE_TEXT, 0));
	}

	// SIMPLE_TEXT
	@Test
	public void testTruncateHtmlWords_SimpleText_AndMoreChar() throws Exception {
		assertEquals(SIMPLE_TEXT, StringUtil.truncateHtmlWords(SIMPLE_TEXT, 12));
	}

	@Test
	public void testTruncateHtmlWords_SimpleText_SameLenght() throws Exception {
		assertEquals(SIMPLE_TEXT, StringUtil.truncateHtmlWords(SIMPLE_TEXT, 10));
	}

	@Test
	public void testTruncateHtmlWords_SimpleText_TruncateInWordSeparator() throws Exception {
		assertEquals("Du", StringUtil.truncateHtmlWords(SIMPLE_TEXT, 3));
		assertEquals("Du texte", StringUtil.truncateHtmlWords(SIMPLE_TEXT, 8));
	}

	@Test
	public void testTruncateHtmlWords_SimpleText_TruncateAtEndOfWord() throws Exception {
		assertEquals("Du", StringUtil.truncateHtmlWords(SIMPLE_TEXT, 2));
		assertEquals("Du texte", StringUtil.truncateHtmlWords(SIMPLE_TEXT, 7));
	}

	@Test
	public void testTruncateHtmlWords_SimpleText_TruncateInMiddleOfLastWord_RemoveLastWord() throws Exception {
		assertEquals("Du texte", StringUtil.truncateHtmlWords(SIMPLE_TEXT, 9));
	}

	// SIMPLE_HTML
	public final static String SIMPLE_HTML = "text<p>tot to</p>t xt";

	@Test
	public void testTruncateHtmlWords_SimpleHtml_SameLength() throws Exception {
		assertEquals(SIMPLE_HTML, StringUtil.truncateHtmlWords(SIMPLE_HTML, 12));
	}

	@Test
	public void testTruncateHtmlWords_SimpleHtml_AndMoreChar() throws Exception {
		assertEquals(SIMPLE_HTML, StringUtil.truncateHtmlWords(SIMPLE_HTML, 13));
	}

	@Test
	public void testTruncateHtmlWords_SimpleHtml_TruncateInMiddleOfLastWord() throws Exception {
		assertEquals("text<p>tot to</p>t", StringUtil.truncateHtmlWords(SIMPLE_HTML, 11));
	}

	private static final String HTML_WITH_ATTRIBUTES = "text<a href=\"http://github.com/pkernevez/\">tuu t</a> er";

	@Test
	public void testTruncateHtmlWords_SimpleHtml_TruncateInMiddleOfTagWithoutContent() throws Exception {
		// TODO pas de lien si pas de texte...
		assertEquals("text<p></p>", StringUtil.truncateHtmlWords(SIMPLE_HTML, 6));
		// TODO pas de lien si pas de texte...
		assertEquals("text<a href=\"http://github.com/pkernevez/\"></a>", StringUtil.truncateHtmlWords(HTML_WITH_ATTRIBUTES, 6));
	}

	@Test
	public void testTruncateHtmlWords_SimpleHtml_TruncateInMiddleOfTagWithContent() throws Exception {
		// assertEquals("text<p>tot</p>", StringUtil.truncateHtmlWords(SIMPLE_HTML, 7));
		assertEquals("text<p>tot to</p>", StringUtil.truncateHtmlWords(SIMPLE_HTML, 9));
		assertEquals("text<a href=\"http://github.com/pkernevez/\">tuu</a>", StringUtil.truncateHtmlWords(HTML_WITH_ATTRIBUTES, 7));
	}

	@Test
	public void testTruncateHtmlWords_SimpleHtml_TruncateAfterTag() throws Exception {
		assertEquals("text<p>tot to</p>t", StringUtil.truncateHtmlWords(SIMPLE_HTML, 10));
	}

	// IMBRICATED_HTML
	public final static String IMBRICATED_HTML = "text<p>tot <a href=\"http://wewe.com/ewe\">to</a>we</p>";

	@Test
	public void testTruncateHtmlWords_ImbricatedHtml_MoreChar() throws Exception {
		assertEquals(IMBRICATED_HTML, StringUtil.truncateHtmlWords(IMBRICATED_HTML, 50));
	}

	@Test
	public void testTruncateHtmlWords_ImbricatedHtml_SameLenght() throws Exception {
		assertEquals(IMBRICATED_HTML, StringUtil.truncateHtmlWords(IMBRICATED_HTML, 11));
	}

	@Test
	public void testTruncateHtmlWords_ImbricatedHtml_TruncateInMiddleOfSecondTag() throws Exception {
		assertEquals("text<p>tot <a href=\"http://wewe.com/ewe\">to</a></p>", StringUtil.truncateHtmlWords(IMBRICATED_HTML, 10));
	}

	@Test
	public void testTruncateHtmlWords_ImbricatedHtml_TruncateInMiddleOfFirstTag() throws Exception {
		// assertEquals("text<p>tot <a href=\"http://wewe.com/ewe\">to</a></p>", StringUtil.truncateHtmlWords(IMBRICATED_HTML, 9));
		assertEquals("text<p>tot <a href=\"http://wewe.com/ewe\"></a></p>", StringUtil.truncateHtmlWords(IMBRICATED_HTML, 7));
	}

	// Test HTML encoding &ecute;
	public final static String SIMPLE_HTML_ENCODING = "Du copi&ecute; cel&agrave; coll&egrave;";

	@Test
	public void testTruncateHtmlWords_HtmlEncoding_TruncateBeforeHtmlChar() throws Exception {
		assertEquals("Du copi", StringUtil.truncateHtmlWords(SIMPLE_HTML_ENCODING, 6));
	}

	@Test
	public void testTruncateHtmlWords_HtmlEncoding_TruncateHtmlChar() throws Exception {
		assertEquals("Du copi&ecute;", StringUtil.truncateHtmlWords(SIMPLE_HTML_ENCODING, 7));
	}

	@Test
	public void testTruncateHtmlWords_HtmlEncoding_TruncateWordAfterHtmlChar() throws Exception {
		assertEquals("Du copi&ecute;", StringUtil.truncateHtmlWords(SIMPLE_HTML_ENCODING, 8));
		assertEquals("Du copi&ecute;", StringUtil.truncateHtmlWords(SIMPLE_HTML_ENCODING, 9));
	}

	@Test
	public void testTruncateHtmlWords_HtmlEncoding_TruncateWordsAfterHtmlChar() throws Exception {
		assertEquals("Du copi&ecute; cel", StringUtil.truncateHtmlWords(SIMPLE_HTML_ENCODING, 10));
		assertEquals("Du copi&ecute; cel&agrave;", StringUtil.truncateHtmlWords(SIMPLE_HTML_ENCODING, 11));
	}

	public final static String HTML_WITH_ENCODING = "Du <p>co<br/>pi&ecute;<br> a <br/>b</p>l&agrave; coll&egrave;";

	@Test
	public void testTruncateHtmlWords_HtmlEncoding_TruncateAfterAutocloseTag() throws Exception {
		assertEquals("Du <p>co</p>", StringUtil.truncateHtmlWords(HTML_WITH_ENCODING, 4));
		assertEquals("Du <p>co<br/>pi</p>", StringUtil.truncateHtmlWords(HTML_WITH_ENCODING, 6));
	}

	@Test
	public void testTruncateHtmlWords_HtmlEncoding_TruncateAfterSingletTag() throws Exception {
		assertEquals("Du <p>co<br/>pi&ecute;</p>", StringUtil.truncateHtmlWords(HTML_WITH_ENCODING, 7));
		assertEquals("Du <p>co<br/>pi&ecute;<br> a</p>", StringUtil.truncateHtmlWords(HTML_WITH_ENCODING, 8));
	}

	private final String REAL_LIFE_1 = new StringBuilder()
	.append("<p style=\"text-align: justify\">Didier Chan Voc Chun: <strong>Frontier Markets</strong>\n")
	.append("<br/>\n")
	.append("Giuseppe Caltabiano: 2Y USD (Quanto) Express Certificate on Worst-of S&amp;P500, EuroStoxx50, Nikkei225<br/>")
	.append("<br/>")
	.append("UBPTV - LIVE <a href=\"http://srvvideogva01/Investment%20Update%20Meetings/live.php\" target=\"_blank\">Access</a>")
	.append("<br/>")
	.append("UBPTV - ARCHIVES <a href=\"http://srvvideogva01/Investment%20Update%20Meetings/ondemand.php\" target=\"_blank\">Access</a>")
	.append("</p>").toString();

	private final String REAL_LIFE_TRUNCATE_1 = new StringBuilder()
	.append("<p style=\"text-align: justify\">Didier Chan Voc Chun: <strong>Frontier Markets</strong>\n")
	.append("<br/>\n")
	.append("Giuseppe Caltabiano: 2Y USD (Quanto) Express Certificate on Worst-of S&amp;P500, EuroStoxx50, Nikkei225<br/>")
	.append("<br/>")
	.append("UBPTV - LIVE <a href=\"http://srvvideogva01/Investment%20Update%20Meetings/live.php\" target=\"_blank\">Access</a>")
	.append("<br/>")
	.append("UBPTV - ARCHIVES <a href=\"http://srvvideogva01/Investment%20Update%20Meetings/ondemand.php\" target=\"_blank\">Access</a>")
	.append("</p>").toString();

	@Test
	public void testRealLife(){
		assertEquals("", StringUtil.truncateHtmlWords(REAL_LIFE_1, 67));
	}

}
