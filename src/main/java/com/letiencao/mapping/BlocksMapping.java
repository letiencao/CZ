package com.letiencao.mapping;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.letiencao.model.BlocksModel;

public class BlocksMapping implements IRowMapping<BlocksModel> {

	@Override
	public BlocksModel mapRow(ResultSet resultSet) {
		try {
			BlocksModel blocksModel = new BlocksModel();
			blocksModel.setId(resultSet.getLong("id"));
			blocksModel.setDeleted(resultSet.getBoolean("deleted"));
			blocksModel.setCreatedDate(resultSet.getTimestamp("createddate"));
			blocksModel.setCreatedBy(resultSet.getString("createdby"));
			blocksModel.setIdBlocked(resultSet.getLong("idblocked"));
			blocksModel.setIdBlocks(resultSet.getLong("idblocks"));
			blocksModel.setModifiedBy(resultSet.getString("modifiedby"));
			blocksModel.setModifiedDate(resultSet.getTimestamp("modifieddate"));
			return blocksModel;
		} catch (SQLException e) {
			System.out.println("Failed Row Mapping BlocksMapping : "+e.getMessage());
			return null;
		}
	}

}
