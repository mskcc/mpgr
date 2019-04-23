package org.mskcc.mpgr.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="samplecmoinforecords")
@Getter @Setter @ToString @NoArgsConstructor
public class SampleCMOInfoRecord {

    private long recordId;

    @Id
    private String sampleId;
    private String userSampleId;
    private String correctedCMOID;  // Very important for the Mapping file
    @Column(name="othersampleid")
    private String cmoSampleId;
    private String altId;

    private String cmoPatientId;

    private String requestId;  // blank for 1/3 of entries, could populate via Sapio LIMS API

    private String tumorType;
    private String species;
    private String preservation;
    private String tumorOrNormal;
}
