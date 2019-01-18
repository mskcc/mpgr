package org.mskcc.mpgr.repository;

import org.mskcc.mpgr.model.SampleQCPM;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Set;

public interface SampleQCPMTeamRepository extends CrudRepository<SampleQCPM, Long> {
    List<SampleQCPM> findByCmoSampleId(String cmoSampleId);

    List<SampleQCPM> findBycmoSampleIdAndSequencerRunFolder(String cmoSampleId, String sequencerRunFolder);

    Set<SampleQCPM> findByStatusNot(String status);
}