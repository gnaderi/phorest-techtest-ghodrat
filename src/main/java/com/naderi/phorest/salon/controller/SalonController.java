package com.naderi.phorest.salon.controller;

import com.naderi.phorest.salon.common.BaseController;
import com.naderi.phorest.salon.service.SalonService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class SalonController extends BaseController {
    private SalonService salonService;

    public SalonController(SalonService salonService) {
        this.salonService = salonService;
    }

    @RequestMapping(value = {"/secured/clients"})
    public String findAllClients(Model model) {
        model.addAttribute("clients", salonService.findAllClients());
        return "pages/clients";
    }

    @RequestMapping(value = {"/secured/clients/{clientId}/appointments"})
    public String findClientAppointments(Model model, @PathVariable String clientId) {
        model.addAttribute("client", salonService.findClientById(clientId));
        model.addAttribute("appointments", salonService.findClientAppointments(clientId));
        return "pages/appointments";
    }

    @RequestMapping(value = {"/secured/clients/{clientId}/appointments/{appointmentId}/services"})
    public String findAppointmentServices(Model model, @PathVariable String clientId, @PathVariable String appointmentId) {
        model.addAttribute("client", salonService.findClientById(clientId));
        model.addAttribute("appointmentId", appointmentId);
        model.addAttribute("services", salonService.findAppointmentServices(appointmentId));
        return "pages/services";
    }
    @RequestMapping(value = {"/secured/clients/{clientId}/appointments/{appointmentId}/purchases"})
    public String findAppointmentPurchases(Model model, @PathVariable String clientId, @PathVariable String appointmentId) {
        model.addAttribute("client", salonService.findClientById(clientId));
        model.addAttribute("appointmentId", appointmentId);
        model.addAttribute("purchases", salonService.findAppointmentPurchases(appointmentId));
        return "pages/purchases";
    }
}
