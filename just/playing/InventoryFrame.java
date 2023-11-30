package just.playing;

import javax.swing.JFrame;
import javax.swing.JTabbedPane;

public class InventoryFrame extends JFrame {

    public InventoryFrame() {
        setTitle("Restaurant Inventory Management");
        setSize(1200, 300);
        setLocationRelativeTo(null); // Center the window

        JTabbedPane tabbedPane = new JTabbedPane();

        tabbedPane.add("Furniture", new InventoryPanel("Furniture"));
        tabbedPane.add("Glassware", new InventoryPanel("Glassware"));
        tabbedPane.add("Cleaning Products", new InventoryPanel("Cleaning Products"));
        tabbedPane.add("Food", new InventoryPanel("Food"));
        tabbedPane.add("Uniforms", new InventoryPanel("Uniforms"));
        tabbedPane.add("Interior Decorations", new InventoryPanel("Interior Decorations"));

        add(tabbedPane);
    }
}
