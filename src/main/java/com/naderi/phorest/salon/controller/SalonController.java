package com.naderi.phorest.salon.controller;

import com.naderi.phorest.salon.common.BaseController;
import com.naderi.phorest.salon.dto.ClientDto;
import com.naderi.phorest.salon.service.SalonService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotNull;

@Controller
public class SalonController extends BaseController {
    private SalonService salonService;

    public SalonController(SalonService salonService) {
        this.salonService = salonService;
    }

    @RequestMapping(value = {"/clients/{clientId}/appointments"}, method = RequestMethod.GET)
    public String findClientAppointments(Model model, @NotNull @PathVariable String clientId) {
        model.addAttribute("client", salonService.findClientById(clientId));
        model.addAttribute("appointments", salonService.findClientAppointments(clientId));
        return "pages/appointments";
    }

    @RequestMapping(value = {"/restapi/v1/clients/{clientId}/appointments"}, method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<?> findClientAppointments(@NotNull @PathVariable String clientId) {
        return ResponseEntity.ok(salonService.findClientAppointments(clientId));
    }

    @RequestMapping(value = {"/clients/{clientId}/appointments/{appointmentId}/services"})
    public String findAppointmentServices(Model model, @NotNull @PathVariable String clientId, @NotNull @PathVariable String appointmentId) {
        model.addAttribute("client", salonService.findClientById(clientId));
        model.addAttribute("appointmentId", appointmentId);
        model.addAttribute("services", salonService.findAppointmentServices(appointmentId));
        return "pages/services";
    }

    @RequestMapping(value = {"/restapi/v1/clients/{clientId}/appointments/{appointmentId}/services"}, method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<?> findAppointmentServices(@NotNull @PathVariable String clientId, @NotNull @PathVariable String appointmentId) {
        return ResponseEntity.ok(salonService.findAppointmentServices(appointmentId));
    }

    @RequestMapping(value = {"/clients/{clientId}/appointments/{appointmentId}/purchases"})
    public String findAppointmentPurchases(Model model, @NotNull @PathVariable String clientId, @NotNull @PathVariable String appointmentId) {
        model.addAttribute("client", salonService.findClientById(clientId));
        model.addAttribute("appointmentId", appointmentId);
        model.addAttribute("purchases", salonService.findAppointmentPurchases(appointmentId));
        return "pages/purchases";
    }

    @RequestMapping(value = {"/restapi/v1/clients/{clientId}/appointments/{appointmentId}/purchases"}, method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<?> findAppointmentPurchases(@NotNull @PathVariable String clientId, @NotNull @PathVariable String appointmentId) {
        return ResponseEntity.ok(salonService.findAppointmentPurchases(appointmentId));
    }


}
