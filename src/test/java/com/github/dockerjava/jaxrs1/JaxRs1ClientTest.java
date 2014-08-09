package com.github.dockerjava.jaxrs1;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

import java.lang.reflect.Method;

import com.github.dockerjava.api.DockerException;
import com.github.dockerjava.api.command.CreateContainerResponse;
import com.github.dockerjava.client.AbstractDockerClientTest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

/**
 * Unit test for DockerClient.
 *
 * @author Konstantin Pelykh (kpelykh@gmail.com)
 */
public class JaxRs1ClientTest extends AbstractDockerClientTest {
	public static final Logger LOG = LoggerFactory
			.getLogger(JaxRs1ClientTest.class);

	@BeforeTest
	public void beforeTest() throws DockerException {
		super.beforeTest();
	}
	@AfterTest
	public void afterTest() {
		super.afterTest();
	}

	@BeforeMethod
	public void beforeMethod(Method method) {
	    super.beforeMethod(method);
	}

	@AfterMethod
	public void afterMethod(ITestResult result) {
		super.afterMethod(result);
	}


	@Test
	public void testRunShlex() throws DockerException {

		String[] commands = new String[] {
				"true",
				"echo \"The Young Descendant of Tepes & Septette for the Dead Princess\"",
				"echo -n 'The Young Descendant of Tepes & Septette for the Dead Princess'",
				"/bin/sh -c echo Hello World", "/bin/sh -c echo 'Hello World'",
				"echo 'Night of Nights'", "true && echo 'Night of Nights'" };

		for (String command : commands) {
			LOG.info("Running command: [{}]", command);

			CreateContainerResponse container = dockerClient
					.createContainerCmd("busybox").withCmd(commands).exec();
			dockerClient.startContainerCmd(container.getId());
			tmpContainers.add(container.getId());
			int exitcode = dockerClient.waitContainerCmd(container.getId()).exec();
			assertThat(exitcode, equalTo(0));
		}
	}


}