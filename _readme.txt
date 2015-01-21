一、采购单
create table if not exists purchase(
id               integer    primary key   autoincrement,
sn               text(32)   ,
supplier_id      integer    ,
manager_id	     integer    ,
pay_type         integer    ,
pur_date         integer    ,
cost             real       ,
remark           text(64)   ,
status           integer
);

insert into purchase(sn, supplier_id,manager_id,pay_type,pur_date,cost,remark,status) values(?,?,?,?,?,?,?);
update purchase set sn=?, supplier_id=?,manager_id=?,pay_type=?,pur_date=?,cost=?,remark=?,status=? where id=?;

二、采购项
create table if not exists  purchase_item(
id               integer    primary key   autoincrement,
purchase_id      integer    ,
product_id	     integer    ,
num              integer    ,
price            real       
);

insert into purchase_item(purchase_id,product_id,num,price) values(?,?,?,?);
update purchase_item set purchase_id=?,product_id=?,num=?,price=? where id=?;

1. 日期时间问题
sqlite数据库中可以用integer类型来存储日期时间的毫秒值。在java程序中需要定义成long类型。

2. 价格问题
sqlite数据库中使用real类型来表示浮点数，在java实体类中要采用BigDecimal类型。