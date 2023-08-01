package dev.devs;

public class Main {
    public static void main(String[] args) {
        /**
         * Checks
         *
         * Setting
         * TierTwo extends TierOne
         *
         * Check point1. Property of upcasted interface.
         * 1. method add in TierTwo is hidden
         * 2. method overridden in TierTwo is not hidden
         * 3. fields added in TierTwo are hidden
         * 4. fields changed in TierTwo are not hidden
         *
         */

        TierOne t1 = (TierOne) new TierTwo(101, 102, 103, 104);
        t1.method1();
//        t1.method2(); // cannot resolve
//        t1.field3; // cannot resolve
        System.out.println("field2 of TierOne(0~100), " +
                "which is upcasted from TierTwo(100~200): " + Integer.toString(t1.field2));


    }
}