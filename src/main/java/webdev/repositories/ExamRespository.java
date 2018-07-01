package webdev.repositories;

import org.springframework.data.repository.CrudRepository;

import webdev.models.Exam;

public interface ExamRespository 
	extends CrudRepository<Exam, Integer>{

}
