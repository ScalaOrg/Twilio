package com.clara.twilio

import com.twilio.sdk.TwilioRestClient
import com.twitter.finagle.stats.StatsReceiver

object Twilio {
  /**
    * Construct a dummy Twilio client that does nothing and is always happy
    * @param statsReceiver
    * @return Service[TwilioRequest, TwilioResponse]
    */
  def withNullClient(statsReceiver: StatsReceiver): TwilioService = {
    new TwilioObservingFilter(statsReceiver) andThen NullTwilio
  }

  /**
    * Construct a Twilio client with an actual Twilio client. Stats/Logging are provided
    * by way of a observing filter.
    *
    * @param accountSid - Account_SID from Twilio
    * @param authToken - Auth_token from Twilio
    * @param statsReceiver
    * @return Service[TwilioRequest, TwilioResponse]
    */
  def withCredentials(
    accountSid: String,
    authToken: String,
    statsReceiver: StatsReceiver
  ): TwilioService = {
    val client = new TwilioRestClient(accountSid, authToken)
    new TwilioObservingFilter(statsReceiver) andThen new TwilioBackedTwilio(client)
  }
}
