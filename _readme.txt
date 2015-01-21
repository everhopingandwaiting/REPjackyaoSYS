һ���ɹ���
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

�����ɹ���
create table if not exists  purchase_item(
id               integer    primary key   autoincrement,
purchase_id      integer    ,
product_id	     integer    ,
num              integer    ,
price            real       
);

insert into purchase_item(purchase_id,product_id,num,price) values(?,?,?,?);
update purchase_item set purchase_id=?,product_id=?,num=?,price=? where id=?;

1. ����ʱ������
sqlite���ݿ��п�����integer�������洢����ʱ��ĺ���ֵ����java��������Ҫ�����long���͡�

2. �۸�����
sqlite���ݿ���ʹ��real��������ʾ����������javaʵ������Ҫ����BigDecimal���͡�