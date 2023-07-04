package com.earlmazip.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name="ip_block")
@Getter
@Setter
public class IpBlock {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int seq;
    private String ipAddress;
    private int blockCnt;
    private String note;

    private Date udt;
    private Date rdt;
}
