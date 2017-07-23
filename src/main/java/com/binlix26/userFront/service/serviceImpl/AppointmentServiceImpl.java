package com.binlix26.userFront.service.serviceImpl;

import com.binlix26.userFront.model.Appointment;
import com.binlix26.userFront.model.User;
import com.binlix26.userFront.repository.AppointmentRepository;
import com.binlix26.userFront.service.AppointmentService;
import com.binlix26.userFront.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by binlix26 on 22/07/17.
 */
@Service
public class AppointmentServiceImpl implements AppointmentService {

    @Autowired
    private AppointmentRepository appointmentRepository;

    @Autowired
    private UserService userService;

    @Override
    public Appointment findAppointment(Long id) {
        return appointmentRepository.findOne(id);
    }

    @Override
    public Appointment createAppointment(Appointment appointment) {
        return appointmentRepository.save(appointment);
    }

    @Override
    public List<Appointment> findAll() {
        return appointmentRepository.findAll();
    }

    @Override
    public List<Appointment> findAll(String username) {
        User user = userService.findByUsername(username);
        List<Appointment> appointmentList = appointmentRepository.findAll().stream()
                .filter(appointment -> user.getUserId() == appointment.getUser().getUserId())
                .collect(Collectors.toList());
        return appointmentList;
    }

    @Override
    public void confirmAppointment(Long id) {
        Appointment appointment = findAppointment(id);
        appointment.setConfirmed(true);
        appointmentRepository.save(appointment);
    }
}
