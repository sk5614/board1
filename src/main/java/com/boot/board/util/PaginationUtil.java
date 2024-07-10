package com.boot.board.util;

import java.util.ArrayList;
import java.util.List;

import com.boot.board.domain.Pagination;

public class PaginationUtil {

    public static Pagination getPagination(int page, int size, int totalItems) {
        int totalPages = (int) Math.ceil((double) totalItems / size);
        int startPage = Math.max(1, page - 4);
        int endPage = Math.min(startPage + 5, totalPages);

        List<Integer> pageNumbers = new ArrayList<>();
        for (int i = startPage; i <= endPage; i++) {
            pageNumbers.add(i);
        }

        return new Pagination(size, page, startPage, endPage, totalPages, pageNumbers);
    }
}
