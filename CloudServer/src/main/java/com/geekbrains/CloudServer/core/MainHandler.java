package com.geekbrains.CloudServer.core;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
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
        /*while (byteBuf.readableBytes() > 0) {

        }*/
        String line = new String(byteBuf.toString(Charset.defaultCharset())).trim();
        System.out.print(line);

        String result = commandProcessor.process(line);
        ctx.writeAndFlush(Unpooled.copiedBuffer(result, CharsetUtil.UTF_8));


        byteBuf.release();
    }



    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }


}
