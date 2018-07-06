package webdev.repositories;

import org.springframework.data.repository.CrudRepository;

import webdev.models.FillInBlanksQuestion;

public interface FillInBlanksQuestionRepository 
		extends CrudRepository<FillInBlanksQuestion, Integer>{

}
