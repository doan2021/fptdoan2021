package com.doanfpt.application.responsitories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.doanfpt.application.entities.Answer;

@Repository
public interface AnswerRespository extends JpaRepository<Answer, Long> {
}