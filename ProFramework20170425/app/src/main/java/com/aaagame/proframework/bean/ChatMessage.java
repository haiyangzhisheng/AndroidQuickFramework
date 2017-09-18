package com.aaagame.proframework.bean;

import com.aaagame.proframework.R;

import java.util.ArrayList;
import java.util.List;

;

public class ChatMessage {
    private int icon;
    private String name;
    private String content;
    private String createDate;
    private boolean isReveiveMsg;

    public final static int RECIEVE_MSG = 0;
    public final static int SEND_MSG = 1;

    public ChatMessage(int icon, String name, String content,
                       String createDate, boolean isReveiveMsg) {
        this.icon = icon;
        this.name = name;
        this.content = content;
        this.createDate = createDate;
        this.isReveiveMsg = isReveiveMsg;
    }

    public boolean isReveiveMsg() {
        return isReveiveMsg;
    }

    public void setReveiveMsg(boolean reveiveMsg) {
        isReveiveMsg = reveiveMsg;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getIcon() {
        return icon;
    }

    public void setIcon(int icon) {
        this.icon = icon;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCreateDate() {
        return createDate;
    }

    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }

    @Override
    public String toString() {
        return "ChatMessage [icon=" + icon + ", name=" + name + ", content="
                + content + ", createDate=" + createDate + ", isComing = " + isReveiveMsg() + "]";
    }

    public static List<ChatMessage> MOCK_DATAS = new ArrayList<>();

    static {
        ChatMessage msg = null;
        msg = new ChatMessage(R.drawable.default_head, "店小二", "您好！请问有什么可以帮助您吗？ ",
                null, true);
        MOCK_DATAS.add(msg);
        msg = new ChatMessage(R.drawable.empty_default, "张三", "我想了解一下你们这个充电桩的积分兑换，例如多少积分可以充电，谢谢了。",
                null, false);
        MOCK_DATAS.add(msg);
        msg = new ChatMessage(R.drawable.default_head, "店小二", " 嗯，好的，没问题，一会儿把具体兑换信息发送给你，好吧？",
                null, true);
        MOCK_DATAS.add(msg);
        msg = new ChatMessage(R.drawable.empty_default, "张三", "嗯，好的",
                null, false);
        MOCK_DATAS.add(msg);

    }


}
