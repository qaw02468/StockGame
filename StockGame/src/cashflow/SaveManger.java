package cashflow;

import commands.Command;

import java.io.*;
import java.util.Stack;

public class SaveManger {
    private final File savePath = new File("./CashFlowSave.txt");
    private Save saveRepository = new Save();


    public SaveManger(Stack<Command> usedCommands) {
        saveRepository.setUsedCommands(usedCommands);
    }

    public boolean checkSave() {
        boolean checkSave = true;

        try {
            checkSave = savePath.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return !checkSave;
    }

    public boolean isLoad(String select) {
        return select.equals("Y");
    }


    public Save loadSave() throws IOException, ClassNotFoundException {

        ObjectInputStream load = new ObjectInputStream(new FileInputStream(savePath));
        return (Save) load.readObject();

    }

    public void setUpNewSave() {
        try {
            savePath.delete();
            savePath.createNewFile();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void save() {
        try (ObjectOutputStream save = new ObjectOutputStream(
                new FileOutputStream(new File("./CashFlowSave.txt")))) {
            save.writeObject(saveRepository);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void deleteSave(){
        File save = new File("./CashFlowSave.txt");
        save.delete();
    }
}
