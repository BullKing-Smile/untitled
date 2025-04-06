package model;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Objects;

/**
 * @Author shenfei.wang@g42.ai
 * @Description
 * @Date 2025/2/24 23:06
 */
@Data
@AllArgsConstructor
public class Person2 {
    private String name;
    private Integer age;

    public Person2(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }


//    @Override
//    public boolean equals(Object o) {
//        if (this == o) return true;
//        if (!(o instanceof Person2 person2)) return false;
//        return Objects.equals(name, person2.name) && Objects.equals(age, person2.age);
//    }
//
//    @Override
//    public int hashCode() {
//        return Objects.hash(name, age);
//    }
}
