package cashflow;

import commands.Command;

import java.io.Serializable;
import java.util.Stack;

public class Save implements Serializable {
    private Stack<Command> usedCommands;

    public Stack<Command> getUsedCommands() {
        return usedCommands;
    }

    public void setUsedCommands(Stack<Command> usedCommands) {
        this.usedCommands = usedCommands;
    }
}
