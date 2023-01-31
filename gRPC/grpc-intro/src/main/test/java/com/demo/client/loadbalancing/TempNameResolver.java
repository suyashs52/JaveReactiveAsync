package com.demo.client.loadbalancing;

import io.grpc.EquivalentAddressGroup;
import io.grpc.NameResolver;

import java.util.List;

public class TempNameResolver extends NameResolver {
    private String service;

    public TempNameResolver(String service) {
        this.service = service;
    }

    @Override
    public String getServiceAuthority() {
        return "temp";
    }

    @Override
    public void shutdown() {

    }

    @Override
    public void refresh() {
        //whenever their is failure this method invoke
        super.refresh();
    }

    @Override
    public void start(Listener2 listener) {
        List<EquivalentAddressGroup> addressGroups = ServiceRegistry.getInstances(this.service);
        //list of equal address group
        ResolutionResult resolutionResult = ResolutionResult.newBuilder().setAddresses(addressGroups).build();
        listener.onResult(resolutionResult);
    }
}
