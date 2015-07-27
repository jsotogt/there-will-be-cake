package cake.machinery;

import cake.models.WorkOrderRequest;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * Thread-safe singleton object that holds WorkOrderRequests that need to be processed by TIM.
 */
@Component
public class PriorityQueue {

    private List<WorkOrderRequest> queue;

    public PriorityQueue() {
        queue = new ArrayList<>();
    }

    /**
     * @return the element at the beginning of the queue, or null if the queue is empty
     */
    public synchronized WorkOrderRequest dequeue() {

        if (queue.isEmpty())
            return null;

        return queue.remove(0);

    }

    /**
     * Adds the given WorkOrderRequest to the end of the queue
     * @param w the order request to be added
     * @return true if the element was added to the queue, false otherwise
     */
    public synchronized boolean enqueue(WorkOrderRequest w) {
        return queue.add(w);
    }
}
