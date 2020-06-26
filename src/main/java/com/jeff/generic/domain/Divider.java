package com.jeff.generic.domain;

import java.util.Collection;

public interface Divider<T,U,R> {
	Collection<R> divide(T t, U u);
}
