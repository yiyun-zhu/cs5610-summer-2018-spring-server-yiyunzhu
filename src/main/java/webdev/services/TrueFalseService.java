package webdev.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import webdev.models.Exam;
import webdev.models.Question;
import webdev.models.TrueFalseQuestion;
import webdev.repositories.ExamRepository;
import webdev.repositories.QuestionRepository;
import webdev.repositories.TrueFalseQuestionRepository;

@RestController
@CrossOrigin(origins = "*", maxAge=3600)
public class TrueFalseService {
	@Autowired
	ExamRepository examRepository;
	@Autowired
	QuestionRepository baseRepo;
	@Autowired
	TrueFalseQuestionRepository truefRepo;
		
	// create truefalse
	@PostMapping("api/exam/{examId}/truefalse")
	public Question createTrueFalseQuestion(
					@PathVariable("examId") int examId,
					@RequestBody TrueFalseQuestion newQ) {
		Optional<Exam> optionalExam = examRepository.findById(examId);
		if(optionalExam.isPresent()) {		
			Exam exam = optionalExam.get();
			newQ.setExam(exam);
			newQ.setType("TrueFalse");
			return truefRepo.save(newQ);
		}
		return null;
	}
	// update true false 
	@PutMapping("api/question/{questionId}/truefalse") 
	public Question updateTrueFalseQuestion(
			@PathVariable("questionId")int questionId,
			@RequestBody TrueFalseQuestion updatedQ) {
		Optional<TrueFalseQuestion> optionalQ = truefRepo.findById(questionId);
		if(optionalQ.isPresent()) {		
			TrueFalseQuestion Q = optionalQ.get();
			Q.setTitle(updatedQ.getTitle());
			Q.setDescription(updatedQ.getDescription());
			Q.setPoints(updatedQ.getPoints());
			Q.setIsTrue(updatedQ.getIsTrue());
			return truefRepo.save(Q);
		}	
		return null;
	}
}
