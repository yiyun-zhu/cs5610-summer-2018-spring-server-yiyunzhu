package webdev.repositories;

import org.springframework.data.repository.CrudRepository;

import webdev.models.Question;

public interface QuestionRepository 
			extends CrudRepository<Question, Integer>{

}
