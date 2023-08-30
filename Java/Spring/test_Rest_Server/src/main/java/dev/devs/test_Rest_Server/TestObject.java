package dev.devs.test_Rest_Server;

import lombok.Getter;
import lombok.Setter;

public class TestObject {
    @Getter @Setter
    private String testMember;

    public void printMember() {
        System.out.println("Member of TestObject is " + testMember);
    }


}
