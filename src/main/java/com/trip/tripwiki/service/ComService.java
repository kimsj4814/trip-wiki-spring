package com.trip.tripwiki.service;

import java.util.List;

import com.trip.tripwiki.domain.ComBoardList;

public interface ComService {

	public int getSearchListCount(String index, String search_word);

	public List<ComBoardList> getSearchList(String index, String search_word, int page, int limit);

}
