package com.ss.pj.common.util;

import java.util.List;

import com.ss.pj.common.vo.PageObject;

/**
 * 页面处理工具类
 * @author td
 */
public class PageUtil {
	private static int pageSize=5;
	
	public static int getPageSize() {
		return pageSize;
	}

	public static int getStartIndex(Integer pageCurrent) {
		return pageSize*(pageCurrent-1);
	}
	
	/**
	 * 新建页面vo对象
	 * @param <T>
	 * @param pageCurrent 当前页码
	 * @param rowCount 总记录数
	 * @param pageSize 页面大小
	 * @param records 当前页面记录(当前页表数据集合)
	 * @return
	 */
	public static <T>PageObject<T> newPageObject(Integer pageCurrent, int rowCount, int pageSize, List<T> records) {
		PageObject<T> po = new PageObject<>();
		po.setRowCount(rowCount);
		po.setRecords(records);
		po.setPageSize(pageSize);
		po.setPageCount((rowCount-1)/pageSize + 1);
		po.setPageCurrent(pageCurrent);
		return po;
	}
}
