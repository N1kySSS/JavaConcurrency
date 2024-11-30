package producers_and_consumers_with_thread_states;

import java.util.LinkedList;
import java.util.Queue;

import static producers_and_consumers_with_thread_states.ProducerConsumerTest.updateGUI;

public class Table<ProductType> {

    private final Queue<ProductType> table;
    private final int capacity;

    public Table(int capacity) {
        this.table = new LinkedList<>();
        this.capacity = capacity;
    }

    public synchronized void produce(ProductType product) throws InterruptedException {
        while (table.size() == capacity) {
            updateGUI(this, Thread.currentThread(), Thread.State.WAITING);
            wait();
        }

        table.add(product);
        updateGUI(this, Thread.currentThread(), Thread.State.TIMED_WAITING);
        System.out.println(Thread.currentThread().getName() + " produced product: " + product + ".\n" +
                "Product count - " + table.size());
        notifyAll();
    }

    public synchronized ProductType consume() throws InterruptedException {
        while (table.isEmpty()) {
            updateGUI(this, Thread.currentThread(), Thread.State.WAITING);
            wait();
        }

        ProductType product = table.poll();
        updateGUI(this, Thread.currentThread(), Thread.State.TIMED_WAITING);
        System.out.println(Thread.currentThread().getName() + " consumed product: " + product + ".\n" +
                "Product count - " + table.size());
        notifyAll();

        return product;
    }

    public synchronized int getCapacity() {
        return table.size();
    }
}
