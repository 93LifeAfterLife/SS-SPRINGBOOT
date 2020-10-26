package com.ss.pj.common.vo;

import java.io.Serializable;
import java.util.List;

import lombok.Data;
import lombok.ToString;

/**
 * 负责封装业务层当前页记录以及分页信息
 * @author td
 * @param <T>
 */

@Data
@ToString
public class PageObject<T> implements Serializable {
	private static final long serialVersionUID = -6396332754299900831L;
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
