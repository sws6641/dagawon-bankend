package com.bankle.common.util.httpapi.vo;

import java.lang.reflect.Field;

public class ConvertVo {
	public final <T extends ConvertVo> T castVo(Class<T> responseType) {
		T res = null;
		try {
			res = responseType.getDeclaredConstructor().newInstance();
		} catch (Exception e) {
		}
		if (res != null) {
			recusiveRefCopyAll(res, this, responseType);
		}
		return res;
	}

	private static final void recusiveRefCopyAll(Object toBe, Object asIs, Class<?> toBeClass) {
		Field[] toBeFields = toBeClass.getDeclaredFields();
		for (Field toBeField : toBeFields) {
			recusiveRefSet(toBe, asIs, toBeField, asIs.getClass());
		}
		if (toBeClass.getSuperclass() != Object.class) {
			recusiveRefCopyAll(toBe, asIs, toBeClass.getSuperclass());
		}
	}

	private static final void recusiveRefSet(Object toBe, Object asIs, Field toBeField, Class<?> asIsClass) {
		Field[] asIsFields = asIsClass.getDeclaredFields();
		boolean isMatch = false;
		for (Field asIsField : asIsFields) {
			if (toBeField.getName().equals(asIsField.getName()) && toBeField.getType().equals(asIsField.getType())) {
				asIsField.setAccessible(true);
				toBeField.setAccessible(true);
				try {
					toBeField.set(toBe, asIsField.get(asIs));
				} catch (IllegalArgumentException | IllegalAccessException e) {
				}
				isMatch = true;
			}
		}
		if (!isMatch) {
			if (asIsClass.getSuperclass() != Object.class) {
				recusiveRefSet(toBe, asIs, toBeField, asIsClass.getSuperclass());
			}
		}
	}
}
