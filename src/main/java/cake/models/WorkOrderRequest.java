package cake.models;

import java.util.Date;

/**
 * Represents a work order for Tim to process.
 */
public class WorkOrderRequest {

    private Integer requestorId;
    private Date requestDate;

    public WorkOrderRequest(Integer id, Date date) {
        requestorId = id;
        requestDate = date;
    }

    public Integer getId() {
        return requestorId;
    }

    public Date getDate() {
        return requestDate;
    }
}
