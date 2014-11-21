/*
 * Copyright Elasticsearch B.V. and/or licensed to Elasticsearch B.V. under one
 * or more contributor license agreements. Licensed under the Elastic License;
 * you may not use this file except in compliance with the Elastic License.
 */
package org.elasticsearch.shield.transport.netty;

import org.elasticsearch.common.netty.channel.ChannelEvent;
import org.elasticsearch.common.netty.channel.ChannelHandler;
import org.elasticsearch.common.netty.channel.ChannelHandlerContext;
import org.elasticsearch.common.netty.handler.ipfilter.IpFilteringHandlerImpl;
import org.elasticsearch.shield.transport.n2n.IPFilteringN2NAuthenticator;

import java.net.InetSocketAddress;

/**
 *
 */
@ChannelHandler.Sharable
public class N2NNettyUpstreamHandler extends IpFilteringHandlerImpl {

    private final IPFilteringN2NAuthenticator authenticator;
    private final String profile;

    public N2NNettyUpstreamHandler(IPFilteringN2NAuthenticator authenticator, String profile) {
        this.authenticator = authenticator;
        this.profile = profile;
    }

    @Override
    protected boolean accept(ChannelHandlerContext channelHandlerContext, ChannelEvent channelEvent, InetSocketAddress inetSocketAddress) throws Exception {
        // at this stage no auth has happened, so we do not have any principal anyway
        return authenticator.authenticate(null, profile, inetSocketAddress.getAddress(), inetSocketAddress.getPort());
    }

}
