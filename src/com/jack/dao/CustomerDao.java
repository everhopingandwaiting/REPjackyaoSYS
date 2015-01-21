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
import com.jack.entity.Customer;

/**
 * 针对管理员实体类进行操作的DAO类
 * @author Administrator
 */
public class CustomerDao {
	/**定义一个SQL执行器的实例 */
	private QueryRunner run = new QueryRunner();
	private BeanHandler<Customer> beanHeander = new BeanHandler<Customer>(Customer.class);
	private BeanListHandler<Customer> beanListHandler = new BeanListHandler<Customer>(Customer.class);
	private ScalarHandler<Integer> scalarHandler = new ScalarHandler<Integer>();
	
	public void save(Customer cus){
		String sql = "insert into customer (sn,name,contact,address,tel,code,email,remark)  values(?,?,?,?,?,?,?,?)";
		
		Object[] params = {cus.getSn(),cus.getName(),cus.getContact(),cus.getAddress(),cus.getTel(),
				cus.getCode(),cus.getEmail(),cus.getRemark()};
		
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
		String sql = "delete from customer where id=?";
		
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
	
	public void update(Customer cus){
		String sql = "update customer set sn= ?,name = ?,contact = ?,address = ? ,tel = ?,code = ?,email = ? ,remark = ?   where id=?";
		
		Object[] params = {cus.getSn(),cus.getName(),cus.getContact(),
				cus.getAddress(),cus.getTel(),cus.getCode(),cus.getEmail(),cus.getRemark(),cus.getId()};
		
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
	
	public Customer findOne(int id){
		Customer cus = null;
		
		String sql = "select * from customer where id=?";
		
		Connection conn = null;
		try {
			conn = SQLiteHelper.getConn();
			
			//第三个参数结果集处理器
			cus = run.query(conn, sql, beanHeander, id);
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally{
			SQLiteHelper.close(conn);
		}
		
		return cus;
	}
	

	public List<Customer> findAll(){
		List<Customer> list = new ArrayList<Customer>();
		
		String sql = "select * from customer";
		
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
		
		String sql = "select count(id) from customer";
		
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