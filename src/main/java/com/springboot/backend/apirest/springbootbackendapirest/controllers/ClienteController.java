package com.springboot.backend.apirest.springbootbackendapirest.controllers;

import com.springboot.backend.apirest.springbootbackendapirest.models.entity.Cliente;
import com.springboot.backend.apirest.springbootbackendapirest.models.services.IClienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@CrossOrigin(origins = {"http://localhost:4200"})
@RestController
@RequestMapping("/api")
public class ClienteController {

    @Autowired
    private IClienteService clienteService;

    @RequestMapping("/clientes")
    public List<Cliente> index() {
        return clienteService.findAll();
    }

}
