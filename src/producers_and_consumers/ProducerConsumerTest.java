package producers_and_consumers;

import javax.swing.*;
import java.awt.*;

public class ProducerConsumerTest {
    private static JPanel[] squares;
    private static JFrame frame;
    private static final int capacity = 5;

    public static void main(String[] args) {
        Table<String> table = new Table<>(capacity);
        createAndShowGUI();

        Producer<String> firstProducer = new Producer<>(table, 500);
        Producer<String> secondProducer = new Producer<>(table, 600);

        Consumer<String> firstConsumer = new Consumer<>(table, 1000);
        Consumer<String> secondConsumer = new Consumer<>(table, 900);
        Consumer<String> thirdConsumer = new Consumer<>(table, 800);

        new Thread(firstProducer, "First producer").start();
        new Thread(secondProducer, "Second producer").start();
        new Thread(firstConsumer, "First consumer").start();
        new Thread(secondConsumer, "Second consumer").start();
        new Thread(thirdConsumer, "Third consumer").start();
    }

    private static void createAndShowGUI() {
        frame = new JFrame("Producer-Consumer Visualization");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(600, 200);

        JPanel panel = new JPanel(new GridLayout(1, ProducerConsumerTest.capacity));
        squares = new JPanel[ProducerConsumerTest.capacity];

        for (int i = 0; i < ProducerConsumerTest.capacity; i++) {
            squares[i] = new JPanel();
            squares[i].setBackground(Color.LIGHT_GRAY);
            squares[i].setBorder(BorderFactory.createLineBorder(Color.BLACK));
            panel.add(squares[i]);
        }

        frame.add(panel);
        frame.setVisible(true);
    }

    public static void updateGUI(Table table) {
        SwingUtilities.invokeLater(() -> {
            int currentMessages = table.currentSize;

            for (int i = 0; i < squares.length; i++) {
                squares[i].setBackground(i < currentMessages ? Color.GREEN : Color.LIGHT_GRAY);
            }

            frame.repaint();
        });
    }
}
