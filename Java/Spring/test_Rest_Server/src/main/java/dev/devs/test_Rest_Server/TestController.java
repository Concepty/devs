package dev.devs.test_Rest_Server;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {

    @GetMapping("/test")
    public String testMethod(@RequestParam(value = "member") String member) {
        /**
         * curl "http://localhost:8080/test?member=ABCDE"
         */

        TestObject to = new TestObject();
        to.setTestMember(member);
        return to.getTestMember();
    }
}
