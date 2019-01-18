package org.mskcc.mpgr.model;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class LimsUser {
    @Id
    public String userName;

    public String firstName;
    public String lastName;
    public String emailAddress;
}