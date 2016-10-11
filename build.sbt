name := """datetime-generator"""

version := "0.2-SNAPSHOT"

scalaVersion := "2.11.7"

organization := "com.cogpp"


libraryDependencies += "org.scalacheck" %% "scalacheck" % "1.13.2"

publishMavenStyle := true

publishTo := {
  val nexus = "https://oss.sonatype.org/"
  if (isSnapshot.value)
    Some("snapshots" at nexus + "content/repositories/snapshots")
  else
    Some("releases"  at nexus + "service/local/staging/deploy/maven2")
}

publishArtifact in Test := false

pomIncludeRepository := { _ => false }

pomExtra := (
  <url>https://github.com/cogpp/datetime-generator</url>
    <licenses>
      <license>
        <name>Apache 2.0</name>
        <url>http://www.apache.org/licenses/LICENSE-2.0.txt</url>
        <distribution>repo</distribution>
        <comments>A business-friendly OSS license</comments>
      </license>
    </licenses>
    <scm>
      <url>git@github.com:cogpp/datetime-generator.git</url>
      <connection>scm:git@github.com:cogpp/datetime-generator.git</connection>
    </scm>
    <developers>
      <developer>
        <id>cogpp</id>
        <name>Graeme Phillipson</name>
        <url>http://cogpp.com</url>
      </developer>
    </developers>)


