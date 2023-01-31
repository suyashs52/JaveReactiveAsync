package com.demo.client.deadline;

import io.grpc.*;

import java.util.Objects;
import java.util.concurrent.TimeUnit;

public class DeadlineInterceptor implements ClientInterceptor {
    @Override
    public <ReqT, RespT> ClientCall<ReqT, RespT> interceptCall(MethodDescriptor<ReqT, RespT> methodDescriptor, CallOptions callOptions, Channel channel) {

        Deadline deadline = callOptions.getDeadline();
        if (Objects.isNull(deadline)) { //if no deadline options was passed
            callOptions = callOptions.withDeadline(Deadline.after(14, TimeUnit.SECONDS));
        }

        return channel.newCall(methodDescriptor, callOptions
        );
    }
}
