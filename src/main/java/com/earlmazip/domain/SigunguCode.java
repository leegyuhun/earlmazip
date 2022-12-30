package com.earlmazip.domain;

import lombok.Getter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "sigungu_code")
@Getter
public class SigunguCode {
    @Id
    @GeneratedValue
    private int seq;

    private String areaCode;

    private String sigunguCode;

    private String sigunguName;
}
