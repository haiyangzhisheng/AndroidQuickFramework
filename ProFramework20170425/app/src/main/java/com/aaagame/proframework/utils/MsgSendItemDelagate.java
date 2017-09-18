package com.aaagame.proframework.utils;

import com.aaagame.proframework.R;
import com.aaagame.proframework.bean.ChatMessage;
import com.aaagame.proframework.recycleradapter.base.AARecViewHolder;
import com.aaagame.proframework.recycleradapter.base.ItemViewDelegate;


/**
 * Created by zhy on 16/6/22.
 */
public class MsgSendItemDelagate implements ItemViewDelegate<ChatMessage> {

    @Override
    public int getItemViewLayoutId() {
        return R.layout.me_activity_msg_send_item;
    }

    @Override
    public boolean isForViewType(ChatMessage item, int position) {
        return !item.isReveiveMsg();
    }

    @Override
    public void convert(AARecViewHolder holder, ChatMessage chatMessage, int position) {
        holder.setText(R.id.tv_content_send, chatMessage.getContent());
//        holder.setImageResource(R.id.chat_send_icon, chatMessage.getIcon());
    }
}
