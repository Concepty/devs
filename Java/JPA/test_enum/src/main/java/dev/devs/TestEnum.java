package dev.devs;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name="enum_table")
public class TestEnum {

    public enum MyEnum {
        ENUM1, ENUM2, ENUM3
    }

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private int id;

    @Column(name="ordinal_enum")
    @Enumerated(EnumType.ORDINAL)
    @Getter @Setter
    private MyEnum myOridinalEnum;

    @Column(name="string_enum")
    @Enumerated(EnumType.STRING)
    @Getter @Setter
    private MyEnum myStringEnum;

    public TestEnum(MyEnum ordi, MyEnum str) {
        myOridinalEnum = ordi;
        myStringEnum = str;
    }

}
