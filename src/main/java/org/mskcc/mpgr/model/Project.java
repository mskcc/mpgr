package org.mskcc.mpgr.model;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Project {

    @Id
    public String projectid;

    public String cmoProjectId;
    public String cmoFinalProjectTitle;
    public String cmoProjectBrief;

    public String tumorType;

    long datecreated;

    public Project() {
    }


}