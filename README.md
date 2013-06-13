HtmlTruncate
============

This utility allow to do smart trunction with HTML code.
If you do simple String trunction the result will probably not be a valid html string (the tags will be closed).

The caracters that taken into account are only content caracters (ie not tags). 
An Html caracter (like &nbsp;) count for only 1 characaters.

The trunation occurs only after a full word before reached the expected length.

You can provide a text that will be add at the truncation place, usefull to add a string like "..." inside the tags. Default is "&nbsp;..."

For the time being, space and punctuation characters are not taken into account.

This code has been inspired by this implementation : http://blog.dentcat.com/2011/11/truncating-html-in-java.html

---
__Example 1 :__

    Assert.assertEquals("Simple&nbsp;...", StringUtil.truncateHtmlWords("Simple&nbsp;example", 5) // End of first word, add "&nbsp;..."
    Assert.assertEquals("Simple&nbsp;&nbsp;...", StringUtil.truncateHtmlWords("Simple&nbsp;example", 6) // Html encoded chars count for a single char
    Assert.assertEquals("Simple&nbsp;&nbsp;...", StringUtil.truncateHtmlWords("Simple&nbsp;example", 7) // Not enought chars to take the seconf word
    Assert.assertEquals("Simple&nbsp;example", StringUtil.truncateHtmlWords("Simple&nbsp;example", 14) // Full texte

__Example 2 :__
    Assert.assertEquals("texte <a href="link">End</a>", StringUtil.truncateHtmlWords("texte <a href="link">other text</a>", 8, "End") // Full texte
    Assert.assertEquals("texte <a href="link">otherEnd</a>", StringUtil.truncateHtmlWords("texte <a href="link">other text</a>", 10, "End") // Full texte
