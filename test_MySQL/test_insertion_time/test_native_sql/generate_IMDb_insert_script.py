# mysql-connector-python

import mysql.connector
import time
import sys



if len(sys.argv) == 3:
    INSERT_UNIT = int(sys.argv[1])
else:
    INSERT_UNIT = 100


def generate_sql_queries(DML_only = False):
    out = []
    with open('title.ratings.tsv', 'rt') as f_in:
        f_in.readline() # ignore the first line
        if not DML_only:
            out.append('drop table if exists Title_Rating;')
            out.append('CREATE TABLE Title_Rating (tconst VARCHAR(255) NOT NULL, averageRating DOUBLE PRECISION, numVotes INT, PRIMARY KEY (tconst));')
        while True:
            buff = []
            for cursor in range(INSERT_UNIT):
                line = f_in.readline()
                if not line: break
                id, rating, votes = line.rstrip().split('\t')
                buff.append(f'("{id}", {rating}, {votes})')
            if not buff: break
            out.append('INSERT INTO Title_Rating (tconst, averageRating, numVotes) VALUES ' + ', '.join(buff) + ';')
    with open(f'insert_by_{INSERT_UNIT}.sql', 'wt') as f_out:
        for line in out:
            f_out.write(line + '\n')
    return out

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

    print(f'time spent(INSERT unit: {INSERT_UNIT}): {end_time - start_time}')

def insert_by_file():
    cnx = mysql.connector.connect(user = 'test', password = '1234', host = 'localhost', database = 'test_IMDb')
    cursor = cnx.cursor()

    start_time = time.time()

    with open(f'insert_by_{INSERT_UNIT}.sql', 'rt') as f:
        while True:
            line = f.readline()
            if not line: break
            cursor.execute(line)
            cnx.commit()
    
    end_time = time.time()

    print(f'time spent(INSERT unit: {INSERT_UNIT}): {end_time - start_time}')



if __name__ == '__main__':
    if len(sys.argv) == 3 and sys.argv[2] == 'true':
        generate_sql_queries(True)
    else:
        queries = generate_sql_queries()


    """
    3 -> 60.7 secs
    10 -> 24 secs
    50 -> 10.3 sec
    100 -> 7.4 sec
    """
    # insert_by_queries(queries)
    # insert_by_file()