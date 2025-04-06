package study;

import model.Animal;
import model.Dog;
import model.Person;

/**
 * @Author shenfei.wang@g42.ai
 * @Description
 * @Date 2025/2/24 20:01
 */
public class Main {
    public static void main(String[] args) {
        var dog = new Dog();
        speak(dog);
        System.out.println("--------------------------");
        var person = new Person("Tom", 21);
        var person2 = new Person("Tom", 21);
        System.out.println(person);
        System.out.println(person.equals(person2));
        System.out.println(person.hashCode());
        System.out.println(person2.hashCode());

    }

    public static void speak(Animal animal) {
        if (animal instanceof Dog dog) {
            dog.spark();
        }
    }

}
