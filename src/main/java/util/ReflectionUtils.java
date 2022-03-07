package util;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Modifier;
import java.lang.reflect.UndeclaredThrowableException;
import java.util.Map;
import java.util.Objects;

import org.hibernate.internal.util.collections.ConcurrentReferenceHashMap;

import lombok.NonNull;

public abstract class ReflectionUtils {


	private static final Field[] EMPTY_FIELD_ARRAY = new Field[0];

	/**
	 * Cache for {@link Class#getDeclaredFields()}, allowing for fast iteration.
	 */
	private static final Map<Class<?>, Field[]> declaredFieldsCache = new ConcurrentReferenceHashMap<>(256);


	/**
	 * Set the {@linkplain Field field} with the given {@code name} on the
	 * provided {@code targetObject} to the supplied {@code value}.
	 * <p>This method delegates to {@link #setField(Object, String, Object, Class)},
	 * supplying {@code null} for the {@code type} argument.
	 * @param targetObject the target object on which to set the field; never {@code null}
	 * @param name the name of the field to set; never {@code null}
	 * @param value the value to set
	 * @return targetObject
	 */
	public static <R> R setField(@NonNull R targetObject, @NonNull String name, Object value) {
		return setField(targetObject, name, value, null);
	}

	/**
	 * Set the {@linkplain Field field} with the given {@code name}/{@code type}
	 * on the provided {@code targetObject} to the supplied {@code value}.
	 * <p>This method delegates to {@link #setField(Object, Class, String, Object, Class)},
	 * supplying {@code null} for the {@code targetClass} argument.
	 * @param targetObject the target object on which to set the field; never {@code null}
	 * @param name the name of the field to set; may be {@code null} if
	 * {@code type} is specified
	 * @param value the value to set
	 * @param type the type of the field to set; may be {@code null} if
	 * {@code name} is specified
	 */
	public static <R> R setField(@NonNull R targetObject, @NonNull String name, Object value, Class<?> type) {
		Class<?> targetClass = targetObject.getClass();

		Field field = findField(targetClass, name, type);
		if (field == null) {
			throw new IllegalArgumentException(String.format(
					"Could not find field '%s' of type [%s] on %s or target class [%s]", name, type,
					safeToString(targetObject), targetClass));
		}
		makeAccessible(field);
		setField(field, targetObject, value);
		return targetObject;
	}

	/**
	 * Make the given field accessible, explicitly setting it accessible if
	 * necessary. The {@code setAccessible(true)} method is only called
	 * when actually necessary, to avoid unnecessary conflicts.
	 * @param field the field to make accessible
	 * @see java.lang.reflect.Field#setAccessible
	 */
	public static void makeAccessible(Field field) {
		if ((!Modifier.isPublic(field.getModifiers()) ||
				!Modifier.isPublic(field.getDeclaringClass().getModifiers()) ||
				Modifier.isFinal(field.getModifiers())) && !field.isAccessible()) {
			field.setAccessible(true);
		}
	}

	/**
	 * Set the field represented by the supplied {@linkplain Field field object} on
	 * the specified {@linkplain Object target object} to the specified {@code value}.
	 * <p>In accordance with {@link Field#set(Object, Object)} semantics, the new value
	 * is automatically unwrapped if the underlying field has a primitive type.
	 * <p>This method does not support setting {@code static final} fields.
	 * <p>Thrown exceptions are handled via a call to {@link #handleReflectionException(Exception)}.
	 * @param field the field to set
	 * @param target the target object on which to set the field
	 * (or {@code null} for a static field)
	 * @param value the value to set (may be {@code null})
	 */
	public static void setField(@NonNull Field field, Object target, Object value) {
		try {
			field.set(target, value);
		}
		catch (IllegalAccessException ex) {
			handleReflectionException(ex);
		}
	}

	/**
	 * Attempt to find a {@link Field field} on the supplied {@link Class} with the
	 * supplied {@code name} and/or {@link Class type}. Searches all superclasses
	 * up to {@link Object}.
	 * @param clazz the class to introspect
	 * @param name the name of the field (may be {@code null} if type is specified)
	 * @param type the type of the field (may be {@code null} if name is specified)
	 * @return the corresponding Field object, or {@code null} if not found
	 */
	public static Field findField(Class<?> clazz, String name, Class<?> type) {
		Objects.requireNonNull(clazz, "Class must not be null");
		if (name == null && type == null) {
			throw new IllegalArgumentException("Either `name` or `type` of the field must be specified");
		}
		Class<?> searchType = clazz;
		while (Object.class != searchType && searchType != null) {
			Field[] fields = getDeclaredFields(searchType);
			for (Field field : fields) {
				if ((name == null || name.equals(field.getName())) &&
						(type == null || type.equals(field.getType()))) {
					return field;
				}
			}
			searchType = searchType.getSuperclass();
		}
		return null;
	}

	/**
	 * This variant retrieves {@link Class#getDeclaredFields()} from a local cache
	 * in order to avoid defensive array copying.
	 * @param clazz the class to introspect
	 * @return the cached array of fields
	 * @throws IllegalStateException if introspection fails
	 * @see Class#getDeclaredFields()
	 */
	private static Field[] getDeclaredFields(Class<?> clazz) {
		Objects.requireNonNull(clazz, "Class must not be null");
		Field[] result = declaredFieldsCache.get(clazz);
		if (result == null) {
			try {
				result = clazz.getDeclaredFields();
				declaredFieldsCache.put(clazz, (result.length == 0 ? EMPTY_FIELD_ARRAY : result));
			}
			catch (Throwable ex) {
				throw new IllegalStateException("Failed to introspect Class [" + clazz.getName() +
						"] from ClassLoader [" + clazz.getClassLoader() + "]", ex);
			}
		}
		return result;
	}

	// Exception handling

	/**
	 * Handle the given reflection exception.
	 * <p>Should only be called if no checked exception is expected to be thrown
	 * by a target method, or if an error occurs while accessing a method or field.
	 * <p>Throws the underlying RuntimeException or Error in case of an
	 * InvocationTargetException with such a root cause. Throws an
	 * IllegalStateException with an appropriate message or
	 * UndeclaredThrowableException otherwise.
	 * @param ex the reflection exception to handle
	 */
	public static void handleReflectionException(Exception ex) {
		if (ex instanceof NoSuchMethodException) {
			throw new IllegalStateException("Method not found: " + ex.getMessage());
		}
		if (ex instanceof IllegalAccessException) {
			throw new IllegalStateException("Could not access method or field: " + ex.getMessage());
		}
		if (ex instanceof InvocationTargetException) {
			handleInvocationTargetException((InvocationTargetException) ex);
		}
		if (ex instanceof RuntimeException) {
			throw (RuntimeException) ex;
		}
		throw new UndeclaredThrowableException(ex);
	}

	/**
	 * Handle the given invocation target exception. Should only be called if no
	 * checked exception is expected to be thrown by the target method.
	 * <p>Throws the underlying RuntimeException or Error in case of such a root
	 * cause. Throws an UndeclaredThrowableException otherwise.
	 * @param ex the invocation target exception to handle
	 */
	public static void handleInvocationTargetException(InvocationTargetException ex) {
		rethrowRuntimeException(ex.getTargetException());
	}

	/**
	 * Rethrow the given {@link Throwable exception}, which is presumably the
	 * <em>target exception</em> of an {@link InvocationTargetException}.
	 * Should only be called if no checked exception is expected to be thrown
	 * by the target method.
	 * <p>Rethrows the underlying exception cast to a {@link RuntimeException} or
	 * {@link Error} if appropriate; otherwise, throws an
	 * {@link UndeclaredThrowableException}.
	 * @param ex the exception to rethrow
	 * @throws RuntimeException the rethrown exception
	 */
	public static void rethrowRuntimeException(Throwable ex) {
		if (ex instanceof RuntimeException) {
			throw (RuntimeException) ex;
		}
		if (ex instanceof Error) {
			throw (Error) ex;
		}
		throw new UndeclaredThrowableException(ex);
	}

	/**
	 * Rethrow the given {@link Throwable exception}, which is presumably the
	 * <em>target exception</em> of an {@link InvocationTargetException}.
	 * Should only be called if no checked exception is expected to be thrown
	 * by the target method.
	 * <p>Rethrows the underlying exception cast to an {@link Exception} or
	 * {@link Error} if appropriate; otherwise, throws an
	 * {@link UndeclaredThrowableException}.
	 * @param ex the exception to rethrow
	 * @throws Exception the rethrown exception (in case of a checked exception)
	 */
	public static void rethrowException(Throwable ex) throws Exception {
		if (ex instanceof Exception) {
			throw (Exception) ex;
		}
		if (ex instanceof Error) {
			throw (Error) ex;
		}
		throw new UndeclaredThrowableException(ex);
	}

	protected static String safeToString(Object target) {
		try {
			return String.format("target object [%s]", target);
		}
		catch (Exception ex) {
			return String.format("target of type [%s] whose toString() method threw [%s]",
					(target != null ? target.getClass().getName() : "unknown"), ex);
		}
	}

	protected static String safeToString(Class<?> clazz) {
		return String.format("target class [%s]", (clazz != null ? clazz.getName() : null));
	}
}

