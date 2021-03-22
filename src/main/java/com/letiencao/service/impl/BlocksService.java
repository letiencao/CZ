package com.letiencao.service.impl;

import java.sql.Timestamp;

import com.letiencao.dao.IAccountDAO;
import com.letiencao.dao.IBlocksDAO;
import com.letiencao.dao.impl.AccountDAO;
import com.letiencao.dao.impl.BlocksDAO;
import com.letiencao.model.BlocksModel;
import com.letiencao.service.IBlocksService;

public class BlocksService implements IBlocksService {
	private IBlocksDAO blocksDAO;
	private IAccountDAO accountDAO;
	public BlocksService() {
		blocksDAO = new  BlocksDAO();
		accountDAO = new AccountDAO();
	}
	@Override
	public Long insertOne(Long idBlocks,Long idBlocked) {
		BlocksModel blocksModel = new BlocksModel();
		String phoneNumber = accountDAO.findById(idBlocks).getPhoneNumber();
		blocksModel.setCreatedBy(phoneNumber);
		blocksModel.setCreatedDate(new Timestamp(System.currentTimeMillis()));
		blocksModel.setDeleted(false);
		blocksModel.setIdBlocked(idBlocked);
		blocksModel.setIdBlocks(idBlocks);
		return blocksDAO.insertOne(blocksModel);
	}
	@Override
	public BlocksModel findOne(Long idBlocks, Long idBlocked) {
		return blocksDAO.findOne(idBlocks,idBlocked);
	}

}
