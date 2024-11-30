package producers_and_consumers_with_thread_states;

import static java.util.concurrent.TimeUnit.MILLISECONDS;

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
                ProductType product = table.consume();
                MILLISECONDS.sleep(timeToConsume);
            }

        } catch (InterruptedException interruptedException) {
            Thread.currentThread().interrupt();
        }
    }
}
