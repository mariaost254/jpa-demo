package com.example.jpademo;

import com.example.jpademo.model.*;
import com.example.jpademo.model.StudentIdCard;
import com.example.jpademo.repository.StudentIdCardRepository;
import com.example.jpademo.repository.StudentRepo;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import com.github.javafaker.Faker;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;

import java.time.LocalDateTime;
import java.util.List;

@SpringBootApplication
public class JpaDemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(JpaDemoApplication.class, args);
	}
	@Bean
	CommandLineRunner commandLineRunner(
			StudentRepo studentRepository,
			StudentIdCardRepository studentIdCardRepository) {
		return args -> {
			Faker faker = new Faker();

			String firstName = faker.name().firstName();
			String lastName = faker.name().lastName();
			String email = String.format("%s.%s@amigoscode.edu", firstName, lastName);
			StudentEntity student = new StudentEntity(
					firstName,
					lastName,
					email,
					faker.number().numberBetween(17, 55));

			student.addBook(
					new Book("Clean Code", LocalDateTime.now().minusDays(4)));

			student.addBook(
					new Book("Think and Grow Rich", LocalDateTime.now()));

			student.addBook(
					new Book("Spring Data JPA", LocalDateTime.now().minusYears(1)));

			StudentIdCard studentIdCard = new StudentIdCard(
					"123456789",
					student);

			student.setStudentIdCard(studentIdCard);

			student.addEnrolment(new Enrolment(
					new EnrolmentId(1L, 1L),
					student,
					new Course("Computer Science", "IT"),
					LocalDateTime.now()
			));

			student.addEnrolment(new Enrolment(
					new EnrolmentId(1L, 2L),
					student,
					new Course("Amigoscode Spring Data JPA", "IT"),
					LocalDateTime.now().minusDays(18)
			));

			student.addEnrolment(new Enrolment(
					new EnrolmentId(1L, 2L),
					student,
					new Course("Amigoscode Spring Data JPA", "IT"),
					LocalDateTime.now().minusDays(18)
			));



			studentRepository.save(student);

			studentRepository.findById(1L)
					.ifPresent(s -> {
						System.out.println("fetch book lazy...");
						List<Book> books = student.getBooks();
						books.forEach(book -> {
							System.out.println(
									s.getFirstName() + " borrowed " + book.getBookName());
						});
					});

		};
	}

}
