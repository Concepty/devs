"""
$ sudo apt install mysql-server
//TODO Connector to mysql is missing. 
Error | ModuleNotFoundError: No module named 'MySQLdb'

$ sudo mysql
mysql> create user 'test'@'localhost' identified by 'xxx';
mysql> create database test_orm;
mysql> grant all privileges on test_orm.* to 'test'@'localhost';
mysql> flush privileges;
mysql> create table test_orm.test_table (id INT, name VARCHAR(20));
mysql> desc test_orm.test_table;
"""

from sqlalchemy import create_engine, String
from sqlalchemy.orm import DeclarativeBase, Mapped, mapped_column, Session

engine = create_engine("mysql://test:xxx@localhost/test_orm")

class Test_Table(DeclarativeBase):
    __tablename__ = "test_table"
    id: Mapped[int] = mapped_column
    name: Mapped[str] = mapped_column(String(20))

with Session(engine) as sess:
    test = Test_Table(5, 'abc')
    sess.add(test)
    sess.commit()
    print('succeeded to add')