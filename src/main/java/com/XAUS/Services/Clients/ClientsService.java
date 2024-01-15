package com.XAUS.Services.Clients;

import com.XAUS.DTOS.Clients.ClientsRequestDTO;
import com.XAUS.Exceptions.CustomException;
import com.XAUS.Models.Clients.Clients;
import com.XAUS.Repositories.Clients.ClientsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;
import org.apache.commons.validator.routines.EmailValidator;


import java.util.List;


@Service
public class ClientsService {

    @Autowired
    ClientsRepository repository;

    public void handleNotFoundClients(Clients client){

        if(client == null){
            throw new CustomException("Cliente não encontrado", HttpStatus.NOT_FOUND);
        }
    }

    public void validateCpf(String cpf){
        if(!cpf.matches("[0-9]{3}\\.?[0-9]{3}\\.?[0-9]{3}\\-?[0-9]{2}")){
            throw new CustomException("CPF inválido!", HttpStatus.NOT_ACCEPTABLE);
        }

    }

    public void validateEmail(String email){
        
        boolean valid = EmailValidator.getInstance().isValid(email);

        if(!valid){
            throw new CustomException("Email inválido!", HttpStatus.NOT_ACCEPTABLE);
        }

    }



    public void checkAlreadyExists(String cpf, String email){
        Clients alreadyAddedCPF = repository.findbyCPF(cpf);
        Clients alreadyAddedEmail = repository.findbyEmail(email);

        if(cpf == null || email == null){
            throw new CustomException("O email ou o cpf não pode ser null", HttpStatus.BAD_GATEWAY);
        }
        else if(alreadyAddedCPF != null  ){
            throw new CustomException("CPF já cadastrado", HttpStatus.BAD_GATEWAY);
        }
        else if(alreadyAddedEmail != null){
            throw new CustomException("Email já cadastrado", HttpStatus.BAD_GATEWAY);
        }

    }


    public Clients newClient(@RequestBody ClientsRequestDTO data) {

       this.checkAlreadyExists(data.cpf(), data.email());

        validateCpf(data.cpf());

        validateEmail(data.email());

        Clients clientData = new Clients(data);
        return repository.save(clientData);

    }

    public Clients findClientyByCPF(String CPF){

        Clients client =  repository.findbyCPF(CPF);

        this.handleNotFoundClients(client);

        return client;
    }

    public Clients findById(Long id){

        Clients client =  repository.findById(id).orElse(null);

        this.handleNotFoundClients(client);
        return client;
    }

    public Clients findbyEmail(String Email){

        Clients client = repository.findbyEmail(Email);

        this.handleNotFoundClients(client);

        return client;
    }

    public ResponseEntity updateClient (Long id, @RequestBody ClientsRequestDTO newData){

        if( newData.email() == null || newData.name() == null){
            throw new CustomException("O email ou o nome não pode ser vázio", HttpStatus.BAD_GATEWAY);
        }

        if(id == 1){
            throw new CustomException("Esse cliente não pode ser alterado", HttpStatus.BAD_GATEWAY);
        }

        Clients client = this.findById(id);

        client.setEmail(newData.email());
        client.setName(newData.name());
        repository.save(client);
        return ResponseEntity.ok().build();
    }

    public List<Clients> getAll(){
        return this.repository.findAll();
    }


    public ResponseEntity deleteClient(Long id){

        if(id == 1){
            throw new CustomException("Esse cliente não pode ser deletado", HttpStatus.BAD_GATEWAY);
        }

        //Just checking if isn't null
        this.findById(id);

        repository.deleteById(id);
        return  ResponseEntity.ok().build();
    }

}
