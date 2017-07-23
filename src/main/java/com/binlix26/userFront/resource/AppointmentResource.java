package com.binlix26.userFront.resource;

import com.binlix26.userFront.model.Appointment;
import com.binlix26.userFront.service.AppointmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/appointment")
@PreAuthorize("hasRole('ADMIN')")
public class AppointmentResource {

    @Autowired
    private AppointmentService appointmentService;

    @RequestMapping(value = "/all", method = RequestMethod.GET)
    public List<Appointment> appointmentList() {
        return appointmentService.findAll();
    }

    @RequestMapping(value = "/{id}/confirm", method = RequestMethod.GET)
    public void confirmAppointment(@PathVariable("id") Long id) {
        appointmentService.confirmAppointment(id);
    }
}
