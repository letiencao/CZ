package com.letiencao.dao;

import java.util.List;

import com.letiencao.mapping.IRowMapping;

public interface GenericDAO<T> {
	List<T> findAll(String sql,IRowMapping<T> rowMapping);
	boolean insertOne(String sql,Object... parameters );
	boolean update (String sql,Object... parameters);
	T findOne(String sql,IRowMapping<T> rowMapping,Object... parameters);

}
