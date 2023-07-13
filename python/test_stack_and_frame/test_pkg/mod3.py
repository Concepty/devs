import inspect


def print_trace3():
    cf = inspect.currentframe()
    
    outframe = inspect.getouterframes(cf)

    print('printing outerframes')
    for outf in outframe:
        print(outf)
