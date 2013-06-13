package net.kernevez.html;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
public class StringUtil {
	private static final Pattern WORDS_PATTERN = Pattern.compile("&.*?;|<.*?>|(\\w[\\w-]*)");
	private static final Pattern TAGS_PATTERN = Pattern.compile("<(/)?([^ ]+?)(?: | .*?)?(/)?>");
	private static final List<String> HTML_FOR_SINGLETS = Arrays.asList("br", "col", "link", "base", "img", "param", "area", "hr", "input");

	public static String truncateHtmlWords(String html, int length) {
		return truncateHtmlWords(html, length, "&nbsp;...");
	}

	public static String truncateHtmlWords(String html, int length, String end) {
		if (length <= 0)
			return end;

		Matcher mWords = WORDS_PATTERN.matcher(html);
		// Count non-HTML words and keep note of open tags
		int endTextPos = 0;
		int curLength = 0;
		boolean tContinue = curLength < length;
		List<String> openTags = new ArrayList<String>();
		while (tContinue) {
			if (!mWords.find())
				break;
			if (mWords.group(1) != null || mWords.group().startsWith("&")) {
				// It's an actual non-HTML word
				curLength += (mWords.group(1) == null ? 1 : mWords.group(1).length()); // Html encoding is one char
				if (curLength > length) {
					tContinue = false;
				} else {
					endTextPos = mWords.end();
				}
			} else {
				// Check for tag
				Matcher tag = TAGS_PATTERN.matcher(mWords.group());
				if (!tag.find()) {
					// Don't worry about non tags
					// truncate point
				} else {
					String closingTag = tag.group(1);
					// Element names are always case-insensitive
					String tagName = tag.group(2).toLowerCase();
					String selfClosing = tag.group(3);
					if (closingTag != null) {
						int i = openTags.indexOf(tagName);
						if (i != -1)
							openTags = openTags.subList(i + 1, openTags.size());
						endTextPos = mWords.end();

					} else if (selfClosing == null && !HTML_FOR_SINGLETS.contains(tagName)) {
						openTags.add(0, tagName);
						endTextPos = mWords.end();
					}
				}
			}
		}

		if (curLength <= length)
			return html;
		StringBuilder out = new StringBuilder(html.substring(0, endTextPos)).append(end);
		for (String tag : openTags)
			out.append("</" + tag + ">");

		return out.toString();
	}
}
