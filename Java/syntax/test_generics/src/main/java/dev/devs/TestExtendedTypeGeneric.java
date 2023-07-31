package dev.devs;

public class TestExtendedTypeGeneric <T extends TestInterface> {

    T t;

    public void assign(T t) {
        this.t = t;
    }

    public void testGenericMethod() {
        this.t.testMethod1();
    }
}
