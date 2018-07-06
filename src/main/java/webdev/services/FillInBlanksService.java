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
import webdev.models.FillInBlanksQuestion;
import webdev.models.Question;
import webdev.repositories.ExamRepository;
import webdev.repositories.FillInBlanksQuestionRepository;
import webdev.repositories.QuestionRepository;

@RestController
@CrossOrigin(origins = "*", maxAge=3600)
public class FillInBlanksService {
	@Autowired
	ExamRepository examRepository;
	@Autowired
	QuestionRepository baseRepo;
	@Autowired
	FillInBlanksQuestionRepository fillbRepo;
	
	// create fill in blanks
	@PostMapping("api/exam/{examId}/blanks")
	public Question createFillInBlanksQuestion(
					@PathVariable("examId") int examId,
					@RequestBody FillInBlanksQuestion newQ) {
		Optional<Exam> optionalExam = examRepository.findById(examId);
		if(optionalExam.isPresent()) {		
			Exam exam = optionalExam.get();
			newQ.setExam(exam);
			newQ.setType("FillInBlanks");
			return fillbRepo.save(newQ);
		}
		return null;
	}
	
	// update fill in blanks
	@PutMapping("api/question/{questionId}/blanks") 
	public Question updateFillInBlanksQuestion(
			@PathVariable("questionId")int questionId,
			@RequestBody FillInBlanksQuestion updatedQ) {
		Optional<FillInBlanksQuestion> optionalQ = fillbRepo.findById(questionId);
		if(optionalQ.isPresent()) {		
			FillInBlanksQuestion Q = optionalQ.get();
			Q.setTitle(updatedQ.getTitle());
			Q.setDescription(updatedQ.getDescription());
			Q.setPoints(updatedQ.getPoints());
			Q.setVariables(updatedQ.getVariables());
			return fillbRepo.save(Q);
		}	
		return null;
	}
}
