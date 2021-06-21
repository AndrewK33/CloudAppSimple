package com.geekbrains.CloudServer;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

public class MainHandler extends ChannelInboundHandlerAdapter {




    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        System.out.println("Client Connected");
    }
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        ByteBuf byteBuf = (ByteBuf)msg;
        while (byteBuf.readableBytes() > 0) {
            System.out.print((char)byteBuf.readByte());
        }
        byteBuf.release();
    }
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }


}
