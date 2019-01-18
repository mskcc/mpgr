package org.mskcc.mpgr.repository;

import org.mskcc.mpgr.model.LimsUser;
import org.mskcc.mpgr.model.Sample;
import org.mskcc.mpgr.model.SampleCMOInfoRecord;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface LimsUserRepository extends CrudRepository<LimsUser, String> {
    Optional<LimsUser> findByFirstNameAndLastName(String firstName, String lastName);
}