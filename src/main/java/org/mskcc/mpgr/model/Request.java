package org.mskcc.mpgr.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@ToString @Getter @Setter
public class Request {

    @Id
    String requestId;
    String requestName;

    String projectid;

    String cmoProjectId;

    String laboratoryHead;
    String labHeadEmail;

    String investigator;
    String investigatorEmail;

    String dataAnalyst;
    String dataAnalystEmail;

    String projectManager;
    @Transient
    String projectManagerEmail; // from lookup in limsUser table

    boolean bicAutoRunnable;
    Boolean manualDemux; // sometimes null as of Jan. 2019
    String readMe;

    String piFirstName;
    String piLastName;
    String piEmail;

    long datecreated;

    @OneToMany(fetch = FetchType.LAZY,mappedBy = "request")
    private List<Sample> samples = new ArrayList<>();

    public Request() {
    }

    public boolean isImpact() {
        if (requestName.matches("(.*)PACT(.*)"))
            return true;
        return false;
    }

    public boolean isExome() {
        if (requestName.matches("(.*)WES(.*)"))
            return true;
        return false;
    }
}