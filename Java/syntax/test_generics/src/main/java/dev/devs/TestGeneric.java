package dev.devs;

public class TestGeneric<T> {

    T t;

    public void assign(T t) {
        this.t = t;
    }

    public void testGenericMethod() {
//        this.t.testMethod1(); // Compile Error - symbol not resolved, check TestExtendedTypeGeneric
        System.out.println(this.t.hashCode());
    }
}
