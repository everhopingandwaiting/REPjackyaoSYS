package com.jack.dao;

import java.io.PrintStream;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.ResultSetHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import com.jack.common.SQLiteHelper;
import com.jack.entity.SalesItem;

public class SalesItemDao
{
  private QueryRunner run = new QueryRunner();
  private ScalarHandler<Integer> scalarHandler = new ScalarHandler();

  private ResultSetHandler<SalesItem> beanHeander = new ResultSetHandler()
  {
    public SalesItem handle(ResultSet rs) throws SQLException {
      if (rs.next()) {
        SalesItem item = new SalesItem();
        item.setId(rs.getInt("id"));
        item.setSales_id(rs.getInt("sales_id"));
        item.setProduct_id(rs.getInt("product_id"));
        item.setNum(rs.getInt("num"));
        item.setPrice(new BigDecimal(rs.getString("price")));

        item.setProdName(rs.getString("prodName"));
        item.setProdModel(rs.getString("prodModel"));
        item.setProdUnit(rs.getString("prodUnit"));
        return item;
      }
      return null;
    }
  };

  private ResultSetHandler<List<SalesItem>> beanListHandler = new ResultSetHandler()
  {
    public List<SalesItem> handle(ResultSet rs) throws SQLException {
      List list = new ArrayList();

      while (rs.next()) {
        SalesItem item = new SalesItem();
        item.setId(rs.getInt("id"));
        item.setSales_id(rs.getInt("sales_id"));
        item.setProduct_id(rs.getInt("product_id"));
        item.setNum(rs.getInt("num"));
        item.setPrice(new BigDecimal(rs.getString("price")));

        item.setProdName(rs.getString("prodName"));
        item.setProdModel(rs.getString("prodModel"));
        item.setProdUnit(rs.getString("prodUnit"));

        list.add(item);
      }
      return list;
    }
  };

  private ResultSetHandler<Map<String, Integer>> mapHandler = new ResultSetHandler() {
    public Map<String, Integer> handle(ResultSet rs) throws SQLException {
      Map map = new LinkedHashMap();
      while (rs.next()) {
        map.put(rs.getString(1), Integer.valueOf(rs.getInt(2)));
      }
      return map;
    }
  };

  public void save(SalesItem item)
  {
    String sql = "insert into sales_item(sales_id,product_id,num,price) values(?,?,?,?)";

    Object[] params = { 
      Integer.valueOf(item.getSales_id()), 
      Integer.valueOf(item.getProduct_id()), 
      Integer.valueOf(item.getNum()), 
      item.getPrice() };

    Connection conn = null;
    try {
      conn = SQLiteHelper.getConn();

      this.run.update(conn, sql, params);
    }
    catch (SQLException e) {
      e.printStackTrace();
    } finally {
      SQLiteHelper.close(conn);
    }
  }

  public void delete(int id)
  {
    String sql = "delete from sales_item where id=?";

    Connection conn = null;
    try {
      conn = SQLiteHelper.getConn();

      this.run.update(conn, sql, Integer.valueOf(id));
    }
    catch (SQLException e) {
      e.printStackTrace();
    } finally {
      SQLiteHelper.close(conn);
    }
  }

  public void deleteBySalesId(int sales_id) {
    String sql = "delete from sales_item where sales_id=?";

    Connection conn = null;
    try {
      conn = SQLiteHelper.getConn();

      this.run.update(conn, sql, Integer.valueOf(sales_id));
    }
    catch (SQLException e) {
      e.printStackTrace();
    } finally {
      SQLiteHelper.close(conn);
    }
  }

  public void update(SalesItem item) {
    String sql = "update sales_item set sales_id=?,product_id=?,num=?,price=? where id=?";

    Object[] params = { 
      Integer.valueOf(item.getSales_id()), 
      Integer.valueOf(item.getProduct_id()), 
      Integer.valueOf(item.getNum()), 
      item.getPrice(), 
      Integer.valueOf(item.getId()) };

    Connection conn = null;
    try {
      conn = SQLiteHelper.getConn();

      this.run.update(conn, sql, params);
    }
    catch (SQLException e) {
      e.printStackTrace();
    } finally {
      SQLiteHelper.close(conn);
    }
  }

  public SalesItem findOne(int id) {
    SalesItem item = null;
    String sql = "select si.*,p.name prodName,p.model prodModel,p.unit prodUnit from sales_item si join product p on si.product_id=p.id where si.id=?";

    Connection conn = null;
    try {
      conn = SQLiteHelper.getConn();

      item = (SalesItem)this.run.query(conn, sql, this.beanHeander, new Object[] { Integer.valueOf(id) });
    } catch (SQLException e) {
      e.printStackTrace();
    } finally {
      SQLiteHelper.close(conn);
    }

    return item;
  }

  public List<SalesItem> findBySalesId(int sales_id) {
    List list = new ArrayList();

    String sql = "select si.*,p.name prodName,p.model prodModel,p.unit prodUnit from sales_item si join product p on si.product_id=p.id where si.sales_id=?";

    Connection conn = null;
    try {
      conn = SQLiteHelper.getConn();

      list = (List)this.run.query(conn, sql, this.beanListHandler, new Object[] { Integer.valueOf(sales_id) });
    } catch (SQLException e) {
      e.printStackTrace();
    } finally {
      SQLiteHelper.close(conn);
    }

    return list;
  }

  public List<SalesItem> findAll() {
    List list = new ArrayList();

    String sql = "select si.*,p.name prodName,p.model prodModel,p.unit prodUnit from sales_item si join product p on si.product_id=p.id";

    Connection conn = null;
    try {
      conn = SQLiteHelper.getConn();

      list = (List)this.run.query(conn, sql, this.beanListHandler);
    } catch (SQLException e) {
      e.printStackTrace();
    } finally {
      SQLiteHelper.close(conn);
    }

    return list;
  }

  public long total()
  {
    long count = 0L;

    String sql = "select count(id) from sales_item";

    Connection conn = null;
    try {
      conn = SQLiteHelper.getConn();

      Integer temp = (Integer)this.run.query(conn, sql, this.scalarHandler);

      if (temp != null)
        count = temp.intValue();
    }
    catch (SQLException e) {
      e.printStackTrace();
    } finally {
      SQLiteHelper.close(conn);
    }

    return count;
  }

  public Map<String, Integer> totalByCategory() {
    Map map = new LinkedHashMap();

    String sql = "select c.name, sum(si.num)  from sales_item si  join product pr on si.product_id=pr.id  join category c on pr.cate_id=c.id  join sales s on si.sales_id=s.id  where s.status=2 group by c.id";

    Connection conn = null;
    try {
      conn = SQLiteHelper.getConn();
      map = (Map)this.run.query(conn, sql, this.mapHandler);
    } catch (SQLException e) {
      e.printStackTrace();
    } finally {
      SQLiteHelper.close(conn);
    }
    return map;
  }

  public static void main(String[] args) {
    SalesItemDao dao = new SalesItemDao();
    Map map = dao.totalByCategory();
    System.out.println(map);

    SalesItem item = dao.findOne(2);
    System.out.println(item);
  }
}