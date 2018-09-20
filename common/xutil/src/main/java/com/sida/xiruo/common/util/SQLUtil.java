package com.sida.xiruo.common.util;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * 数据库工具类
 * @author 刘浩
 *
 */
public class SQLUtil {
	
	/**
	 * 关闭数据库连接对�?
	 * @param con
	 * @return
	 */
	public static SQLException close(Connection con) {
		try {
			if (con != null) {
				con.close();
			}
		} catch (SQLException e) {
			return e;
		}
		return null;
	}

	/**
	 * 关闭数据库SQL语句执行对象
	 * @param ps 
	 * @return
	 */
	public static SQLException close(Statement ps) {
		try {
			if (ps != null) {
				ps.close();
			}
		} catch (SQLException e) {
			return e;
		}
		return null;
	}

	/**
	 * 关闭数据结果集对�?
	 * @param rs
	 * @return
	 */
	public static SQLException close(ResultSet rs) {
		try {
			if (rs != null) {
				rs.close();
			}
		} catch (SQLException e) {
			return e;
		}
		return null;
	}

	/**
	 * 关闭数据库操作所产生的各种对�?
	 * @param rs 结果集对�?
	 * @param pstm SQL语句执行对象
	 * @param con 数据库连接对�?
	 */
	public static void close(ResultSet rs, Statement pstm, Connection con) {
		close(rs);
		close(pstm);
		close(con);
	}
}