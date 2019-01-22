package org.mskcc.mpgr.repository;

import org.mskcc.mpgr.model.Sample;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface SampleRepository extends CrudRepository<Sample, Long> {
    Optional<Sample> findFirstByRequestIdAndCmoSampleIdOrderByDateCreatedAsc(String requestId, String cmoSampleId);
}
