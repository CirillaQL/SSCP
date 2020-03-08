/*创建表空间*/
CREATE TABLESPACE Hotel
         LOGGING
         DATAFILE '/u01/app/oracle/oradata/XE/Hotel.DBF'
         SIZE 500M
         AUTOEXTEND ON
         NEXT 32M MAXSIZE UNLIMITED
         EXTENT MANAGEMENT LOCAL;

--创建经理角色
create role The_manager;
--赋予经理角色全部的权限
grant connect,resource,dba to The_manager;

--创建默认经理用户
create user hotel_manager identified by 123456 default tablespace Hotel;
--将经理角色赋给该用户
grant The_manager to hotel_manager;

--工资表
create table salary(
    job varchar2(15) not null primary key,
    salary number(10,2) not null
);

--员工
create table employee(
    id varchar2(10) not null primary key,
    employee_name varchar2(10) not null,
    gender varchar2(2) not null,
    job varchar2(15) not null references salary(job),
    phone_number varchar2(15),
    resumme clob,
    photo blob
);

--员工评价
create table employee_grade(
    id varchar2(10) not null references employee(id),
    time date not null,
    grade varchar2(10) not null,
    description clob
);

--员工工作时间安排
create table work_time(
    id varchar2(10) not null references employee(id),
    begin_time date not null,
    end_time date not null
);

--财政记录
create table record(
    record_id varchar2(10) not null primary key,
    employee_id varchar2(10) not null references employee(id),
    amount number(20,2) not null,
    record_date date not null
);

--vip
create table vip(
    id varchar2(10) not null primary key,
    vip_name varchar2(10) not null,
    phone_number varchar2(15),
    apply_date date not null,
    balance number(10,2) not null
);

--vip充值记录
create table vip_record(
    id varchar2(10) not null references vip(id),
    amount number(10,2) not null
);

--房间
create table room(
    room_number varchar2(10) not null primary key,
    room_type varchar2(10) not null,
    room_size number(5,1) not null,
    current_state varchar2(5) not null,
    photo blob
);

--房间价格
create table price(
    room_type varchar2(10) not null,
    price number(5,2) not null
);

--房间优惠价格
create table sale_policy(
    room_number varchar2(10) not null references room(room_number),
    off_precent number(5,2),
    vip_off_precent number(5,2),
    description clob
);

--订单
create table book_order(
    order_id varchar2(10) not null primary key,
    employee_name varchar2(10) not null,
    cus_name varchar2(10) not null,
    cus_phone_number varchar2(15),
    room_number varchar2(10) not null references room(room_number),
    create_time date not null,
    book_time_begin date not null,
    book_time_end date not null,
    check_in_time date,
    check_out_time date,
    vip_id varchar2(10) references vip(id),
    origin_price number(5,2) not null,
    actuall_price number(5,2) not null
);

--创建普通员工角色
create role worker;
--赋予普通员工创建修改删除订单的权限
grant select,delete,update,insert on hotel_manager.vip_record to worker;
grant select,delete,update,insert on hotel_manager.vip to worker;
grant select,delete,update,insert on hotel_manager.book_order to worker;


--创建财务角色
create role accountant;
--赋予财务修改财政的权限
grant select,delete,update,insert on hotel_manager.record to accountant;

insert into HOTEL_MANAGER.room (room_number,room_type,room_size,current_state) values('A01','Big',30.2,'Empty');
insert into HOTEL_MANAGER.room (room_number,room_type,room_size,current_state) values('A02','Big',31.5,'Empty');
insert into HOTEL_MANAGER.room (room_number,room_type,room_size,current_state) values('A03','Medium',25,'Empty');
insert into HOTEL_MANAGER.room (room_number,room_type,room_size,current_state) values('B01','Medium',26,'Empty');
insert into HOTEL_MANAGER.room (room_number,room_type,room_size,current_state) values('B02','Small',20,'Empty');
insert into HOTEL_MANAGER.room (room_number,room_type,room_size,current_state) values('C01','Small',19,'Empty');
commit;
