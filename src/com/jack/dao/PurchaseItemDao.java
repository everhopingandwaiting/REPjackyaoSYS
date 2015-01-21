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
import com.jack.entity.PurchaseItem;

public class PurchaseItemDao
{
  private QueryRunner run = new QueryRunner();
  private ScalarHandler<Integer> scalarHandler = new ScalarHandler();

  private ResultSetHandler<PurchaseItem> beanHeander = new ResultSetHandler()
  {
    public PurchaseItem handle(ResultSet rs) throws SQLException {
      if (rs.next()) {
        PurchaseItem item = new PurchaseItem();
        item.setId(rs.getInt("id"));
        item.setPurchase_id(rs.getInt("purchase_id"));
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

  private ResultSetHandler<List<PurchaseItem>> beanListHandler = new ResultSetHandler()
  {
    public List<PurchaseItem> handle(ResultSet rs) throws SQLException {
      List list = new ArrayList();

      while (rs.next()) {
        PurchaseItem item = new PurchaseItem();
        item.setId(rs.getInt("id"));
        item.setPurchase_id(rs.getInt("purchase_id"));
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

  public void save(PurchaseItem item)
  {
    String sql = "insert into purchase_item(purchase_id,product_id,num,price) values(?,?,?,?)";

    Object[] params = { 
      Integer.valueOf(item.getPurchase_id()), 
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
    String sql = "delete from purchase_item where id=?";

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

  public void deleteByPurchaseId(int purchase_id) {
    String sql = "delete from purchase_item where purchase_id=?";

    Connection conn = null;
    try {
      conn = SQLiteHelper.getConn();

      this.run.update(conn, sql, Integer.valueOf(purchase_id));
    }
    catch (SQLException e) {
      e.printStackTrace();
    } finally {
      SQLiteHelper.close(conn);
    }
  }

  public void update(PurchaseItem item) {
    String sql = "update purchase_item set purchase_id=?,product_id=?,num=?,price=? where id=?";

    Object[] params = { 
      Integer.valueOf(item.getPurchase_id()), 
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

  public PurchaseItem findOne(int id) {
    PurchaseItem item = null;

    String sql = "select pi.*,p.name prodName,p.model prodModel,p.unit prodUnit from purchase_item pi join product p on pi.product_id=p.id where pi.id=?";

    Connection conn = null;
    try {
      conn = SQLiteHelper.getConn();

      item = (PurchaseItem)this.run.query(conn, sql, this.beanHeander, new Object[] { Integer.valueOf(id) });
    } catch (SQLException e) {
      e.printStackTrace();
    } finally {
      SQLiteHelper.close(conn);
    }

    return item;
  }

  public List<PurchaseItem> findByPurchaseId(int purchase_id) {
    List list = new ArrayList();

    String sql = "select pi.*,p.name prodName,p.model prodModel,p.unit prodUnit from purchase_item pi join product p on pi.product_id=p.id where pi.purchase_id=?";

    Connection conn = null;
    try {
      conn = SQLiteHelper.getConn();

      list = (List)this.run.query(conn, sql, this.beanListHandler, new Object[] { Integer.valueOf(purchase_id) });
    } catch (SQLException e) {
      e.printStackTrace();
    } finally {
      SQLiteHelper.close(conn);
    }

    return list;
  }

  public List<PurchaseItem> findAll() {
    List list = new ArrayList();

    String sql = "select pi.*,p.name prodName,p.model prodModel,p.unit prodUnit from purchase_item pi join product p on pi.product_id=p.id";

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

    String sql = "select count(id) from purchase_item";

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

  public Map<String, Integer> totalByCategory(int status) {
    Map map = new LinkedHashMap();

    String sql = "select c.name, sum(pi.num)  from purchase_item pi  join product pr on pi.product_id=pr.id  join category c on pr.cate_id=c.id  join purchase p on pi.purchase_id=p.id  where p.status=? group by c.id";

    Connection conn = null;
    try {
      conn = SQLiteHelper.getConn();
      map = (Map)this.run.query(conn, sql, this.mapHandler, new Object[] { Integer.valueOf(status) });
    } catch (SQLException e) {
      e.printStackTrace();
    } finally {
      SQLiteHelper.close(conn);
    }
    return map;
  }

  public Map<String, Integer> totalByProduct(int status) {
    Map map = new LinkedHashMap();

    String sql = "select pr.name, sum(pi.num)  from purchase_item pi  join product pr on pi.product_id=pr.id  join purchase p on pi.purchase_id=p.id  where p.status=? group by pr.id";

    Connection conn = null;
    try {
      conn = SQLiteHelper.getConn();
      map = (Map)this.run.query(conn, sql, this.mapHandler, new Object[] { Integer.valueOf(status) });
    } catch (SQLException e) {
      e.printStackTrace();
    } finally {
      SQLiteHelper.close(conn);
    }
    return map;
  }

  public static void main(String[] args) {
    PurchaseItemDao dao = new PurchaseItemDao();
    Map map = dao.totalByCategory(2);
    System.out.println(map);
  }
}