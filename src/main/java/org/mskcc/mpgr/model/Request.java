package org.mskcc.mpgr.model;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Request {

    @Id
    public String requestId;
    String projectid;

    public String cmoProjectId;

    String requestName;

    String laboratoryHead;
    public String labHeadEmail;

    public String investigator;
    public String investigatorEmail;

    public String dataAnalyst;
    public String dataAnalystEmail;

    public String projectManager;
    @Transient
    public String projectManagerEmail; // from lookup in limsUser table

    boolean bicAutoRunnable;
    Boolean manualDemux; // sometimes null as of Jan. 2019
    String readMe;

    public String piFirstName;
    public String piLastName;
    public String piEmail;

    long datecreated;

    @OneToMany(fetch = FetchType.EAGER,mappedBy = "request")
    private List<Sample> samples = new ArrayList<>();

    public Request() {
    }

    @Override
    public String toString() {
        return "Request{" +
                "requestId='" + requestId + '\'' +
                ", projectid='" + projectid + '\'' +
                ", cmoProjectId='" + cmoProjectId + '\'' +
                ", requestName='" + requestName + '\'' +
                ", laboratoryHead='" + laboratoryHead + '\'' +
                ", labHeadEmail='" + labHeadEmail + '\'' +
                ", investigator='" + investigator + '\'' +
                ", investigatorEmail='" + investigatorEmail + '\'' +
                ", dataAnalyst='" + dataAnalyst + '\'' +
                ", dataAnalystEmail='" + dataAnalystEmail + '\'' +
                ", projectManager='" + projectManager + '\'' +
                ", bicAutoRunnable=" + bicAutoRunnable +
                ", manualDemux=" + manualDemux +
                ", readMe='" + readMe + '\'' +
                ", piFirstName='" + piFirstName + '\'' +
                ", piLastName='" + piLastName + '\'' +
                ", piEmail='" + piEmail + '\'' +
                ", datecreated=" + datecreated +
                ", samples=" + samples +
                '}';
    }
}