package commands;

import java.io.Serializable;

public interface Command extends Serializable {

    void execute();

    void undo();
}
