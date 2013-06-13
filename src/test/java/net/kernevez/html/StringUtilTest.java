package net.kernevez.html;

import junit.framework.Assert;

import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Copyright (c)2013, Philippe Kernevez. All rights reserved.
 * 
 * <pre>
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 2.1 of the License, or (at your option) any later version.
 * 
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * Lesser General Public License for more details.
 * </pre>
 */
public class StringUtilTest {

	private static final String NBSP = "&nbsp;...";
	public final static String SIMPLE_TEXT = "Du texte ici";

	@Test
	public void testTruncateHtmlWords_NoChar_NoEnd() throws Exception {
		assertEquals("", StringUtil.truncateHtmlWords(SIMPLE_TEXT, 0, ""));
	}

	@Test
	public void testTruncateHtmlWords_NoChar() throws Exception {
		assertEquals(NBSP, StringUtil.truncateHtmlWords(SIMPLE_TEXT, 0));
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
		assertEquals("Du"+NBSP, StringUtil.truncateHtmlWords(SIMPLE_TEXT, 3));
		assertEquals("Du texte"+NBSP, StringUtil.truncateHtmlWords(SIMPLE_TEXT, 8));
	}

	@Test
	public void testTruncateHtmlWords_SimpleText_TruncateAtEndOfWord() throws Exception {
		assertEquals("Du"+NBSP, StringUtil.truncateHtmlWords(SIMPLE_TEXT, 2));
		assertEquals("Du texte"+NBSP, StringUtil.truncateHtmlWords(SIMPLE_TEXT, 7));
	}

	@Test
	public void testTruncateHtmlWords_SimpleText_TruncateInMiddleOfLastWord_RemoveLastWord() throws Exception {
		assertEquals("Du texte"+NBSP, StringUtil.truncateHtmlWords(SIMPLE_TEXT, 9));
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
		assertEquals("text<p>tot to</p>t"+NBSP, StringUtil.truncateHtmlWords(SIMPLE_HTML, 11));
	}

	private static final String HTML_WITH_ATTRIBUTES = "text<a href=\"http://github.com/pkernevez/\">tuu t</a> er";

	@Test
	public void testTruncateHtmlWords_SimpleHtml_TruncateInMiddleOfTagWithoutContent() throws Exception {
		// TODO pas de lien si pas de texte...
		assertEquals("text<p>"+NBSP+"</p>", StringUtil.truncateHtmlWords(SIMPLE_HTML, 6));
		// TODO pas de lien si pas de texte...
		assertEquals("text<a href=\"http://github.com/pkernevez/\">"+NBSP+"</a>", StringUtil.truncateHtmlWords(HTML_WITH_ATTRIBUTES, 6));
	}

	@Test
	public void testTruncateHtmlWords_SimpleHtml_TruncateInMiddleOfTagWithContent() throws Exception {
		 assertEquals("text<p>tot"+NBSP+"</p>", StringUtil.truncateHtmlWords(SIMPLE_HTML, 7));
		assertEquals("text<p>tot to</p>"+NBSP, StringUtil.truncateHtmlWords(SIMPLE_HTML, 9));
		assertEquals("text<a href=\"http://github.com/pkernevez/\">tuu"+NBSP+"</a>", StringUtil.truncateHtmlWords(HTML_WITH_ATTRIBUTES, 7));
	}

	@Test
	public void testTruncateHtmlWords_SimpleHtml_TruncateAfterTag() throws Exception {
		assertEquals("text<p>tot to</p>t"+NBSP, StringUtil.truncateHtmlWords(SIMPLE_HTML, 10));
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
		assertEquals("text<p>tot <a href=\"http://wewe.com/ewe\">to</a>"+NBSP+"</p>", StringUtil.truncateHtmlWords(IMBRICATED_HTML, 10));
	}

	@Test
	public void testTruncateHtmlWords_ImbricatedHtml_TruncateInMiddleOfFirstTag() throws Exception {
		assertEquals("text<p>tot <a href=\"http://wewe.com/ewe\">to</a>"+NBSP+"</p>", StringUtil.truncateHtmlWords(IMBRICATED_HTML, 9));
		assertEquals("text<p>tot <a href=\"http://wewe.com/ewe\">"+NBSP+"</a></p>", StringUtil.truncateHtmlWords(IMBRICATED_HTML, 7));
	}

	// Test HTML encoding &ecute;
	public final static String SIMPLE_HTML_ENCODING = "Du copi&ecute; cel&agrave; coll&egrave;";

	@Test
	public void testTruncateHtmlWords_HtmlEncoding_TruncateBeforeHtmlChar() throws Exception {
		assertEquals("Du copi"+NBSP, StringUtil.truncateHtmlWords(SIMPLE_HTML_ENCODING, 6));
	}

	@Test
	public void testTruncateHtmlWords_HtmlEncoding_TruncateHtmlChar() throws Exception {
		assertEquals("Du copi&ecute;"+NBSP, StringUtil.truncateHtmlWords(SIMPLE_HTML_ENCODING, 7));
	}

	@Test
	public void testTruncateHtmlWords_HtmlEncoding_TruncateWordAfterHtmlChar() throws Exception {
		assertEquals("Du copi&ecute;"+NBSP, StringUtil.truncateHtmlWords(SIMPLE_HTML_ENCODING, 8));
		assertEquals("Du copi&ecute;"+NBSP, StringUtil.truncateHtmlWords(SIMPLE_HTML_ENCODING, 9));
	}

	@Test
	public void testTruncateHtmlWords_HtmlEncoding_TruncateWordsAfterHtmlChar() throws Exception {
		assertEquals("Du copi&ecute; cel", StringUtil.truncateHtmlWords(SIMPLE_HTML_ENCODING, 10,""));
		assertEquals("Du copi&ecute; cel&agrave;"+NBSP, StringUtil.truncateHtmlWords(SIMPLE_HTML_ENCODING, 11));
	}

	public final static String HTML_WITH_ENCODING = "Du <p>co<br/>pi&ecute;<br> a <br/>b</p>l&agrave; coll&egrave;";

	@Test
	public void testTruncateHtmlWords_HtmlEncoding_TruncateAfterAutocloseTag() throws Exception {
		assertEquals("Du <p>co"+NBSP+"</p>", StringUtil.truncateHtmlWords(HTML_WITH_ENCODING, 4));
		assertEquals("Du <p>co<br/>pi"+NBSP+"</p>", StringUtil.truncateHtmlWords(HTML_WITH_ENCODING, 6));
	}

	@Test
	public void testTruncateHtmlWords_HtmlEncoding_TruncateAfterSingletTag() throws Exception {
		assertEquals("Du <p>co<br/>pi&ecute;"+NBSP+"</p>", StringUtil.truncateHtmlWords(HTML_WITH_ENCODING, 7));
		assertEquals("Du <p>co<br/>pi&ecute;<br> a</p>", StringUtil.truncateHtmlWords(HTML_WITH_ENCODING, 8,""));
	}

}
