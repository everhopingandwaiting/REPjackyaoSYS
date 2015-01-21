package com.jack.dao;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import com.jack.common.SQLiteHelper;
import com.jack.entity.Manager;

/**
 * 针对管理员实体类进行操作的DAO类
 * @author Administrator
 */
public class ManagerDao {
	/**定义一个SQL执行器的实例 */
	private QueryRunner run = new QueryRunner();
	private BeanHandler<Manager> beanHeander = new BeanHandler<Manager>(Manager.class);
	private BeanListHandler<Manager> beanListHandler = new BeanListHandler<Manager>(Manager.class);
	private ScalarHandler<Integer> scalarHandler = new ScalarHandler<Integer>();
	
	public void save(Manager mgr){
		String sql = "insert into manager(lname, pwd, mobile,email,status) values(?,?,?,?,?)";
		
		Object[] params = {mgr.getLname(), mgr.getPwd(), mgr.getMobile(), mgr.getEmail(), mgr.getStatus()};
		
		Connection conn = null;
		try {
			conn = SQLiteHelper.getConn();
			
			run.update(conn, sql, params);
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally{
			SQLiteHelper.close(conn);
		}
	}
	
	
	public void delete(int id){
		String sql = "delete from manager where id=?";
		
		Connection conn = null;
		try {
			conn = SQLiteHelper.getConn();
			
			run.update(conn, sql, id);
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally{
			SQLiteHelper.close(conn);
		}
	}
	
	public void update(Manager mgr){
		String sql = "update manager set lname=?, pwd=?, mobile=?,email=?,status=?  where id=?";
		
		Object[] params = {mgr.getLname(), mgr.getPwd(), mgr.getMobile(), 
				mgr.getEmail(), mgr.getStatus(), mgr.getId()};
		
		Connection conn = null;
		try {
			conn = SQLiteHelper.getConn();
			
			run.update(conn, sql, params);
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally{
			SQLiteHelper.close(conn);
		}
	}
	
	public Manager findOne(int id){
		Manager mgr = null;
		
		String sql = "select * from manager where id=?";
		
		Connection conn = null;
		try {
			conn = SQLiteHelper.getConn();
			
			//第三个参数结果集处理器
			mgr = run.query(conn, sql, beanHeander, id);
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally{
			SQLiteHelper.close(conn);
		}
		
		return mgr;
	}
	
	public Manager findOne(String lname){
		Manager mgr = null;
		
		String sql = "select * from manager where lname=?";
		
		Connection conn = null;
		try {
			conn = SQLiteHelper.getConn();
			
			mgr = run.query(conn, sql, beanHeander, lname);
		} catch (SQLException e) {
			e.printStackTrace();
		} finally{
			SQLiteHelper.close(conn);
		}
		
		return mgr;
	}
	
	public List<Manager> findAll(){
		List<Manager> list = new ArrayList<Manager>();
		
		String sql = "select * from manager";
		
		Connection conn = null;
		try {
			conn = SQLiteHelper.getConn();
			
			list = run.query(conn, sql, beanListHandler);
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally{
			SQLiteHelper.close(conn);
		}
		
		return list;
	}
	// update passwd 
		public boolean updatePWD(Manager mgr){
			   boolean flag=false;
			String sql = "update manager set  pwd=? where id=?";
			
			Object[] params = {mgr.getPwd(), mgr.getId()};
			
			Connection conn = null;
			try {
				conn = SQLiteHelper.getConn();
				
				if(run.update(conn, sql, params)==1)
				{
					 flag=true;
				}
				
			} catch (SQLException e) {
				e.printStackTrace();
			} finally{
				SQLiteHelper.close(conn);
			}
			return flag;
		}
	/**
	 * 统计总记录数
	 * @return
	 */
	public long total(){
		long count = 0;
		
		String sql = "select count(id) from manager";
		
		Connection conn = null;
		try {
			conn = SQLiteHelper.getConn();
			
			Integer temp = run.query(conn, sql, scalarHandler);
			
			if(temp != null){
				count = temp.intValue();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally{
			SQLiteHelper.close(conn);
		}
		
		return count;
	}
}