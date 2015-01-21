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
import com.jack.entity.Sales;

public class SalesDao
{
  private QueryRunner run = new QueryRunner();
  private ScalarHandler<Integer> scalarHandler = new ScalarHandler();

  private ResultSetHandler<Sales> beanHeander = new ResultSetHandler()
  {
    public Sales handle(ResultSet rs) throws SQLException {
      if (rs.next()) {
        Sales sale = new Sales();
        sale.setId(rs.getInt("id"));
        sale.setSn(rs.getString("sn"));
        sale.setCustomer_id(rs.getInt("customer_id"));
        sale.setManager_id(rs.getInt("manager_id"));
        sale.setPay_type(rs.getInt("pay_type"));
        sale.setSale_date(rs.getLong("sale_date"));
        sale.setCost(new BigDecimal(rs.getString("cost")));
        sale.setRemark(rs.getString("remark"));
        sale.setStatus(rs.getInt("status"));

        sale.setCustName(rs.getString("custName"));
        sale.setMgrName(rs.getString("mgrName"));

        return sale;
      }
      return null;
    }
  };

  private ResultSetHandler<List<Sales>> beanListHandler = new ResultSetHandler()
  {
    public List<Sales> handle(ResultSet rs) throws SQLException {
      List list = new ArrayList();

      while (rs.next()) {
        Sales sale = new Sales();
        sale.setId(rs.getInt("id"));
        sale.setSn(rs.getString("sn"));
        sale.setCustomer_id(rs.getInt("customer_id"));
        sale.setManager_id(rs.getInt("manager_id"));
        sale.setPay_type(rs.getInt("pay_type"));
        sale.setSale_date(rs.getLong("sale_date"));
        sale.setCost(new BigDecimal(rs.getString("cost")));
        sale.setRemark(rs.getString("remark"));
        sale.setStatus(rs.getInt("status"));

        sale.setCustName(rs.getString("custName"));
        sale.setMgrName(rs.getString("mgrName"));

        list.add(sale);
      }
      return list;
    }
  };

  public void save(Sales purc)
  {
    String sql = "insert into sales(sn,customer_id,manager_id,pay_type,sale_date,cost,remark,status) values(?,?,?,?,?,?,?,?);";

    Object[] params = { purc.getSn(), 
      Integer.valueOf(purc.getCustomer_id()), 
      Integer.valueOf(purc.getManager_id()), 
      Integer.valueOf(purc.getPay_type()), 
      Long.valueOf(purc.getSale_date()), 
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
    String sql = "delete from sales where id=?";

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

  public void update(Sales purc) {
    String sql = "update sales set sn=?,customer_id=?,manager_id=?,pay_type=?,sale_date=?,cost=?,remark=?,status=? where id=?";

    Object[] params = { purc.getSn(), 
      Integer.valueOf(purc.getCustomer_id()), 
      Integer.valueOf(purc.getManager_id()), 
      Integer.valueOf(purc.getPay_type()), 
      Long.valueOf(purc.getSale_date()), 
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

  public Sales findOne(int id) {
    Sales purc = null;

    String sql = "select s.*,c.name custName ,m.lname mgrName from sales s join customer c on s.customer_id=c.id join manager m on s.manager_id = m.id where s.id=?";

    Connection conn = null;
    try {
      conn = SQLiteHelper.getConn();

      purc = (Sales)this.run.query(conn, sql, this.beanHeander, new Object[] { Integer.valueOf(id) });
    } catch (SQLException e) {
      e.printStackTrace();
    } finally {
      SQLiteHelper.close(conn);
    }

    return purc;
  }

  public List<Sales> findAll() {
    List list = new ArrayList();

    String sql = "select s.*,c.name custName ,m.lname mgrName from sales s join customer c on s.customer_id=c.id join manager m on s.manager_id = m.id";

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

  public List<Sales> findByCondition(int status, long start_date, long end_date) {
    List list = new ArrayList();

    StringBuilder sql = new StringBuilder("select s.*,c.name custName,m.lname mgrName from sales s ");
    sql.append(" join customer c on s.customer_id=c.id ");
    sql.append(" join manager m on s.manager_id=m.id ");
    sql.append(" where 1=1 ");

    List params = new ArrayList();
    if (status >= 0) {
      sql.append(" and s.status=? ");
      params.add(Integer.valueOf(status));
    }

    if (start_date > 0L) {
      sql.append(" and s.sale_date>=? ");
      params.add(Long.valueOf(start_date));
    }

    if (end_date > 0L) {
      sql.append(" and s.sale_date<? ");
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

    String sql = "select count(id) from sales";

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