class MyClass:
    def __init__(self):
        self._values = (0, 0, 0)

    @property
    def values(self):
        return self._values

    @values.setter
    def values(self, *args):
        if len(args) != 3:
            raise ValueError("expected 3 values")
        self._values = tuple(args)

obj = MyClass()
obj.values = 1, 2, 3
print(obj.values)  # output: (1, 2, 3)

obj.values = 4, 5, 6, 7  # Raises a ValueError