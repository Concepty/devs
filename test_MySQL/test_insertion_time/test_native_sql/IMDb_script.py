# mysql-connector-python

import mysql.connector
import time
import sys
from optparse import OptionParser

CONFIG = {}

parser = OptionParser()
parser.add_option('--unit', '-u', help = 'insert unit',
                    dest = 'unit', type = 'int', default = 100)
parser.add_option('--JPA', help = 'if set, generate a script for JPA',
                   dest = 'JPA', action = 'store_true', default = False)

options, args = parser.parse_args()
CONFIG['INSERT UNIT'] = options.unit
CONFIG['JPA'] = options.JPA

def generate_sql_queries(unit: int, for_jpa: bool = False):
    out = []
    with open('title.ratings.tsv', 'rt') as f_in:
        f_in.readline() # ignore the first line
        if not for_jpa:
            out.append('drop table if exists Title_Rating;')
            out.append('CREATE TABLE Title_Rating (tconst VARCHAR(255) NOT NULL, averageRating DOUBLE PRECISION, numVotes INT, PRIMARY KEY (tconst));')
        while True:
            buff = []
            for _ in range(unit):
                line = f_in.readline()
                if not line: break
                id, rating, votes = line.rstrip().split('\t')
                buff.append(f'("{id}", {rating}, {votes})')
            if not buff: break
            out.append('INSERT INTO Title_Rating (tconst, averageRating, numVotes) VALUES ' + ', '.join(buff) + ';')
    file_name = f'insert_by_{unit}.sql' if not for_jpa else f'insert_by_{unit}_with_jpa.sql'
    with open(file_name, 'wt') as f_out:
        for line in out:
            f_out.write(line + '\n')
    return out

# test functions
def insert_by_queries(queries: list):
    cnx = mysql.connector.connect(user = 'test', password = '1234', host = 'localhost', database = 'test_IMDb')

    cursor = cnx.cursor()

    start_time = time.time()

    for query in queries:
        if query:
            cursor.execute(query)
            cnx.commit()

    # cnx.commit()

    end_time = time.time()

    print(f'time spent(INSERT unit: {CONFIG["INSERT UNIT"]}): {end_time - start_time}')

def insert_by_file():
    cnx = mysql.connector.connect(user = 'test', password = '1234', host = 'localhost', database = 'test_IMDb')
    cursor = cnx.cursor()

    start_time = time.time()

    with open(f'insert_by_{CONFIG["INSERT UNIT"]}.sql', 'rt') as f:
        while True:
            line = f.readline()
            if not line: break
            cursor.execute(line)
            cnx.commit()
    
    end_time = time.time()




if __name__ == '__main__':
    generate_sql_queries(CONFIG['INSERT UNIT'])

    """
    3 -> 60.7 secs
    10 -> 24 secs
    50 -> 10.3 sec
    100 -> 7.4 sec
    """
    # insert_by_queries(queries)
    # insert_by_file()