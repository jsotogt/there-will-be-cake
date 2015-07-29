package cake.machinery;

import cake.models.WorkOrderRequest;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
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

        queue.add(newRequest);
        Collections.sort(queue, Collections.reverseOrder(new WorkOrderComparator(newRequest.time())));

        return true;
    }

    /**
     * @return a list containing the queued ids, in order
     */
    public synchronized List<Long> getListOfIds() {

        List<Long> list = new ArrayList<>();

        for(WorkOrderRequest w : queue) {
            list.add(w.id());
        }

        return list;

    }

    /**
     * Removes the work request with the given id, if one is present.
     * @param id the id of the work request to remove
     * @return the removed work request, or null if not matching request was found
     */
    public synchronized WorkOrderRequest delete(Long id) {

        for(int i=0; i<queue.size(); i++) {
            WorkOrderRequest w = queue.get(i);
            if(w.id().equals(id)) {
                return queue.remove(i);
            }
        }

        return  null;

    }

    /**
     * @return the position in the queue of the work request with the given id, or null if not found
     */
    public synchronized Integer position(Long id) {

        for(int i=0; i<queue.size(); i++) {
            WorkOrderRequest w = queue.get(i);
            if(w.id().equals(id)) {
                return i;
            }
        }

        return  null;

    }

    /**
     * Returns the average waiting time for all elements in the queue.
     * @param reference the current system time
     * @return the average waiting time in seconds
     */
    public synchronized Double average(Date reference) {

        double t = 0;
        int n = 0;

        for(WorkOrderRequest w : queue) {

            if(w.time().getTime() > reference.getTime()) { // ignore dates in the future
                continue;
            }

            t = t + (reference.getTime() - w.time().getTime()) / 1000.0;
            n = n + 1;
        }

        return (t / n);

    }
}
