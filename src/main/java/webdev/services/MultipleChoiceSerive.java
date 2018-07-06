package webdev.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import webdev.models.Exam;
import webdev.models.MultipleChoiceQuestion;
import webdev.models.Question;
import webdev.repositories.ExamRepository;
import webdev.repositories.MultipleChoiceQuestionRepository;
import webdev.repositories.QuestionRepository;

@RestController
@CrossOrigin(origins = "*", maxAge=3600)
public class MultipleChoiceSerive {
	@Autowired
	ExamRepository examRepository;
	@Autowired
	QuestionRepository baseRepo;
	@Autowired
	MultipleChoiceQuestionRepository multiRepo;

	// find specific question by id
	@GetMapping("api/question/{questionId}/choice")
	public MultipleChoiceQuestion findMultipleChoiceQuestionById
				(@PathVariable("questionId") int questionId) {
		Optional<MultipleChoiceQuestion> optionalQuestion = 
						multiRepo.findById(questionId);
		if (optionalQuestion.isPresent()) {
			return optionalQuestion.get();
		}
		return null;
	}
	// create multiple
	@PostMapping("api/exam/{examId}/choice")
	public Question createMutipleChoiceQuestion(
					@PathVariable("examId") int examId,
					@RequestBody MultipleChoiceQuestion newQ) {
		Optional<Exam> optionalExam = examRepository.findById(examId);
		if(optionalExam.isPresent()) {		
			Exam exam = optionalExam.get();
			newQ.setExam(exam);
			newQ.setType("MultipleChoice");
			return multiRepo.save(newQ);
		}	
		return null;
	}
	// update multiple choice
	@PutMapping("api/question/{questionId}/choice") 
	public Question updateMultipleChoiceQuestion(
			@PathVariable("questionId")int questionId,
			@RequestBody MultipleChoiceQuestion updatedQ) {
		Optional<MultipleChoiceQuestion> optionalQ = multiRepo.findById(questionId);
		if(optionalQ.isPresent()) {		
			MultipleChoiceQuestion Q = optionalQ.get();
			Q.setTitle(updatedQ.getTitle());
			Q.setDescription(updatedQ.getDescription());
			Q.setPoints(updatedQ.getPoints());
			Q.setOptions(updatedQ.getOptions());
			Q.setCorrectOption(updatedQ.getCorrectOption());
			return multiRepo.save(Q);
		}	
		return null;
	}

}
