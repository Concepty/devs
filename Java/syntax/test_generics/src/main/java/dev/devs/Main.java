package dev.devs;

public class Main {
    public static void main(String[] args) {
        TestInterface t1 = new TestInterface();
        TestInterface t2 = new TestInterface();

        TestGeneric<TestInterface> tg = new TestGeneric();
        tg.assign(t1);
        tg.testGenericMethod();

        TestExtendedTypeGeneric<TestInterface> tetg = new TestExtendedTypeGeneric<>();
        tetg.assign(t2);
        tetg.testGenericMethod();
    }
}