package org.mskcc.mpgr;

import org.mskcc.mpgr.model.*;
import org.mskcc.mpgr.model.output.MappingFile;
import org.mskcc.mpgr.model.output.RequestFile;
import org.mskcc.mpgr.model.output.SampleMPGR;
import org.mskcc.mpgr.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.*;

@SpringBootApplication
public class MpgrApplication implements CommandLineRunner {

	@Autowired
	private ProjectRepository projectRepository;

	@Autowired
	private RequestRepository requestRepository;

	@Autowired
	private SampleRepository sampleRepository;

	@Autowired
	private SampleQCRepository sampleQCRepository;

	@Autowired
	private SampleCMOInfoRepository sampleCMOInfoRepository;

	@Autowired
	private SampleQCPMTeamRepository sampleQCPMTeamRepository;

	@Autowired
	private LimsUserRepository limsUserRepository;

	public static void main(String[] args) {
		SpringApplication.run(MpgrApplication.class, args);
	}

	@Override
	public void run(String... args) {
		String requestName = "08236_E"; // "06208";
		Optional<Project> project = projectRepository.findById(requestName.substring(0,5));
		Request request = requestRepository.findById(requestName).get();

		List<SampleQC> samplesPassed = sampleQCRepository.findByRequestAndStatus(requestName, "Passed");
		System.out.println("\nSamples that passed IGO QC: " + samplesPassed.size() + " -> " + Arrays.toString(samplesPassed.toArray()));

		// verify samples are also passed in PM QC table
		Set<SampleQCPM> notPassed = sampleQCPMTeamRepository.findByStatusNot("Passed"); // 176 rows as of Jan. 2019
		for (int i=samplesPassed.size()-1; i >= 0; i--) {
			SampleQC s = samplesPassed.get(i);
			if (notPassed.contains(new SampleQCPM(s.cmoSampleId, s.sequencerRunFolder))) {
				System.out.println("Removing sample failed by the PM Team." + s);
				samplesPassed.remove(i);
			}
		}

		// look up sample cmo info record & sample table record
		List<SampleMPGR> samples = new ArrayList<>();
		for (SampleQC sampleQC : samplesPassed) {
			System.out.println("Querying CMO Info table for sample: " + sampleQC);
			String cmoId = sampleQC.cmoSampleId;
			Optional<Sample> sample = sampleRepository.findFirstByRequestIdAndCmoSampleIdOrderByDateCreatedAsc(sampleQC.request, cmoId);
			if (sample.isPresent())
				System.out.println("Found sample record." + sample.get());
			Optional<SampleCMOInfoRecord> cmoSample = sampleCMOInfoRepository.findByCmoSampleIdAndRequestId(cmoId, requestName);
			if (!cmoSample.isPresent()) {
				System.out.println("Failed to find CMO sample by CMO id & request id.");
				List<SampleCMOInfoRecord> cmoSamples = sampleCMOInfoRepository.findByCmoSampleId(cmoId);
				cmoSample = Optional.ofNullable(cmoSamples.get(0));
			}
			samples.add(new SampleMPGR(sample.get(), cmoSample.get(), sampleQC));
		}

		String s = new MappingFile(samples).toString();
		System.out.println("\n\n" + s);

		String pm = request.projectManager.trim();
		if (!"".equals(pm) && !"NO PM".equals(pm)) { // No email to lookup for "NO PM" or ""
			// yes the table has 'Bourque, Caitlin' and 'Caitlin Bourque'
			String firstName = "";
			String lastName = "";

			String[] temp = pm.replaceAll(",", "")
					.replace(".", "")
					.split("\\s+");

			if (temp.length == 1) {
				System.err.println(String.format("Not valid full name: <%s>.", pm));
			} else if (temp.length == 2) {
				firstName = temp[1].trim();
				lastName = temp[0].trim();
			} else if (temp.length == 3) {
				firstName = temp[2].trim();
				lastName = temp[0].trim();
			} else {
				System.err.println(String.format("Not valid full name: <%s>.", pm));
			}

			System.out.println("Querying LIMSUSER table for " + firstName + " " + lastName);
			Optional<LimsUser> user = limsUserRepository.findByFirstNameAndLastName(firstName.trim(), lastName.trim());
			if (user.isPresent()) {
				request.projectManagerEmail = user.get().emailAddress;
			} else {
				System.err.println(String.format("LIMS user not found for <%s %s>.", lastName, firstName));
			}
		}

		String r = RequestFile.toString(project.get(), request, samples);
		System.out.println("\n\n" + r);
		// find path to each sample in /ifs/archive
		// if no normal use pooled normal
	}
}