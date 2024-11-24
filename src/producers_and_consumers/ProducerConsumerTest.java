package producers_and_consumers;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

public class ProducerConsumerTest {
    private static JPanel[] actionCircles;
    private static JPanel[] squares;
    private static JFrame frame;
    private static final int capacity = 5;
    private static final int countOfProducers = 2;
    private static final int countOfConsumers = 3;

    public static void main(String[] args) {
        createAndShowGUI();

        Random random = new Random();
        Table<String> table = new Table<>(capacity);
        ArrayList<Producer> producers = new ArrayList<>();
        ArrayList<Consumer> consumers = new ArrayList<>();

        for (int i = 0; i < countOfProducers; i++) {
            producers.add(new Producer<>(table, random.nextInt(500, 600)));
        }

        for (int i = 0; i < countOfConsumers; i++) {
            consumers.add(new Consumer<>(table, random.nextInt(800, 1000)));
        }

        for (int i = 0; i < producers.size(); i++) {
            new Thread(producers.get(i), "Producer №" + i).start();
        }

        for (int i = 0; i < consumers.size(); i++) {
            new Thread(consumers.get(i), "Consumer №" + i).start();
        }
        System.out.println(Arrays.toString(actionCircles));
    }

    private static void createAndShowGUI() {
        frame = new JFrame("Producer-Consumer Visualization");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(600, 200);

        actionCircles = new JPanel[countOfProducers + countOfConsumers];

        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        for (int i = 0; i < countOfProducers; i++) {
            actionCircles[i] = createCirclePanel();
            topPanel.add(actionCircles[i]);
        }

        JPanel centerPanel = new JPanel(new GridLayout(1, ProducerConsumerTest.capacity));
        squares = new JPanel[ProducerConsumerTest.capacity];

        for (int i = 0; i < ProducerConsumerTest.capacity; i++) {
            squares[i] = new JPanel();
            squares[i].setBackground(Color.LIGHT_GRAY);
            squares[i].setBorder(BorderFactory.createLineBorder(Color.BLACK));
            centerPanel.add(squares[i]);
        }

        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        for (int i = 0; i < countOfConsumers; i++) {
            actionCircles[countOfProducers + i] = createCirclePanel();
            bottomPanel.add(actionCircles[countOfProducers + i]);
        }

        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.add(topPanel, BorderLayout.NORTH);
        mainPanel.add(centerPanel, BorderLayout.CENTER);
        mainPanel.add(bottomPanel, BorderLayout.SOUTH);

        frame.add(mainPanel);
        frame.setVisible(true);
    }

    private static JPanel createCirclePanel() {
        JPanel circlePanel = new JPanel() {
            private Color currentColor = Color.RED;

            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;
                g2d.setColor(currentColor);
                g2d.fillOval(10, 10, 30, 30);
            }
        };
        circlePanel.setPreferredSize(new Dimension(50, 50));
        return circlePanel;
    }

    public synchronized static void updateGUI(Table table, Thread thread, Thread.State threadState) {
        SwingUtilities.invokeLater(() -> {
            int currentMessages = table.getCapacity();

            for (int i = 0; i < squares.length; i++) {
                squares[i].setBackground(i < currentMessages ? Color.GREEN : Color.LIGHT_GRAY);
            }

            String worker = thread.getName().split(" ")[0];
            int indexOfThread = Character.getNumericValue(thread.getName().charAt(thread.getName().length() - 1));
            if (worker.equals("Consumer")) indexOfThread += countOfProducers;

            JPanel circlePanel = actionCircles[indexOfThread];
            if (circlePanel != null) {
                System.out.println(thread.getName() + " " + thread.getState());
                if (threadState == Thread.State.WAITING) {
                    (circlePanel).setBackground(Color.YELLOW);
                } else if (threadState == Thread.State.TIMED_WAITING) {
                    (circlePanel).setBackground(Color.GREEN);
                } else {
                    (circlePanel).setBackground(Color.GRAY);
                }
                circlePanel.repaint();
            }

            frame.repaint();
        });
    }
}
