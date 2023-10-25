package com.kuui.kuzalr.application.students.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "\"STUDENTS\"")
@Getter @Setter
public class Student {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "student_id")
    Long id;

    @Column(name = "name")
    String name;

    @Column(name = "christian_name")
    String christian_name;

    @Column(name = "cnt")
    int cnt;

    @Column(name = "img_url")
    String imgUrl;
}
