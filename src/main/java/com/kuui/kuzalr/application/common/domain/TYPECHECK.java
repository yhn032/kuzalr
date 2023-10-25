package com.kuui.kuzalr.application.common.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "\"TYPECHECK\"")
@Getter @Setter
public class TYPECHECK {

    @Id
    @Column(name = "type")
    String type;

    @Column(name = "q_num")
    String qNum;
}
