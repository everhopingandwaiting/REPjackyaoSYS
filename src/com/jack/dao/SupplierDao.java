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
import com.jack.entity.Supplier;

/**
 * 针对管理员实体类进行操作的DAO类
 * @author Administrator
 */
public class SupplierDao {
	/**定义一个SQL执行器的实例 */
	private QueryRunner run = new QueryRunner();
	private BeanHandler<Supplier> beanHeander = new BeanHandler<Supplier>(Supplier.class);
	private BeanListHandler<Supplier> beanListHandler = new BeanListHandler<Supplier>(Supplier.class);
	private ScalarHandler<Integer> scalarHandler = new ScalarHandler<Integer>();
	
	public void save(Supplier sup){
		String sql = "insert into supplier (sn,name,contact,address,tel,code,email,remark)  values(?,?,?,?,?,?,?,?)";
		
		Object[] params = {sup.getSn(),sup.getName(),sup.getContact(),sup.getAddress(),sup.getTel(),
				sup.getCode(),sup.getEmail(),sup.getRemark()};
		
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
		String sql = "delete from supplier where id=?";
		
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
	
	public void update(Supplier sup){
		String sql = "update supplier set sn= ?,name = ?,contact = ?,address = ? ,tel = ?,code = ?,email = ? ,remark = ?   where id=?";
		
		Object[] params = {sup.getSn(),sup.getName(),sup.getContact(),
				sup.getAddress(),sup.getTel(),sup.getCode(),sup.getEmail(),sup.getRemark(),sup.getId()};
		
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
	
	public Supplier findOne(int id){
		Supplier sup = null;
		
		String sql = "select * from supplier where id=?";
		
		Connection conn = null;
		try {
			conn = SQLiteHelper.getConn();
			
			//第三个参数结果集处理器
			sup = run.query(conn, sql, beanHeander, id);
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally{
			SQLiteHelper.close(conn);
		}
		
		return sup;
	}
	

	public List<Supplier> findAll(){
		List<Supplier> list = new ArrayList<Supplier>();
		
		String sql = "select * from supplier";
		
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
		
		String sql = "select count(id) from supplier";
		
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