package pl.jj.app.model;

import pl.jj.app.util.Const;
import pl.jj.app.util.IsNotAddressEmail;

import java.util.ArrayList;
import java.util.List;

public class Email {

    private String tittle;

    private List<EmailAddress> sendTo;

    private List<EmailAddress> copyTo;

    private List<EmailElement> emailElements;

    private Email(EmailBuilder emailBuilder){
        this.tittle = emailBuilder.tittle;
        this.sendTo = emailBuilder.sendTo;
        this.copyTo = emailBuilder.copyTo;
        this.emailElements = emailBuilder.emailElements;

    }

    public static class EmailBuilder {

        private String tittle;

        private List<EmailAddress> sendTo = new ArrayList<>();

        private List<EmailAddress> copyTo = new ArrayList<>();

        private List<EmailElement> emailElements = new ArrayList<>();


        public EmailBuilder setTittle(String tittle){
            this.tittle = tittle;
            return this;
        }

        public EmailBuilder addRecipient(String email){
            this.sendTo.add(new EmailAddress(email));
            return this;
        }

        public EmailBuilder addCopyRecipient(String email){
            this.copyTo.add(new EmailAddress(email));
            return this;
        }

        public EmailBuilder addParagraph(String text){
            this.emailElements.add(new EmailClosedTag(CloseTag.PARAGRAPH, text));
            return this;
        }

        public EmailBuilder addHeader(String text){
            this.emailElements.add(new EmailClosedTag(CloseTag.HEADER2, text));
            return this;
        }

        public EmailBuilder addBreakLine(){
            this.emailElements.add(new EmailSingleTag(SingleTag.BR));
            return this;
        }

        public EmailBuilder addLink(String link, String text){
            EmailClosedTag emailClosedTag = new EmailClosedTag(CloseTag.LINK, text);
            emailClosedTag.setAttributes("href=\"" + link + "\"");
            this.emailElements.add(emailClosedTag);
            return this;
        }

        public Email build(){
            return new Email(this);
        }


    }

    private static class EmailSingleTag extends EmailElement{

        private SingleTag singleTag;

        public EmailSingleTag(SingleTag singleTag) {
            this.singleTag = singleTag;
        }

        @Override
        public String getElementValue() {
            return singleTag.getTag();
        }
    }

    private enum SingleTag{

        BR("<br>");

        private String tag;

        SingleTag(String tag) {
            this.tag = tag;
        }

        public String getTag() {
            return tag;
        }

    }

    private static class EmailClosedTag extends EmailElement{

        private CloseTag closeTag;
        private String text;
        private String attributes;

        public EmailClosedTag(CloseTag closeTag, String text) {
            this.closeTag = closeTag;
            this.text = text;
        }

        public void setAttributes(String attributes) {
            this.attributes = attributes;
        }

        @Override
        public String getElementValue() {
            String startTag = closeTag.getStartTag();
            if(attributes != null && (!attributes.isEmpty())){
                String start = startTag.substring(0, startTag.length() - 1);
                String end = startTag.substring(startTag.length() - 1);
                startTag = start + Const.SPACE + attributes.trim() + end;
            }
            return startTag + text + closeTag.getEndTag();
        }
    }

    private enum CloseTag{

        HEADER2("<h2>","</h2>"),
        PARAGRAPH("<p>", "</p>"),
        LINK("<a>", "</a>");

        private String startTag;
        private String endTag;

        CloseTag(String startTag, String endTag) {
            this.startTag = startTag;
            this.endTag = endTag;
        }

        public String getStartTag() {
            return startTag;
        }

        public String getEndTag() {
            return endTag;
        }

    }

    private static abstract class EmailElement{
        public abstract String getElementValue();
    }

    public static final class EmailAddress {

        private String emailAddress;

        public EmailAddress(String emailAddress) {
            if(Const.VALID_EMAIL_ADDRESS_REGEX.matcher(emailAddress).matches()){
                this.emailAddress = emailAddress;
            } else {
                throw new IsNotAddressEmail();
            }

        }

        public String getEmailAddress() {
            return emailAddress;
        }

    }

    public String getTittle() {
        return tittle;
    }

    public List<EmailAddress> getSendTo() {
        return sendTo;
    }

    public List<EmailAddress> getCopyTo() {
        return copyTo;
    }

    public String getEmailContent(){
        StringBuilder sb = new StringBuilder();
        for(EmailElement element: emailElements){
            sb.append(element.getElementValue());
        }
        return sb.toString();
    }

}
