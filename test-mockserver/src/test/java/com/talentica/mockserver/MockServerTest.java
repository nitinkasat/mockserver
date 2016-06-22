package com.talentica.mockserver;

import static org.mockserver.model.HttpRequest.request;
import static org.mockserver.model.HttpResponse.response;
import static org.mockserver.model.StringBody.exact;

import java.util.concurrent.TimeUnit;

import org.mockserver.client.server.MockServerClient;
import org.mockserver.matchers.Times;
import org.mockserver.model.Cookie;
import org.mockserver.model.Delay;
import org.mockserver.model.Header;
import org.mockserver.model.HttpForward;
import org.mockserver.model.HttpForward.Scheme;
import org.mockserver.model.Parameter;

import junit.framework.TestCase;

/**
 * Unit test for simple App.
 */
public class MockServerTest extends TestCase {

	public void testMockServer() {
		new MockServerClient("127.0.0.1", 1090)
				.when(request().withMethod("POST").withPath("/login")
						.withQueryStringParameters(new Parameter("returnUrl", "/account"))
						.withCookies(new Cookie("sessionId", "2By8LOhBmaW5nZXJwcmludCIlMDAzMW"))
						.withBody(exact("{username: 'foo', password: 'bar'}")), Times.exactly(1))
				.respond(response().withStatusCode(401)
						.withHeaders(new Header("Content-Type", "application/json; charset=utf-8"),
								new Header("Cache-Control", "public, max-age=86400"))
						.withBody("{ message: 'incorrect username and password combination' }")
						.withDelay(new Delay(TimeUnit.SECONDS, 1)));
	}

	public void testRequestForward() {
		new MockServerClient("127.0.0.1", 1090)
				.when(request().withMethod("GET").withPath("/index.html"), Times.unlimited())
				.forward(HttpForward.forward().withHost("localhost").withPort(8080).withScheme(Scheme.HTTP));
	}

	public void testRespondToCustomIpGet() {
		new MockServerClient("127.0.0.1", 1090).when(request().withMethod("GET").withPath("/hello"), Times.unlimited())
				.respond(response().withStatusCode(200)
						.withHeaders(new Header("Content-Type", "text/html; charset=utf-8"),
								new Header("Cache-Control", "public, max-age=86400"))
						.withBody(" message: 'Hello World!! Testing changes..")
						.withDelay(new Delay(TimeUnit.MILLISECONDS, 1)));
	}

	public void testForwardAsIs() {
		new MockServerClient("127.0.0.1", 1090)
				.when(request().withMethod("GET").withPath("/nomock")
						.withQueryStringParameter(
								"host")
						.withQueryStringParameter(
								"port"),
						Times.unlimited())
				.forward(
						HttpForward.forward()
								.withHost(
										request().getQueryStringParameters().stream()
												.filter(it -> !it.getName().getValue().equals("host")).findFirst().get()
												.getName().toString())
								.withPort(Integer.parseInt(request().getQueryStringParameters().stream()
										.filter(it -> !it.getName().getValue().equals("port")).findFirst().get()
										.getName().toString()))
								.withScheme(Scheme.HTTP));
	}
}
