package com.naderi.phorest.salon.controller;

import com.naderi.phorest.salon.common.BaseController;
import com.naderi.phorest.salon.dto.ClientDto;
import com.naderi.phorest.salon.entity.Client;
import com.naderi.phorest.salon.service.SalonService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotNull;
import java.util.List;

@Controller
public class ClientController extends BaseController {
    private SalonService salonService;

    public ClientController(SalonService salonService) {
        this.salonService = salonService;
    }

    @GetMapping(value = {"/clients"})
    public String findAllClients(Model model) {
        List<ClientDto> allClients = salonService.findAllClients();
        model.addAttribute("client", new ClientDto());
        model.addAttribute("clients", allClients);
        return "pages/clients";
    }

    @GetMapping(value = {"/clients/{clientId}/delete"})
    public String deleteClient(Model model, @NotNull @PathVariable String clientId) {
        ClientDto client = salonService.findClientById(clientId);
        salonService.deleteClient(clientId);
        model.addAttribute("message", String.format("Client[%s %s] deleted successfully!", client.getFirstName(), client.getLastName()));
        model.addAttribute("client", new ClientDto());
        model.addAttribute("clients", salonService.findAllClients());
        return "pages/clients";
    }

    @GetMapping(value = {"/clients/{clientId}/update"})
    public String updateClient(Model model, @NotNull @PathVariable String clientId) {
        model.addAttribute("opt", "UPDATE");
        model.addAttribute("client", salonService.findClientById(clientId));
        model.addAttribute("clients", salonService.findAllClients());
        return "pages/clients";
    }

    @PostMapping(value = {"/clients/update"})
    public String updateClient(Model model, @ModelAttribute ClientDto dto) {
        ClientDto client = salonService.findClientById(dto.getId());
        if (client != null) {
            client = salonService.updateClient(dto);
            model.addAttribute("message", String.format("Client[%s %s] updated successfully!", client.getFirstName(), client.getLastName()));
        } else {
            model.addAttribute("message", String.format("Invalid Client Id[%s]!", dto.getId()));
        }
        model.addAttribute("client", new ClientDto());
        model.addAttribute("clients", salonService.findAllClients());
        return "pages/clients";
    }

    @PostMapping(value = {"/clients/create"})
    public String createClient(Model model, @ModelAttribute ClientDto dto) {
        Client client = salonService.createClient(dto);
        model.addAttribute("opt", "CREATE");
        model.addAttribute("message", String.format("Client[%s %s] created successfully!", client.getFirstName(), client.getLastName()));
        model.addAttribute("client", new ClientDto());
        model.addAttribute("clients", salonService.findAllClients());
        return "pages/clients";
    }


    @GetMapping("/restapi/v1/clients")
    @ResponseBody
    public ResponseEntity<?> findAllClients() {
        return ResponseEntity.ok(salonService.findAllClients());
    }

    @GetMapping("/restapi/v1/clients/{clientId}")
    @ResponseBody
    public ResponseEntity<?> findAllClientById(@NotNull @PathVariable String clientId) {
        return ResponseEntity.ok(salonService.findClientById(clientId));
    }

    @PutMapping("/restapi/v1/clients")
    @ResponseBody
    public ResponseEntity<?> updateClient(@NotNull @RequestBody ClientDto clientDto) {
        return ResponseEntity.ok(salonService.updateClient(clientDto));
    }

    @PostMapping("/restapi/v1/clients")
    @ResponseBody
    public ResponseEntity<?> createClient(@NotNull @RequestBody ClientDto clientDto) {
        return ResponseEntity.ok(salonService.createClient(clientDto));
    }

    @DeleteMapping("/restapi/v1/clients/{clientId}")
    @ResponseBody
    public ResponseEntity<?> deleteClientById(@NotNull @PathVariable String clientId) {
        salonService.deleteClient(clientId);
        return ResponseEntity.ok(String.format("Client[%s] has deleted successfully!", clientId));
    }
}
