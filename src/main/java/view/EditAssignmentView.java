package view;

import interface_adapter.EditAssignment.EditAssignmentController;
import interface_adapter.EditAssignment.EditAssignmentState;
import interface_adapter.EditAssignment.EditAssignmentViewModel;

import javax.swing.*;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.time.*;
import java.util.ArrayList;
import java.util.List;

public class EditAssignmentView extends JPanel implements PropertyChangeListener {
    private final String viewName = "Edit Assignment";
    private final EditAssignmentViewModel editAssignmentViewModel;
    private EditAssignmentController editAssignmentController;
    private final JLabel courseValue;

    // Components
    private final JTextField nameField = new JTextField(20);
    private final JTextArea descriptionArea = new JTextArea(5, 30);
    private final JTextField totalPointsField = new JTextField(8);
    private final JSpinner dueDateTime = new JSpinner(new SpinnerDateModel());

    private final JCheckBox cbPdf = new JCheckBox("PDF");
    private final JCheckBox cbZip = new JCheckBox("ZIP");
    private final JCheckBox cbPy = new JCheckBox("Python (.py)");
    private final JCheckBox cbJava = new JCheckBox("Java (.java)");
    private final JCheckBox cbTxt = new JCheckBox("Text (.txt)");
    private final JPanel typesPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 5));
    private final JTextField customExtField = new JTextField(8);
    private final JButton addTypeBtn = new JButton("Add");

    private final JButton saveButton = new JButton("Save Changes");
    private final JButton cancelButton = new JButton("Cancel");

    // Styles
    private final Color PRIMARY_COLOR = new Color(79, 70, 229); // Indigo-600
    private final Color BACKGROUND_COLOR = new Color(249, 250, 251); // Gray-50
    private final Color SURFACE_COLOR = Color.WHITE;
    private final Color TEXT_PRIMARY = new Color(17, 24, 39); // Gray-900
    private final Color TEXT_SECONDARY = new Color(107, 114, 128); // Gray-500
    private final Color BORDER_COLOR = new Color(209, 213, 219); // Gray-300

    private final Font HEADER_FONT = new Font("Segoe UI", Font.BOLD, 24);
    private final Font LABEL_FONT = new Font("Segoe UI", Font.BOLD, 13);
    private final Font INPUT_FONT = new Font("Segoe UI", Font.PLAIN, 14);

    public EditAssignmentView(EditAssignmentViewModel editAssignmentViewModel) {
        this.editAssignmentViewModel = editAssignmentViewModel;
        this.editAssignmentViewModel.addPropertyChangeListener(this);
        this.courseValue = new JLabel("");
        this.courseValue.setFont(INPUT_FONT);

        initRoot();
        addHeader();

        JPanel contentPanel = new JPanel();
        contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));
        contentPanel.setOpaque(false);
        contentPanel.setBorder(new EmptyBorder(0, 0, 20, 0));

        contentPanel.add(buildForm());
        contentPanel.add(Box.createVerticalStrut(20));
        contentPanel.add(buildTypesSection());

        JScrollPane mainScroll = new JScrollPane(contentPanel);
        mainScroll.setBorder(null);
        mainScroll.setOpaque(false);
        mainScroll.getViewport().setOpaque(false);
        mainScroll.getVerticalScrollBar().setUnitIncrement(16);

        add(mainScroll, BorderLayout.CENTER);
        addActions();

        initDueDateSpinner();
        wireSupportedTypes();
        setupButtonListeners();
    }

    private void initRoot() {
        setLayout(new BorderLayout(20, 20));
        setBorder(new EmptyBorder(30, 40, 30, 40));
        setBackground(BACKGROUND_COLOR);
    }

    private void addHeader() {
        JLabel title = new JLabel("Edit Assignment");
        title.setFont(HEADER_FONT);
        title.setForeground(TEXT_PRIMARY);

        JPanel header = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
        header.setOpaque(false);
        header.add(title);
        header.setBorder(new EmptyBorder(0, 0, 20, 0));
        add(header, BorderLayout.NORTH);
    }

    private JPanel buildForm() {
        JPanel form = new JPanel(new GridBagLayout());
        form.setOpaque(false);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(0, 0, 15, 20);
        gbc.anchor = GridBagConstraints.NORTHWEST;

        // Row 1: Course & Name
        addFormField(form, "Course", courseValue, gbc, 0, 0, 1);
        addFormField(form, "Assignment Name*", styleTextField(nameField), gbc, 1, 0, 1);

        // Row 2: Description (Full width)
        gbc.gridwidth = 2;
        descriptionArea.setFont(INPUT_FONT);
        descriptionArea.setLineWrap(true);
        descriptionArea.setWrapStyleWord(true);
        descriptionArea.setBorder(BorderFactory.createEmptyBorder(8, 8, 8, 8));
        JScrollPane descScroll = new JScrollPane(descriptionArea);
        descScroll.setBorder(new LineBorder(BORDER_COLOR));
        addFormField(form, "Description", descScroll, gbc, 0, 1, 2);
        gbc.gridwidth = 1;

        // Row 3: Due Date
        addFormField(form, "Due Date & Time*", styleSpinner(dueDateTime), gbc, 1, 2, 1);

        return form;
    }

    private void addFormField(JPanel panel, String labelText, Component field, GridBagConstraints gbc, int x, int y,
            int width) {
        gbc.gridx = x;
        gbc.gridy = y;
        gbc.gridwidth = width;

        JPanel fieldPanel = new JPanel(new BorderLayout(0, 5));
        fieldPanel.setOpaque(false);

        JLabel label = new JLabel(labelText);
        label.setFont(LABEL_FONT);
        label.setForeground(TEXT_SECONDARY);

        fieldPanel.add(label, BorderLayout.NORTH);
        fieldPanel.add(field, BorderLayout.CENTER);

        panel.add(fieldPanel, gbc);
    }

    private JTextField styleTextField(JTextField field) {
        field.setFont(INPUT_FONT);
        field.setBorder(new CompoundBorder(
                new LineBorder(BORDER_COLOR),
                new EmptyBorder(8, 10, 8, 10)));
        return field;
    }

    private JSpinner styleSpinner(JSpinner spinner) {
        spinner.setFont(INPUT_FONT);
        spinner.setBorder(new CompoundBorder(
                new LineBorder(BORDER_COLOR),
                new EmptyBorder(5, 5, 5, 5)));
        return spinner;
    }

    private JPanel buildTypesSection() {
        JPanel panel = new JPanel(new BorderLayout(0, 10));
        panel.setOpaque(false);

        JLabel label = new JLabel("Supported File Types*");
        label.setFont(LABEL_FONT);
        label.setForeground(TEXT_SECONDARY);
        panel.add(label, BorderLayout.NORTH);

        typesPanel.setOpaque(false);
        styleCheckBox(cbPdf);
        styleCheckBox(cbZip);
        styleCheckBox(cbPy);
        styleCheckBox(cbJava);
        styleCheckBox(cbTxt);

        typesPanel.add(cbPdf);
        typesPanel.add(cbZip);
        typesPanel.add(cbPy);
        typesPanel.add(cbJava);
        typesPanel.add(cbTxt);

        JPanel customPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 0));
        customPanel.setOpaque(false);
        customPanel.add(new JLabel("Other extension:"));
        customPanel.add(styleTextField(customExtField));
        styleSecondaryButton(addTypeBtn);
        customPanel.add(addTypeBtn);

        JPanel container = new JPanel();
        container.setLayout(new BoxLayout(container, BoxLayout.Y_AXIS));
        container.setOpaque(false);
        container.add(typesPanel);
        container.add(Box.createVerticalStrut(5));
        container.add(customPanel);

        panel.add(container, BorderLayout.CENTER);
        return panel;
    }

    private void styleCheckBox(JCheckBox cb) {
        cb.setOpaque(false);
        cb.setFont(INPUT_FONT);
        cb.setForeground(TEXT_PRIMARY);
    }

    private void addActions() {
        JPanel actions = new JPanel(new FlowLayout(FlowLayout.RIGHT, 15, 0));
        actions.setOpaque(false);
        actions.setBorder(new EmptyBorder(20, 0, 0, 0));

        styleSecondaryButton(cancelButton);
        stylePrimaryButton(saveButton);

        actions.add(cancelButton);
        actions.add(saveButton);
        add(actions, BorderLayout.SOUTH);
    }

    private void stylePrimaryButton(JButton btn) {
        btn.setFont(new Font("Segoe UI", Font.BOLD, 14));
        btn.setForeground(Color.WHITE);
        btn.setBackground(PRIMARY_COLOR);
        btn.setBorder(new EmptyBorder(10, 20, 10, 20));
        btn.setFocusPainted(false);
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
    }

    private void styleSecondaryButton(JButton btn) {
        btn.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        btn.setForeground(TEXT_PRIMARY);
        btn.setBackground(SURFACE_COLOR);
        btn.setBorder(new CompoundBorder(
                new LineBorder(BORDER_COLOR),
                new EmptyBorder(8, 15, 8, 15)));
        btn.setFocusPainted(false);
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
    }

    private void initDueDateSpinner() {
        JSpinner.DateEditor dtEditor = new JSpinner.DateEditor(dueDateTime, "yyyy-MM-dd HH:mm");
        dueDateTime.setEditor(dtEditor);
        dueDateTime.setValue(new java.util.Date());
    }

    private void wireSupportedTypes() {
        addTypeBtn.addActionListener(e -> {
            String ext = customExtField.getText().trim().toLowerCase();
            if (ext.startsWith("."))
                ext = ext.substring(1);
            if (!ext.matches("[a-z0-9]+")) {
                JOptionPane.showMessageDialog(this,
                        "Use letters/digits only (e.g., pdf, csv, md).",
                        "Invalid extension", JOptionPane.WARNING_MESSAGE);
                return;
            }
            if (hasType(ext)) {
                JOptionPane.showMessageDialog(this,
                        "Extension already listed: " + ext,
                        "Duplicate", JOptionPane.INFORMATION_MESSAGE);
                return;
            }
            addTypeCheckbox(ext);
            customExtField.setText("");
        });
    }

    private void setupButtonListeners() {
        saveButton.addActionListener(e -> {
            if (editAssignmentController != null) {
                java.util.Date d = (java.util.Date) dueDateTime.getValue();
                LocalDateTime dueAt = d.toInstant()
                        .atZone(ZoneId.systemDefault())
                        .toLocalDateTime();

                editAssignmentController.execute(
                        nameField.getText().trim(),
                        courseValue.getText(),
                        descriptionArea.getText(),
                        dueAt,
                        getSupportedFileTypes());
            }
        });

        cancelButton.addActionListener(e -> {
            if (editAssignmentController != null) {
                editAssignmentController.switchToAssignmentView();
            }
        });
    }

    private boolean hasType(String ext) {
        for (Component c : typesPanel.getComponents()) {
            if (c instanceof JCheckBox j && j.getText().equalsIgnoreCase(ext)) {
                return true;
            }
        }
        return false;
    }

    private void addTypeCheckbox(String ext) {
        JCheckBox box = new JCheckBox(ext, true);
        styleCheckBox(box);
        typesPanel.add(box);
        typesPanel.revalidate();
        typesPanel.repaint();
    }

    private List<String> getSupportedFileTypes() {
        List<String> out = new ArrayList<>();
        for (Component c : typesPanel.getComponents()) {
            if (c instanceof JCheckBox j && j.isSelected()) {
                String text =  j.getText().trim().toLowerCase();
                if (text.contains("py")) {
                    out.add(".py");
                }
                else if (text.contains("java")) {
                    out.add(".java");
                }
                else {
                    out.add("." + text);
                }
            }
        }
        return out;
    }

    public void setEditAssignmentController(EditAssignmentController controller) {
        this.editAssignmentController = controller;
    }

    public String getViewName() {
        return viewName;
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        EditAssignmentState state = (EditAssignmentState) evt.getNewValue();
        if (state.getErrorMessage() != null && !state.getErrorMessage().isEmpty()) {
            JOptionPane.showMessageDialog(this, state.getErrorMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        } else if (state.getName() != null && !state.getName().isEmpty()) {
            populateForm(state.getCourseCode(), state.getName(), state.getDescription(),
                    state.getDueDate(), state.getSupportedFileTypes());
        }
    }

    private void populateForm(String courseCode, String name, String description,
            LocalDateTime dueDate, List<String> supportedFileTypes) {
        nameField.setText(name);
        courseValue.setText(courseCode);
        descriptionArea.setText(description);

        if (dueDate != null) {
            java.util.Date date = java.util.Date.from(dueDate
                    .atZone(ZoneId.systemDefault())
                    .toInstant());
            dueDateTime.setValue(date);
        }

        // Reset checkboxes
        cbPdf.setSelected(false);
        cbZip.setSelected(false);
        cbPy.setSelected(false);
        cbJava.setSelected(false);
        cbTxt.setSelected(false);

        // Remove custom ones
        List<Component> toRemove = new ArrayList<>();
        for (Component c : typesPanel.getComponents()) {
            if (c instanceof JCheckBox j) {
                String txt = j.getText();
                if (!txt.equals("PDF") && !txt.equals("ZIP") && !txt.equals("Python (.py)") &&
                        !txt.equals("Java (.java)") && !txt.equals("Text (.txt)")) {
                    toRemove.add(c);
                }
            }
        }
        for (Component c : toRemove)
            typesPanel.remove(c);

        if (supportedFileTypes != null) {
            for (String type : supportedFileTypes) {
                boolean found = false;
                for (Component c : typesPanel.getComponents()) {
                    if (c instanceof JCheckBox j && j.getText().equalsIgnoreCase(type)) {
                        j.setSelected(true);
                        found = true;
                        break;
                    }
                }
                if (!found) {
                    addTypeCheckbox(type);
                }
            }
        }

        typesPanel.revalidate();
        typesPanel.repaint();
    }
}
