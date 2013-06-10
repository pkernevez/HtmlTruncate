package net.kernevez.html;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StringUtil {
	private static final Pattern WORDS_PATTERN = Pattern.compile("&.*?;|<.*?>|(\\w[\\w-]*)");
	private static final Pattern TAGS_PATTERN = Pattern.compile("<(/)?([^ ]+?)(?: | .*?)?(/)?>");

	public static String truncateHtmlWords(String html, int length) {
		if (length <= 0)
			return new String();

		List<String> html4Singlets = Arrays.asList("br", "col", "link", "base", "img", "param", "area", "hr", "input");
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

					} else if (selfClosing == null && !html4Singlets.contains(tagName)) {
						openTags.add(0, tagName);
						endTextPos = mWords.end();
					}
				}
			}
		}

		if (curLength <= length)
			return html;
		StringBuilder out = new StringBuilder(html.substring(0, endTextPos));
		for (String tag : openTags)
			out.append("</" + tag + ">");

		return out.toString();
	}
}
