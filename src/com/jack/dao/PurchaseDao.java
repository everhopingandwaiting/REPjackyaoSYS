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
import com.jack.entity.Purchase;

public class PurchaseDao
{
  private QueryRunner run = new QueryRunner();
  private ScalarHandler<Integer> scalarHandler = new ScalarHandler();

  private ResultSetHandler<Purchase> beanHeander = new ResultSetHandler()
  {
    public Purchase handle(ResultSet rs) throws SQLException {
      if (rs.next()) {
        Purchase purc = new Purchase();
        purc.setId(rs.getInt("id"));
        purc.setSn(rs.getString("sn"));
        purc.setSupplier_id(rs.getInt("supplier_id"));
        purc.setManager_id(rs.getInt("manager_id"));
        purc.setPay_type(rs.getInt("pay_type"));
        purc.setPur_date(rs.getLong("pur_date"));
        purc.setCost(new BigDecimal(rs.getString("cost")));
        purc.setRemark(rs.getString("remark"));
        purc.setStatus(rs.getInt("status"));

        purc.setSuppName(rs.getString("suppName"));
        purc.setMgrName(rs.getString("mgrName"));

        return purc;
      }
      return null;
    }
  };

  private ResultSetHandler<List<Purchase>> beanListHandler = new ResultSetHandler()
  {
    public List<Purchase> handle(ResultSet rs) throws SQLException {
      List list = new ArrayList();

      while (rs.next()) {
        Purchase purc = new Purchase();
        purc.setId(rs.getInt("id"));
        purc.setSn(rs.getString("sn"));
        purc.setSupplier_id(rs.getInt("supplier_id"));
        purc.setManager_id(rs.getInt("manager_id"));
        purc.setPay_type(rs.getInt("pay_type"));
        purc.setPur_date(rs.getLong("pur_date"));
        purc.setCost(new BigDecimal(rs.getString("cost")));
        purc.setRemark(rs.getString("remark"));
        purc.setStatus(rs.getInt("status"));

        purc.setSuppName(rs.getString("suppName"));
        purc.setMgrName(rs.getString("mgrName"));

        list.add(purc);
      }
      return list;
    }
  };

  public void save(Purchase purc)
  {
    String sql = "insert into purchase(sn, supplier_id,manager_id,pay_type,pur_date,cost,remark,status) values(?,?,?,?,?,?,?,?)";

    Object[] params = { purc.getSn(), 
      Integer.valueOf(purc.getSupplier_id()), 
      Integer.valueOf(purc.getManager_id()), 
      Integer.valueOf(purc.getPay_type()), 
      Long.valueOf(purc.getPur_date()), 
      purc.getCost(), 
      purc.getRemark(), 
      Integer.valueOf(purc.getStatus()) };

    Connection conn = null;
    try {
      conn = SQLiteHelper.getConn();

      this.run.update(conn, sql, params);

      Integer temp = (Integer)this.run.query(conn, "select last_insert_rowid()", this.scalarHandler);
      if (temp != null)
        purc.setId(temp.intValue());
    }
    catch (SQLException e) {
      e.printStackTrace();
    } finally {
      SQLiteHelper.close(conn);
    }
  }

  public void delete(int id)
  {
    String sql = "delete from purchase where id=?";

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

  public void update(Purchase purc) {
    String sql = "update purchase set sn=?, supplier_id=?,manager_id=?,pay_type=?,pur_date=?,cost=?,remark=?,status=? where id=?";

    Object[] params = { purc.getSn(), 
      Integer.valueOf(purc.getSupplier_id()), 
      Integer.valueOf(purc.getManager_id()), 
      Integer.valueOf(purc.getPay_type()), 
      Long.valueOf(purc.getPur_date()), 
      purc.getCost(), 
      purc.getRemark(), 
      Integer.valueOf(purc.getStatus()), 
      Integer.valueOf(purc.getId()) };

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

  public Purchase findOne(int id) {
    Purchase purc = null;

    String sql = "select p.*,s.name suppName ,m.lname mgrName from purchase p join supplier s on p.supplier_id=s.id join manager m on p.manager_id = m.id where p.id=?";

    Connection conn = null;
    try {
      conn = SQLiteHelper.getConn();

      purc = (Purchase)this.run.query(conn, sql, this.beanHeander, new Object[] { Integer.valueOf(id) });
    } catch (SQLException e) {
      e.printStackTrace();
    } finally {
      SQLiteHelper.close(conn);
    }

    return purc;
  }

  public List<Purchase> findAll() {
    List list = new ArrayList();

    String sql = "select p.*,s.name suppName,m.lname mgrName from purchase p join supplier s on p.supplier_id=s.id join manager m on p.manager_id = m.id";

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

  public List<Purchase> findByCondition(int status, long start_date, long end_date) {
    List list = new ArrayList();

    StringBuilder sql = new StringBuilder("select p.*,s.name suppName,m.lname mgrName from purchase p ");
    sql.append(" join supplier s on p.supplier_id=s.id ");
    sql.append(" join manager m on p.manager_id=m.id ");
    sql.append(" where 1=1 ");

    List params = new ArrayList();
    if (status >= 0) {
      sql.append(" and p.status=? ");
      params.add(Integer.valueOf(status));
    }

    if (start_date > 0L) {
      sql.append(" and p.pur_date>=? ");
      params.add(Long.valueOf(start_date));
    }

    if (end_date > 0L) {
      sql.append(" and p.pur_date<? ");
      params.add(Long.valueOf(end_date));
    }

    Connection conn = null;
    try {
      conn = SQLiteHelper.getConn();

      list = (List)this.run.query(conn, sql.toString(), this.beanListHandler, params.toArray());
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

    String sql = "select count(id) from purchase";

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
}