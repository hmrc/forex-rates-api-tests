package uk.gov.hmrc.test.api.utils

object Zap {

  val enabled: Boolean = sys.props.get("zap.proxy").isDefined

}
