package com.letiencao.service.impl;

import java.sql.Timestamp;
import java.util.List;

import com.letiencao.dao.IAccountDAO;
import com.letiencao.dao.IBlocksDAO;
import com.letiencao.dao.impl.AccountDAO;
import com.letiencao.dao.impl.BlocksDAO;
import com.letiencao.model.AccountModel;
import com.letiencao.model.BlocksModel;
import com.letiencao.request.account.SignInRequest;
import com.letiencao.request.account.SignUpRequest;
import com.letiencao.service.IAccountService;

public class AccountService extends BaseService implements IAccountService {

	private IAccountDAO accountDAO;
	private IBlocksDAO blocksDAO;

	public AccountService() {
		// TODO Auto-generated constructor stub
		accountDAO = new AccountDAO();
		blocksDAO = new BlocksDAO();
	}

	@SuppressWarnings("null")
	@Override
	public AccountModel signUp(SignUpRequest signUpRequest) {
		String phoneNumber = signUpRequest.getPhoneNumber();
		AccountModel model = accountDAO.findByPhoneNumber(phoneNumber);
		if (model != null) {
			return null;
		} else {
			AccountModel accountModel = new AccountModel();
			accountModel.setPhoneNumber(signUpRequest.getPhoneNumber());
			accountModel.setName(signUpRequest.getPhoneNumber());
			accountModel.setDeleted(false);
			accountModel.setAvatar(" ");
			accountModel.setCreatedDate(new Timestamp(System.currentTimeMillis()));
			accountModel.setCreatedBy(signUpRequest.getPhoneNumber());
			accountModel.setCreatedDateLong(convertTimestampToSeconds(accountModel.getCreatedDate()));
//			accountModel.setPassword(getMD5(signUpRequest.getPassword()));
			accountModel.setPassword(signUpRequest.getPassword());
			accountModel.setUuid(signUpRequest.getUuid());
			boolean b = accountDAO.signUp(accountModel);
			if (b) {
				return accountModel;
			}
			return null;
		}

	}

	@Override
	public String signIn(SignInRequest signInRequest) {
		String jwt = createJWT(signInRequest.getPhoneNumber());
//		signInRequest.setPassword(getMD5(signInRequest.getPassword()));
		if (accountDAO.signIn(signInRequest) != null) {
			return jwt;
		}
		return null;
	}

	@Override
	public AccountModel findByPhoneNumber(String phoneNumber) {
		return accountDAO.findByPhoneNumber(phoneNumber);
	}

	@Override
	public AccountModel findById(Long id) {
		return accountDAO.findById(id);
	}

	@Override
	public List<AccountModel> findListAccountByKeyword(String keyword, String token) {
		List<AccountModel> list = accountDAO.findListAccountByKeyword(keyword);
		// get accountId From token
		Long accountId = findByPhoneNumber(getPhoneNumberFromToken(token)).getId();
		for (AccountModel accountModel : list) {
			// Check block
			BlocksModel b1 = blocksDAO.findOne(accountId, accountModel.getId());
			BlocksModel b2 = blocksDAO.findOne(accountModel.getId(), accountId);
			if (b1 != null || b2 != null) {
				list.remove(accountModel);
			}
		}
		return list;
	}

	@Override
	public boolean changePassword(String token, String password, String newPassword) {
		String phoneNumber = getPhoneNumberFromToken(token);
		AccountModel accountModel = findByPhoneNumber(phoneNumber);
		String pw = accountModel.getPassword();
		if (pw.equals(password)) {
			accountModel.setModifiedDate(new Timestamp(System.currentTimeMillis()));
			accountModel.setPassword(newPassword);
			accountModel.setModifiedBy(phoneNumber);
			return accountDAO.changePassword(accountModel);
		} else {
			return false;
		}

	}
}
