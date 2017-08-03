package emailGetter;

import java.io.InputStream;

public class EmailAttachment {

  private InputStream in;
  private String filename;

  public InputStream getIn() {
    return in;
  }

  public void setIn(InputStream in) {
    this.in = in;
  }

  public String getFilename() {
    return filename;
  }

  public void setFilename(String filename) {
    this.filename = filename;
  }

}
