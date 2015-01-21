package com.jack.dao;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.ResultSetHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import com.jack.common.SQLiteHelper;
import com.jack.entity.Product;

public class ProductDao {
	private QueryRunner run = new QueryRunner();
	private ScalarHandler<Integer> scalarHandler = new ScalarHandler<Integer>();
	
	/**自定义的结果集处理器，用于把结果集中的第一行数据封装成JavaBean对象 */
	private ResultSetHandler<Product> beanHeander = new ResultSetHandler<Product>() {
		public Product handle(ResultSet rs) throws SQLException {
			Product pro = new Product();
			pro.setId(rs.getInt("id"));
			pro.setIsbn(rs.getString("isbn"));
			pro.setName(rs.getString("name"));
			pro.setSecurity_num(rs.getInt("security_num"));
			pro.setUnit(rs.getString("unit"));
			pro.setModel(rs.getString("model"));
			pro.setSummary(rs.getString("summary"));
			
			//从数据库表中以字符串形式来获取sale_price字段的值，转换成BigDecimal类型
			pro.setSale_price(new BigDecimal(rs.getString("sale_price")));
			
			pro.setCate_id(rs.getInt("cate_id"));
			return pro;
		}
	};
	
	/**自定义的结果集处理器，用于把结果集中的每一行数据封装成JavaBean对象，并添加到List中存放 */
	private ResultSetHandler<List<Product>> beanListHandler = new ResultSetHandler<List<Product>>() {
		public List<Product> handle(ResultSet rs) throws SQLException {
			List<Product> list = new ArrayList<Product>();
			
			while(rs.next()){
				Product pro = new Product();
				pro.setId(rs.getInt("id"));
				pro.setIsbn(rs.getString("isbn"));
				pro.setName(rs.getString("name"));
				pro.setSecurity_num(rs.getInt("security_num"));
				pro.setUnit(rs.getString("unit"));
				pro.setModel(rs.getString("model"));
				pro.setSummary(rs.getString("summary"));
				pro.setSale_price(new BigDecimal(rs.getString("sale_price")));
				pro.setCate_id(rs.getInt("cate_id"));
				list.add(pro);
			}
			return list;
		}
	};
	
	public void save(Product pro){
		String sql = "insert into product(isbn,name,model,summary,sale_price,security_num,unit,cate_id) values(?,?,?,?,?,?,?,?)";
		
		Object[] params = {pro.getIsbn(),
				pro.getName(), 
				pro.getModel(),
				pro.getSummary(),
				pro.getSale_price(),
				pro.getSecurity_num(),
				pro.getUnit(),
				pro.getCate_id()};
		
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
		String sql = "delete from product where id=?";
		
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
	
	public void update(Product pro){
		String sql = "update product set isbn=?,name=?,model=?,summary=?,sale_price=?,security_num=?,unit=?,cate_id=? where id=?";
		
		Object[] params = {pro.getIsbn(),
				pro.getName(), 
				pro.getModel(),
				pro.getSummary(),
				pro.getSale_price(),
				pro.getSecurity_num(),
				pro.getUnit(),
				pro.getCate_id(),
				pro.getId()};
		
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
	
	public Product findOne(int id){
		Product prod = null;
		
		String sql = "select * from product where id=?";
		
		Connection conn = null;
		try {
			conn = SQLiteHelper.getConn();
			
			prod = run.query(conn, sql, beanHeander, id);
		} catch (SQLException e) {
			e.printStackTrace();
		} finally{
			SQLiteHelper.close(conn);
		}
		
		return prod;
	}
	
	public List<Product> findAll(){
		List<Product> list = new ArrayList<Product>();
		
		String sql = "select * from product";
		
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
		
		String sql = "select count(id) from product";
		
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
