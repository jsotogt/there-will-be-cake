package cake.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PriorityQueueController {

    @RequestMapping(value = "/requests/{id}/{date}", method = RequestMethod.POST)
    public ResponseEntity enqueue(@PathVariable("id") Integer id, @PathVariable("date") String date) {

        return new ResponseEntity(HttpStatus.OK); // TODO implement

    }

    @RequestMapping(value = "/requests", method = RequestMethod.POST)
    public ResponseEntity dequeue() {

        return new ResponseEntity(HttpStatus.OK); // TODO implement

    }

    @RequestMapping(value = "/requests", method = RequestMethod.GET)
    public ResponseEntity list() {

        return new ResponseEntity(HttpStatus.OK); // TODO implement

    }

    @RequestMapping(value = "/requests/{id}", method = RequestMethod.DELETE)
    public ResponseEntity remove(@PathVariable("id") Integer id) {

        return new ResponseEntity(HttpStatus.OK); // TODO implement

    }

    @RequestMapping(value = "/requests/{id}/position", method = RequestMethod.GET)
    public ResponseEntity position(@PathVariable("id") Integer id) {

        return new ResponseEntity(HttpStatus.OK); // TODO implement

    }

    @RequestMapping(value = "/requests/average/{time}", method = RequestMethod.GET)
    public ResponseEntity average(@PathVariable("time") String time) {

        return new ResponseEntity(HttpStatus.OK); // TODO implement

    }
}
