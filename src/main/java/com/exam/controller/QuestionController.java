package com.exam.controller;


import com.exam.model.exam.Question;
import com.exam.model.exam.Quiz;
import com.exam.service.QuestionService;
import com.exam.service.QuizService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@CrossOrigin("*")
@RequestMapping("/question")
public class QuestionController {

    @Autowired
    private QuestionService questionService;

    @Autowired
    private QuizService quizService;

    @PostMapping("/")
    public ResponseEntity<Question> add(@RequestBody Question question)
    {
        return ResponseEntity.ok(this.questionService.addQuestion(question));
    }

    //update question
    @PutMapping("/")
    public ResponseEntity<Question> update(@RequestBody Question question)
    {
        return ResponseEntity.ok(this.questionService.updateQuestion(question));
    }

    //no of. question  pass of any quiz randomly
    @GetMapping("/quiz/{qid}")
    public ResponseEntity<?> getQuestionOfQuiz(@PathVariable("qid")Long qid)
    {
//        Quiz quiz =new Quiz();
//        quiz.setQid(qid);
//        Set<Question> questionsOfQuiz = this.questionService.getQuestionsOfQuiz(quiz);
//        return ResponseEntity.ok(questionsOfQuiz);
        Quiz quiz = this.quizService.getQuiz(qid);
         Set<Question> questions = quiz.getQuestions();
         List <Question> list =new ArrayList(questions);
         if (list.size()>Integer.parseInt(quiz.getNumberOfQuestions()))
         {
             list=list.subList(0,Integer.parseInt(quiz.getNumberOfQuestions()+1));
         }

         list.forEach((q)->{
             q.setAnswer("");
         });

        Collections.shuffle(list);
         return ResponseEntity.ok(list);
    }
    //get all question of any quiz
    @GetMapping("/quiz/all/{qid}")
    public ResponseEntity<?> getQuestionOfQuizAdmin(@PathVariable("qid")Long qid) {
        Quiz quiz =new Quiz();
        quiz.setQid(qid);
        Set<Question> questionsOfQuiz = this.questionService.getQuestionsOfQuiz(quiz);
        return ResponseEntity.ok(questionsOfQuiz);
    }

    // get single Question
    @GetMapping("/{quesId}")
    public Question getQuestionById (@PathVariable("quesId")Long quesId)
    {
        return  this.questionService.getQuestion(quesId);
    }

    // delete Question
    @DeleteMapping("/{quesId}")
    public void delete (@PathVariable("quesId") Long quesId)
    {
        this.questionService.deleteQuestion(quesId);
    }

    //eval quiz
    @PostMapping("/eval-quiz")
    public ResponseEntity<?> evalQuiz(@RequestBody List<Question> questions){
        //       Local variable
        double marksGot = 0;
        int correctAnswers = 0;
        int attempted = 0;
        double marksSingle = 0;

        for (Question q : questions) {
//            System.out.println(q.getGivenAnswer());
            Question questionByID = this.questionService.get(q.getQuesId());
           System.out.println("========================> " +questionByID.getAnswer());
            System.out.println("========================> " +q.getGivenAnswer());
            if (questionByID.getAnswer().equals(q.getGivenAnswer())) {
                //answer is correct
                correctAnswers++;
                marksSingle = Double.parseDouble(questions.get(0).getQuiz().getMaxMarks()) / questions.size();
                marksGot += marksSingle;
            }
            if (q.getGivenAnswer() != null ) {
                attempted++;
            }
        }
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("marksGot",marksGot);
        map.put("correctAnswers",correctAnswers);
        map.put("attempted",attempted);
        return ResponseEntity.ok(map);
    }

}
