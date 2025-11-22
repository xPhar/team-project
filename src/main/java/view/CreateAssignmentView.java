package view;

import entity.Assignment;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.ActionListener;
import java.io.File;
import java.time.*;
import java.util.ArrayList;
import java.util.List;

public class CreateAssignmentView extends JPanel {
    private final JLabel courseValue;
    private final Assignment assignment = new Assignment();

    private final JTextField nameField = new JTextField(18);
    private final JTextArea descriptionArea = new JTextArea(4, 30);
    private final JTextField totalPointsField = new JTextField(6);
    private final JSpinner dueDateTime = new JSpinner(new SpinnerDateModel());
    private final JTextField gracePeriodDaysField = new JTextField(4);
    private final JTextField latePenaltyField = new JTextField(4);

    private final JCheckBox cbPdf  = new JCheckBox("pdf");
    private final JCheckBox cbZip  = new JCheckBox("zip");
    private final JCheckBox cbPy   = new JCheckBox("py");
    private final JCheckBox cbJava = new JCheckBox("java");
    private final JCheckBox cbTxt  = new JCheckBox("txt");
    private final JPanel typesPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 8, 4));
    private final JTextField customExtField = new JTextField(6);
    private final JButton addTypeBtn = new JButton("+ Add");

    private final DefaultListModel<File> starterModel = new DefaultListModel<>();
    private final JList<File> starterList = new JList<>(starterModel);
    private final JButton addStarterBtn = new JButton("Add…");
    private final JButton removeStarterBtn = new JButton("Remove Selected");

    private final JButton createButton = new JButton("Create");
    private final JButton cancelButton = new JButton("Cancel");

    public CreateAssignmentView(String courseName) {
        this.courseValue = new JLabel(courseName);
        initRoot();
        addTitle();
        JPanel form = buildForm();
        addSupportedTypesRow(form);
        addStarterFilesRow(form);
        add(form, BorderLayout.CENTER);
        addActions();
        initDueDateSpinner();
        wireSupportedTypes();
        wireStarterFiles();
    }

    private void initRoot() {
        setLayout(new BorderLayout(8, 8));
        setBorder(BorderFactory.createEmptyBorder(12, 12, 12, 12));
        setBackground(Color.WHITE);
    }

    private void addTitle() {
        JLabel title = new JLabel("Create Assignment");
        title.setFont(title.getFont().deriveFont(Font.BOLD, 16f));
        JPanel header = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
        header.setOpaque(false);
        header.add(title);
        add(header, BorderLayout.NORTH);
    }

    private JPanel createRow(String labelText, Component fieldComp) {
        JPanel row = new JPanel(new FlowLayout(FlowLayout.LEFT, 8, 4));
        row.setOpaque(false);
        JLabel label = new JLabel(labelText);
        row.add(label);
        row.add(fieldComp);
        Dimension pref = row.getPreferredSize();
        row.setMaximumSize(new Dimension(Integer.MAX_VALUE, pref.height));
        return row;
    }

    private JPanel buildForm() {
        JPanel form = new JPanel();
        form.setOpaque(false);
        form.setLayout(new BoxLayout(form, BoxLayout.Y_AXIS));

        form.add(createRow("Course:", courseValue));
        form.add(Box.createVerticalStrut(4));

        form.add(createRow("Name*:", nameField));
        form.add(Box.createVerticalStrut(4));

        descriptionArea.setLineWrap(true);
        descriptionArea.setWrapStyleWord(true);
        JScrollPane descScroll = new JScrollPane(
                descriptionArea,
                ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED,
                ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER
        );
        form.add(createRow("Description:", descScroll));
        form.add(Box.createVerticalStrut(4));

        form.add(createRow("Total Points*:", totalPointsField));
        form.add(Box.createVerticalStrut(4));

        form.add(createRow("Due Date & Time*:", dueDateTime));
        form.add(Box.createVerticalStrut(4));

        form.add(createRow("Grace Period (days):", gracePeriodDaysField));
        form.add(Box.createVerticalStrut(4));

        form.add(createRow("Late Penalty (% per day):", latePenaltyField));
        form.add(Box.createVerticalStrut(8));

        return form;
    }

    private void addSupportedTypesRow(JPanel form) {
        typesPanel.setOpaque(false);
        typesPanel.add(cbPdf);
        typesPanel.add(cbZip);
        typesPanel.add(cbPy);
        typesPanel.add(cbJava);
        typesPanel.add(cbTxt);

        JPanel rightForTypes = new JPanel();
        rightForTypes.setOpaque(false);
        rightForTypes.setLayout(new BoxLayout(rightForTypes, BoxLayout.Y_AXIS));
        rightForTypes.add(typesPanel);

        JPanel addCustom = new JPanel(new FlowLayout(FlowLayout.LEFT, 4, 0));
        addCustom.setOpaque(false);
        addCustom.add(new JLabel("Custom ext:"));
        addCustom.add(customExtField);
        addCustom.add(addTypeBtn);
        rightForTypes.add(addCustom);

        form.add(createRow("Supported types*:", rightForTypes));
        form.add(Box.createVerticalStrut(8));
    }

    private void addStarterFilesRow(JPanel form) {
        JPanel right = new JPanel();
        right.setOpaque(false);
        right.setLayout(new BoxLayout(right, BoxLayout.Y_AXIS));

        starterList.setVisibleRowCount(4);
        starterList.setFixedCellWidth(260);
        starterList.setCellRenderer(new DefaultListCellRenderer() {
            @Override
            public Component getListCellRendererComponent(
                    JList<?> list, Object value, int index,
                    boolean isSelected, boolean cellHasFocus) {
                super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
                if (value instanceof File f) {
                    setText(f.getName() + "   (" + f.getParent() + ")");
                }
                return this;
            }
        });

        JScrollPane listScroll = new JScrollPane(
                starterList,
                ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED,
                ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER
        );
        right.add(listScroll);

        JPanel btns = new JPanel(new FlowLayout(FlowLayout.LEFT, 4, 4));
        btns.setOpaque(false);
        btns.add(addStarterBtn);
        btns.add(removeStarterBtn);
        right.add(btns);

        form.add(createRow("Starter files (optional):", right));
        form.add(Box.createVerticalStrut(8));
    }

    private void addActions() {
        JPanel actions = new JPanel(new FlowLayout(FlowLayout.RIGHT, 8, 0));
        actions.setOpaque(false);
        actions.add(cancelButton);
        actions.add(createButton);
        add(actions, BorderLayout.SOUTH);
    }

    private void initDueDateSpinner() {
        JSpinner.DateEditor dtEditor = new JSpinner.DateEditor(dueDateTime, "yyyy-MM-dd HH:mm");
        dueDateTime.setEditor(dtEditor);
        dueDateTime.setValue(new java.util.Date());
    }

    private void wireSupportedTypes() {
        addTypeBtn.addActionListener(e -> {
            String ext = customExtField.getText().trim().toLowerCase();
            if (ext.startsWith(".")) ext = ext.substring(1);
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

    private void wireStarterFiles() {
        addStarterBtn.addActionListener(e -> {
            JFileChooser chooser = new JFileChooser();
            chooser.setDialogTitle("Choose starter files");
            chooser.setMultiSelectionEnabled(true);
            chooser.addChoosableFileFilter(
                    new FileNameExtensionFilter(
                            "Common (zip, pdf, py, java, txt)",
                            "zip", "pdf", "py", "java", "txt"));
            chooser.setAcceptAllFileFilterUsed(true);

            int result = chooser.showOpenDialog(this);
            if (result == JFileChooser.APPROVE_OPTION) {
                File[] files = chooser.getSelectedFiles();
                for (File f : files) {
                    if (!containsFile(f)) {
                        starterModel.addElement(f);
                    }
                }
            }
        });

        removeStarterBtn.addActionListener(e -> {
            for (File f : starterList.getSelectedValuesList()) {
                starterModel.removeElement(f);
            }
        });
    }

    private boolean hasType(String ext) {
        for (Component c : typesPanel.getComponents()) {
            if (c instanceof JCheckBox j
                    && j.getText().equalsIgnoreCase(ext)) {
                return true;
            }
        }
        return false;
    }

    private void addTypeCheckbox(String ext) {
        JCheckBox box = new JCheckBox(ext, true);
        typesPanel.add(box);
        typesPanel.revalidate();
        typesPanel.repaint();
    }

    private boolean containsFile(File f) {
        for (int i = 0; i < starterModel.size(); i++) {
            if (starterModel.get(i).equals(f)) return true;
        }
        return false;
    }

    private List<String> getSupportedFileTypes() {
        List<String> out = new ArrayList<>();
        for (Component c : typesPanel.getComponents()) {
            if (c instanceof JCheckBox j && j.isSelected()) {
                out.add(j.getText().toLowerCase());
            }
        }
        return out;
    }

    public Assignment getAssignment() {
        assignment.setName(nameField.getText().trim());
        assignment.setDescription(descriptionArea.getText());
        assignment.setCreationDate(LocalDateTime.now());

        java.util.Date d = (java.util.Date) dueDateTime.getValue();
        LocalDateTime dueAt = d.toInstant()
                .atZone(ZoneId.systemDefault())
                .toLocalDateTime();
        assignment.setDueDate(dueAt);

        try {
            int days = Integer.parseInt(gracePeriodDaysField.getText().trim());
            assignment.setGracePeriod(days);
        } catch (NumberFormatException ex) {
            assignment.setGracePeriod(0.0);
        }

        try {
            String raw = latePenaltyField.getText().trim();
            if (raw.endsWith("%")) {
                raw = raw.substring(0, raw.length() - 1).trim();
            }
            double v = Double.parseDouble(raw);
            double fraction = (v > 1.0) ? v / 100.0 : v;
            assignment.setLatePenalty(String.valueOf(fraction));
        } catch (NumberFormatException ex) {
            assignment.setLatePenalty("");
        }

        assignment.setSupportedFileTypes(getSupportedFileTypes());

        return assignment;
    }

    public void clearForm() {
        nameField.setText("");
        descriptionArea.setText("");
        totalPointsField.setText("");
        gracePeriodDaysField.setText("");
        latePenaltyField.setText("");
        dueDateTime.setValue(new java.util.Date());
        starterModel.clear();
        descriptionArea.setCaretPosition(0);
    }

    public List<File> getStarterFiles() {
        List<File> out = new ArrayList<>();
        for (int i = 0; i < starterModel.size(); i++) {
            out.add(starterModel.get(i));
        }
        return out;
    }

    public void addCreateListener(ActionListener l) { createButton.addActionListener(l); }
    public void addCancelListener(ActionListener l) { cancelButton.addActionListener(l); }
}
