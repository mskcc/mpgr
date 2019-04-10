package org.mskcc.mpgr.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="samplecmoinforecords")
@Getter @Setter @ToString
public class SampleCMOInfoRecord {

    long recordId;

    @Id
    String sampleId;
    String userSampleId;
    String correctedCMOID;  // Very important for the Mapping file
    @Column(name="othersampleid")
    String cmoSampleId;
    String altId;

    String cmoPatientId;

    String requestId;  // blank for 1/3 of entries

    String tumorType;
    String species;
    String preservation;
    String tumorOrNormal;
}
