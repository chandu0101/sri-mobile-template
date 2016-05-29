import org.scalajs.sbtplugin.ScalaJSPlugin.AutoImport._
import sbt.Keys._
import sbt._

object LauncherConfigs {


  /**
   * react-native prod bundler needs require function without name spaces
   * @param text
   * @return
   */
  def processRequireFunctions(text: String): String = {
    val SJS_NAME_SPACE = "exportsNamespace:"
    val i = text.indexOf(SJS_NAME_SPACE) + SJS_NAME_SPACE.length
    val j = text.substring(i).indexOf(";") + i // TODO look for non valid identifier ![_$0-9a-zA-Z]
    val nameSpace = text.substring(i,j)
    text.replaceAll(s"$nameSpace.require\\(", "require\\(")
  }

  /**
   * react-native prod bundler needs require function without name spaces
   * @param text
   * @return
   */
  def processRequireFunctionsInFastOpt(text: String): String = {
    text.replaceAll("\\$g.require\\(", "require\\(")
  }
}