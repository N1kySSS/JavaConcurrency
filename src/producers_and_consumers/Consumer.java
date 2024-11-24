package producers_and_consumers;

import static java.util.concurrent.TimeUnit.MILLISECONDS;
import static producers_and_consumers.ProducerConsumerTest.updateGUI;

public class Consumer<ProductType> implements Runnable {

    private final Table<ProductType> table;
    private final int timeToConsume;

    public Consumer(Table<ProductType> table, int timeToConsume) {
        this.table = table;
        this.timeToConsume = timeToConsume;
    }

    @Override
    public void run() {
        try {
            while (true) {
                updateGUI(table, Thread.currentThread(), Thread.State.WAITING);
                ProductType product = table.consume();
                updateGUI(table, Thread.currentThread(), Thread.State.TIMED_WAITING);
                MILLISECONDS.sleep(timeToConsume);
            }

        } catch (InterruptedException interruptedException) {
            Thread.currentThread().interrupt();
        }
    }
}
