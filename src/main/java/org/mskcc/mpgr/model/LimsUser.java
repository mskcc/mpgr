package org.mskcc.mpgr.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
@Getter @Setter
public class LimsUser {
    @Id
    String userName;

    String firstName;
    String lastName;
    String emailAddress;
}