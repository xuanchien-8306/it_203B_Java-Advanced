package Session_01.Bai_03;

import Session_01.Bai_05.InvalidAgeException;

public class User {

    private int age;

    public void setAge(int age) throws InvalidAgeException {
        if (age < 0) {
            throw new InvalidAgeException("Tuổi không hợp lệ! Tuổi không thể âm.");
        }
        this.age = age;
    }

    public int getAge() {
        return age;
    }
}