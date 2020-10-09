package com.xknower.utils.excel;

import java.util.Collection;
import java.util.Iterator;
import java.util.Map;
import java.util.regex.Pattern;

/**
 * <p>This class assists in validating arguments. The validation methods are
 * based along the following principles:
 * <ul>
 * <li>An invalid {@code null} argument causes a {@link NullPointerException}.</li>
 * <li>A non-{@code null} argument causes an {@link IllegalArgumentException}.</li>
 * <li>An invalid index into an array/collection/map/string causes an {@link IndexOutOfBoundsException}.</li>
 * </ul>
 *
 * <p>All exceptions messages are
 * <a href="http://docs.oracle.com/javase/1.5.0/docs/api/java/util/Formatter.html#syntax">format strings</a>
 * as defined by the Java platform. For example:</p>
 *
 * <pre>
 * Validate.isTrue(i &gt; 0, "The value must be greater than zero: %d", i);
 * Validate.notNull(surname, "The surname must not be %s", null);
 * </pre>
 *
 * <p>#ThreadSafe#</p>
 *
 * @version $Id: Validate.java 1606051 2014-06-27 12:22:17Z ggregory $
 * @see java.lang.String#format(String, Object...)
 * @since 2.0
 */
public class Validate {

    private static final String DEFAULT_IS_NULL_EX_MESSAGE = "The validated object is null";
    private static final String DEFAULT_NOT_EMPTY_ARRAY_EX_MESSAGE = "The validated array is empty";
    private static final String DEFAULT_NOT_EMPTY_CHAR_SEQUENCE_EX_MESSAGE =
            "The validated character sequence is empty";
    private static final String DEFAULT_NOT_EMPTY_COLLECTION_EX_MESSAGE = "The validated collection is empty";
    private static final String DEFAULT_NOT_EMPTY_MAP_EX_MESSAGE = "The validated map is empty";

    /**
     * Constructor. This class should not normally be instantiated.
     */
    public Validate() {
        super();
    }

    /**
     * <p>Validate that the specified argument is not {@code null};
     * otherwise throwing an exception.
     *
     * <pre>Validate.notNull(myObject, "The object must not be null");</pre>
     *
     * <p>The message of the exception is &quot;The validated object is
     * null&quot;.</p>
     *
     * @param <T>    the object type
     * @param object the object to check
     * @return the validated object (never {@code null} for method chaining)
     * @throws NullPointerException if the object is {@code null}
     * @see #notNull(Object, String, Object...)
     */
    public static <T> T notNull(final T object) {
        return notNull(object, DEFAULT_IS_NULL_EX_MESSAGE);
    }

    /**
     * <p>Validate that the specified argument is not {@code null};
     * otherwise throwing an exception with the specified message.
     *
     * <pre>Validate.notNull(myObject, "The object must not be null");</pre>
     *
     * @param <T>     the object type
     * @param object  the object to check
     * @param message the {@link String#format(String, Object...)} exception message if invalid, not null
     * @param values  the optional values for the formatted exception message
     * @return the validated object (never {@code null} for method chaining)
     * @throws NullPointerException if the object is {@code null}
     * @see #notNull(Object)
     */
    public static <T> T notNull(final T object, final String message, final Object... values) {
        if (object == null) {
            throw new NullPointerException(String.format(message, values));
        }
        return object;
    }

    /**
     * <p>Validate that the specified argument array is neither {@code null}
     * nor a length of zero (no elements); otherwise throwing an exception
     * with the specified message.
     *
     * <pre>Validate.notEmpty(myArray, "The array must not be empty");</pre>
     *
     * @param <T>     the array type
     * @param array   the array to check, validated not null by this method
     * @param message the {@link String#format(String, Object...)} exception message if invalid, not null
     * @param values  the optional values for the formatted exception message, null array not recommended
     * @return the validated array (never {@code null} method for chaining)
     * @throws NullPointerException     if the array is {@code null}
     * @throws IllegalArgumentException if the array is empty
     * @see #notEmpty(Object[])
     */
    public static <T> T[] notEmpty(final T[] array, final String message, final Object... values) {
        if (array == null) {
            throw new NullPointerException(String.format(message, values));
        }
        if (array.length == 0) {
            throw new IllegalArgumentException(String.format(message, values));
        }
        return array;
    }

    /**
     * <p>Validate that the specified argument array is neither {@code null}
     * nor a length of zero (no elements); otherwise throwing an exception.
     *
     * <pre>Validate.notEmpty(myArray);</pre>
     *
     * <p>The message in the exception is &quot;The validated array is
     * empty&quot;.
     *
     * @param <T>   the array type
     * @param array the array to check, validated not null by this method
     * @return the validated array (never {@code null} method for chaining)
     * @throws NullPointerException     if the array is {@code null}
     * @throws IllegalArgumentException if the array is empty
     * @see #notEmpty(Object[], String, Object...)
     */
    public static <T> T[] notEmpty(final T[] array) {
        return notEmpty(array, DEFAULT_NOT_EMPTY_ARRAY_EX_MESSAGE);
    }

    /**
     * <p>Validate that the specified argument collection is neither {@code null}
     * nor a size of zero (no elements); otherwise throwing an exception
     * with the specified message.
     *
     * <pre>Validate.notEmpty(myCollection, "The collection must not be empty");</pre>
     *
     * @param <T>        the collection type
     * @param collection the collection to check, validated not null by this method
     * @param message    the {@link String#format(String, Object...)} exception message if invalid, not null
     * @param values     the optional values for the formatted exception message, null array not recommended
     * @return the validated collection (never {@code null} method for chaining)
     * @throws NullPointerException     if the collection is {@code null}
     * @throws IllegalArgumentException if the collection is empty
     * @see #notEmpty(Object[])
     */
    public static <T extends Collection<?>> T notEmpty(final T collection, final String message, final Object... values) {
        if (collection == null) {
            throw new NullPointerException(String.format(message, values));
        }
        if (collection.isEmpty()) {
            throw new IllegalArgumentException(String.format(message, values));
        }
        return collection;
    }

    /**
     * <p>Validate that the specified argument collection is neither {@code null}
     * nor a size of zero (no elements); otherwise throwing an exception.
     *
     * <pre>Validate.notEmpty(myCollection);</pre>
     *
     * <p>The message in the exception is &quot;The validated collection is
     * empty&quot;.</p>
     *
     * @param <T>        the collection type
     * @param collection the collection to check, validated not null by this method
     * @return the validated collection (never {@code null} method for chaining)
     * @throws NullPointerException     if the collection is {@code null}
     * @throws IllegalArgumentException if the collection is empty
     * @see #notEmpty(Collection, String, Object...)
     */
    public static <T extends Collection<?>> T notEmpty(final T collection) {
        return notEmpty(collection, DEFAULT_NOT_EMPTY_COLLECTION_EX_MESSAGE);
    }

    /**
     * <p>Validate that the specified argument map is neither {@code null}
     * nor a size of zero (no elements); otherwise throwing an exception
     * with the specified message.
     *
     * <pre>Validate.notEmpty(myMap, "The map must not be empty");</pre>
     *
     * @param <T>     the map type
     * @param map     the map to check, validated not null by this method
     * @param message the {@link String#format(String, Object...)} exception message if invalid, not null
     * @param values  the optional values for the formatted exception message, null array not recommended
     * @return the validated map (never {@code null} method for chaining)
     * @throws NullPointerException     if the map is {@code null}
     * @throws IllegalArgumentException if the map is empty
     * @see #notEmpty(Object[])
     */
    public static <T extends Map<?, ?>> T notEmpty(final T map, final String message, final Object... values) {
        if (map == null) {
            throw new NullPointerException(String.format(message, values));
        }
        if (map.isEmpty()) {
            throw new IllegalArgumentException(String.format(message, values));
        }
        return map;
    }

    /**
     * <p>Validate that the specified argument map is neither {@code null}
     * nor a size of zero (no elements); otherwise throwing an exception.
     *
     * <pre>Validate.notEmpty(myMap);</pre>
     *
     * <p>The message in the exception is &quot;The validated map is
     * empty&quot;.</p>
     *
     * @param <T> the map type
     * @param map the map to check, validated not null by this method
     * @return the validated map (never {@code null} method for chaining)
     * @throws NullPointerException     if the map is {@code null}
     * @throws IllegalArgumentException if the map is empty
     * @see #notEmpty(Map, String, Object...)
     */
    public static <T extends Map<?, ?>> T notEmpty(final T map) {
        return notEmpty(map, DEFAULT_NOT_EMPTY_MAP_EX_MESSAGE);
    }

    /**
     * <p>Validate that the specified argument character sequence is
     * neither {@code null} nor a length of zero (no characters);
     * otherwise throwing an exception with the specified message.
     *
     * <pre>Validate.notEmpty(myString, "The string must not be empty");</pre>
     *
     * @param <T>     the character sequence type
     * @param chars   the character sequence to check, validated not null by this method
     * @param message the {@link String#format(String, Object...)} exception message if invalid, not null
     * @param values  the optional values for the formatted exception message, null array not recommended
     * @return the validated character sequence (never {@code null} method for chaining)
     * @throws NullPointerException     if the character sequence is {@code null}
     * @throws IllegalArgumentException if the character sequence is empty
     * @see #notEmpty(CharSequence)
     */
    public static <T extends CharSequence> T notEmpty(final T chars, final String message, final Object... values) {
        if (chars == null) {
            throw new NullPointerException(String.format(message, values));
        }
        if (chars.length() == 0) {
            throw new IllegalArgumentException(String.format(message, values));
        }
        return chars;
    }

    /**
     * <p>Validate that the specified argument character sequence is
     * neither {@code null} nor a length of zero (no characters);
     * otherwise throwing an exception with the specified message.
     *
     * <pre>Validate.notEmpty(myString);</pre>
     *
     * <p>The message in the exception is &quot;The validated
     * character sequence is empty&quot;.</p>
     *
     * @param <T>   the character sequence type
     * @param chars the character sequence to check, validated not null by this method
     * @return the validated character sequence (never {@code null} method for chaining)
     * @throws NullPointerException     if the character sequence is {@code null}
     * @throws IllegalArgumentException if the character sequence is empty
     * @see #notEmpty(CharSequence, String, Object...)
     */
    public static <T extends CharSequence> T notEmpty(final T chars) {
        return notEmpty(chars, DEFAULT_NOT_EMPTY_CHAR_SEQUENCE_EX_MESSAGE);
    }

    public static <T extends CharSequence> T notBlank(final T chars, final String message, final Object... values) {
        if (chars == null) {
            throw new NullPointerException(String.format(message, values));
        }
        if (StringUtils.isBlank(chars)) {
            throw new IllegalArgumentException(String.format(message, values));
        }
        return chars;
    }
}
