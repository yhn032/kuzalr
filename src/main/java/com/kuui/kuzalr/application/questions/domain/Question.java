package com.kuui.kuzalr.application.questions.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "\"QUESTIONS\"")
@Getter @Setter
public class Question {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "question_id")
    Long id;

    @Column(name = "question")
    String question;

    @Column(name = "answer")
    String answer;

    @Column(name = "use_yn")
    String useYn;

    @Column(name = "check_yn")
    String checkYn;

    @Column(name = "type")
    String type;
}
