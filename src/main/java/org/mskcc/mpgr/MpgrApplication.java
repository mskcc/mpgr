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

import java.io.IOException;
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

	public static Boolean runAsExome = true; // currently unused


	public static void main(String[] args) {
		SpringApplication.run(MpgrApplication.class, args);
	}

	@Override
	public void run(String... args) throws IOException {
		//"08390_C"; // "07951_AC"; //""08236_E"; // "06208";
        String jiraIssueKey = "RSL-200";
        JiraConnect jira = new JiraConnect();
        String requestName = jira.getRequestName(jiraIssueKey);

		Optional<Project> project = projectRepository.findById(requestName.substring(0,5));
		Request request = requestRepository.findById(requestName).get();

		List<SampleQC> samplesPassed = sampleQCRepository.findByRequestAndStatus(requestName, "Passed");
		System.out.println("\nSamples that passed IGO QC: " + samplesPassed.size() + " -> " + Arrays.toString(samplesPassed.toArray()));

		// verify samples are also passed in PM QC table
		Set<SampleQCPM> notPassed = sampleQCPMTeamRepository.findByStatusNot("Passed"); // 176 rows as of Jan. 2019
		for (int i=samplesPassed.size()-1; i >= 0; i--) {
			SampleQC s = samplesPassed.get(i);
			if (notPassed.contains(new SampleQCPM(s.getCmoSampleId(), s.getSequencerRunFolder()))) {
				System.out.println("Removing sample failed by the PM Team." + s);
				samplesPassed.remove(i);
			}
		}

		// look up sample cmo info record & sample table record
		List<SampleMPGR> samples = new ArrayList<>();
		for (SampleQC sampleQC : samplesPassed) {
			System.out.println("Querying CMO Info table for sample: " + sampleQC);
			String cmoId = sampleQC.getCmoSampleId();
			Optional<Sample> sample =
					sampleRepository.findFirstByRequestIdAndCmoSampleIdOrderByDateCreatedAsc(sampleQC.getRequest(), cmoId);
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

		String pm = request.getProjectManager().trim();
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
				request.setProjectManagerEmail(user.get().getEmailAddress());
			} else {
				System.err.println(String.format("LIMS user not found for <%s %s>.", lastName, firstName));
			}
		}

		String updatedRF = RequestFile.toString(project.get(), request, samples);
		System.out.println("\n\n" + updatedRF);

		String rf200 = jira.getRequestFile(jiraIssueKey);
		System.out.println(rf200);
		String diff = RequestFileDiff.diff(rf200, updatedRF);
		System.out.println("THE DIFF:" + diff);

		// TODO find path to each sample in /ifs/archive
		// if no normal use pooled normal
	}

	public ProjectRepository getProjectRepository() {
		return projectRepository;
	}

	public void setProjectRepository(ProjectRepository projectRepository) {
		this.projectRepository = projectRepository;
	}

	public RequestRepository getRequestRepository() {
		return requestRepository;
	}

	public void setRequestRepository(RequestRepository requestRepository) {
		this.requestRepository = requestRepository;
	}

	public SampleRepository getSampleRepository() {
		return sampleRepository;
	}

	public void setSampleRepository(SampleRepository sampleRepository) {
		this.sampleRepository = sampleRepository;
	}

	public SampleQCRepository getSampleQCRepository() {
		return sampleQCRepository;
	}

	public void setSampleQCRepository(SampleQCRepository sampleQCRepository) {
		this.sampleQCRepository = sampleQCRepository;
	}

	public SampleCMOInfoRepository getSampleCMOInfoRepository() {
		return sampleCMOInfoRepository;
	}

	public void setSampleCMOInfoRepository(SampleCMOInfoRepository sampleCMOInfoRepository) {
		this.sampleCMOInfoRepository = sampleCMOInfoRepository;
	}

	public SampleQCPMTeamRepository getSampleQCPMTeamRepository() {
		return sampleQCPMTeamRepository;
	}

	public void setSampleQCPMTeamRepository(SampleQCPMTeamRepository sampleQCPMTeamRepository) {
		this.sampleQCPMTeamRepository = sampleQCPMTeamRepository;
	}

	public LimsUserRepository getLimsUserRepository() {
		return limsUserRepository;
	}

	public void setLimsUserRepository(LimsUserRepository limsUserRepository) {
		this.limsUserRepository = limsUserRepository;
	}

	public static Boolean getRunAsExome() {
		return runAsExome;
	}

	public static void setRunAsExome(Boolean runAsExome) {
		MpgrApplication.runAsExome = runAsExome;
	}
}