package gestionClientesSpringboot.controllers;

import gestionClientesSpringboot.domain.Customer;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/clientes")
public class CustomerController {

    private final List<Customer> customers = new ArrayList<>(Arrays.asList(
            new Customer(123, "pedro", "userpedro", "123"),
            new Customer(124, "juan", "userjuan", "123"),
            new Customer(125, "gavi", "usergavi", "123"),
            new Customer(126, "maria", "usermaria", "123")
    ));

    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<List<Customer>> getCustomers() {
        return ResponseEntity.ok(customers);
    }

    @RequestMapping(value = "/{username}", method = RequestMethod.GET)
    public ResponseEntity<?> getCliente(@PathVariable String username) {
        for (Customer c : customers) {
            if (c.getUsername().equals(username)) {
                return ResponseEntity.ok(c);
            }
        }

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Cliente no encontrado con username: " + username);
    }

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<?> postCliente(@RequestBody Customer customer) {
        customers.add(customer);

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{username}")
                .buildAndExpand(customer.getUsername())
                .toUri();

        return ResponseEntity.created(location).body(customer);
    }

    @RequestMapping(method = RequestMethod.PUT)
    public ResponseEntity<?> putCliente(@RequestBody Customer customer) {
        for (Customer c : customers) {
            if (c.getID() == customer.getID()) {
                c.setName(customer.getName());
                c.setUsername(customer.getUsername());
                c.setPassword(customer.getUsername());

                return ResponseEntity.noContent().build();
            }
        }

        return ResponseEntity.notFound().build();
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public Customer deleteCliente(@PathVariable int id) {
        for (Customer c : customers) {
            if (c.getID() == id) {
                customers.remove(c);

                return c;
            }
        }

        return null;
    }

    @RequestMapping(method = RequestMethod.PATCH)
    public Customer patchCliente(@RequestBody Customer customer) {
        for (Customer c : customers) {
            if (c.getID() == customer.getID()) {
                if (customer.getName() != null) {
                    c.setName(customer.getName());
                }
                if (customer.getUsername() != null) {
                    c.setName(customer.getUsername());
                }
                if (customer.getPassword() != null) {
                    c.setName(customer.getPassword());
                }

                return c;
            }
        }
        return null;
    }
}