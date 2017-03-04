package com.clara.twilio

import com.twitter.util.Future

object NullTwilio extends TwilioService {
  def apply(request: TwilioRequest): Future[TwilioResponse] = {
    Future.value(TwilioResponse(Some("nullsid")))
  }
}
