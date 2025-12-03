package interface_adapter.submit;

import java.awt.Color;
import java.time.LocalDateTime;

public class SubmitState {

    private String message = "Click the button below to submit";
    private Color msgColor = Color.BLACK;
    private String courseCode;
    private String assignmentName;
    private String assignmentDescription;
    private LocalDateTime dueDate;
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Color getMsgColor() {
        return msgColor;
    }

    public void setMsgColor(Color msgColor) {
        this.msgColor = msgColor;
    }

    public String getCourseCode() {
        return courseCode;
    }

    public void setCourseCode(String courseCode) {
        this.courseCode = courseCode;
    }

    public String getAssignmentName() {
        return assignmentName;
    }

    public void setAssignmentName(String assignmentName) {
        this.assignmentName = assignmentName;
    }

    public String getAssignmentDescription() {
        return assignmentDescription;
    }

    public void setAssignmentDescription(String assignmentDescription) {
        this.assignmentDescription = assignmentDescription;
    }

    public LocalDateTime getDueDate() {
        return dueDate;
    }

    public void setDueDate(LocalDateTime dueDate) {
        this.dueDate = dueDate;
    }
}
