package emailGetter;

import java.util.List;

public class Email {

  private List<EmailAttachment> attachment;
  private String subject;
  private String from;

  public List<EmailAttachment> getEmailAttachment() {
    return attachment;
  }

  public void setEmailAttachment(List<EmailAttachment> attachment) {
    this.attachment = attachment;
  }

  public String getSubject() {
    return subject;
  }

  public void setSubject(String subject) {
    this.subject = subject;
  }

  public String getFrom() {
    return from;
  }

  public void setFrom(String from) {
    this.from = from;
  }

}
