package webdev.repositories;

import org.springframework.data.repository.CrudRepository;

import webdev.models.EssayQuestion;

public interface EssayQuestionRepository 
		extends CrudRepository<EssayQuestion, Integer>{

}
