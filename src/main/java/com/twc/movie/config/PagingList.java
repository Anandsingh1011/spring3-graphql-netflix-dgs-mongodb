package com.twc.movie.config;


import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class PagingList<T> {

	@SuppressWarnings("rawtypes")
	private static final List<?> BLANK_RESPONSE = new ArrayList(0);

	private List<T> totallist;

	private List<T> paginatedList;

	private int pageSize;
	private int rowsPerPage;
	private int pageNum;
	private int totalPages;
	private int totalElements;

	/**
	 * Create a new PagingList based on the page size and number.
	 * 
	 * @param c   - Content
	 * @param pageSize  - Total Elements in a given page
	 * @param pageNum   - Page Number
	 * @param defaultSize - Default total elements in a given page (25).
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public PagingList(Collection<T> c, String pageSize, String pageNum,int defaultSize) {
		calculatePageNumandSize(pageSize,pageNum,defaultSize);
		this.totalPages = 0;
		this.totalElements = 0;
		this.totallist = new ArrayList(c);
		getCurrentPageElements();
	}

	private void calculatePageNumandSize(String pageSize, String pageNum, int defaultSize) {
		// TODO Auto-generated method stub
		this.pageSize = defaultSize;
		this.pageNum = 1;

		try {
			if (null != pageSize && !pageSize.trim().isEmpty()) {
				this.pageSize = ((Integer.parseInt(pageSize)) <= 0) ? defaultSize : Integer.parseInt(pageSize);
			}

		} catch (NumberFormatException exNum) {
		}
		try {
			if (null != pageNum && !pageNum.trim().isEmpty()) {
				this.pageNum = ((Integer.parseInt(pageNum)) <= 0) ? 1 : Integer.parseInt(pageNum);
			}

		} catch (NumberFormatException exNum) {
		}
	}

	@SuppressWarnings("unchecked")
	public List<T> getCurrentPageElements() {
		if (null == totallist || totallist.isEmpty()) {
			paginatedList = (List<T>)BLANK_RESPONSE;
		} else {

			totalElements = totallist.size();
			totalPages = 0;

			if (totalElements <= pageSize && pageNum <= 1) {
				totalPages = 1;
				rowsPerPage = totalElements;
				paginatedList = totallist;

			} else if (totalElements <= pageSize && pageNum > 1) {
				paginatedList = new ArrayList<T>();

			} else if (totalElements >= pageSize) {
				totalPages = (totalElements / pageSize);
				if ((totalElements % pageSize) > 0) {
					totalPages = totalPages + 1;
				}
				int startList = (pageNum - 1) * pageSize;
				int endList = ((pageNum * pageSize) <= totalElements) ? (pageNum * pageSize) : totalElements;
				if (startList > endList) {
					paginatedList = (List<T>)BLANK_RESPONSE;
				} else {
					paginatedList = totallist.subList(startList, endList);
					rowsPerPage = paginatedList.size();
				}

			}
		}

		return paginatedList;
	}

	public int getCurrentPage() {
		return pageNum;
	}

	public int getRowsPerPage() {
		return rowsPerPage;
	}

	public int getTotalPages() {
		return totalPages;
	}

	public int getTotalElements() {
		return totalElements;
	}
}
