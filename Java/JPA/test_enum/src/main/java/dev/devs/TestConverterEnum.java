package dev.devs;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.stream.Stream;

@Entity
@Table(name="converter_enum")
public class TestConverterEnum {
    public enum ConvertedEnum {
        ENUM1("C_EN1"), ENUM2("C_EN2"), ENUM3("C_EN3");

        private String enumVal;
        
        private ConvertedEnum(String value) {
            this.enumVal = value;
        }

        public String getVal() {
            return this.enumVal;
        }
    }

    @Converter(autoApply = true)
    public class MyConverter implements AttributeConverter<ConvertedEnum, String> { 
        // Orm object type, database type
        @Override
        public String convertToDatabaseColumn(ConvertedEnum cev) {
            if (cev == null) return null;
            return cev.getVal();
        }
        @Override
        public ConvertedEnum convertToEntityAttribute (String value) {
            if (value == null) return null;
            return Stream.of(ConvertedEnum.values())
                .filter(v -> v.getVal().equals(value))
                .findFirst()
                .orElseThrow(IllegalArgumentException::new);
        }
        public MyConverter() {}
    }

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private int id;

    private ConvertedEnum cev;

    public TestConverterEnum() {}

    public TestConverterEnum(ConvertedEnum cev) {
        this.cev = cev;
    }
}
