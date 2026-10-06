import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class ZooFrame extends JFrame {

    // Menyimpan semua hewan
    private ArrayList<Animal> animals = new ArrayList<>();

    // Komponen input
    private JTextField nameField;
    private JTextField ageField;
    private JComboBox<String> animalTypeComboBox;
    private JTextField furColorField;
    private JCheckBox canFlyCheckBox;

    // Komponen lainnya
    private JButton addAnimalButton;
    private JTextArea logArea;

    public ZooFrame() {

        // =========================
        // FRAME SETUP
        // =========================
        setTitle("Digital Zoo Manager");
        setSize(600, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // =========================
        // FORM PANEL
        // =========================
        JPanel formPanel = new JPanel(new GridLayout(5, 2, 10, 10));
        formPanel.setBorder(
                BorderFactory.createEmptyBorder(15, 15, 15, 15)
        );

        // Name
        formPanel.add(new JLabel("Name:"));
        nameField = new JTextField();
        formPanel.add(nameField);

        // Age
        formPanel.add(new JLabel("Age:"));
        ageField = new JTextField();
        formPanel.add(ageField);

        // Animal Type
        formPanel.add(new JLabel("Animal Type:"));
        animalTypeComboBox = new JComboBox<>(
                new String[]{"Generic Mammal", "Generic Bird"}
        );
        formPanel.add(animalTypeComboBox);

        // Fur Color
        formPanel.add(new JLabel("Fur Color:"));
        furColorField = new JTextField();
        formPanel.add(furColorField);

        // Can Fly
        formPanel.add(new JLabel("Can Fly:"));
        canFlyCheckBox = new JCheckBox("Yes");
        formPanel.add(canFlyCheckBox);

        // Button
        addAnimalButton = new JButton("Add Animal");

        // Log area
        logArea = new JTextArea();
        logArea.setEditable(false);

        JScrollPane scrollPane = new JScrollPane(logArea);

        // =========================
        // MAIN PANEL
        // =========================
        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.add(formPanel, BorderLayout.CENTER);
        topPanel.add(addAnimalButton, BorderLayout.SOUTH);

        setLayout(new BorderLayout());
        add(topPanel, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);

        // =========================
        // EVENT
        // =========================
        addAnimalButton.addActionListener(e -> addAnimal());

        animalTypeComboBox.addActionListener(e -> updateAnimalFields());

        // Kondisi awal
        updateAnimalFields();
    }

    // =========================
    // UPDATE INPUT SESUAI TIPE
    // =========================
    private void updateAnimalFields() {

        String selectedType =
                (String) animalTypeComboBox.getSelectedItem();

        boolean isMammal =
                selectedType.equals("Generic Mammal");

        // Jika Mammal, Fur Color aktif
        furColorField.setEnabled(isMammal);

        // Jika Bird, Can Fly aktif
        canFlyCheckBox.setEnabled(!isMammal);

        // Reset field yang tidak digunakan
        if (isMammal) {
            canFlyCheckBox.setSelected(false);
        } else {
            furColorField.setText("");
        }
    }

    // =========================
    // ADD ANIMAL
    // =========================
    private void addAnimal() {

        try {
            String name = nameField.getText().trim();
            int age = Integer.parseInt(ageField.getText().trim());

            String animalType =
                    (String) animalTypeComboBox.getSelectedItem();

            Animal animal;

            if (animalType.equals("Generic Mammal")) {

                String furColor =
                        furColorField.getText().trim();

                animal = new Mammal(name, age, furColor);

            } else {

                boolean canFly =
                        canFlyCheckBox.isSelected();

                animal = new Bird(name, age, canFly);
            }

            // Simpan ke ArrayList
            animals.add(animal);

            // Tampilkan ke log
            logArea.append(
                    "Added a new " + animalType + "!\n"
                            + "Info: " + animal.getInfo() + "\n"
                            + "Sound: " + animal.makeSound() + "\n\n"
            );

            // Kosongkan input
            nameField.setText("");
            ageField.setText("");
            furColorField.setText("");
            canFlyCheckBox.setSelected(false);

        } catch (NumberFormatException ex) {

            JOptionPane.showMessageDialog(
                    this,
                    "Age must be a valid number.",
                    "Input Error",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }
}