package com.letiencao.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.letiencao.dao.IFriendDAO;
import com.letiencao.mapping.FriendMapping;
import com.letiencao.model.FriendModel;

public class FriendDAO extends BaseDAO<FriendModel> implements IFriendDAO {

	@Override
	public Long insertOne(FriendModel friendModel) {
		String sql = "INSERT INTO friend(deleted,createddate,createdby,idA,idB,is_friend) VALUES(?,?,?,?,?,?)";
		return insertOne(sql, friendModel.isDeleted(), friendModel.getCreatedDate(), friendModel.getCreatedBy(),
				friendModel.getIdA(), friendModel.getIdB(), friendModel.isFriend());
	}

	@Override
	public List<FriendModel> findListFriendRequestById(Long id) {
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		List<FriendModel> list = new ArrayList<FriendModel>();
		try {
			connection = getConnection();
			String sql = "SELECT * FROM friend WHERE idA = ? AND is_friend = false";
			preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setLong(1, id);
			resultSet = preparedStatement.executeQuery();
			while (resultSet.next()) {
				FriendModel friendModel = new FriendModel();
				friendModel.setId(resultSet.getLong("id"));
				friendModel.setDeleted(resultSet.getBoolean("deleted"));
				friendModel.setCreatedBy(resultSet.getString("createdby"));
				friendModel.setCreatedDate(resultSet.getTimestamp("createddate"));
				friendModel.setFriend(resultSet.getBoolean("is_friend"));
				friendModel.setIdA(resultSet.getLong("idA"));
				friendModel.setIdB(resultSet.getLong("idB"));
				friendModel.setModifiedBy(resultSet.getString("modifiedby"));
				friendModel.setModifiedDate(resultSet.getTimestamp("modifieddate"));
				list.add(friendModel);
			}
			return list;
		} catch (SQLException e) {
			System.out.println("Failed FriendDAO 1 : " + e.getMessage());
			return null;
		} finally {
			try {
				if (preparedStatement != null) {
					preparedStatement.close();
				}
				if (resultSet != null) {
					resultSet.close();
				}
			} catch (SQLException e2) {
				System.out.println("Failed FriendDAO 2 : " + e2.getMessage());
				return null;
			}
		}
	}
	@Override
	public List<FriendModel> findAll() {
		String sql = "SELECT * FROM friend";
		return findAll(sql, new FriendMapping());
	}

	@Override
	public boolean setIsFriend(Long idRequest, Long idRequested) {
		String sql = "UPDATE friend SET is_friend = true WHERE idA = ? AND idB = ?";
		return update(sql, idRequest,idRequested);
	}

	@Override
	public boolean deleteRequest(Long idRequest, Long idRequested) {
		String sql = "DELETE FROM friend WHERE idA = ? AND idB = ?";
		return delete(sql, idRequest,idRequested);
	}

	@Override
	public FriendModel findOne(Long idRequest, Long idRequested,boolean isFriend) {
		try {
			String sql = "SELECT * FROM friend WHERE idA = ? AND idB = ? AND is_friend = ?";
			return findOne(sql, new FriendMapping(), idRequest,idRequested,isFriend);
		} catch (ClassCastException e) {
			System.out.println("Failed FindOne FriendDAO : "+e.getMessage());
			return null;
		}
	}

	@Override
	public List<FriendModel> findListFriendById(Long id) {
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		List<FriendModel> list = new ArrayList<FriendModel>();
		try {
			String sql = "SELECT * FROM friend WHERE (idA = ? OR idB = ?) AND is_friend = true";
			connection = getConnection();
			preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setLong(1, id);
			preparedStatement.setLong(2, id);
			resultSet = preparedStatement.executeQuery();
			while(resultSet.next()) {
				FriendModel friendModel = new FriendModel();
				friendModel.setId(resultSet.getLong("id"));
				friendModel.setDeleted(resultSet.getBoolean("deleted"));
				friendModel.setCreatedBy(resultSet.getString("createdby"));
				friendModel.setCreatedDate(resultSet.getTimestamp("createddate"));
				friendModel.setFriend(resultSet.getBoolean("is_friend"));
				friendModel.setIdA(resultSet.getLong("idA"));
				friendModel.setIdB(resultSet.getLong("idB"));
				friendModel.setModifiedBy(resultSet.getString("modifiedby"));
				friendModel.setModifiedDate(resultSet.getTimestamp("modifieddate"));
				list.add(friendModel);
			}
			return list;
		} catch (SQLException e) {
			System.out.println("Failed findListFriendById FriendDAO 1 : "+e.getMessage());
			return null;
		}finally {
			try {
				if(preparedStatement != null) {
					preparedStatement.close();
				}
				if(resultSet != null) {
					resultSet.close();
				}
			} catch (SQLException e2) {
				System.out.println("Failed findListFriendById FriendDAO 2 : "+e2.getMessage());
				return null;
			}
		}
		
	}

	@Override
	public FriendModel findOne(Long idRequest, Long idRequested) {
		try {
			String sql = "SELECT * FROM friend WHERE idA = ? AND idB = ?";
			return findOne(sql, new FriendMapping(), idRequest,idRequested);
		} catch (ClassCastException e) {
			System.out.println("Failed FindOne FriendDAO : "+e.getMessage());
			return null;
		}
	}

	
	

}
