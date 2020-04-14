package com.springboot.backend.apirest.springbootbackendapirest.controllers;

import com.springboot.backend.apirest.springbootbackendapirest.models.entity.Cliente;
import com.springboot.backend.apirest.springbootbackendapirest.models.services.IClienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@CrossOrigin(origins = {"http://localhost:4200"})
@RestController
@RequestMapping("/api")
public class ClienteController {

    private static final String MENSAJE = "mensaje";
    private static final String ERROR = "error";

    @Autowired
    private IClienteService clienteService;

    @GetMapping(value = "/clientes")
    public List<Cliente> index() {
        return clienteService.findAll();
    }

    @GetMapping("/clientes/{id}")
    public ResponseEntity<?> show(@PathVariable Long id) {
        Cliente cliente = null;
        Map<String, Object> response = new HashMap<>();

        try {
            cliente = clienteService.findById(id);
        } catch (DataAccessException e) {
            response.put(MENSAJE, "Error al realizar la consulta en la base de datos");
            response.put(ERROR, e.getMessage().concat(":").concat(e.getMostSpecificCause().getMessage()));
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        if (cliente == null) {
            response.put(MENSAJE, "El cliente con el ID ".concat(id.toString()).concat(" no existe "));
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(cliente, HttpStatus.OK);
    }

    @PostMapping(value = "/clientes")
    public ResponseEntity<?> create(@Valid @RequestBody  Cliente cliente, BindingResult result) {
        Cliente clienteNew = null;
        Map<String, Object> response = new HashMap<>();
        if (result.hasErrors()) {
            List<String> errors = result.getFieldErrors().stream()
                    .map(e -> "El campo '" + e.getField() + "' " + e.getDefaultMessage())
                    .collect(Collectors.toList());
            response.put("errors", errors);
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
        try{
            clienteNew = clienteService.save(cliente);
        } catch (DataAccessException e) {
            response.put(MENSAJE, "Error al realizar la insercion en la base de datos");
            response.put(ERROR, e.getMessage().concat(":").concat(e.getMostSpecificCause().getMessage()));
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        response.put(MENSAJE, "el cliente se ha insertado con exito!");
        response.put("cliente", clienteNew);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @PutMapping(value = "/clientes/{id}")
    public ResponseEntity<?> update (@Valid @RequestBody Cliente cliente, BindingResult result, @PathVariable Long id) {
        Cliente clienteActual = null;
        Map<String, Object> response = new HashMap<>();

        if (result.hasErrors()) {
            List<String> errors = result.getFieldErrors().stream()
                    .map(e -> "El campo '" + e.getField() + "' " + e.getDefaultMessage())
                    .collect(Collectors.toList());
            response.put("errors", errors);
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }

        clienteActual = clienteService.findById(id);

        if (clienteActual == null) {
            response.put(MENSAJE, "El cliente con el ID ".concat(id.toString()).concat(" no existe!"));
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }

        try {
            clienteActual.setApellido(cliente.getApellido());
            clienteActual.setNombre(cliente.getNombre());
            clienteActual.setEmail(cliente.getEmail());
            clienteService.save(clienteActual);
        } catch (DataAccessException e) {
            response.put(MENSAJE, "Error al realizar la modificacion en la base de datos");
            response.put(ERROR, e.getMessage().concat(":").concat(e.getMostSpecificCause().getMessage()));
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        response.put(MENSAJE, "cliente actualizado con exito!");
        response.put("cliente", clienteActual);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @DeleteMapping(value = "/clientes/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        Map<String, Object> response = new HashMap<>();
        try {
            clienteService.delete(id);
        } catch (DataAccessException e) {
            response.put(MENSAJE, "Error al realizar la eliminacion en la base de datos");
            response.put(ERROR, e.getMessage().concat(":").concat(e.getMostSpecificCause().getMessage()));
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        response.put(MENSAJE, "cliente borrado con exito!");
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

}
