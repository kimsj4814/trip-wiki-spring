package com.trip.tripwiki.service;

import java.util.List;

import com.trip.tripwiki.domain.ComBoardList;

public interface ComService {

	public int getSearchListCount(String index, String search_word);

	public List<ComBoardList> getSearchList(String index, String search_word, int page, int limit);

	public void insertBoard(ComBoardList comBoard);

	public ComBoardList getDetail(int num);

	public int boardReply(ComBoardList comBoard);

	public boolean isBoardWriter(int board_NUM, String board_PASS);

	public int boardModify(ComBoardList boarddata);

	public int setReadCountUpdate(int num);



}
