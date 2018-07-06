package webdev.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import webdev.models.EssayQuestion;
import webdev.models.Exam;
import webdev.models.Question;
import webdev.repositories.EssayQuestionRepository;
import webdev.repositories.ExamRepository;
import webdev.repositories.QuestionRepository;

@RestController
@CrossOrigin(origins = "*", maxAge=3600)
public class EssayService {
	@Autowired
	ExamRepository examRepository;
	@Autowired
	QuestionRepository baseRepo;
	@Autowired
	EssayQuestionRepository essayRepo;
	
	//create essay
	@PostMapping("api/exam/{examId}/essay")
	public Question createTrueFalseQuestion(
					@PathVariable("examId") int examId,
					@RequestBody EssayQuestion newQ) {
		Optional<Exam> optionalExam = examRepository.findById(examId);
		if(optionalExam.isPresent()) {		
			Exam exam = optionalExam.get();
			newQ.setExam(exam);
			newQ.setType("Essay");
			return essayRepo.save(newQ);
		}
		return null;
	}
	
	// update essay
	@PutMapping("api/question/{questionId}/essay") 
	public Question updateEssayQuestion(
			@PathVariable("questionId")int questionId,
			@RequestBody EssayQuestion updatedQ) {
		Optional<EssayQuestion> optionalQ = essayRepo.findById(questionId);
		if(optionalQ.isPresent()) {		
			EssayQuestion Q = optionalQ.get();
			Q.setTitle(updatedQ.getTitle());
			Q.setDescription(updatedQ.getDescription());
			Q.setPoints(updatedQ.getPoints());
			return essayRepo.save(Q);
		}	
		return null;
	}
}
