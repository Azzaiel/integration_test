package net.virtela.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import net.virtela.utils.CommonHelper;

public class Mail {
	private String sender;
	private String recipient;
	private String body;
	private String subject;
	private String attachment;
	private String cc;

	public static final String SENDER_DEFAULT = "auto-tester-noreply@virtela.net";

	public Mail() {
		super();
	}

	public Mail(String recipient, String body, String subject) {
		this.recipient = recipient;
		this.body = body;
		this.subject = subject;
	}

	public Mail(String recipient, String body, String subject, String cc) {
		super();
		this.recipient = recipient;
		this.body = body;
		this.subject = subject;
		this.cc = cc;
	}

	public Mail(String recipient, String sender, String body, String subject, String cc) {
		super();
		this.sender = sender;
		this.recipient = recipient;
		this.body = body;
		this.subject = subject;
		this.cc = cc;
	}

	public Mail(String recipient, String sender, String body, String subject, String cc, String attachment) {
		super();
		this.sender = sender;
		this.recipient = recipient;
		this.body = body;
		this.subject = subject;
		this.attachment = attachment;
		this.cc = cc;
	}

	public String getSender() {
		if (CommonHelper.hasValidValue(this.sender)) {
			return sender;
		}
		return SENDER_DEFAULT;

	}

	public void setSender(String sender) {
		this.sender = sender;
	}

	public String getRecipient() {
		if (this.recipient != null && this.recipient.contains(";")) {
			String[] toArr = this.recipient.split(";");
			int index = 0;

			final StringBuffer mTo = new StringBuffer();

			for (String temp : toArr) {
				mTo.append(temp.trim());
				if ((index + 1) < toArr.length) {
					mTo.append(",");
				}
				index += 1;
			}
			return mTo.toString();
		}
		return recipient;
	}

	public List<String> getAttchments() {
		final List<String> attachments = new ArrayList<String>();

		if (this.attachment == null) {
			return attachments;
		}

		if (this.attachment.contains(",") || this.attachment.contains(";")) {
			this.attachment.replaceAll(",", ";");
			String[] attachmentArr = this.attachment.split(";");
			attachments.addAll(Arrays.asList(attachmentArr));
		} else {
			attachments.add(this.attachment);
		}
		return attachments;
	}

	public void setRecipient(String recipient) {
		this.recipient = recipient;
	}

	public String getBody() {
		return body;
	}

	public void setBody(String body) {
		this.body = body;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public boolean isValidForSendMail() {
		if (this.recipient != null) {
			return true;
		}
		return false;
	}

	public void setAttachment(String attachment) {
		this.attachment = attachment;
	}

	public String getCc() {
		if (this.cc == null) {
			this.cc = "";
		}
		if (this.cc.contains(";")) {
			String[] toArr = this.cc.split(";");
			int index = 0;

			final StringBuffer mCC = new StringBuffer();

			for (String temp : toArr) {
				mCC.append(temp.trim());
				if ((index + 1) < toArr.length) {
					mCC.append(",");
				}
				index += 1;
			}
			return mCC.toString();
		}
		return this.cc;
	}

	public void setCc(String cc) {
		this.cc = cc;
	}


}
