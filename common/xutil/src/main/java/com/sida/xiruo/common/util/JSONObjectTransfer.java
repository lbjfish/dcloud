package com.sida.xiruo.common.util;

import java.io.Serializable;

import net.sf.json.JSONObject;

/**
 * description:转JSONObject 接口
 *
 * @author: zhaocong
 * @version May 25, 2011
 */
public interface JSONObjectTransfer extends Serializable {
	public JSONObject toJSONObject();
}
