package com.binlix26.userFront.service;

import com.binlix26.userFront.model.Appointment;

import java.util.List;

/**
 * Created by binlix26 on 22/07/17.
 */
public interface AppointmentService {
    Appointment findAppointment(Long id);

    Appointment createAppointment(Appointment appointment);

    List<Appointment> findAll(String username);

    List<Appointment> findAll();

    void confirmAppointment(Long id);
}
