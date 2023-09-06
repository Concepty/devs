#!/Library/Frameworks/Python.framework/Versions/3.10/bin/python3

import sys
import os


if len(sys.argv) == 2:
    INSERT_UNIT = int(sys.argv[1])
else:
    INSERT_UNIT = 5


MODULE_DIR = os.path.dirname(os.path.abspath(__file__)) + '/'
DATA_SET_DIR = os.path.expanduser("~") + '/devs/Test_Data_Set/'
TSV_FILE = 'title.ratings.tsv'
OUT_FILE = 'split_TR_[N].tsv'


with open(MODULE_DIR + TSV_FILE) as tsv_file:
    tsv_file.readline()
    eof = False

    out_file_names = [OUT_FILE.replace('[N]', str(i+1)) for i in range(INSERT_UNIT)]

    out_files = [open(DATA_SET_DIR + fname, 'wt') for fname in out_file_names]

    while True:
        for out in out_files:
            line = tsv_file.readline()
            if not line: eof = True; break
            out.write(line)
        if eof: break

    
        