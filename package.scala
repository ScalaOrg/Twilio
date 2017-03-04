package com.clara

import com.twitter.finagle.Service

package object twilio {
  type TwilioService = Service[TwilioRequest, TwilioResponse]
}
