package com.letiencao.api;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.letiencao.request.AddBlocksRequest;
import com.letiencao.service.IAccountService;
import com.letiencao.service.IBlocksService;
import com.letiencao.service.impl.AccountService;
import com.letiencao.service.impl.BlocksService;

@WebServlet("/api/blocks")
public class BlocksAPI extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private IBlocksService blocksService;
	private IAccountService accountService;
	public BlocksAPI() {
		blocksService = new BlocksService();
		accountService = new AccountService();
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		response.setContentType("application/json");
		Gson gson = new Gson();
		AddBlocksRequest addBlocksRequest = gson.fromJson(request.getReader(), AddBlocksRequest.class);
		Long idBlocks = addBlocksRequest.getIdBlocks();
		Long idBlocked = addBlocksRequest.getIdBlocked();
		Long id = 0L;
		if (idBlocks == idBlocked) {
			id = -1L;
			System.out.println("1");
		}else if(accountService.findById(idBlocked) == null || accountService.findById(idBlocks) == null) {
			id = -1L;
			System.out.println("2");
		}
		else {
			 id = blocksService.insertOne(addBlocksRequest.getIdBlocks(),addBlocksRequest.getIdBlocked());
		}
		response.getWriter().print(gson.toJson(id));
	}
}
