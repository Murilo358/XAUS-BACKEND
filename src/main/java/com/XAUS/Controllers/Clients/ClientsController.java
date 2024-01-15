package com.XAUS.Controllers.Clients;

import com.XAUS.DTOS.Clients.ClientsRequestDTO;
import com.XAUS.Models.Clients.Clients;
import com.XAUS.Services.Clients.ClientsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("clients")
public class ClientsController {

    @Autowired
    ClientsService clientsService;


    @PostMapping("/create")
    public Clients createNewClient(@RequestBody ClientsRequestDTO data){

        return this.clientsService.newClient(data);

    }

    @GetMapping("/bycpf/{CPF}")
    public Clients getClientByCPF(@PathVariable("CPF") String CPF){

        return this.clientsService.findClientyByCPF(CPF);
    }

    @GetMapping("{id}")
    public Clients findById(@PathVariable Long id ){
        return this.clientsService.findById(id);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity updateClient(@PathVariable Long id, @RequestBody ClientsRequestDTO newData){

        return this.clientsService.updateClient(id, newData);
    }

    @GetMapping("/getall")
    public List<Clients> getAllClients(){
        return this.clientsService.getAll();
    }

    @DeleteMapping("/delete/{clientId}")
    public ResponseEntity deleteProduct(@PathVariable Long clientId){

        return this.clientsService.deleteClient(clientId);
    }
}
