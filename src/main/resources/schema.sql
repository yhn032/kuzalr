DROP TABLE IF EXISTS students;
DROP TABLE IF EXISTS questions;
DROP TABLE IF EXISTS type_check;

CREATE TABLE STUDENTS
(
    student_id      BIGINT      NOT NULL AUTO_INCREMENT,
    name    VARCHAR(20) NOT NULL,
    christian_name    VARCHAR(20) NOT NULL,
    cnt     INT         NOT NULL,
    img_url varchar(50),
    PRIMARY KEY (student_id)
);

CREATE TABLE QUESTIONS
(
    question_id          BIGINT       NOT NULL AUTO_INCREMENT,
    question    VARCHAR(100) NOT NULL,
    answer      VARCHAR(100)  NOT NULL,
    use_yn      CHAR(10)     NOT NULL,
    check_yn    CHAR(10)     NOT NULL,
    type        char(10)     NOT NULL,
    PRIMARY KEY (question_id)
);

CREATE TABLE TYPE_CHECK
(
    type char(10) NOT NULL,
    q_num varchar(50) NOT NULL ,
    primary key (type)
);