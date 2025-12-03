package interface_adapter.resubmit;

import java.awt.Color;

public class ResubmitState {

    private String message = "Are you sure you want to resubmit? Only the latest one will be marked";
    private Color msgColor = Color.BLACK;

    public Color getMsgColor() {
        return msgColor;
    }

    public void setMsgColor(Color msgColor) {
        this.msgColor = msgColor;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
