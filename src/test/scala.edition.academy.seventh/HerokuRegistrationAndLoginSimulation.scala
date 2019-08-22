package edition.academy.seventh

import java.util.concurrent.ThreadLocalRandom

import scala.concurrent.duration._
import io.gatling.core.Predef._
import io.gatling.http.Predef._
import io.gatling.jdbc.Predef._

import scala.util.Random

class HerokuRegistrationAndLoginSimulation extends Simulation {

	val httpProtocol = http
		.baseUrl("https://bookrobot-front.herokuapp.com")
		.acceptHeader("image/webp,*/*")
		.acceptEncodingHeader("gzip, deflate")
		.acceptLanguageHeader("en-US,en;q=0.5")
		.userAgentHeader("Mozilla/5.0 (X11; Ubuntu; Linux x86_64; rv:68.0) Gecko/20100101 Firefox/68.0")

	val headers_0 = Map(
		"Accept" -> "text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8",
		"If-Modified-Since" -> "Wed, 21 Aug 2019 10:15:47 GMT",
		"If-None-Match" -> """W/"297-16cb3ace038"""",
		"Upgrade-Insecure-Requests" -> "1")

	val headers_1 = Map(
		"If-Modified-Since" -> "Wed, 21 Aug 2019 10:15:47 GMT",
		"If-None-Match" -> """W/"4d67-16cb3ace038"""")

	val headers_2 = Map(
		"If-Modified-Since" -> "Wed, 21 Aug 2019 10:15:47 GMT",
		"If-None-Match" -> """W/"12fc-16cb3ace038"""")

	val headers_3 = Map(
		"Accept" -> "application/json, text/plain, */*",
		"Origin" -> "https://bookrobot-front.herokuapp.com")

	val headers_4 = Map(
		"Accept" -> "text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8",
		"If-Modified-Since" -> "Wed, 31 Jul 2019 06:56:14 GMT",
		"If-None-Match" -> """W/"297-16c46d0a2b0"""",
		"Upgrade-Insecure-Requests" -> "1")

	val headers_6 = Map(
		"Accept" -> "*/*",
		"Access-Control-Request-Headers" -> "content-type",
		"Access-Control-Request-Method" -> "POST",
		"Origin" -> "https://bookrobot-front.herokuapp.com")

	val headers_7 = Map(
		"Accept" -> "application/json, text/plain, */*",
		"Content-Type" -> "application/json",
		"Origin" -> "https://bookrobot-front.herokuapp.com")

    val uri2 = "https://bookrobotja7.herokuapp.com"

	val user_data = Iterator.continually(
		Map(
			"username" -> ("testowy" + ThreadLocalRandom.current.nextInt(1000)),
			"email" -> ("testowy" + Random.alphanumeric.take(20).mkString + "@testowy.com"),
			"password" -> ("test" + ThreadLocalRandom.current.nextInt(100)),
		)
	)

	val scn = scenario("HerokuRegistrationAndLoginSimulation")
		// start
		.exec(http("request_0")
			.get("/home")
			.headers(headers_0))
		.pause(1)
		.exec(http("request_1")
			.get("/assets/img/logo.png")
			.headers(headers_1))
		.exec(http("request_2")
			.get("/assets/img/sort.png")
			.headers(headers_2))
		.pause(143 milliseconds)
		.exec(http("request_3")
			.get(uri2 + "/books/pagination")
			.headers(headers_3))
		.pause(18)
		// registration
		.exec(http("request_4")
			.get("/signup")
			.headers(headers_4))
		.pause(1)
		.exec(http("request_5")
			.get("/assets/img/logo.png")
			.headers(headers_1))
		.pause(22)
		.exec(http("request_6")
			.options(uri2 + "/auth/sign_up")
			.headers(headers_6))
		.feed(user_data)
		.exec(http("request_7")
			.post(uri2 + "/auth/sign_up")
			.headers(headers_7)
			.body(StringBody("""{"username":"${username}","email":"${email}","password":"${password}","role":["user"]}""")).asJson)
		.pause(20)
		// login
		.exec(http("request_8")
			.options(uri2 + "/auth/sign_in")
			.headers(headers_6))
		.exec(http("request_9")
			.post(uri2 + "/auth/sign_in")
			.headers(headers_7)
			.body(StringBody("""{"username":"${username}","password":"${password}"}""")).asJson)
		.exec(http("request_10")
			.get("/home")
			.headers(headers_0))
		.pause(1)
		.exec(http("request_11")
			.get("/assets/img/logo.png")
			.headers(headers_1))
		.exec(http("request_12")
			.get("/assets/img/sort.png")
			.headers(headers_2))
		.exec(http("request_13")
			.get(uri2 + "/books/pagination")
			.headers(headers_3))

	setUp(scn.inject(atOnceUsers(100))).protocols(httpProtocol)
}