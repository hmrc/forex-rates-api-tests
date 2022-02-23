# forex-rates-api-tests
API test suite for the `forex-rates` service using ScalaTest and [play-ws](https://github.com/playframework/play-ws) client.  

## Running the tests

Prior to executing the tests ensure you have:
 - Installed [MongoDB](https://docs.mongodb.com/manual/installation/) 
 - Installed/configured [service manager](https://github.com/hmrc/service-manager).  

Run the following commands to start services locally:

MongoDB:

Run at least 4.0 with a replica set: `docker run --restart unless-stopped -d -p 27017-27019:27017-27019 --name mongo4 mongo:4.0 --replSet rs0`
Connect to said replica set: `docker exec -it mongo4 mongo`
When that console is there: `rs.initiate()`
You then should be running 4.0 with a replica set. You may have to re-run the rs.initiate() after you've restarted


Service Manager:
```
sm --start ONE_STOP_SHOP_ALL -r
```
If running a branch of forex-rates locally, use the following command in the terminal:
```
"sbt run -Dapplication.router=testOnlyDoNotUseInAppConf.Routes"
```

Then execute the `run_tests.sh` script:

`./run_tests.sh <environment>`

The tests default to the `local` environment.  For a complete list of supported param values, see:
 - `src/test/resources/application.conf` for **environment** 

#### Running the tests against a test environment

To run the tests against an environment set the corresponding `host` environment property as specified under
 `<env>.host.services` in the [application.conf](src/test/resources/application.conf). 

 ### Scalafmt
 This repository uses [Scalafmt](https://scalameta.org/scalafmt/), a code formatter for Scala. The formatting rules configured for this repository are defined within [.scalafmt.conf](.scalafmt.conf).

 To apply formatting to this repository using the configured rules in [.scalafmt.conf](.scalafmt.conf) execute:

 ```
 sbt scalafmtAll scalafmtSbt
 ```

 To check files have been formatted as expected execute:

 ```
 sbt scalafmtCheckAll scalafmtSbtCheck
 ```

[Visit the official Scalafmt documentation to view a complete list of tasks which can be run.](https://scalameta.org/scalafmt/docs/installation.html#task-keys)