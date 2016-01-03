
lazy val benchmark = 
  (project in file("."))
    .settings(commonSettings: _*)
    .aggregate(
    	`benchmark-core`, 
    	`benchmark-quill-jdbc`,
    	`benchmark-quill-async`,
    	`benchmark-slick`, 
    	`benchmark-doobie`
    )

lazy val `benchmark-core` = 
  (project in file("benchmark-core"))
    .settings(commonSettings: _*)

lazy val `benchmark-quill` = 
  (project in file("benchmark-quill"))
    .settings(commonSettings: _*)
    .settings(
      libraryDependencies ++= Seq(
          "org.postgresql" % "postgresql" % "9.4-1206-jdbc41",
          "io.getquill" %% "quill-sql" % "0.2.1"
        )
     )

lazy val `benchmark-quill-jdbc` = 
  (project in file("benchmark-quill-jdbc"))
    .settings(commonSettings: _*)
    .settings(
    	libraryDependencies ++= Seq(
      		"org.postgresql" % "postgresql" % "9.4-1206-jdbc41",
  			  "io.getquill" %% "quill-jdbc" % "0.2.1"
      	)
     )

lazy val `benchmark-quill-async` = 
  (project in file("benchmark-quill-async"))
    .settings(commonSettings: _*)
    .settings(
    	libraryDependencies ++= Seq(
  			"io.getquill" %% "quill-async" % "0.2.1"
      	)
     )

lazy val `benchmark-slick` = 
  (project in file("benchmark-slick"))
    .settings(commonSettings: _*)
    .settings(
    	libraryDependencies ++= Seq(
      		"com.typesafe.slick" %% "slick" % "3.1.1"
      	)	
     )

lazy val `benchmark-doobie` = 
  (project in file("benchmark-doobie"))
    .settings(commonSettings: _*)
    .settings(
    	libraryDependencies ++= Seq(
      		"org.tpolecat" %% "doobie-core" % "0.2.3"
      	)	
     )

lazy val commonSettings = Seq(
  scalaVersion := "2.11.7",
	libraryDependencies += "com.storm-enroute" %% "scalameter" % "0.7",
	testFrameworks += new TestFramework("org.scalameter.ScalaMeterFramework"),
	parallelExecution in Test := false,
	EclipseKeys.createSrc := EclipseCreateSrc.Default + EclipseCreateSrc.Resource
)