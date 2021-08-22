package com.example.jpademo.repository;

import com.example.jpademo.model.StudentEntity;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Repository
@Transactional
public interface StudentRepo extends PagingAndSortingRepository<StudentEntity, Long> {
    @Query("SELECT s FROM Student s WHERE s.email = ?1")
    Optional<StudentEntity> findStudentByEmail(String email);

    @Query("SELECT s FROM Student s WHERE s.firstName = ?1 AND s.age >= ?2")
    List<StudentEntity> selectStudentWhereFirstNameAndAgeGreaterOrEqual(
            String firstName, Integer age);

    @Query(
            value = "SELECT * FROM student WHERE first_name = :firstName AND age >= :age",
            nativeQuery = true)
    List<StudentEntity> selectStudentWhereFirstNameAndAgeGreaterOrEqualNative(
            @Param("firstName") String firstName,
            @Param("age") Integer age);

    @Transactional
    @Modifying
    @Query("DELETE FROM Student u WHERE u.id = ?1")
    int  deleteStudentById(Long id);
}
