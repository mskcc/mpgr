package org.mskcc.mpgr.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter @Setter
public class Sample {
    @Id
    long recordId;

    String requestId;

    @Column(name="othersampleid")
    String cmoSampleId;
    String sampleId; // aka igo Sample Id
    String altId;

    String exemplarSampleStatus; // 175 possible values, "Failed - Completed" samples are skipped

    String patientId;
    String createdBy;

    String tumorType;
    String species;

    long dateCreated;

    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="requestid", insertable=false, updatable = false)
    Request request;

    @Override
    public String toString() {
        return "Sample{" +
                "recordId=" + recordId +
                ", requestId='" + requestId + '\'' +
                ", cmoSampleId='" + cmoSampleId + '\'' +
                ", sampleId='" + sampleId + '\'' +
                ", altId='" + altId + '\'' +
                ", exemplarSampleStatus='" + exemplarSampleStatus + '\'' +
                ", patientId='" + patientId + '\'' +
                ", createdBy='" + createdBy + '\'' +
                ", tumorType='" + tumorType + '\'' +
                ", species='" + species + '\'' +
                ", dateCreated=" + dateCreated +
                '}';
    }
}