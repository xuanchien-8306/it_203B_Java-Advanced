package Session_08.Bai_03;

public class Main {
    public static void main(String[] args) {

        Light light = new Light();
        AC ac = new AC();

        RemoteControl remote = new RemoteControl();

        // Gán nút
        remote.setCommand(1, new LightOnCommand(light));
        remote.setCommand(2, new LightOffCommand(light));
        remote.setCommand(3, new ACSetTemperatureCommand(ac, 26));

        // Thao tác
        remote.pressButton(1); // bật đèn
        remote.pressButton(2); // tắt đèn
        remote.pressUndo();    // undo

        remote.pressButton(3); // set 26
        remote.pressUndo();    // về 25
    }
}
