package com.earlmazip.domain;

import lombok.Getter;

import javax.persistence.*;

@Entity
@Table(name = "code_info")
@Getter
public class CodeInfo {
    @Id
    @GeneratedValue
    private int seq;

    private String codeNmbr;

    private String codeName;

    private String codeNote;
}
