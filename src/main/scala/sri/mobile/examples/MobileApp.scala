package sri.mobile.examples

import sri.mobile.ReactNative
import sri.mobile.all._
import sri.mobile.examples.router.AppRouter
import scala.scalajs.js.JSApp


object MobileApp extends JSApp {

  def main() = {

    val root = createMobileRoot(
      AppRouter.router
    )
    ReactNative.AppRegistry.registerComponent("SriMobileTemplate", () => root)
  }
}
