package com.clara.twilio

import com.twitter.finagle.SimpleFilter
import com.twitter.finagle.stats.{Stat, StatsReceiver}
import com.twitter.logging.Logger
import com.twitter.util.Future

class TwilioObservingFilter(
  statsReceiver: StatsReceiver
) extends SimpleFilter[TwilioRequest, TwilioResponse] {
  private[this] val logger         = Logger("Twilio")
  private[this] val scopedStats    = statsReceiver.scope("Twilio")
  private[this] val sendCounter    = scopedStats.counter("requests")
  private[this] val successCounter = scopedStats.counter("success")
  private[this] val latencyStat    = scopedStats.stat("time_ms")

  def apply(
    request: TwilioRequest,
    service: TwilioService
  ): Future[TwilioResponse] = {
    sendCounter.incr()

    Stat.timeFuture(latencyStat) {
      logger.info(s"Sending to: ${request.to} from: ${request.from} - ${request.body}")
      service(request).onSuccess { _ =>
        successCounter.incr()
      }.onFailure {
        case ex: Throwable =>
          scopedStats.scope("failures").counter(ex.getClass.getSimpleName).incr()
      }
    }
  }
}
