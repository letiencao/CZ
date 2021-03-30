package com.letiencao.dao.impl;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import com.letiencao.dao.GenericDAO;
import com.letiencao.mapping.IRowMapping;

public class BaseDAO<T> implements GenericDAO<T> {
	public Connection getConnection() {

		try {
			Class.forName("com.mysql.jdbc.Driver");
			String url = "jdbc:mysql://localhost:3306/czone";
			String user = "root";
			String password = "123456789";
			return DriverManager.getConnection(url, user, password);

		} catch (ClassNotFoundException | SQLException e) {
			return null;
		}
	}

	public void setParameter(PreparedStatement preparedStatement, Object... parameters) {
		for (int i = 0; i < parameters.length; i++) {
			Object parameter = parameters[i];
			int index = i + 1;
			try {
				if (parameter instanceof String) {
					System.out.println("String");
					preparedStatement.setString(index, (String) parameter);
				}
				if (parameter instanceof Long) {
					System.out.println("Long");
					preparedStatement.setLong(index, (long) parameter);
				}
				if (parameter instanceof Timestamp) {
					System.out.println("Timestamp");
					preparedStatement.setTimestamp(index, (Timestamp) parameter);
				}
				if (parameter instanceof Boolean) {
					System.out.println("Boolean");
					preparedStatement.setBoolean(index, (boolean) parameter);
				}
				if (parameter instanceof Integer) {
					System.out.println("Int");
					preparedStatement.setInt(index, (int) parameter);
				}
			} catch (SQLException e) {
				System.out.println("Loi parameter");
			}

		}

	}

	@Override
	public List<T> findAll(String sql, IRowMapping<T> rowMapping) {
		List<T> list = new ArrayList<>();
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		try {
			connection = getConnection();
			preparedStatement = connection.prepareStatement(sql);
			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {
				T t = (T) new Object();
				t = rowMapping.mapRow(resultSet);
				list.add(t);
			}
			return list;
		} catch (SQLException e) {
			System.out.println("Failed_FindAll_AbstractDAO_1");
		} finally {
			try {
				if (preparedStatement != null) {
					preparedStatement.close();
				}
				if (resultSet != null) {
					resultSet.close();
				}
			} catch (SQLException e2) {
				System.out.println("Failed_FindAll_AbstractDAO_2");
				return null;
			}
		}

		return list;
	}

	@Override
	public Long insertOne(String sql, Object... parameters) {
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		Long id = -1L;
		try {
			connection = getConnection();
			connection.setAutoCommit(false);
			preparedStatement = connection.prepareStatement(sql,Statement.RETURN_GENERATED_KEYS);
			setParameter(preparedStatement, parameters);
			preparedStatement.executeUpdate();
			ResultSet resultSet = preparedStatement.getGeneratedKeys();
			while(resultSet.next()) {
				id = resultSet.getLong(1);
			}
			connection.commit();
		} catch (SQLException e) {

			try {
				if (connection != null) {
					connection.rollback();
				}
			} catch (SQLException e1) {
				System.out.println("Failed__InsertOne__AbstractDAO__0");
				return -1L;
			}
			System.out.println("Failed__InsertOne__AbstractDAO__1");
			System.out.println(""+e.getMessage());
			return -1L;
		} finally {
			try {
				if (preparedStatement != null) {
					preparedStatement.close();
				}
			} catch (SQLException e2) {
				System.out.println("Failed__InsertOne__AbstractDAO__2");
				return -1L;
			}
		}
		return id;
	}

	@Override
	public boolean update(String sql, Object... parameters) {
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		try {
			connection = getConnection();
			connection.setAutoCommit(false);
			preparedStatement = connection.prepareStatement(sql);
			setParameter(preparedStatement, parameters);
			preparedStatement.executeUpdate();
			connection.commit();
		} catch (SQLException e) {

			try {
				if (connection != null) {
					connection.rollback();
				}
			} catch (SQLException e1) {
				System.out.println("Failed__Update__AbstractDAO__0");
				return false;
			}
			System.out.println("Failed__Update__AbstractDAO__1 : "+e.getMessage());
			return false;
		} finally {
			try {
				if (preparedStatement != null) {
					preparedStatement.close();
				}
			} catch (SQLException e2) {
				System.out.println("Failed__Update__AbstractDAO__2 : "+e2.getMessage());
				return false;
			}
		}
		return true;
	}

	@Override
	public T findOne(String sql, IRowMapping<T> rowMapping, Object... parameters) {
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		try {
			connection = getConnection();
			preparedStatement = connection.prepareStatement(sql);
			setParameter(preparedStatement, parameters);
			resultSet = preparedStatement.executeQuery();
			T t = (T) new Object();
			while (resultSet.next()) {

				t = rowMapping.mapRow(resultSet);
			}
			return t;
		} catch (SQLException | NullPointerException e) {
			
			System.out.println("Failed_FindOne_AbstractDAO_1");
			System.out.println(""+e.getMessage());
		} finally {
			try {
				if (preparedStatement != null) {
					preparedStatement.close();
				}
				if (resultSet != null) {
					resultSet.close();
				}
			} catch (SQLException | NullPointerException e2) {
				System.out.println("Failed_FindOne_AbstractDAO_2");
				return null;
			}
		}

		return null;
	}

	@Override
	public boolean delete(String sql, Object... parameters) {
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		try {
			connection = getConnection();
			connection.setAutoCommit(false);
			preparedStatement = connection.prepareStatement(sql);
			setParameter(preparedStatement, parameters);
			preparedStatement.executeUpdate();
			connection.commit();
		} catch (SQLException e) {

			try {
				if (connection != null) {
					connection.rollback();
				}
			} catch (SQLException e1) {
				System.out.println("Failed__Delete__AbstractDAO__0");
				return false;
			}
			System.out.println("Failed__Delete__AbstractDAO__1");
			return false;
		} finally {
			try {
				if (preparedStatement != null) {
					preparedStatement.close();
				}
			} catch (SQLException e2) {
				System.out.println("Failed__Update__AbstractDAO__2");
				return false;
			}
		}
		return true;
	}
}
