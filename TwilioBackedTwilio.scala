package com.clara.twilio

import com.twilio.sdk.TwilioRestClient
import com.twitter.util.{Future, FuturePool}

import scala.collection.JavaConverters._

class TwilioBackedTwilio(rawTwilioClient: TwilioRestClient) extends TwilioService {
  def apply(request: TwilioRequest): Future[TwilioResponse] = {
    FuturePool.unboundedPool {
      val messageFactory = rawTwilioClient.getAccount().getMessageFactory()
      val message = messageFactory.create(request.toParams.asJava)

      TwilioResponse(Option(message.getSid))
    }
  }
}
