package com.example.jpademo.repository;

import com.example.jpademo.model.StudentIdCard;
import org.springframework.data.repository.CrudRepository;

public interface StudentIdCardRepository  extends CrudRepository<StudentIdCard, Long> {
}
