package Session_02.Bai_02;

@FunctionalInterface
interface PasswordValidator {
    boolean validate(String password);
}
