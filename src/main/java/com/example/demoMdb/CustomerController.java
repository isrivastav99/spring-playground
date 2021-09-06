package com.example.demoMdb;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RequestMapping("api/v1/")
@RestController
public class CustomerController {

    @Autowired
    private CustomerRepository customerrepository;

    @PostMapping("/customer")
    public ResponseEntity<Customer> createCustomer(@RequestBody Customer customer) {
        try {
            Customer _customer = customerrepository.save(new Customer(customer.getFirstName(),customer.getLastName()));
            return new ResponseEntity<>(_customer, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/customer")
    public ResponseEntity<List<Customer>> getAllCustomers(@RequestParam(required = false) String title) {
        try {
            List<Customer> customers = new ArrayList<Customer>();


                customerrepository.findAll().forEach(customers::add);


            if (customers.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }

            return new ResponseEntity<>(customers, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/customer/{id}")
    public ResponseEntity<List<Customer>> updateCustomer(@PathVariable("id") String id, @RequestBody Customer customer) {
        Optional<Customer> customerData = customerrepository.findById(id);

        if(customerData.isPresent()){
            Customer _customer = customerData.get();
            _customer.setFirstName(customer.getFirstName());
            _customer.setLastName(customer.getLastName());
            customerrepository.save(_customer);
            return new ResponseEntity<>(_customer, HttpStatus.OK);
        }
        else{
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

}
