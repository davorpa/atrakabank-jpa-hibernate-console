package util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Stream;

/**
 * Strings utilities
 */
public abstract class Strings
{

	/**
	 * A String for a space character.
	 */
	public static final String SPACE = " ";

	/**
	 * The empty String {@code ""}.
	 */
	public static final String EMPTY = "";

	/**
	 * A String for linefeed LF ("\n").
	 *
	 * @see <a href="http://docs.oracle.com/javase/specs/jls/se7/html/jls-3.html#jls-3.10.6">JLF:
	 *      Escape Sequences for Character and String Literals</a>
	 */
	public static final String LF = "\n";

	/**
	 * A String for carriage return CR ("\r").
	 *
	 * @see <a href="http://docs.oracle.com/javase/specs/jls/se7/html/jls-3.html#jls-3.10.6">JLF:
	 *      Escape Sequences for Character and String Literals</a>
	 */
	public static final String CR = "\r";

	public static final String BOX_HORIZONTAL_LINE = "═";
	public static final String BOX_VERTICAL_LINE = "║";
	public static final String BOX_SQUARE_TOPLEFT = "╔";
	public static final String BOX_SQUARE_TOPRIGHT = "╗";
	public static final String BOX_SQUARE_MEDIUMLEFT = "╠";
	public static final String BOX_SQUARE_MEDIUMRIGHT = "╣";
	public static final String BOX_SQUARE_BOTTOMLEFT = "╚";
	public static final String BOX_SQUARE_BOTTOMRIGHT = "╝";

	/**
	 * Represents a failed index search.
	 */
	public static final int INDEX_NOT_FOUND = -1;

	/**
	 * <p>The maximum size to which the padding constant(s) can expand: 8kb</p>
	 */
	private static final int PAD_LIMIT = 8192;

	/**
	 * Represents a empty string array.
	 */
	public static final String[] EMPTY_STRING_ARRAY = new String[0];

	/**
	 * <p>Centers a String in a larger String of size {@code size}
	 * using the space character (' ').</p>
	 *
	 * <p>If the size is less than the String length, the original String is returned.
	 * A {@code null} String returns {@code null}.
	 * A negative size is treated as zero.</p>
	 *
	 * <p>Equivalent to {@code center(str, size, " ")}.</p>
	 *
	 * <pre>
	 * Strings.center(null, *)   = null
	 * Strings.center("", 4)     = "    "
	 * Strings.center("ab", -1)  = "ab"
	 * Strings.center("ab", 4)   = " ab "
	 * Strings.center("abcd", 2) = "abcd"
	 * Strings.center("a", 4)    = " a  "
	 * </pre>
	 *
	 * @param str  the String to center, may be null
	 * @param size  the int size of new String, negative treated as zero
	 * @return centered String, {@code null} if null String input
	 */
	public static String center(final String str, final int size) {
		return center(str, size, ' ');
	}

	/**
	 * <p>Centers a String in a larger String of size {@code size}.
	 * Uses a supplied character as the value to pad the String with.</p>
	 *
	 * <p>If the size is less than the String length, the String is returned.
	 * A {@code null} String returns {@code null}.
	 * A negative size is treated as zero.</p>
	 *
	 * <pre>
	 * Strings.center(null, *, *)     = null
	 * Strings.center("", 4, ' ')     = "    "
	 * Strings.center("ab", -1, ' ')  = "ab"
	 * Strings.center("ab", 4, ' ')   = " ab "
	 * Strings.center("abcd", 2, ' ') = "abcd"
	 * Strings.center("a", 4, ' ')    = " a  "
	 * Strings.center("a", 4, 'y')    = "yayy"
	 * </pre>
	 *
	 * @param str  the String to center, may be null
	 * @param size  the int size of new String, negative treated as zero
	 * @param padChar  the character to pad the new String with
	 * @return centered String, {@code null} if null String input
	 */
	public static String center(String str, final int size, final char padChar) {
		if (str == null || size <= 0) {
			return str;
		}
		final int strLen = str.length();
		final int pads = size - strLen;
		if (pads <= 0) {
			return str;
		}
		str = leftPad(str, strLen + pads / 2, padChar);
		str = rightPad(str, size, padChar);
		return str;
	}

	/**
	 * <p>Centers a String in a larger String of size {@code size}.
	 * Uses a supplied String as the value to pad the String with.</p>
	 *
	 * <p>If the size is less than the String length, the String is returned.
	 * A {@code null} String returns {@code null}.
	 * A negative size is treated as zero.</p>
	 *
	 * <pre>
	 * Strings.center(null, *, *)     = null
	 * Strings.center("", 4, " ")     = "    "
	 * Strings.center("ab", -1, " ")  = "ab"
	 * Strings.center("ab", 4, " ")   = " ab "
	 * Strings.center("abcd", 2, " ") = "abcd"
	 * Strings.center("a", 4, " ")    = " a  "
	 * Strings.center("a", 4, "yz")   = "yayz"
	 * Strings.center("abc", 7, null) = "  abc  "
	 * Strings.center("abc", 7, "")   = "  abc  "
	 * </pre>
	 *
	 * @param str  the String to center, may be null
	 * @param size  the int size of new String, negative treated as zero
	 * @param padStr  the String to pad the new String with, must not be null or empty
	 * @return centered String, {@code null} if null String input
	 * @throws IllegalArgumentException if padStr is {@code null} or empty
	 */
	public static String center(String str, final int size, String padStr) {
		if (str == null || size <= 0) {
			return str;
		}
		if (isEmpty(padStr)) {
			padStr = SPACE;
		}
		final int strLen = str.length();
		final int pads = size - strLen;
		if (pads <= 0) {
			return str;
		}
		str = leftPad(str, strLen + pads / 2, padStr);
		str = rightPad(str, size, padStr);
		return str;
	}

	public static String defaultIfEmpty(
			final String str,
			final String fallback)
	{
		return isEmpty(str) ? fallback : str;
	}

	public static String defaultIfBlank(
			final String str,
			final String fallback)
	{
		return isBlank(str) ? fallback : str;
	}

	public static boolean isEmpty(
			final String str)
	{
		return str == null || str.isEmpty();
	}

	public static boolean isBlank(
			final String str)
	{
		return str == null || trim(str).isEmpty();
	}

	public static boolean isNotEmpty(
			final String str)
	{
		return !isEmpty(str);
	}

	public static boolean isNotBlank(
			final String str)
	{
		return isBlank(str);
	}

	/**
	 * <p>Left pad a String with spaces (' ').</p>
	 *
	 * <p>The String is padded to the size of {@code size}.</p>
	 *
	 * <pre>
	 * Strings.leftPad(null, *)   = null
	 * Strings.leftPad("", 3)     = "   "
	 * Strings.leftPad("bat", 3)  = "bat"
	 * Strings.leftPad("bat", 5)  = "  bat"
	 * Strings.leftPad("bat", 1)  = "bat"
	 * Strings.leftPad("bat", -1) = "bat"
	 * </pre>
	 *
	 * @param str  the String to pad out, may be null
	 * @param size  the size to pad to
	 * @return left padded String or original String if no padding is necessary,
	 *  {@code null} if null String input
	 */
	public static String leftPad(
			final String str, final int size)
	{
		return leftPad(str, size, ' ');
	}

	/**
	 * <p>Left pad a String with a specified character.</p>
	 *
	 * <p>Pad to a size of {@code size}.</p>
	 *
	 * <pre>
	 * Strings.leftPad(null, *, *)     = null
	 * Strings.leftPad("", 3, 'z')     = "zzz"
	 * Strings.leftPad("bat", 3, 'z')  = "bat"
	 * Strings.leftPad("bat", 5, 'z')  = "zzbat"
	 * Strings.leftPad("bat", 1, 'z')  = "bat"
	 * Strings.leftPad("bat", -1, 'z') = "bat"
	 * </pre>
	 *
	 * @param str  the String to pad out, may be null
	 * @param size  the size to pad to
	 * @param padChar  the character to pad with
	 * @return left padded String or original String if no padding is necessary,
	 *  {@code null} if null String input
	 */
	public static String leftPad(
			final String str, final int size, final char padChar)
	{
		if (str == null) {
			return null;
		}
		final int pads = size - str.length();
		if (pads <= 0) {
			return str; // returns original String when possible
		}
		if (pads > PAD_LIMIT) {
			return leftPad(str, size, String.valueOf(padChar));
		}
		return repeat(padChar, pads).concat(str);
	}

	/**
	 * <p>Left pad a String with a specified String.</p>
	 *
	 * <p>Pad to a size of {@code size}.</p>
	 *
	 * <pre>
	 * Strings.leftPad(null, *, *)      = null
	 * Strings.leftPad("", 3, "z")      = "zzz"
	 * Strings.leftPad("bat", 3, "yz")  = "bat"
	 * Strings.leftPad("bat", 5, "yz")  = "yzbat"
	 * Strings.leftPad("bat", 8, "yz")  = "yzyzybat"
	 * Strings.leftPad("bat", 1, "yz")  = "bat"
	 * Strings.leftPad("bat", -1, "yz") = "bat"
	 * Strings.leftPad("bat", 5, null)  = "  bat"
	 * Strings.leftPad("bat", 5, "")    = "  bat"
	 * </pre>
	 *
	 * @param str  the String to pad out, may be null
	 * @param size  the size to pad to
	 * @param padStr  the String to pad with, null or empty treated as single space
	 * @return left padded String or original String if no padding is necessary,
	 *  {@code null} if null String input
	 */
	public static String leftPad(
			final String str, final int size, String padStr)
	{
		if (str == null) {
			return null;
		}
		if (isEmpty(padStr)) {
			padStr = SPACE;
		}
		final int padLen = padStr.length();
		final int strLen = str.length();
		final int pads = size - strLen;
		if (pads <= 0) {
			return str; // returns original String when possible
		}
		if (padLen == 1 && pads <= PAD_LIMIT) {
			return leftPad(str, size, padStr.charAt(0));
		}

		if (pads == padLen) {
			return padStr.concat(str);
		}
		if (pads < padLen) {
			return padStr.substring(0, pads).concat(str);
		}
		final char[] padding = new char[pads];
		final char[] padChars = padStr.toCharArray();
		for (int i = 0; i < pads; i++) {
			padding[i] = padChars[i % padLen];
		}
		return new String(padding).concat(str);
	}

	/**
	 * Gets a CharSequence length or {@code 0} if the CharSequence is
	 * {@code null}.
	 *
	 * @param cs
	 *            a CharSequence or {@code null}
	 * @return CharSequence length or {@code 0} if the CharSequence is
	 *         {@code null}.
	 */
	public static int length(
			final CharSequence cs)
	{
		return cs == null ? 0 : cs.length();
    }

	/**
	 * <p>
	 * Gets a substring from the specified String avoiding exceptions.
	 * </p>
	 *
	 * <p>
	 * A negative start position can be used to start {@code n} characters from the
	 * end of the String.
	 * </p>
	 *
	 * <p>
	 * A {@code null} String will return {@code null}. An empty ("") String will
	 * return "".
	 * </p>
	 *
	 * <pre>
	 * Strings.substring(null, *)   = null
	 * Strings.substring("", *)     = ""
	 * Strings.substring("abc", 0)  = "abc"
	 * Strings.substring("abc", 2)  = "c"
	 * Strings.substring("abc", 4)  = ""
	 * Strings.substring("abc", -2) = "bc"
	 * Strings.substring("abc", -4) = "abc"
	 * </pre>
	 *
	 * @param str   the String to get the substring from, may be null
	 * @param start the position to start from, negative means count back from the
	 *              end of the String by this many characters
	 * @return substring from start position, {@code null} if null String input
	 */
	public static String substring(
			final String str, int start)
	{
		if (str == null) {
			return null;
		}

		// handle negatives, which means last n characters
		if (start < 0) {
			start = str.length() + start; // remember start is negative
		}

		if (start < 0) {
			start = 0;
		}
		if (start > str.length()) {
			return EMPTY;
		}

		return str.substring(start);
	}

	/**
	 * <p>
	 * Gets a substring from the specified String avoiding exceptions.
	 * </p>
	 *
	 * <p>
	 * A negative start position can be used to start/end {@code n} characters from
	 * the end of the String.
	 * </p>
	 *
	 * <p>
	 * The returned substring starts with the character in the {@code start}
	 * position and ends before the {@code end} position. All position counting is
	 * zero-based -- i.e., to start at the beginning of the string use
	 * {@code start = 0}. Negative start and end positions can be used to specify
	 * offsets relative to the end of the String.
	 * </p>
	 *
	 * <p>
	 * If {@code start} is not strictly to the left of {@code end}, "" is returned.
	 * </p>
	 *
	 * <pre>
	 * Strings.substring(null, *, *)    = null
	 * Strings.substring("", * ,  *)    = "";
	 * Strings.substring("abc", 0, 2)   = "ab"
	 * Strings.substring("abc", 2, 0)   = ""
	 * Strings.substring("abc", 2, 4)   = "c"
	 * Strings.substring("abc", 4, 6)   = ""
	 * Strings.substring("abc", 2, 2)   = ""
	 * Strings.substring("abc", -2, -1) = "b"
	 * Strings.substring("abc", -4, 2)  = "ab"
	 * </pre>
	 *
	 * @param str   the String to get the substring from, may be null
	 * @param start the position to start from, negative means count back from the
	 *              end of the String by this many characters
	 * @param end   the position to end at (exclusive), negative means count back
	 *              from the end of the String by this many characters
	 * @return substring from start position to end position, {@code null} if null
	 *         String input
	 */
	public static String substring(
			final String str, int start, int end)
	{
		if (str == null) {
			return null;
		}

		// handle negatives
		if (end < 0) {
			end = str.length() + end; // remember end is negative
		}
		if (start < 0) {
			start = str.length() + start; // remember start is negative
		}

		// check length next
		if (end > str.length()) {
			end = str.length();
		}

		// if start is greater than end, return ""
		if (start > end) {
			return EMPTY;
		}

		if (start < 0) {
			start = 0;
		}
		if (end < 0) {
			end = 0;
		}

		return str.substring(start, end);
	}

	/**
	 * <p>
	 * Gets the substring after the first occurrence of a separator. The separator
	 * is not returned.
	 * </p>
	 *
	 * <p>
	 * A {@code null} string input will return {@code null}. An empty ("") string
	 * input will return the empty string.
	 *
	 * <p>
	 * If nothing is found, the empty string is returned.
	 * </p>
	 *
	 * <pre>
	 * Strings.substringAfter(null, *)      = null
	 * Strings.substringAfter("", *)        = ""
	 * Strings.substringAfter("abc", 'a')   = "bc"
	 * Strings.substringAfter("abcba", 'b') = "cba"
	 * Strings.substringAfter("abc", 'c')   = ""
	 * Strings.substringAfter("abc", 'd')   = ""
	 * Strings.substringAfter(" abc", 32)   = "abc"
	 * </pre>
	 *
	 * @param str       the String to get a substring from, may be null
	 * @param separator the character (Unicode code point) to search.
	 * @return the substring after the first occurrence of the separator,
	 *         {@code null} if null String input
	 */
	public static String substringAfter(
			final String str, final int separator)
	{
		if (isEmpty(str)) {
			return str;
		}
		final int pos = str.indexOf(separator);
		if (pos == INDEX_NOT_FOUND) {
			return EMPTY;
		}
		return str.substring(pos + 1);
	}

	/**
	 * <p>
	 * Gets the substring after the first occurrence of a separator. The separator
	 * is not returned.
	 * </p>
	 *
	 * <p>
	 * A {@code null} string input will return {@code null}. An empty ("") string
	 * input will return the empty string. A {@code null} separator will return the
	 * empty string if the input string is not {@code null}.
	 * </p>
	 *
	 * <p>
	 * If nothing is found, the empty string is returned.
	 * </p>
	 *
	 * <pre>
	 * Strings.substringAfter(null, *)      = null
	 * Strings.substringAfter("", *)        = ""
	 * Strings.substringAfter(*, null)      = ""
	 * Strings.substringAfter("abc", "a")   = "bc"
	 * Strings.substringAfter("abcba", "b") = "cba"
	 * Strings.substringAfter("abc", "c")   = ""
	 * Strings.substringAfter("abc", "d")   = ""
	 * Strings.substringAfter("abc", "")    = "abc"
	 * </pre>
	 *
	 * @param str       the String to get a substring from, may be null
	 * @param separator the String to search for, may be null
	 * @return the substring after the first occurrence of the separator,
	 *         {@code null} if null String input
	 */
	public static String substringAfter(
			final String str, final String separator)
	{
		if (isEmpty(str)) {
			return str;
		}
		if (separator == null) {
			return EMPTY;
		}
		final int pos = str.indexOf(separator);
		if (pos == INDEX_NOT_FOUND) {
			return EMPTY;
		}
		return str.substring(pos + separator.length());
	}

	/**
	 * <p>
	 * Gets the substring after the last occurrence of a separator. The separator is
	 * not returned.
	 * </p>
	 *
	 * <p>
	 * A {@code null} string input will return {@code null}. An empty ("") string
	 * input will return the empty string.
	 *
	 * <p>
	 * If nothing is found, the empty string is returned.
	 * </p>
	 *
	 * <pre>
	 * Strings.substringAfterLast(null, *)      = null
	 * Strings.substringAfterLast("", *)        = ""
	 * Strings.substringAfterLast("abc", 'a')   = "bc"
	 * Strings.substringAfterLast(" bc", 32)    = "bc"
	 * Strings.substringAfterLast("abcba", 'b') = "a"
	 * Strings.substringAfterLast("abc", 'c')   = ""
	 * Strings.substringAfterLast("a", 'a')     = ""
	 * Strings.substringAfterLast("a", 'z')     = ""
	 * </pre>
	 *
	 * @param str       the String to get a substring from, may be null
	 * @param separator the character (Unicode code point) to search.
	 * @return the substring after the last occurrence of the separator,
	 *         {@code null} if null String input
	 */
	public static String substringAfterLast(
			final String str, final int separator)
	{
		if (isEmpty(str)) {
			return str;
		}
		final int pos = str.lastIndexOf(separator);
		if (pos == INDEX_NOT_FOUND || pos == str.length() - 1) {
			return EMPTY;
		}
		return str.substring(pos + 1);
	}

	/**
	 * <p>
	 * Gets the substring after the last occurrence of a separator. The separator is
	 * not returned.
	 * </p>
	 *
	 * <p>
	 * A {@code null} string input will return {@code null}. An empty ("") string
	 * input will return the empty string. An empty or {@code null} separator will
	 * return the empty string if the input string is not {@code null}.
	 * </p>
	 *
	 * <p>
	 * If nothing is found, the empty string is returned.
	 * </p>
	 *
	 * <pre>
	 * Strings.substringAfterLast(null, *)      = null
	 * Strings.substringAfterLast("", *)        = ""
	 * Strings.substringAfterLast(*, "")        = ""
	 * Strings.substringAfterLast(*, null)      = ""
	 * Strings.substringAfterLast("abc", "a")   = "bc"
	 * Strings.substringAfterLast("abcba", "b") = "a"
	 * Strings.substringAfterLast("abc", "c")   = ""
	 * Strings.substringAfterLast("a", "a")     = ""
	 * Strings.substringAfterLast("a", "z")     = ""
	 * </pre>
	 *
	 * @param str       the String to get a substring from, may be null
	 * @param separator the String to search for, may be null
	 * @return the substring after the last occurrence of the separator,
	 *         {@code null} if null String input
	 */
	public static String substringAfterLast(
			final String str, final String separator)
	{
		if (isEmpty(str)) {
			return str;
		}
		if (isEmpty(separator)) {
			return EMPTY;
		}
		final int pos = str.lastIndexOf(separator);
		if (pos == INDEX_NOT_FOUND || pos == str.length() - separator.length()) {
			return EMPTY;
		}
		return str.substring(pos + separator.length());
	}

	/**
	 * <p>
	 * Gets the substring before the first occurrence of a separator. The separator
	 * is not returned.
	 * </p>
	 *
	 * <p>
	 * A {@code null} string input will return {@code null}. An empty ("") string
	 * input will return the empty string.
	 * </p>
	 *
	 * <p>
	 * If nothing is found, the string input is returned.
	 * </p>
	 *
	 * <pre>
	 * Strings.substringBefore(null, *)      = null
	 * Strings.substringBefore("", *)        = ""
	 * Strings.substringBefore("abc", 'a')   = ""
	 * Strings.substringBefore("abcba", 'b') = "a"
	 * Strings.substringBefore("abc", 'c')   = "ab"
	 * Strings.substringBefore("abc", 'd')   = "abc"
	 * </pre>
	 *
	 * @param str       the String to get a substring from, may be null
	 * @param separator the character (Unicode code point) to search.
	 * @return the substring before the first occurrence of the separator,
	 *         {@code null} if null String input
	 */
	public static String substringBefore(
			final String str, final int separator)
	{
		if (isEmpty(str)) {
			return str;
		}
		final int pos = str.indexOf(separator);
		if (pos == INDEX_NOT_FOUND) {
			return str;
		}
		return str.substring(0, pos);
	}

	/**
	 * <p>
	 * Gets the substring before the first occurrence of a separator. The separator
	 * is not returned.
	 * </p>
	 *
	 * <p>
	 * A {@code null} string input will return {@code null}. An empty ("") string
	 * input will return the empty string. A {@code null} separator will return the
	 * input string.
	 * </p>
	 *
	 * <p>
	 * If nothing is found, the string input is returned.
	 * </p>
	 *
	 * <pre>
	 * Strings.substringBefore(null, *)      = null
	 * Strings.substringBefore("", *)        = ""
	 * Strings.substringBefore("abc", "a")   = ""
	 * Strings.substringBefore("abcba", "b") = "a"
	 * Strings.substringBefore("abc", "c")   = "ab"
	 * Strings.substringBefore("abc", "d")   = "abc"
	 * Strings.substringBefore("abc", "")    = ""
	 * Strings.substringBefore("abc", null)  = "abc"
	 * </pre>
	 *
	 * @param str       the String to get a substring from, may be null
	 * @param separator the String to search for, may be null
	 * @return the substring before the first occurrence of the separator,
	 *         {@code null} if null String input
	 */
	public static String substringBefore(
			final String str, final String separator)
	{
		if (isEmpty(str) || separator == null) {
			return str;
		}
		if (separator.isEmpty()) {
			return EMPTY;
		}
		final int pos = str.indexOf(separator);
		if (pos == INDEX_NOT_FOUND) {
			return str;
		}
		return str.substring(0, pos);
	}

	/**
	 * <p>
	 * Gets the substring before the last occurrence of a separator. The separator
	 * is not returned.
	 * </p>
	 *
	 * <p>
	 * A {@code null} string input will return {@code null}. An empty ("") string
	 * input will return the empty string. An empty or {@code null} separator will
	 * return the input string.
	 * </p>
	 *
	 * <p>
	 * If nothing is found, the string input is returned.
	 * </p>
	 *
	 * <pre>
	 * Strings.substringBeforeLast(null, *)      = null
	 * Strings.substringBeforeLast("", *)        = ""
	 * Strings.substringBeforeLast("abcba", "b") = "abc"
	 * Strings.substringBeforeLast("abc", "c")   = "ab"
	 * Strings.substringBeforeLast("a", "a")     = ""
	 * Strings.substringBeforeLast("a", "z")     = "a"
	 * Strings.substringBeforeLast("a", null)    = "a"
	 * Strings.substringBeforeLast("a", "")      = "a"
	 * </pre>
	 *
	 * @param str       the String to get a substring from, may be null
	 * @param separator the String to search for, may be null
	 * @return the substring before the last occurrence of the separator,
	 *         {@code null} if null String input
	 */
	public static String substringBeforeLast(
			final String str, final String separator)
	{
		if (isEmpty(str) || isEmpty(separator)) {
			return str;
		}
		final int pos = str.lastIndexOf(separator);
		if (pos == INDEX_NOT_FOUND) {
			return str;
		}
		return str.substring(0, pos);
	}

	/**
	 * <p>
	 * Gets the String that is nested in between two instances of the same String.
	 * </p>
	 *
	 * <p>
	 * A {@code null} input String returns {@code null}. A {@code null} tag returns
	 * {@code null}.
	 * </p>
	 *
	 * <pre>
	 * Strings.substringBetween(null, *)            = null
	 * Strings.substringBetween("", "")             = ""
	 * Strings.substringBetween("", "tag")          = null
	 * Strings.substringBetween("tagabctag", null)  = null
	 * Strings.substringBetween("tagabctag", "")    = ""
	 * Strings.substringBetween("tagabctag", "tag") = "abc"
	 * </pre>
	 *
	 * @param str the String containing the substring, may be null
	 * @param tag the String before and after the substring, may be null
	 * @return the substring, {@code null} if no match
	 */
	public static String substringBetween(
			final String str, final String tag)
	{
		return substringBetween(str, tag, tag);
	}

	/**
	 * <p>
	 * Gets the String that is nested in between two Strings. Only the first match
	 * is returned.
	 * </p>
	 *
	 * <p>
	 * A {@code null} input String returns {@code null}. A {@code null} open/close
	 * returns {@code null} (no match). An empty ("") open and close returns an
	 * empty string.
	 * </p>
	 *
	 * <pre>
	 * Strings.substringBetween("wx[b]yz", "[", "]") = "b"
	 * Strings.substringBetween(null, *, *)          = null
	 * Strings.substringBetween(*, null, *)          = null
	 * Strings.substringBetween(*, *, null)          = null
	 * Strings.substringBetween("", "", "")          = ""
	 * Strings.substringBetween("", "", "]")         = null
	 * Strings.substringBetween("", "[", "]")        = null
	 * Strings.substringBetween("yabcz", "", "")     = ""
	 * Strings.substringBetween("yabcz", "y", "z")   = "abc"
	 * Strings.substringBetween("yabczyabcz", "y", "z")   = "abc"
	 * </pre>
	 *
	 * @param str   the String containing the substring, may be null
	 * @param open  the String before the substring, may be null
	 * @param close the String after the substring, may be null
	 * @return the substring, {@code null} if no match
	 */
	public static String substringBetween(
			final String str, final String open, final String close)
	{
		if (Stream.of(str, open, close).anyMatch(Objects::isNull)) {
			return null;
		}
		final int start = str.indexOf(open);
		if (start != INDEX_NOT_FOUND) {
			final int end = str.indexOf(close, start + open.length());
			if (end != INDEX_NOT_FOUND) {
				return str.substring(start + open.length(), end);
			}
		}
		return null;
	}

	/**
	 * <p>
	 * Searches a String for substrings delimited by a start and end tag, returning
	 * all matching substrings in an array.
	 * </p>
	 *
	 * <p>
	 * A {@code null} input String returns {@code null}. A {@code null} open/close
	 * returns {@code null} (no match). An empty ("") open/close returns
	 * {@code null} (no match).
	 * </p>
	 *
	 * <pre>
	 * Strings.substringsBetween("[a][b][c]", "[", "]") = ["a","b","c"]
	 * Strings.substringsBetween(null, *, *)            = null
	 * Strings.substringsBetween(*, null, *)            = null
	 * Strings.substringsBetween(*, *, null)            = null
	 * Strings.substringsBetween("", "[", "]")          = []
	 * </pre>
	 *
	 * @param str   the String containing the substrings, null returns null, empty
	 *              returns empty
	 * @param open  the String identifying the start of the substring, empty returns
	 *              null
	 * @param close the String identifying the end of the substring, empty returns
	 *              null
	 * @return a String Array of substrings, or {@code null} if no match
	 */
	public static String[] substringsBetween(
			final String str, final String open, final String close)
	{
		if (str == null || isEmpty(open) || isEmpty(close)) {
			return null;
		}
		final int strLen = str.length();
		if (strLen == 0) {
			return EMPTY_STRING_ARRAY;
		}
		final int closeLen = close.length();
		final int openLen = open.length();
		final List<String> list = new ArrayList<>();
		int pos = 0;
		while (pos < strLen - closeLen) {
			int start = str.indexOf(open, pos);
			if (start < 0) {
				break;
			}
			start += openLen;
			final int end = str.indexOf(close, start);
			if (end < 0) {
				break;
			}
			list.add(str.substring(start, end));
			pos = end + closeLen;
		}
		if (list.isEmpty()) {
			return null;
		}
		return list.toArray(EMPTY_STRING_ARRAY);
	}

	/**
	 * <p>Returns padding using the specified delimiter repeated
	 * to a given length.</p>
	 *
	 * <pre>
	 * StringUtils.repeat('e', 0)  = ""
	 * StringUtils.repeat('e', 3)  = "eee"
	 * StringUtils.repeat('e', -2) = ""
	 * </pre>
	 *
	 * <p>Note: this method does not support padding with
	 * <a href="http://www.unicode.org/glossary/#supplementary_character">Unicode Supplementary Characters</a>
	 * as they require a pair of {@code char}s to be represented.
	 * If you are needing to support full I18N of your applications
	 * consider using {@link #repeat(String, int)} instead.
	 * </p>
	 *
	 * @param ch  character to repeat
	 * @param repeat  number of times to repeat char, negative treated as zero
	 * @return String with repeated character
	 * @see #repeat(String, int)
	 */
	public static String repeat(
			final char ch, final int repeat)
	{
		if (repeat <= 0) {
			return EMPTY;
		}
		final char[] buf = new char[repeat];
		Arrays.fill(buf, ch);
		return new String(buf);
	}

	/**
	 * <p>Repeat a String {@code repeat} times to form a
	 * new String.</p>
	 *
	 * <pre>
	 * StringUtils.repeat(null, 2) = null
	 * StringUtils.repeat("", 0)   = ""
	 * StringUtils.repeat("", 2)   = ""
	 * StringUtils.repeat("a", 3)  = "aaa"
	 * StringUtils.repeat("ab", 2) = "abab"
	 * StringUtils.repeat("a", -2) = ""
	 * </pre>
	 *
	 * @param str  the String to repeat, may be null
	 * @param repeat  number of times to repeat str, negative treated as zero
	 * @return a new String consisting of the original String repeated,
	 *  {@code null} if null String input
	 */
	public static String repeat(
			final String str, final int repeat)
	{
		if (str == null) return null;
		if (repeat <= 0) return EMPTY;
		final int inputLength = str.length();
		if (repeat == 1 || inputLength == 0) return str;
		if (inputLength == 1 && repeat <= PAD_LIMIT) return repeat(str.charAt(0), repeat);

		final int outputLength = inputLength * repeat;
		switch (inputLength) {
			case 1 :
				return repeat(str.charAt(0), repeat);
			case 2 :
				final char ch0 = str.charAt(0);
				final char ch1 = str.charAt(1);
				final char[] output2 = new char[outputLength];
				for (int i = repeat * 2 - 2; i >= 0; i -= 2) {
					output2[i] = ch0;
					output2[i + 1] = ch1;
				}
				return new String(output2);
			default :
				final StringBuilder buf = new StringBuilder(outputLength);
				for (int i = 0; i < repeat; i++) {
					buf.append(str);
				}
				return buf.toString();
		}
	}

	/**
	 * <p>Right pad a String with spaces (' ').</p>
	 *
	 * <p>The String is padded to the size of {@code size}.</p>
	 *
	 * <pre>
	 * StringUtils.rightPad(null, *)   = null
	 * StringUtils.rightPad("", 3)     = "   "
	 * StringUtils.rightPad("bat", 3)  = "bat"
	 * StringUtils.rightPad("bat", 5)  = "bat  "
	 * StringUtils.rightPad("bat", 1)  = "bat"
	 * StringUtils.rightPad("bat", -1) = "bat"
	 * </pre>
	 *
	 * @param str  the String to pad out, may be null
	 * @param size  the size to pad to
	 * @return right padded String or original String if no padding is necessary,
	 *  {@code null} if null String input
	 */
	public static String rightPad(
			final String str, final int size)
	{
		return rightPad(str, size, ' ');
	}

	/**
	 * <p>Right pad a String with a specified character.</p>
	 *
	 * <p>The String is padded to the size of {@code size}.</p>
	 *
	 * <pre>
	 * StringUtils.rightPad(null, *, *)     = null
	 * StringUtils.rightPad("", 3, 'z')     = "zzz"
	 * StringUtils.rightPad("bat", 3, 'z')  = "bat"
	 * StringUtils.rightPad("bat", 5, 'z')  = "batzz"
	 * StringUtils.rightPad("bat", 1, 'z')  = "bat"
	 * StringUtils.rightPad("bat", -1, 'z') = "bat"
	 * </pre>
	 *
	 * @param str  the String to pad out, may be null
	 * @param size  the size to pad to
	 * @param padChar  the character to pad with
	 * @return right padded String or original String if no padding is necessary,
	 *  {@code null} if null String input
	 * @since 2.0
	 */
	public static String rightPad(
			final String str, final int size, final char padChar)
	{
		if (str == null) {
			return null;
		}
		final int pads = size - str.length();
		if (pads <= 0) {
			return str; // returns original String when possible
		}
		if (pads > PAD_LIMIT) {
			return rightPad(str, size, String.valueOf(padChar));
		}
		return str.concat(repeat(padChar, pads));
	}

	/**
	 * <p>Right pad a String with a specified String.</p>
	 *
	 * <p>The String is padded to the size of {@code size}.</p>
	 *
	 * <pre>
	 * StringUtils.rightPad(null, *, *)      = null
	 * StringUtils.rightPad("", 3, "z")      = "zzz"
	 * StringUtils.rightPad("bat", 3, "yz")  = "bat"
	 * StringUtils.rightPad("bat", 5, "yz")  = "batyz"
	 * StringUtils.rightPad("bat", 8, "yz")  = "batyzyzy"
	 * StringUtils.rightPad("bat", 1, "yz")  = "bat"
	 * StringUtils.rightPad("bat", -1, "yz") = "bat"
	 * StringUtils.rightPad("bat", 5, null)  = "bat  "
	 * StringUtils.rightPad("bat", 5, "")    = "bat  "
	 * </pre>
	 *
	 * @param str  the String to pad out, may be null
	 * @param size  the size to pad to
	 * @param padStr  the String to pad with, null or empty treated as single space
	 * @return right padded String or original String if no padding is necessary,
	 *  {@code null} if null String input
	 */
	public static String rightPad(
			final String str, final int size, String padStr)
	{
		if (str == null) {
			return null;
		}
		if (isEmpty(padStr)) {
			padStr = SPACE;
		}
		final int padLen = padStr.length();
		final int strLen = str.length();
		final int pads = size - strLen;
		if (pads <= 0) {
			return str; // returns original String when possible
		}
		if (padLen == 1 && pads <= PAD_LIMIT) {
			return rightPad(str, size, padStr.charAt(0));
		}

		if (pads == padLen) {
			return str.concat(padStr);
		}
		if (pads < padLen) {
			return str.concat(padStr.substring(0, pads));
		}
		final char[] padding = new char[pads];
		final char[] padChars = padStr.toCharArray();
		for (int i = 0; i < pads; i++) {
			padding[i] = padChars[i % padLen];
		}
		return str.concat(new String(padding));
	}

	public static String trim(
			final String str)
	{
		return str == null ? null : str.trim();
	}

	public static String trimToNull(
			final String str)
	{
		String trimmed = trim(str);
		return defaultIfEmpty(trimmed, null);
	}

	public static String trimToEmpty(
			final String str)
	{
		String trimmed = trim(str);
		return defaultIfEmpty(trimmed, EMPTY);
	}

}
