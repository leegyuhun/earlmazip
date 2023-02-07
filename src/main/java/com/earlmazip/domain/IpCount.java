package com.earlmazip.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name="ip_count")
@Getter
@Setter
public class IpCount {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int seq;

    private String countYear;

    private String countMonth;

    private String countDay;

    private String countDate;

    private String ipAddress;

    private int cnt;

    private String countryCode;

    private String ispName;

    private String ispAddr;

    private String userName;

    private String userAddr;
}
