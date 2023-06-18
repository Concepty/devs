package test_closure;

interface OneMethodInterface {
    public int run(String str1, String str2);
}

class MainClass {
    public static void main(String args[]) {
        new WorkingClass().work();
    }
}

class WorkingClass {

    public String const_str = "const";

    public void exec(OneMethodInterface interface1, OneMethodInterface interface2, String s1, String s2) {
        interface1.run(s1, s2);
        interface2.run(s1, s2);
    }

    public void work() {
        OneMethodInterface impl1 = (s1, s2) -> {

            System.out.println(s1);
            System.out.println(s2);
            System.out.println(this.const_str);
            this.const_str = this.const_str + "+";
            System.out.println("hashCode in impl1: " + this.const_str.hashCode());
            return 0;
        };

        OneMethodInterface impl2 = (s1, s2) -> {
            System.out.println(s2);
            System.out.println(s1);
            System.out.println(const_str);
            const_str = const_str + "+";
            System.out.println("hashCode in impl2: " + this.const_str.hashCode());
            return 0;
        };
        System.out.println("hashCode in work: " + this.const_str.hashCode());
        impl1.run("a", "b");
        impl2.run("a", "b");
        System.out.println("const_str in work: " + const_str);
        return;
    }
}

