package practice1;

import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.extensions.java6.auth.oauth2.AuthorizationCodeInstalledApp;
import com.google.api.client.extensions.jetty.auth.oauth2.LocalServerReceiver;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.gson.GsonFactory;
import com.google.api.client.util.store.FileDataStoreFactory;
import com.google.api.services.gmail.Gmail;
import com.google.api.services.gmail.GmailScopes;
import com.google.api.services.gmail.model.*;
import jakarta.mail.MessagingException;
import jakarta.mail.Session;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;

import java.io.*;
import java.security.GeneralSecurityException;
import java.util.Base64;
import java.util.List;
import java.util.Properties;

public class Main {
    private static final String APPLICATION_NAME = "Gmail API Java 11";
    private static final JsonFactory JSON_FACTORY = GsonFactory.getDefaultInstance();
    private static final String TOKENS_DIRECTORY_PATH = "tokens";

    private static final List<String> SCOPES = List.of(GmailScopes.GMAIL_READONLY,
            GmailScopes.GMAIL_COMPOSE,
            GmailScopes.GMAIL_LABELS);
    private static final String CREDENTIALS_FILE_PATH = "/credentials.json";

    private static Credential getCredentials(final com.google.api.client.http.HttpTransport HTTP_TRANSPORT) throws IOException {
        InputStream in = Main.class.getResourceAsStream(CREDENTIALS_FILE_PATH);
        if (in == null) {
            throw new FileNotFoundException("Resource not found: " + CREDENTIALS_FILE_PATH);
        }
        GoogleAuthorizationCodeFlow flow = new GoogleAuthorizationCodeFlow.Builder(
                HTTP_TRANSPORT, JSON_FACTORY, GoogleClientSecrets.load(JSON_FACTORY, new InputStreamReader(in)), SCOPES)
                .setDataStoreFactory(new FileDataStoreFactory(new java.io.File(TOKENS_DIRECTORY_PATH)))
                .setAccessType("offline")
                .build();
        LocalServerReceiver receiver = new LocalServerReceiver.Builder().setPort(8888).build();
        return new AuthorizationCodeInstalledApp(flow, receiver).authorize("user");
    }

    public static List<Message> getMessages(Gmail service) throws IOException {
        ListMessagesResponse messagesResponse = service.users().messages().list("me").setMaxResults(10L).execute();
        return messagesResponse.getMessages();
    }

    public static void main(String... args) throws IOException, GeneralSecurityException, MessagingException {
        var HTTP_TRANSPORT = GoogleNetHttpTransport.newTrustedTransport();
        Gmail service = new Gmail.Builder(HTTP_TRANSPORT, JSON_FACTORY, getCredentials(HTTP_TRANSPORT))
                .setApplicationName(APPLICATION_NAME)
                .build();

        List<Message> messages = getMessages(service);

        if (messages == null || messages.isEmpty()) {
            System.out.println("Нет сообщений.");
        } else {
            System.out.println("Последние письма:");
            for (Message msg : messages) {
                Message fullMessage = service.users().messages().get("me", msg.getId()).execute();
                System.out.println("ID: " + fullMessage.getId() + " | Отрезок: " + fullMessage.getSnippet());
            }
        }


        MimeMessage email = GmailDraft.createEmail(
                "somebody@example.com",
                "me@example.com",
                "Тестовый черновик",
                "Привет! Это письмо сохранено как черновик."
        );
        Draft draft = GmailDraft.createDraft(service, "me", email);
        System.out.println("Черновик создан, ID: " + draft.getId());

        GmailDraft.updateEmailWithAdd(email, "Новый текст");
        draft = GmailDraft.updateDraft(service, "me", draft.getId(), email);
        System.out.println("Черновик обновлён, ID: " + draft.getId());

        Draft draftFofDelete = GmailDraft.createDraft(service, "me", email);
        System.out.println("Черновик удалён, ID: " + draftFofDelete.getId());
        GmailDraft.deleteDraft(service, "me", draftFofDelete.getId());
    }
}
