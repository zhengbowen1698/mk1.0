/**
 * 
 */
package com.haaa.cloudmedical.wechat.service;

import com.haaa.cloudmedical.common.entity.StdDTO;

/**
 * @author Bowen Fan
 *
 */
public interface IWeixinLungCapacityService {

	public StdDTO getGraph(String open_id, int days) throws Exception;

	public StdDTO getPage(String open_id, String year_month, int pageno, int pagesize);

	public StdDTO getMonths(String open_id);
}
