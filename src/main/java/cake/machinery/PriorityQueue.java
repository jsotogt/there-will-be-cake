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
     * Adds the given WorkOrderRequest to the end of the queue. Only
     * one request with the same id can exist in the queue at any
     * given time. If a second request with the same id is sent,
     * the request will not be added to the queue.
     * @param newRequest the order request to be added
     * @return true if the element was added to the queue, false otherwise
     */
    public synchronized boolean enqueue(WorkOrderRequest newRequest) {

        if(newRequest == null) {
            throw new IllegalArgumentException();
        }

        for(WorkOrderRequest pendingRequest : queue) {
            if(newRequest.id().equals(pendingRequest.id())) {
                return false;
            }
        }

        return queue.add(newRequest);
    }
}
