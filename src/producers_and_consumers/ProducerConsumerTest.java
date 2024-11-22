package producers_and_consumers;

public class ProducerConsumerTest {

    public static void main(String[] args) {
        Table<String> table = new Table<>(5);

        Producer<String> firstProducer = new Producer<>(table, 400);
        Producer<String> secondProducer = new Producer<>(table, 800);

        Consumer<String> firstConsumer = new Consumer<>(table, 600);
        Consumer<String> secondConsumer = new Consumer<>(table, 1000);

        new Thread(firstProducer, "First producer").start();
        new Thread(secondProducer, "Second producer").start();
        new Thread(firstConsumer, "First consumer").start();
        new Thread(secondConsumer, "First consumer").start();
    }
}
