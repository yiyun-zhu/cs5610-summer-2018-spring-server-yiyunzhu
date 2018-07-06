package webdev.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import webdev.models.*;
import webdev.repositories.*;

@RestController
@CrossOrigin(origins = "*", maxAge=3600)
public class ExamService {
	@Autowired
	ExamRepository examRepository;
	@Autowired
	QuestionRepository baseRepo;
	@Autowired
	MultipleChoiceQuestionRepository multiRepo;
	@Autowired
	TrueFalseQuestionRepository truefRepo;
	@Autowired
	EssayQuestionRepository essayRepo;
	@Autowired
	FillInBlanksQuestionRepository fillbRepo;

	// find//////////////////////////////////////////////////////////////////////
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
	// find question by id
	@GetMapping("api/question/{questionId}")
	public Question findQuestionById
				(@PathVariable("questionId") int questionId) {
		Optional<Question> optionalQuestion = 
						baseRepo.findById(questionId);
		if (optionalQuestion.isPresent()) {
			return optionalQuestion.get();
		}
		return null;
	}
	// find all questions for an exam
	@GetMapping("/api/exam/{examId}/question")
	public List<Question> findAllQuestionsForExam(@PathVariable("examId") int examId) {
		Optional<Exam> optionalExam = examRepository.findById(examId);
		if(optionalExam.isPresent()) {
			Exam exam = optionalExam.get();
			List<Question> questions = exam.getQuestions();
			int count = questions.size();
			return questions;
		}
		return null;
	}
	// create////////////////////////////////////////////////////////////////
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
	// update///////////////////////////////////////////////////////////////////
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
