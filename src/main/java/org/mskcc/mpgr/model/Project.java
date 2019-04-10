package org.mskcc.mpgr.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
public class Project {

    @Id
    String projectid;

    String cmoProjectId;
    String cmoFinalProjectTitle;
    String cmoProjectBrief;

    String tumorType;

    long datecreated;

    public Project() {
    }
}