package io.github.ohofun.dim.starter.filter;

/**
 * @author ohofun
 * @version 1.0
 * @date 2021/12/10 23:20
 */
public interface OnMessageFilter {
    /**
     * 如果你想在接收消息的时候进行一些数据或者业务逻辑的处理，例如，保存这个数据到数据库中，那么你需要重写这个方法。
     * @param message 接收到的信息
     * @return 返回处理之后的消息信息,如果你不需要，请不要将消息内容进行修改后返回，尽量保持数据的原始。
     */
    String onMessage(String message);



}
