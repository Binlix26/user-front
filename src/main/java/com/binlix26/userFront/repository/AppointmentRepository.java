package com.binlix26.userFront.repository;

import com.binlix26.userFront.model.Appointment;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

/**
 * Created by binlix26 on 17/07/17.
 */
public interface AppointmentRepository extends CrudRepository<Appointment, Long> {
    List<Appointment> findAll();
}
