package com.letiencao.service.impl;

import java.sql.Timestamp;
import java.util.List;

import com.letiencao.dao.IAccountDAO;
import com.letiencao.dao.ICommentDAO;
import com.letiencao.dao.impl.AccountDAO;
import com.letiencao.dao.impl.CommentDAO;
import com.letiencao.model.AccountModel;
import com.letiencao.model.CommentModel;
import com.letiencao.request.AddCommentRequest;
import com.letiencao.service.ICommentService;

public class CommentService extends BaseService implements ICommentService {

	private ICommentDAO commentDAO;
	private IAccountDAO accountDAO;

	public CommentService() {
		commentDAO = new CommentDAO();
		accountDAO = new AccountDAO();
	}

	@Override
	public Long insertOne(AddCommentRequest addCommentRequest) {
		CommentModel commentModel = new CommentModel();
		commentModel.setAccountId(addCommentRequest.getAccountId());
		commentModel.setContent(addCommentRequest.getContent());
		commentModel.setPostId(addCommentRequest.getPostId());
		commentModel.setDeleted(false);
		commentModel.setCreatedDate(new Timestamp(System.currentTimeMillis()));
		AccountModel accountModel = accountDAO.findById(commentModel.getAccountId());
		commentModel.setCreatedBy(accountModel.getPhoneNumber());
		return commentDAO.insertOne(commentModel);
	}

	@Override
	public int findByPostId(Long postId) {
		List<CommentModel> commentModels = commentDAO.findByPostId(postId);
		return commentModels.size();
	}

}
