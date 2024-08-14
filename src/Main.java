import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.HashMap;
import java.util.Map;

 class CoolCurrencyConverterGUI {

    // Currency data provided
    private static final String BASE_CURRENCY = "USD";
    private static final Map<String, Double> exchangeRates = new HashMap<>();

    static {
        exchangeRates.put("EUR", 0.90702);
        exchangeRates.put("GBP", 0.77862);
        exchangeRates.put("CHF", 0.86297);
    }

    public static double convertCurrency(double amount, String fromCurrency, String toCurrency) {
        if (fromCurrency.equals(BASE_CURRENCY)) {
            if (exchangeRates.containsKey(toCurrency)) {
                return amount * exchangeRates.get(toCurrency);
            } else {
                throw new IllegalArgumentException("Target currency not available.");
            }
        } else if (toCurrency.equals(BASE_CURRENCY)) {
            if (exchangeRates.containsKey(fromCurrency)) {
                return amount / exchangeRates.get(fromCurrency);
            } else {
                throw new IllegalArgumentException("Source currency not available.");
            }
        } else {
            if (exchangeRates.containsKey(fromCurrency) && exchangeRates.containsKey(toCurrency)) {
                double usdAmount = amount / exchangeRates.get(fromCurrency);
                return usdAmount * exchangeRates.get(toCurrency);
            } else {
                throw new IllegalArgumentException("Currency conversion not possible with provided data.");
            }
        }
    }

    public static void main(String[] args) {
        // Creating the frame
        JFrame frame = new JFrame("Cool Currency Converter");
        frame.setSize(600, 350);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new GridBagLayout());
        frame.getContentPane().setBackground(new Color(45, 52, 54));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);

        // Creating and placing components with custom styling
        JLabel labelAmount = new JLabel("Amount:");
        labelAmount.setForeground(Color.WHITE);
        labelAmount.setFont(new Font("Arial", Font.BOLD, 14));
        gbc.gridx = 0;
        gbc.gridy = 0;
        frame.add(labelAmount, gbc);

        JTextField textAmount = new JTextField(10);
        textAmount.setFont(new Font("Arial", Font.PLAIN, 14));
        textAmount.setBorder(new LineBorder(new Color(178, 190, 195), 2));
        textAmount.setToolTipText("Enter the amount you want to convert");
        gbc.gridx = 1;
        gbc.gridy = 0;
        frame.add(textAmount, gbc);

        JLabel labelFromCurrency = new JLabel("From Currency:");
        labelFromCurrency.setForeground(Color.WHITE);
        labelFromCurrency.setFont(new Font("Arial", Font.BOLD, 14));
        gbc.gridx = 0;
        gbc.gridy = 1;
        frame.add(labelFromCurrency, gbc);

        String[] currencies = {"USD", "EUR", "GBP", "CHF"};
        JComboBox<String> fromCurrencyComboBox = new JComboBox<>(currencies);
        fromCurrencyComboBox.setFont(new Font("Arial", Font.PLAIN, 14));
        fromCurrencyComboBox.setBorder(new LineBorder(new Color(178, 190, 195), 2));
        gbc.gridx = 1;
        gbc.gridy = 1;
        frame.add(fromCurrencyComboBox, gbc);

        JLabel labelToCurrency = new JLabel("To Currency:");
        labelToCurrency.setForeground(Color.WHITE);
        labelToCurrency.setFont(new Font("Arial", Font.BOLD, 14));
        gbc.gridx = 0;
        gbc.gridy = 2;
        frame.add(labelToCurrency, gbc);

        JComboBox<String> toCurrencyComboBox = new JComboBox<>(currencies);
        toCurrencyComboBox.setFont(new Font("Arial", Font.PLAIN, 14));
        toCurrencyComboBox.setBorder(new LineBorder(new Color(178, 190, 195), 2));
        gbc.gridx = 1;
        gbc.gridy = 2;
        frame.add(toCurrencyComboBox, gbc);

        JButton convertButton = new JButton("Convert");
        convertButton.setFont(new Font("Arial", Font.BOLD, 14));
        convertButton.setBackground(new Color(99, 110, 114));
        convertButton.setForeground(Color.WHITE);
        convertButton.setFocusPainted(false);
        gbc.gridx = 1;
        gbc.gridy = 3;
        frame.add(convertButton, gbc);

        JLabel resultLabel = new JLabel("");
        resultLabel.setForeground(new Color(178, 190, 195));
        resultLabel.setFont(new Font("Arial", Font.BOLD, 16));
        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.gridwidth = 2;
        frame.add(resultLabel, gbc);

        // Live conversion as the user types
        KeyAdapter keyAdapter = new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                try {
                    double amount = Double.parseDouble(textAmount.getText());
                    String fromCurrency = fromCurrencyComboBox.getSelectedItem().toString();
                    String toCurrency = toCurrencyComboBox.getSelectedItem().toString();
                    double convertedAmount = convertCurrency(amount, fromCurrency, toCurrency);
                    resultLabel.setText(amount + " " + fromCurrency + " = " + convertedAmount + " " + toCurrency);
                } catch (NumberFormatException ex) {
                    resultLabel.setText("Please enter a valid number.");
                } catch (IllegalArgumentException ex) {
                    resultLabel.setText(ex.getMessage());
                }
            }
        };
        textAmount.addKeyListener(keyAdapter);
        fromCurrencyComboBox.addActionListener(e -> keyAdapter.keyReleased(null));
        toCurrencyComboBox.addActionListener(e -> keyAdapter.keyReleased(null));

        // Making the frame visible
        frame.setVisible(true);
    }
}
