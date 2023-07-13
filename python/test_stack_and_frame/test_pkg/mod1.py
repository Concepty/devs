import inspect
from test_pkg.mod2 import print_trace2

def print_test_pkg_m1_inspect_info():
    stack = inspect.stack()
    trace = inspect.trace()

    print('printing test_pkg_m1_stack...')
    for s in stack:
        print(s)

def print_trace1():
    print_trace2()