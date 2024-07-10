package com.boot.board.domain;

import java.util.List;

public class Pagination {
   
	private int size;
    private int nowPage;
    private int startPage;
    private int endPage;
    private int totalPages;
    private List<Integer> pageNumbers;

    public Pagination(int size, int nowPage, int startPage, int endPage, int totalPages, List<Integer> pageNumbers) {
        this.size = size;
        this.nowPage = nowPage;
        this.startPage = startPage;
        this.endPage = endPage;
        this.totalPages = totalPages;
        this.pageNumbers = pageNumbers;
    }
    
    public int getSize() {
		return size;
	}

	public void setSize(int size) {
		this.size = size;
	}

	public int getNowPage() {
		return nowPage;
	}

	public void setNowPage(int nowPage) {
		this.nowPage = nowPage;
	}

	public int getStartPage() {
		return startPage;
	}

	public void setStartPage(int startPage) {
		this.startPage = startPage;
	}

	public int getEndPage() {
		return endPage;
	}

	public void setEndPage(int endPage) {
		this.endPage = endPage;
	}

	public int getTotalPages() {
		return totalPages;
	}

	public void setTotalPages(int totalPages) {
		this.totalPages = totalPages;
	}

	public List<Integer> getPageNumbers() {
		return pageNumbers;
	}

	public void setPageNumbers(List<Integer> pageNumbers) {
		this.pageNumbers = pageNumbers;
	}


}
