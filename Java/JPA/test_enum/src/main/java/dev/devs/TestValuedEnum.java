package dev.devs;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.stream.Stream;

@Entity
@Table(name="valued_enum")
public class TestValuedEnum {

    public enum MyValuedEnum {
        ENUM1("EN1"), ENUM2("EN2"), ENUM3("EN3");

        @Getter @Setter
        private String enumVal;

        private MyValuedEnum(String value) {
            this.enumVal = value;
        }

        public String getValue() {
            return enumVal;
        }

        // reference: https://www.baeldung.com/jpa-persisting-enums-in-jpa
        public static MyValuedEnum of(String value) {
            return Stream.of(MyValuedEnum.values())
                    .filter(p -> p.getValue().equals(value))
                    .findFirst()
                    .orElseThrow(IllegalArgumentException::new);
        }
    }

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private int id;

    @Column(name="enum")
    private String ev;

    @Transient
    @Getter @Setter
    private MyValuedEnum e;

    @PostLoad
    void postLoadCB() {
        this.e = MyValuedEnum.of(this.ev);
    }
    @PrePersist
    void prePersistCB() {
        this.ev = e.getValue();
    }

    public TestValuedEnum() {}

    public TestValuedEnum(MyValuedEnum e) {
        this.e = e;
    }
}
