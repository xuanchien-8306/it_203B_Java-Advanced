package utils;

import org.jline.reader.LineReader;
import org.jline.reader.LineReaderBuilder;
import org.jline.terminal.Terminal;
import org.jline.terminal.TerminalBuilder;

import java.io.IOException;
import java.util.Scanner;

public class InputUtils {
    public static Scanner sc = new Scanner(System.in);
    private static LineReader lineReader;

    static {
        try {
            Terminal terminal = TerminalBuilder.builder()
                    .system(true)
                    .dumb(true)
                    .build();

            lineReader = LineReaderBuilder.builder()
                    .terminal(terminal)
                    .build();
        } catch (IOException e) {
            System.err.println("[Cảnh báo] Không thể khởi tạo JLine. Sẽ dùng Scanner làm dự phòng.");
        }
    }

    public static String readPassword(String prompt) {
        if (lineReader != null) {
            try {
                return lineReader.readLine(prompt, '*').trim();
            } catch (Exception e) {
                // Bỏ qua lỗi ngắt luồng
            }
        }

        System.out.print(prompt + " (Không hỗ trợ ẩn): ");
        System.out.flush();
        return sc.nextLine().trim();
    }

    public static String readLine(String prompt) {
        if (lineReader != null) {
            return lineReader.readLine(prompt).trim();
        }
        System.out.print(prompt);
        System.out.flush();
        return sc.nextLine().trim();
    }
}