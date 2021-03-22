package com.letiencao.dao;

import com.letiencao.model.BlocksModel;

public interface IBlocksDAO {
	Long insertOne(BlocksModel blocksModel);
	BlocksModel findOne(Long idBlocks, Long idBlocked);
}
