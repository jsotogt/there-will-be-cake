package cake.controllers;

import cake.models.WorkOrderRequest;
import cake.services.PriorityQueueService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.text.ParseException;
import java.util.Date;
import java.util.List;

@RestController
public class PriorityQueueController {

    @Autowired
    PriorityQueueService priorityQueueService;

    @RequestMapping(value = "/requests/{id}/{time}", method = RequestMethod.POST)
    public ResponseEntity<String> enqueue(@PathVariable("id") Long id, @PathVariable("time") String time) {

        if(id <= 0) {
            return new ResponseEntity<>("invalid id", HttpStatus.UNPROCESSABLE_ENTITY);
        }

        Date d;
        try {
            d = priorityQueueService.parse(time);
        } catch (ParseException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }

        WorkOrderRequest newRequest = new WorkOrderRequest(id, d);

        if(priorityQueueService.enqueue(newRequest)){
            return new ResponseEntity(HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Requester already has a pending request", HttpStatus.CONFLICT);
        }

    }

    @RequestMapping(value = "/requests", method = RequestMethod.POST)
    public ResponseEntity<WorkOrderRequest> dequeue() {

        WorkOrderRequest workRequest = priorityQueueService.dequeue();

        if(workRequest == null) {
            return new ResponseEntity(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity(workRequest, HttpStatus.OK);
        }

    }

    @RequestMapping(value = "/requests", method = RequestMethod.GET)
    public ResponseEntity<List<WorkOrderRequest>> list() {
        return new ResponseEntity(priorityQueueService.list(), HttpStatus.OK);
    }

    @RequestMapping(value = "/requests/{id}", method = RequestMethod.DELETE)
    public ResponseEntity remove(@PathVariable("id") Long id) {

        if(priorityQueueService.delete(id) != null) {
            return new ResponseEntity(HttpStatus.OK);
        } else {
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }

    }

    @RequestMapping(value = "/requests/{id}/position", method = RequestMethod.GET)
    public ResponseEntity<Integer> position(@PathVariable("id") Long id) {

        if(id <= 0) {
            return new ResponseEntity(HttpStatus.UNPROCESSABLE_ENTITY);
        }

        Integer position = priorityQueueService.position(id);

        if(position == null) {
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity(position, HttpStatus.OK);

    }

    @RequestMapping(value = "/requests/average/{time}", method = RequestMethod.GET)
    public ResponseEntity<Long> average(@PathVariable("time") String time) {

        return new ResponseEntity(HttpStatus.OK); // TODO implement

    }
}
