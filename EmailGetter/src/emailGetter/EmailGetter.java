package emailGetter;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import javax.mail.BodyPart;
import javax.mail.Folder;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.Session;
import javax.mail.Store;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeUtility;
import org.apache.commons.io.IOUtils;
import com.sun.mail.util.BASE64DecoderStream;

public class EmailGetter {

  public static List<Email> getEmails(String host, int port, String storeType, String user,
    String password) throws Exception {
    List<Email> emails = new ArrayList();
    try {

      Properties properties = new Properties();

      properties.put("mail.pop3.host", host);
      properties.put("mail.pop3.port", port);
      properties.put("mail.pop3.starttls.enable", "true");
      Session emailSession = Session.getDefaultInstance(properties);

      Store store = emailSession.getStore("pop3s");

      store.connect(host, user, password);

      Folder emailFolder = store.getFolder("INBOX");
      emailFolder.open(Folder.READ_ONLY);

      Message[] messages = emailFolder.getMessages();
      System.out.println("messages.length---" + messages.length);

      for (Message message : messages) {
        List<EmailAttachment> attachments = new ArrayList();

        Email email = new Email();

        email.setFrom(((InternetAddress) message.getFrom()[0]).getAddress());
        email.setSubject(message.getSubject());

        Multipart multiPart = (Multipart) message.getContent();
        for (int n = 0; n < multiPart.getCount(); n++) {
          BodyPart bodyPart = multiPart.getBodyPart(n);
          if ("attachment".equals(bodyPart.getDisposition())) {
            BASE64DecoderStream base64DecoderStream = (BASE64DecoderStream) bodyPart.getContent();
            byte[] byteArray = IOUtils.toByteArray(base64DecoderStream);
            InputStream in = new ByteArrayInputStream(byteArray);

            EmailAttachment attachment = new EmailAttachment();
            attachment.setFilename(MimeUtility.decodeText(bodyPart.getFileName()));
            attachment.setIn(in);
            attachments.add(attachment);

          }
        }
        if (!attachments.isEmpty()) {
          email.setEmailAttachment(attachments);
        }
        emails.add(email);
      }

      emailFolder.close(false);
      store.close();

    } catch (MessagingException e) {
      throw new MessagingException();
    } catch (Exception e) {
      throw new Exception();
    }

    return emails;
  }

  public static void main(String[] args) throws Exception {

    String host = "pop.gmail.com";
    int port = 110;
    String mailStoreType = "pop3";
    String username = "";
    String password = "";

    getEmails(host, port, mailStoreType, username, password);

  }

}
