
enablePlugins(ScalaJSPlugin)

name := "sri-mobile-template"

scalaVersion := "2.11.8"

val sriVersion = "0.7.0"

libraryDependencies ++= Seq("com.github.chandu0101" %%% "sri-mobile" % sriVersion)



scalaJSModuleKind := ModuleKind.CommonJSModule

/** ================ React_native task   ================ */

val SJS_OUTPUT_PATH = "assets/scalajs-output.js"

val fastOptMobile = Def.taskKey[File]("Generate mobile output file for fastOptJS")


artifactPath in Compile in fastOptJS :=
  baseDirectory.value / SJS_OUTPUT_PATH
artifactPath in Compile in fastOptMobile :=
  baseDirectory.value / "index.ios.js"
fastOptMobile in Compile := {
  (artifactPath in Compile in fastOptMobile).value.delete()
  val outFile = (artifactPath in Compile in fastOptMobile).value

  val fastoptOutputCode = IO.read((fastOptJS in Compile).value.data)

  val outString = fastoptOutputCode.replace("this[\"__ScalaJSExportsNamespace\"] = $e;", "") //TODO we don't need this in scala.js 0.6.15

  IO.write(baseDirectory.value / SJS_OUTPUT_PATH, outString)

  val launcher = (scalaJSLauncher in Compile).value.data.content
  IO.append(outFile, launcher)

  IO.copyFile(outFile, baseDirectory.value / "index.android.js")
  outFile
}


val fullOptMobile = Def.taskKey[File]("Generate mobile output file for fullOptJS")


artifactPath in Compile in fullOptJS :=
  baseDirectory.value / SJS_OUTPUT_PATH
artifactPath in Compile in fullOptMobile :=
  baseDirectory.value / "index.ios.js"
fullOptMobile in Compile := {
  (artifactPath in Compile in fullOptMobile).value.delete()

  val outFile = (artifactPath in Compile in fullOptMobile).value

  val fulloptOutputCode = IO.read((fullOptJS in Compile).value.data)

  IO.write(baseDirectory.value / SJS_OUTPUT_PATH, fulloptOutputCode)

  val launcher = (scalaJSLauncher in Compile).value.data.content
  IO.append(outFile, launcher)

  IO.copyFile(outFile, baseDirectory.value / "index.android.js")
  outFile
}
