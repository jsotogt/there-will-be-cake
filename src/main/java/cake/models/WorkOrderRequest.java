package cake.models;

import java.util.Date;

/**
 * Represents a work order for Tim to process.
 */
public class WorkOrderRequest {

    private Long requestorId;
    private Date requestDate;

    public WorkOrderRequest(Long id, Date date) {
        requestorId = id;
        requestDate = date;
    }

    public Long getId() {
        return requestorId;
    }

    public Date getDate() {
        return requestDate;
    }
}
