package com.ss.pj.common.vo;

import java.io.Serializable;
import java.util.List;

/**
 * 负责封装业务层当前页记录以及分页信息
 * @author td
 * @param <T>
 */
public class PageObject<T> implements Serializable {
	private static final long serialVersionUID = 3366485607202203927L;
	public Integer getPageCurrent() {
		return pageCurrent;
	}
	public void setPageCurrent(Integer pageCurrent) {
		this.pageCurrent = pageCurrent;
	}
	public int getPageSize() {
		return pageSize;
	}
	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}
	public int getRowCount() {
		return rowCount;
	}
	public void setRowCount(int rowCount) {
		this.rowCount = rowCount;
	}
	public int getPageCount() {
		return pageCount;
	}
	public void setPageCount(int pageCount) {
		this.pageCount = pageCount;
	}
	public List<T> getRecords() {
		return records;
	}
	public void setRecords(List<T> records) {
		this.records = records;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	/** 当前页 */
	private Integer pageCurrent;
	/** 页面大小 */
	private int pageSize;
	/** 查询所得总记录(行)数 */
	private int rowCount;
	/** 计算所得总页数 */
	private int pageCount;
	/** 当前页记录 */
	private List<T> records;
}
