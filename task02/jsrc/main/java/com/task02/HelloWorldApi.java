package com.task02;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyRequestEvent;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyResponseEvent;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.syndicate.deployment.annotations.LambdaUrlConfig;
import com.syndicate.deployment.annotations.lambda.LambdaHandler;
import com.syndicate.deployment.model.lambda.url.AuthType;
import com.syndicate.deployment.model.lambda.url.InvokeMode;

@LambdaHandler(lambdaName = "hello_world_api",
  roleName = "hello_world_api-role",
  isPublishVersion = true
)
@LambdaUrlConfig(
  authType = AuthType.NONE,
  invokeMode = InvokeMode.BUFFERED
)
public class HelloWorldApi implements
  RequestHandler<APIGatewayProxyRequestEvent, APIGatewayProxyResponseEvent> {

  private static final int SC_OK = 200;
  private static final int SC_BAD_REQUEST = 400;
  private final ObjectMapper objectMapper = new ObjectMapper();

  @Override
  public APIGatewayProxyResponseEvent handleRequest(
    APIGatewayProxyRequestEvent apiGatewayProxyRequestEvent, Context context) {
    context.getLogger().log(apiGatewayProxyRequestEvent.toString());
    try {
      return new APIGatewayProxyResponseEvent()
        .withStatusCode(SC_OK)
        .withBody(objectMapper.writeValueAsString(new Response("Hello from lambda")));
    } catch (Exception exception) {
      return new APIGatewayProxyResponseEvent()
        .withStatusCode(SC_BAD_REQUEST)
        .withBody(null);
    }
  }
}
