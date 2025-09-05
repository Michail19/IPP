package practice1;

import com.google.api.services.gmail.Gmail;
import com.google.api.services.gmail.model.Draft;
import com.google.api.services.gmail.model.Message;
import jakarta.mail.MessagingException;
import jakarta.mail.Session;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import jakarta.mail.internet.MimeUtility;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Base64;
import java.util.Properties;

public class GmailDraft {
    public static MimeMessage createEmail(String to, String from, String subject, String bodyText) throws MessagingException, UnsupportedEncodingException {
        Properties props = new Properties();
        Session session = Session.getDefaultInstance(props, null);

        MimeMessage email = new MimeMessage(session);
        email.setFrom(new InternetAddress(from));
        email.addRecipient(jakarta.mail.Message.RecipientType.TO, new InternetAddress(to));
        email.setSubject(MimeUtility.encodeText(subject, "UTF-8", null));
        email.setText(bodyText, "UTF-8");

        return email;
    }

    public static MimeMessage updateEmail(MimeMessage email, String bodyText) throws MessagingException {
        email.setText(bodyText, "UTF-8");
        return email;
    }

    public static MimeMessage updateEmailWithAdd(MimeMessage email, String bodyText) throws MessagingException, IOException {
        Object content = email.getContent();
        if (content instanceof String) {
            String existingText = (String) content;
            email.setText(existingText + "\n" + bodyText, "UTF-8");
        } else {
            email.setText(bodyText, "UTF-8");
        }
        return email;
    }

    public static Message createMessageWithEmail(MimeMessage email) throws MessagingException, IOException {
        ByteArrayOutputStream buffer = new ByteArrayOutputStream();
        email.writeTo(buffer);
        byte[] bytes = buffer.toByteArray();
        String encodedEmail = Base64.getUrlEncoder().withoutPadding().encodeToString(bytes);

        Message message = new Message();
        message.setRaw(encodedEmail);
        return message;
    }

    public static Draft createDraft(Gmail service, String userId, MimeMessage email) throws MessagingException, IOException {
        Message message = createMessageWithEmail(email);
        Draft draft = new Draft();
        draft.setMessage(message);

        return service.users().drafts().create(userId, draft).execute();
    }

    public static Draft updateDraft(Gmail service, String userId, String draftId, MimeMessage email) throws MessagingException, IOException {
        Draft existingDraft = service.users().drafts().get(userId, draftId).execute();

        Message message = createMessageWithEmail(email);
        existingDraft.setMessage(message);

        return service.users().drafts().update(userId, draftId, existingDraft).execute();
    }

    public static void deleteDraft(Gmail service, String userId, String draftId) throws IOException {
        service.users().drafts().delete(userId, draftId).execute();
    }
}
