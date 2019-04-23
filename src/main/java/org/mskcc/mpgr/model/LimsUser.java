package org.mskcc.mpgr.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
@Getter @Setter
public class LimsUser {
    @Id
    private String userName;

    private String firstName;
    private String lastName;
    private String emailAddress;
}