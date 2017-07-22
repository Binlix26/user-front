package com.binlix26.userFront.controller;

import com.binlix26.userFront.model.Appointment;
import com.binlix26.userFront.model.User;
import com.binlix26.userFront.service.AppointmentService;
import com.binlix26.userFront.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.security.Principal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by binlix26 on 22/07/17.
 */
@Controller
@RequestMapping(value = "/appointment")
public class AppointmentController {

    @Autowired
    private UserService userService;

    @Autowired
    private AppointmentService appointmentService;

    @RequestMapping(value = "/create", method = RequestMethod.GET)
    public String getAppointmentForm(Model model) {
        model.addAttribute("appointment", new Appointment());
        model.addAttribute("dateString", "");
        return "appointment";
    }

    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public String createAppointment(@ModelAttribute("appointment") Appointment appointment,
                                    @ModelAttribute("dateString") String dateString, Principal principal) {

//        System.out.println("Appointment: " +
//                appointment.getLocation() + " :: " +
//                appointment.getDescription() + " :: " + dateString);

        User user = userService.findByUsername(principal.getName());
        SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd hh:mm");
        Date date = null;
        try {
            date = format1.parse(dateString);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        appointment.setDate(date);

        appointment.setUser(user);

        appointmentService.createAppointment(appointment);
        return "redirect:/userFront";
    }

    @RequestMapping(value = "/viewAppointment", method = RequestMethod.GET)
    public String viewAppointments(Model model, Principal principal) {
        model.addAttribute("appointmentList", appointmentService.findAll(principal.getName()));
        return "viewAppointment";
    }
}
