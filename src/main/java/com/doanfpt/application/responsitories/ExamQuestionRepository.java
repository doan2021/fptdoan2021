package com.doanfpt.application.responsitories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.doanfpt.application.entities.ExamQuestions;

public interface ExamQuestionRepository extends JpaRepository<ExamQuestions, Long> {

}