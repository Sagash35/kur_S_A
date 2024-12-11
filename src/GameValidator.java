import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;

public class GameValidator {
    private int currentNoteIndex = 0;
    private int errorsCount = 0;
    private int[] notesToPlay;
    private String inputFile;
    private Scanner scanner;

    public GameValidator(String inputFile) {
        this.inputFile = inputFile;
        try {
            int i = 0;
            scanner = new Scanner(new File(this.inputFile));
            int n = scanner.nextInt();
            notesToPlay = new int[n];
            while (scanner.hasNextInt() && i < n) {
                notesToPlay[i] = scanner.nextInt();
                i++;
            }
            scanner.close();
        } catch (FileNotFoundException e) {
            System.out.println("Файл не найден: " + e.getMessage());
        } catch (IOException e) {
            System.out.println("Ошибка ввода/вывода: " + e.getMessage());
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }
    }

    public boolean checkKeyPress(int note, PianoKey key) {
        if (note == notesToPlay[currentNoteIndex]) {
            currentNoteIndex++;
            key.setError(false);
            return true;
        } else {
            errorsCount++;
            key.setError(true);
        }
        return true;
    }

    public int getCurrentNoteIndex() {
        return currentNoteIndex;
    }

    public int getNotesLength() {
        return notesToPlay.length;
    }

    public String getResult() {
        return errorsCount == 0 ? "Молодец!" : "Ошибся "+ errorsCount + " раз";
    }
}