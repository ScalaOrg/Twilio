package com.clara.twilio

import org.apache.http.NameValuePair
import org.apache.http.message.BasicNameValuePair

final case class TwilioRequest(
  to: String,
  from: String,
  body: String
) {
  def toParams: Seq[NameValuePair] = {
    Seq(
      new BasicNameValuePair("Body", body),
      new BasicNameValuePair("To", to),
      new BasicNameValuePair("From", from)
    )
  }
}
