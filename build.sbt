sbtappengine.Plugin.appengineSettings

name := "libraryDependencies"

scalaVersion := "2.11.0"

licenses := Seq("MIT" -> url("http://opensource.org/licenses/MIT"))

scalacOptions ++= Seq("-deprecation", "-Xlint", "-unchecked", "-language:_")

libraryDependencies ++= (
  ("org.eclipse.jetty" % "jetty-webapp" % "7.6.8.v20121106" % "container") ::
  ("javax.servlet" % "servlet-api" % "2.5") ::
  ("org.scala-lang.modules" %% "scala-xml" % "1.0.1") ::
  Nil
)

