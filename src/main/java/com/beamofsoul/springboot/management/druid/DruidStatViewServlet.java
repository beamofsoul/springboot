package com.beamofsoul.springboot.management.druid;

import javax.servlet.annotation.WebInitParam;
import javax.servlet.annotation.WebServlet;

import com.alibaba.druid.support.http.StatViewServlet;

/**
 * Druid数据源状态监控
 * @author MingshuJian
 */
@WebServlet(urlPatterns="/druid/*",initParams={
	@WebInitParam(name="allow",value=""),
	@WebInitParam(name="deny",value=""),
	@WebInitParam(name="loginUsername",value="root"),
	@WebInitParam(name="loginPassword",value="root"),
	@WebInitParam(name="resetEnable",value="false")},
	asyncSupported=true)
public class DruidStatViewServlet extends StatViewServlet {
	private static final long serialVersionUID = 8690242272455275524L;
}
