import LauncherConfigs._

enablePlugins(ScalaJSPlugin)

name := "sri-mobile-template"

scalaVersion := "2.11.8"

val sriVersion = "0.6.0"

libraryDependencies ++= Seq("com.github.chandu0101" %%% "sri-mobile" % sriVersion)

val fastOptMobile = Def.taskKey[File]("Generate mobile output file for fastOptJS")

val fullOptMobile = Def.taskKey[File]("Generate the file given to react native")

artifactPath in Compile in fastOptMobile :=
  baseDirectory.value / "index.ios.js"
fastOptMobile in Compile := {
  val outFile = (artifactPath in Compile in fastOptMobile).value

  val loaderFile = (resourceDirectory in Compile).value / "loader.js"

  IO.copyFile(loaderFile, outFile)

  val fullOutputCode = IO.read((fastOptJS in Compile).value.data)

  val outString = processRequireFunctionsInFastOpt(fullOutputCode)

  IO.write(baseDirectory.value / "scalajs-output.js", outString)

  val launcher = (scalaJSLauncher in Compile).value.data.content
  IO.append(outFile, launcher)

  IO.copyFile(outFile, baseDirectory.value / "index.android.js")
  outFile
}

artifactPath in Compile in fullOptMobile :=
  baseDirectory.value / "index.ios.js"
fullOptMobile in Compile := {
  val outFile = (artifactPath in Compile in fullOptMobile).value

  val loaderFile = (resourceDirectory in Compile).value / "loader.js"

  IO.copyFile(loaderFile, outFile)

  val fullOutputCode = IO.read((fullOptJS in Compile).value.data)

  val outString = processRequireFunctions(fullOutputCode)

  IO.write(baseDirectory.value / "scalajs-output.js", outString)

  val launcher = (scalaJSLauncher in Compile).value.data.content
  IO.append(outFile, launcher)

  IO.copyFile(outFile, baseDirectory.value / "index.android.js")
  outFile
}