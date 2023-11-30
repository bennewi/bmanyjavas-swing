package just.playing;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class InventoryPanel extends JPanel {
    private final JTextField productIdField = new JTextField();
    private final JTextField productIdDataField = new JTextField();
    private final JTextField itemNameField = new JTextField();
    private final JTextField itemNameDataField = new JTextField();
    private final JFormattedTextField quantityField = new JFormattedTextField();
    private final JFormattedTextField quantityDataField = new JFormattedTextField();
    private final JTextField priceField = new JTextField();
    private final JTextField priceDataField = new JTextField();
    private final JFormattedTextField dateField = new JFormattedTextField(new SimpleDateFormat("yyyy-MM-dd"));
    private final JTextField dateDataField = new JTextField();
    private final JComboBox<String> supplierComboBox = new JComboBox<>(new String[]{"Supplier A", "Supplier B", "Supplier C"});
    private final JTextField supplierDataField = new JTextField();
    private final JCheckBox refrigerationCheckBox = new JCheckBox();
    private final JTextField refrigerationDataField = new JTextField();
    private String category;

    public InventoryPanel(String category) {
        this.category = category;
        setLayout(new BorderLayout());

        // Form Panel
        JPanel formPanel = new JPanel(new GridLayout(0, 3));

        formPanel.add(new JLabel("Product ID:"));
        formPanel.add(productIdField);
        formPanel.add(productIdDataField);


        formPanel.add(new JLabel("Item Name:"));
        formPanel.add(itemNameField);
        formPanel.add(itemNameDataField);

        formPanel.add(new JLabel("Quantity:"));
        quantityField.addKeyListener(new KeyAdapter() {
            public void keyTyped(KeyEvent e) {
                char c = e.getKeyChar();
                if (!Character.isDigit(c)) {
                    e.consume();  // Ignore this key event
                }
            }
        });
        formPanel.add(quantityField);
        formPanel.add(quantityDataField);

        formPanel.add(new JLabel("Price:"));
        formPanel.add(priceField);
        formPanel.add(priceDataField);

        formPanel.add(new JLabel("Date Purchased:"));
        formPanel.add(dateField);
        formPanel.add(dateDataField);

        formPanel.add(new JLabel("Supplier:"));
        formPanel.add(supplierComboBox);
        formPanel.add(supplierDataField);

        formPanel.add(new JLabel("Requires Refrigeration:"));
        formPanel.add(refrigerationCheckBox);
        formPanel.add(refrigerationDataField);

        add(formPanel, BorderLayout.CENTER);

        // Save Button
        JButton saveButton = new JButton("Save");
        saveButton.addActionListener(e -> saveInventoryItem(
                productIdField.getText(),
                itemNameField.getText(),
                quantityField.getText(),
                priceField.getText(),
                dateField.getText(),
                (String) supplierComboBox.getSelectedItem(),
                refrigerationCheckBox.isSelected()
        ));
        add(saveButton, BorderLayout.SOUTH);
    }

    private void saveInventoryItem(String productId, String itemName, String quantity, String price, String datePurchased, String supplier, boolean requiresRefrigeration) {
        // Logic to save inventory item
        productIdDataField.setText(productId);
        if (itemName.length() > 5) {
            itemNameDataField.setText(itemName);
        } else {
            itemNameDataField.setText("item name too short. requires at least 5 characters");
        }
        try {
            if (this.category.equals("Glassware") && Integer.parseInt(quantity) % 12 != 0) {
                quantityDataField.setText("Glass products must be entered in multiples of 12");
            } else if (Integer.parseInt(quantity) < 0) {
                quantityDataField.setText("0");
            } else {
                quantityDataField.setText(quantity);
            }
        } catch (Exception e) {
            quantityDataField.setText("must be a number!");
        }
        priceDataField.setText(price);
        try {
            SimpleDateFormat ezIsoDateFormat = new SimpleDateFormat("yyyy-MM-dd");
            Date datePurchasedDate = ezIsoDateFormat.parse(datePurchased);
            System.out.println(datePurchasedDate);
            // corporate policy dictates anything older than 7 years must be incinerated
            Calendar expirydate = new Calendar.Builder().setInstant(new Date()).build();
            if (datePurchasedDate.after(expirydate.getTime())) {
                dateDataField.setText("cannot buy in the future");
                return;
            }
            expirydate.add(Calendar.YEAR, -7);
            if (datePurchasedDate.before(expirydate.getTime())) {
                dateDataField.setText("Product too old and must be trashed");
            } else {
                dateDataField.setText(new SimpleDateFormat("MMMMM dd, YYYY").format(datePurchasedDate));
            }
        } catch (ParseException pe) {
            dateDataField.setText("invalid date. Format should be YYYY-MM-DD");
        }
        // supplier
        supplierDataField.setText(supplier);
        // refrigeration
        refrigerationDataField.setText(String.valueOf(requiresRefrigeration));
        System.out.println("Processed " + category + " Item: " + "Product ID: " + productId + ", Item Name: " + itemName + ", Quantity: " + quantity + ", Price: " + price + ", Date Purchased: " + datePurchased + ", Supplier: " + supplier + ", Requires Refrigeration: " + requiresRefrigeration);
    }
}
