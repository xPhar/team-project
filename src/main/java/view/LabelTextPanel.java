package view;

import javax.swing.*;
import java.awt.*;

/**
 * A panel containing a label and a text field.
 */
class LabelTextPanel extends JPanel {
    LabelTextPanel(JLabel label, JTextField textField) {
        this.setOpaque(false);
        this.setAlignmentY(Component.CENTER_ALIGNMENT);

        label.setFont(new Font("Inter", Font.PLAIN, 16));
        label.setForeground(Color.BLACK);

        textField.setFont(new Font("Inter", Font.PLAIN, 16));
        textField.setBorder(BorderFactory.createLineBorder(Color.BLACK));

        this.add(label);
        this.add(textField);
    }
}