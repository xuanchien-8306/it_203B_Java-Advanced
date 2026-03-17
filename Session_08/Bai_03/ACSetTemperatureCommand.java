package Session_08.Bai_03;

public class ACSetTemperatureCommand implements Command {
    private AC ac;
    private int newTemp;
    private int prevTemp;

    public ACSetTemperatureCommand(AC ac, int newTemp) {
        this.ac = ac;
        this.newTemp = newTemp;
    }

    public void execute() {
        prevTemp = ac.getTemperature(); // lưu trạng thái cũ
        ac.setTemperature(newTemp);
    }

    public void undo() {
        ac.setTemperature(prevTemp);
        System.out.println("Undo: Điều hòa: Nhiệt độ = " + prevTemp);
    }
}
