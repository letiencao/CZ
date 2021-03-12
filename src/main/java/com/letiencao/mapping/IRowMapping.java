package com.letiencao.mapping;

import java.sql.ResultSet;

public interface IRowMapping<T> {
	T mapRow(ResultSet resultSet);
}
