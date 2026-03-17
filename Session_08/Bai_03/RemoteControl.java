package Session_08.Bai_03;

import java.util.HashMap;
import java.util.Map;

public class RemoteControl {
    private Map<Integer, Command> buttons = new HashMap<>();
    private UndoManager undoManager = new UndoManager();

    public void setCommand(int slot, Command command) {
        buttons.put(slot, command);
        System.out.println("Đã gán command cho nút " + slot);
    }

    public void pressButton(int slot) {
        Command cmd = buttons.get(slot);
        if (cmd != null) {
            undoManager.executeCommand(cmd);
        } else {
            System.out.println("Nút chưa được gán");
        }
    }

    public void pressUndo() {
        undoManager.undo();
    }
}