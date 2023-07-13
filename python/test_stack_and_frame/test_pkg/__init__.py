import inspect

def print_test_pkg_inspect_info():
    stack = inspect.stack()
    trace = inspect.trace()

    print('printing test_pkg_init_stack...')
    for s in stack:
        print(s)
