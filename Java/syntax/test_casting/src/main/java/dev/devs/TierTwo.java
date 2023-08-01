package dev.devs;

public class TierTwo extends TierOne{
    public int field3;
    public int field4;

    public TierTwo() {
        super();
        this.field1 += 100;
        this.field2 += 100;
        this.field3 = 100;
        this.field4 = 100;
    }

    public TierTwo(int f1, int f2, int f3, int f4) {
        super(f1, f2);

        this.field1 += 100;
        this.field2 += 100;

        if (f3 < 100 || f3 > 199) f3 = (f3 % 100) + 100;
        if (f4 < 100 || f4 > 199) f4 = (f4 % 100) + 100;

        this.field3 = f3;
        this.field4 = f4;
    }

    @Override
    public void method1() {
        System.out.println("overridden in TierTwo");
    }

    public void method2() {
        System.out.println(("TierTwo method2"));
    }

}
