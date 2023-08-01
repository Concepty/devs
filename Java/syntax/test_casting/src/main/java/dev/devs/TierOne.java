package dev.devs;

public class TierOne {

    public int field1;
    public int field2;

    public TierOne() {
        this.field1 = 0;
        this.field2 = 0;
    }
    public TierOne(int f1, int f2) {
        if (f1 < 0 || f1 > 99) f1 = f1 % 100;
        if (f2 < 0 || f2 > 99) f2 = f2 % 100;

        this.field1 = f1;
        this.field2 = f2;
    }

    public void method1() {
        System.out.println("TierOne method1");
    }

}
