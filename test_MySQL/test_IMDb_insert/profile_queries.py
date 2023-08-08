import time
import subprocess
import sys

if len(sys.argv) == 2:
    INSERT_UNIT = int(sys.argv[1])
else:
    INSERT_UNIT = 100

start_time = time.time()

# mysql -utest -p1234 test_IMDb < insert_by_100.sql 
# how to give stdin to subprocess?

p = subprocess.Popen(['mysql', '-utest', '-p1234', 'test_IMDb'], stdin = subprocess.PIPE)

p.communicate(input = f'insert_by_{INSERT_UNIT}.sql')

end_time = time.time()
print(f'time spent(INSERT unit: {INSERT_UNIT}): {end_time - start_time}')