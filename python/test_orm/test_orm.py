"""
$ sudo apt install mariadb-server-10.6
//TODO Connector to mariadb is missing. 
Error | ModuleNotFoundError: No module named 'MySQLdb'

$ sudo mariadb
MariaDB > create user 'test'@'localhost' identified by 'xxx';
MariaDB > create database test_orm;
MariaDB > grant all privileges on test_orm.* to 'test'@'localhost';
MariaDB > flush privileges;
MariaDB > create table test_table (id INT, name VARCHAR(20));


"""

from sqlalchemy import create_engine, String
from sqlalchemy.orm import DeclarativeBase, Mapped, mapped_column, Session

engine = create_engine("mariadb://test:xxx@localhost/test_orm")

class Test_Table(DeclarativeBase):
    __tablename__ = "test_table"
    id: Mapped[int] = mapped_column
    name: Mapped[str] = mapped_column(String(20))

with Session(engine) as sess:
    test = Test_Table(5, 'abc')
    sess.add(test)
    sess.commit()
    print('succeeded to add')