package com.springboot.backend.apirest.springbootbackendapirest.models.services;

import com.springboot.backend.apirest.springbootbackendapirest.models.entity.Cliente;

import java.util.List;

public interface IClienteService  {

    List<Cliente> findAll();

    Cliente save(Cliente cliente);

    void delete(Long id);

    Cliente findById(Long id);

}
