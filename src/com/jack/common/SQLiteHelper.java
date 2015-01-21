package com.jack.common;

import java.sql.Connection;
import java.sql.DriverManager;

import java.sql.SQLException;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.ScalarHandler;

/**
 * SQLite
 * @author 
 */
public class SQLiteHelper {
	
	private SQLiteHelper(){}
	private static QueryRunner queryRunner=new  QueryRunner();
	//private static BeanHandler<Manager> beanHandler=new BeanHandler<Manager>(Manager.class);
	//private static BeanListHandler<Manager> beanListHandler=new BeanListHandler<Manager>(Manager.class);
	private static ScalarHandler<Integer> scalarHandler=new ScalarHandler<Integer>();
	static { //
		try {
			//
			Class.forName("org.sqlite.JDBC");
			
			initTables();
			
			initData();
		} catch (ClassNotFoundException e) {
			System.err.println("在classpath下未找到SQLite的驱动包，请检查！");
			e.printStackTrace();
		}
	}
	
	
	/**
	 * 
	 * @return 
	 */
	public static Connection getConn() throws SQLException{
		Connection conn = null;
		
		//step2:
		try {
			conn = DriverManager.getConnection("jdbc:sqlite:erpjack.db");
		} catch (SQLException e) {
			throw e;
		}
		return conn;
	}
	
	/**
	 * 
	 * @param conn
	 */
	public static void close(Connection conn){
		if(conn != null){
			try {
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
	
	/**
	 * 用
	 */
	public static void initTables(){
//		String sql_manager = "create table if not exists manager"
//				+ "(id integer primary key autoincrement,"
//				+ "lname text(32) not null , pwd text(32),"
//				+ " mobile text(32), email text(128), status integer,unique(lname))";
//	    String sql_category="create table if not exists category("
//	    		+ "id integer primary key autoincrement,"
//	    		+ "name text(32) not null , summary longtext not null,unique(name,summary))";
//        String sql_Product=" create table if not exists product"
//        		+ "( id integer primary key autoincrement, isbn text(32)"
//        		+ "  not null ,  name text(64) ,model text(32),summary longtext,"
//        		+ "sale_price decimal(7,2), security_num int(11),unit text(8),"
//        		+ "cate_id int(11) not null,unique(isbn))";                    
//        String sql_Customer="create table if not exists customer("
//        		+ "id	integer primary key autoincrement,		 sn	text(32) not null ,	"
//        		+ "	name	text(64) unique,	contact		text(32),"
//        		+ "address		text(128),tel			text(32),code		text(6),"
//        		+ "email		text(128),remark		longtext ,unique(sn))";							            
//                                            
//        String sql_Supplier="create table if not exists supplier("
//        		+ "id	integer primary key autoincrement,		 sn	text(32) not null ,	"
//        		+ "	name	text(64) not null,	contact		text(32),"
//        		+ "address		text(128),tel			text(32),code		text(6),"
//        		+ "email		text(128),remark	longtext,unique(sn,name) )";							            
//                                                           
//         String sql_Purchase="create table if not exists purchase("
//         		+ " id 	integer primary key autoincrement,	sn text(32)"
//         		+ " not null ,			supplier_id	integer(11),"
//         		+ "manager_id	integer(11),pay_type	integer,"
//         		+ "pur_date	datetime,cost		decimal(9,2),"
//         		+ "remark		longtext,status	 integer,unique(sn)	)"  ;                                 
//           String  sql_Purchase_Item="create table if not exists purchase_item("
//           		+ "id integer primary key autoincrement		,"
//           		+ "	purchase_id	integer(11)  not null,"
//           		+ "product_id	integer(11) ,"
//           		+ "num			integer(11),price	decimal(7,2)	,unique(purchase_id))";                           
//           String  sql_Sales="create table if not exists sales("
//           		+ " id integer primary key autoincrement,		"
//           		+ "sn text(32) not null,"
//           		+ "customer_id	 integer(11) ,manager_id	integer ,pay_type	integer,"
//           		+ "sale_date	datetime,cost decimal(9,2),"
//           		+ "remark	longtext,status	integer)"; 
//           
//           String sql_Sales_Item="create table if not exists sales_item("
//           		+ "	id integer primary key autoincrement,		"
//           		+ "	sales_id	integer(11)  not null,product_id	integer(11),"
//           		+ "num			integer(11),price decimal(7,2))";
		String sql_manager = "create table if not exists manager(id integer primary key autoincrement,lname text(32) not null, pwd text(32), mobile text(32), email text(128), status integer,unique(lname))";
		String sql_category = "create table if not exists category( id integer primary key autoincrement,name text(32) not null ,summary text,unique(name))";
		
		String sql_supplier = "create table if not exists supplier(id integer primary key autoincrement,sn text(32) not null,name text(32) not null ,contact	text(32) ,address text(64),tel text(32),code text(32),email	text(64),remark	text,unique(sn,name))";
		String sql_customer = "create table if not exists customer(id integer primary key autoincrement,sn text(32) not null,name text(32) not null ,contact	text(32) ,address text(64),tel text(32),code text(32),email	text(64),remark	text,unique(sn,name))";
		String sql_product = "create table if not exists product(id integer  primary key autoincrement,isbn  text(32) not null,name text(32),model text(32)   ,summary text   ,sale_price  real , security_num  integer ,unit text(64),cate_id integer,unique(isbn))";
		String sql_purchase = "create table if not exists purchase(id integer primary key autoincrement,sn text(32),supplier_id integer,manager_id integer,pay_type integer,pur_date integer,cost real,remark text(64),status integer)";
	    String sql_purchase_item = "create table if not exists purchase_item(id integer primary key autoincrement,purchase_id integer,product_id integer,num integer,price real)";

	    String sql_sales = "create table if not exists sales(id integer primary key autoincrement,sn text(32),customer_id integer,manager_id integer,pay_type integer,sale_date integer,cost real,remark text(64),status integer)";
	    String sql_sales_item = "create table if not exists sales_item(id integer primary key autoincrement,sales_id integer,product_id integer,num integer,price real)";

	    Connection conn = null;
		//PreparedStatement pstmt = null;
		
		try {
			conn = getConn();
//			pstmt = conn.prepareStatement(sql_manager);
//		//	pstmt.executeUpdate();
//			pstmt.addBatch();
//			pstmt=conn.prepareStatement(sql_category);
//			//pstmt.executeUpdate();
//			pstmt.addBatch();
//			pstmt.executeBatch();
			 queryRunner.update(conn,sql_manager);
			 queryRunner.update(conn, sql_category);
			 queryRunner.update(conn, sql_product);
			 queryRunner.update(conn, sql_customer);
			 queryRunner.update(conn, sql_supplier);
			 queryRunner.update(conn, sql_purchase);
			 queryRunner.update(conn, sql_purchase_item);
			 queryRunner.update(conn, sql_sales);
			 queryRunner.update(conn, sql_sales_item);
		} catch (SQLException e) {
			e.printStackTrace();
		} finally{
			close(conn);
		}
	}
	public static void initData() {
		Connection connection=null;
		
		String sql="insert into manager(lname,pwd,mobile,email,status) values(?,?,?,?,?)";
		
		String sql_select_manager="select count(id) from manager";
		
		try {
			     connection=SQLiteHelper.getConn();
			Integer temp= queryRunner.query(connection, sql_select_manager, scalarHandler);
			 if (temp==0) {
					Object[] param={"admin","admin","15620608888","admin@gmail.com",0};
					Object[] param1={"jack","jack","15620608999","jack@gmail.com",0};
					queryRunner.update(connection, sql, param);
					queryRunner.update(connection, sql, param1);
			
			 }

		
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		finally{
			SQLiteHelper.close(connection);
		}
				  
	}
	

}

//package com.jack.common;
//
//import java.sql.Connection;
//import java.sql.DriverManager;
//import java.sql.PreparedStatement;
//import java.sql.ResultSet;
//import java.sql.SQLException;
//
///**
// * SQLite数据库操作工具类
// * @author solo
// */
//public class SQLiteHelper {
//	
//	private SQLiteHelper(){}
//	
//	static { //静态代码块，当前字节码被加载到内存中时，就会执行。
//		try {
//			
//			//第一步：加载数据库的驱动类
//			Class.forName("org.sqlite.JDBC");
//			
//			initTables();
//			
//			initData();
//		} catch (ClassNotFoundException e) {
//			System.err.println("在classpath下未找到SQLite的驱动包，请检查！");
//			e.printStackTrace();
//		}
//	}
//	
//	
//	/**
//	 * 获取数据库的一个连接对象
//	 * @return 成功，返回数据库的连接对象；否则返回null
//	 */
//	public static Connection getConn() throws SQLException{
//		Connection conn = null;
//		
//		//第二步
//		try {
//			conn = DriverManager.getConnection("jdbc:sqlite:erpjack.db");
//		} catch (SQLException e) {
//			throw e;
//		}
//		return conn;
//	}
//	
//	/**
//	 * 关闭数据库连接的方法：数据库操作完毕后要关闭连接，来释放所占用的系统资源。
//	 * @param conn
//	 */
//	public static void close(Connection conn){
//		if(conn != null){
//			try {
//				conn.close();
//			} catch (SQLException e) {
//				e.printStackTrace();
//			}
//		}
//	}
//	
//	/**
//	 * 用来程序启动时，创建表的方法
//	 */
//	public static void initTables(){
//		String sql_manager = "create table if not exists manager(id integer primary key autoincrement,lname text(32) not null, pwd text(32), mobile text(32), email text(128), status integer,unique(lname,pwd)";
//		String sql_category = "create table if not exists category( id integer primary key autoincrement,name text(32) not null ,summary text,unique(name)";
//		
//		String sql_supplier = "create table if not exists supplier(id integer primary key autoincrement,sn text(32) not null,name text(32) not null ,contact	text(32) ,address text(64),tel text(32),code text(32),email	text(64),remark	text,unique(sn,name)";
//		String sql_customer = "create table if not exists customer(id integer primary key autoincrement,sn text(32) not null,name text(32) not null ,contact	text(32) ,address text(64),tel text(32),code text(32),email	text(64),remark	text,unique(sn,name))";
//		String sql_product = "create table if not exists product(id integer  primary key autoincrement,isbn  text(32) not null,name text(32),model text(32)   ,summary text   ,sale_price  real , security_num  integer ,unit text(64),cate_id integer,unique(isbn)";
//		String sql_purchase = "create table if not exists purchase(id integer primary key autoincrement,sn text(32),supplier_id integer,manager_id integer,pay_type integer,pur_date integer,cost real,remark text(64),status integer)";
//	    String sql_purchase_item = "create table if not exists purchase_item(id integer primary key autoincrement,purchase_id integer,product_id integer,num integer,price real)";
//
//	    String sql_sales = "create table if not exists sales(id integer primary key autoincrement,sn text(32),customer_id integer,manager_id integer,pay_type integer,sale_date integer,cost real,remark text(64),status integer)";
//	    String sql_sales_item = "create table if not exists sales_item(id integer primary key autoincrement,sales_id integer,product_id integer,num integer,price real)";
//
//	    Connection conn = null;
//		try {
//			conn = getConn();
//			conn.prepareStatement(sql_manager).executeUpdate();
//			conn.prepareStatement(sql_category).executeUpdate();
//			conn.prepareStatement(sql_supplier).executeUpdate();
//			conn.prepareStatement(sql_customer).executeUpdate();
//			conn.prepareStatement(sql_product).executeUpdate();
//			conn.prepareStatement(sql_purchase).executeUpdate();
//			conn.prepareStatement(sql_purchase_item).executeUpdate();
//			 conn.prepareStatement(sql_sales).executeUpdate();
//		      conn.prepareStatement(sql_sales_item).executeUpdate();
//		    
//		} catch (SQLException e) {
//			e.printStackTrace();
//		} finally{
//			close(conn);
//		}
//	}
//	
//	public static void initData(){
//		String sql_manager_insert = "insert into manager(lname,pwd,email,mobile,status) values(?,?,?,?,?)";
//		String sql_manager_select = "select count(id) from manager";
//		
//		Connection conn = null;
//		PreparedStatement pstmt = null;
//		ResultSet rs = null;
//		try{
//			conn = getConn();
//			pstmt = conn.prepareStatement(sql_manager_select);
//			rs = pstmt.executeQuery();
//			
//			long count = rs.getLong(1);
//			if(count == 0){
//				pstmt = conn.prepareStatement(sql_manager_insert);
//				pstmt.setString(1, "admin");
//				pstmt.setString(2, "admin");
//				pstmt.setString(3, "admin@gmail.com");
//				pstmt.setString(4, "18722637***");
//				pstmt.setInt(5, 0);
//				pstmt.addBatch();
//				
//				pstmt.setString(1, "jack");
//				pstmt.setString(2, "jack");
//				pstmt.setString(3, "154648648qq.com");
//				pstmt.setString(4, "15620608888");
//				pstmt.setInt(5, 0);
//				pstmt.addBatch();
//				
//				pstmt.setString(1, "teacher");
//				pstmt.setString(2, "teacher");
//				pstmt.setString(3, "teacher@gmail.com");
//				pstmt.setString(4, "10010");
//				pstmt.setInt(5,  -1);
//				pstmt.addBatch();
//				
//				pstmt.executeBatch();
//			}
//		}catch(SQLException e){
//			e.printStackTrace();
//		}finally{
//			close(conn);
//		}
//	}
//}