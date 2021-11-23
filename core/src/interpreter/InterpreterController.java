package interpreter;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;

public class InterpreterController {
    @FXML
    Button runButton;
    @FXML
    TextArea textArea;

    public void onRunButtonClick(){
        System.out.println(textArea.getText());
    }


}
