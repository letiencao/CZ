package com.letiencao.service;

import com.letiencao.model.BlocksModel;

public interface IBlocksService {
	Long insertOne(Long idBlocks,Long idBlocked);
	BlocksModel findOne(Long idBlocks,Long idBlocked);
}
