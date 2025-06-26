package com.example.dispatcher.server.data.flow;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

@SpringBootTest
class CustomRequestInterceptorTest {
	private final CustomRequestInterceptor customRequestInterceptor = new CustomRequestInterceptor();
	private final HttpServletRequest request = mock(HttpServletRequest.class);
	private final HttpServletResponse response = mock(HttpServletResponse.class);
	private final Object handler = new Object();

	@Test
	void givenValidURLWhenPreHandleThenSetStartTimeAndHeadersAndReturnTrue() {
		//given
		when(request.getMethod()).thenReturn("GET");
		when(request.getRequestURI()).thenReturn("/test");
		when(request.getRemoteAddr()).thenReturn("127.0.0.1");
		when(request.getRequestURL()).thenReturn(new StringBuffer("http://localhost/test"));

		//when
		boolean result = customRequestInterceptor.preHandle(request, response, handler);

		//then
		assertTrue(result);

		verify(request).setAttribute(eq("startTime"), anyLong());

		ArgumentCaptor<String> headerCaptor = ArgumentCaptor.forClass(String.class);
		verify(response).addHeader(eq("X-Request-ID"), headerCaptor.capture());
	}

	@Test
	void givenInvalidURLWhenPreHandleThenReturnFalse() {
		//given
		when(request.getRequestURL()).thenReturn(new StringBuffer("http://localhost/blocked/test"));

		//when
		boolean result = customRequestInterceptor.preHandle(request, response, handler);

		//then
		assertFalse(result);
	}

	@Test
	void givenValidStartTimeWhenAfterCompletionThenDontThrowException() {
		//given
		long startTime = System.currentTimeMillis() - 123;
		when(request.getAttribute("startTime")).thenReturn(startTime);

		//when
		customRequestInterceptor.afterCompletion(request, response, handler, null);
	}
}
