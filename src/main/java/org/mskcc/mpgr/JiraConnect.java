package org.mskcc.mpgr;

import com.atlassian.jira.rest.client.api.IssueRestClient;
import com.atlassian.jira.rest.client.api.JiraRestClient;
import com.atlassian.jira.rest.client.api.domain.Attachment;
import com.atlassian.jira.rest.client.api.domain.Issue;
import com.atlassian.jira.rest.client.internal.async.AsynchronousJiraRestClientFactory;
import okhttp3.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.net.URI;
import java.util.ArrayList;
import java.util.Arrays;

@Component
public class JiraConnect {
    private static final String JIRA_URL = "http://plvpipetrack1.mskcc.org:8090";

    private static JiraRestClient jiraRestClient;

    private static String jiraUser;
    private static String jiraPass;

    @PostConstruct
    public void postConstruct() {
        try {
            URI jiraServerUri = new URI(JIRA_URL);
            jiraRestClient = new AsynchronousJiraRestClientFactory().createWithBasicHttpAuthentication(jiraServerUri, jiraUser, jiraPass);
            this.setJiraPass(jiraPass);
            this.setJiraUser(jiraUser);
            System.out.println("JIRA Rest client created.");
        } catch (Exception e) {
            System.err.println(e);
            System.exit(1);
        }
    }

    public String getRequestName(String jiraIssueKey) {
        IssueRestClient issueClient = jiraRestClient.getIssueClient();
        Issue issue = (Issue) issueClient.getIssue(jiraIssueKey).claim();
        String summary = issue.getSummary();
        return summary;
    }


    public static class JiraMPGR {
        public String requestFile;
        public String mappingFile;
    }

    /*
     * The JIRA REST API does not download attachments,
     * just using okHttp jar for that once a file URL is known.
     */
    public JiraMPGR getRequestAndMappingFile(String jiraIssueKey) throws IOException {
        IssueRestClient issueClient = jiraRestClient.getIssueClient();
        ArrayList<String> files = getJiraAttachmentsURLList(issueClient, jiraIssueKey);
        System.out.println("For Issue:" + jiraIssueKey  + " found files:" + Arrays.toString(files.toArray()));

        JiraMPGR jiraFiles = new JiraMPGR();
        for (String fileName : files) {
            if (jiraUser == null)
                System.exit(0);
            // TODO some issues have multiple
            if (fileName.endsWith("_request.txt")) {
                String requestFileURL = fileName;
                jiraFiles.requestFile = getJiraFile(requestFileURL);
            }
            if (fileName.endsWith("_mapping.txt")) {
                jiraFiles.mappingFile = getJiraFile(fileName);
            }
        }

        return jiraFiles;
    }

    protected String getJiraFile(String requestFileURL) throws IOException {
        System.out.println("Downloading file: " + requestFileURL);
        OkHttpClient client = new OkHttpClient.Builder()
                .addInterceptor(new BasicAuthInterceptor(jiraUser, jiraPass))
                .build();
        Request request = new Request.Builder().url(requestFileURL).build();
        Response response = client.newCall(request).execute();
        if (!response.isSuccessful()) {
            throw new IOException("Failed to download file: " + response);
        }
        return new String(response.body().bytes());
    }

    protected ArrayList<String> getJiraAttachmentsURLList(IssueRestClient issueRestClient, String jiraIssueKey) {
        ArrayList<String> jiraAttachmentURLList = new ArrayList<String>();
        Issue issue = (Issue) issueRestClient.getIssue(jiraIssueKey).claim();
        Iterable<Attachment> issueAttachments = issue.getAttachments();
        issueAttachments.forEach(attachment -> {
            if (null != attachment.getFilename()) {
                jiraAttachmentURLList.add((attachment.getContentUri().toString()));
            }
        });
        return jiraAttachmentURLList;
    }

    @Value("${jira.username}")
    public void setJiraUser(String jiraUser) {
        this.jiraUser = jiraUser;
    }

    @Value("${jira.password}")
    public void setJiraPass(String jiraPass) {
        this.jiraPass = jiraPass;
    }

    public class BasicAuthInterceptor implements Interceptor {

        private String credentials;

        public BasicAuthInterceptor(String user, String password) {
            this.credentials = Credentials.basic(user, password);
        }

        @Override
        public Response intercept(Chain chain) throws IOException {
            Request request = chain.request();
            Request authenticatedRequest = request.newBuilder()
                    .header("Authorization", credentials).build();
            return chain.proceed(authenticatedRequest);
        }
    }
}