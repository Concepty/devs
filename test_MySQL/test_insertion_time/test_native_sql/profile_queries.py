import time
import subprocess
import sys
import os
import getpass

if len(sys.argv) == 2:
    INSERT_UNIT = int(sys.argv[1])
else:
    INSERT_UNIT = 100

TMP_SQL = 'tmp.sql'



if 0:
    # mysql -utest -p1234 test_IMDb < insert_by_100.sql 
    # how to give stdin to subprocess?

    start_time = time.time()
    p = subprocess.Popen(['mysql', '-utest', '-p1234', 'test_IMDb'], stdin = subprocess.PIPE)

    p.communicate(input = f'insert_by_{INSERT_UNIT}.sql')

    end_time = time.time()
    print(f'time spent(INSERT unit: {INSERT_UNIT}): {end_time - start_time}')

if 1:
    password = getpass.getpass('If you are not on test Database, Drop the operation.\nLOAD requires SUPER or SYSTEM_VARIABLES_ADMIN privilege(s). please insert root password: ')
    setup = 'SET GLOBAL local_infile=1;\n'
    query = r"LOAD DATA LOCAL INFILE './title.ratings.tsv' INTO TABLE Title_Rating FIELDS TERMINATED BY '\t' LINES TERMINATED BY '\n' IGNORE 1 LINES;" + '\n'

    query = r'''SET GLOBAL local_infile=1;
LOAD DATA LOCAL INFILE './title.ratings.tsv' INTO TABLE Title_Rating FIELDS TERMINATED BY '\t' LINES TERMINATED BY '\n' IGNORE 1 LINES;
'''

    # with open(TMP_SQL, 'wt') as t:
    #     t.write(setup)
    #     t.write(query)

    # ['mysql', '-utest', '-p', 'test_load', '<', TMP_SQL]
    # f'mysql -utest -p test_load < {TMP_SQL}'

    start_time = time.time()
    p = subprocess.Popen(['mysql', '--local-infile=1', '-uroot', '-p' + password, 'test_load'], 
                         stdin=subprocess.PIPE,
                         stdout=subprocess.PIPE,
                         stderr=subprocess.PIPE,
                         text=True)
    out, err = p.communicate(query)

    end_time = time.time()
    
    print('--out--\n' + out)
    print('--err--\n' + err)

    print(f'time spent on LOAD Title_Rating : {end_time - start_time}')
    # os.remove(TMP_SQL)