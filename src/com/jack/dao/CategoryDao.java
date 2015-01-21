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
import com.jack.entity.Category;

/**
 * 针对管理员实体类进行操作的DAO类
 * @author Administrator
 */
public class CategoryDao {
	/**定义一个SQL执行器的实例 */
	private QueryRunner run = new QueryRunner();
	private BeanHandler<Category> beanHeander = new BeanHandler<Category>(Category.class);
	private BeanListHandler<Category> beanListHandler = new BeanListHandler<Category>(Category.class);
	private ScalarHandler<Integer> scalarHandler = new ScalarHandler<Integer>();
	
	public void save(Category cate){
		String sql = "insert into category(name, summary) values(?,?)";
		
		Object[] params = {cate.getName(), cate.getSummary() };
		
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
		String sql = "delete from category where id=?";
		
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
	
	public void update(Category cate){
		String sql = "update category set name=?, summary=?  where id=?";
		
		Object[] params = {cate.getName(), cate.getSummary(),cate.getId()};
		
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
	
	public Category findOne(int id){
		Category mgr = null;
		
		String sql = "select * from category where id=?";
		
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

	
	public List<Category> findAll(){
		List<Category> list = new ArrayList<Category>();
		
		String sql = "select * from category";
		
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
	
	/**
	 * 统计总记录数
	 * @return
	 */
	public long total(){
		long count = 0;
		
		String sql = "select count(id) from category";
		
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