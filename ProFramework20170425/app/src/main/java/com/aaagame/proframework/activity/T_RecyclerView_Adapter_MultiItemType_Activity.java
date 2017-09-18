package com.aaagame.proframework.activity;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.aaagame.proframework.R;
import com.aaagame.proframework.bean.ChatMessage;
import com.aaagame.proframework.recycleradapter.AARecAdapter_MultiItemType;
import com.aaagame.proframework.utils.MsgReceiveItemDelagate;
import com.aaagame.proframework.utils.MsgSendItemDelagate;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;

@ContentView(R.layout.t_recyclerview_adapter_multiitemtype_activity)
public class T_RecyclerView_Adapter_MultiItemType_Activity extends BaseFragmentActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @ViewInject(R.id.rec_msg)
    RecyclerView rec_msg;
    @ViewInject(R.id.et_content)
    EditText et_content;
    @ViewInject(R.id.btn_send)
    Button btn_send;

    @Override
    public void initView() {

    }

    @Override
    public void initListener() {
        btn_send.setOnClickListener(this);

    }

    @Override
    public void reqData() {

    }

    AARecAdapter_MultiItemType<ChatMessage> adapter_multiItemType;

    @Override
    public void initData() {
        rec_msg.setLayoutManager(new LinearLayoutManager(this));
        adapter_multiItemType = new AARecAdapter_MultiItemType<>(this, ChatMessage.MOCK_DATAS);
        rec_msg.setAdapter(adapter_multiItemType);
        adapter_multiItemType.addItemViewDelegate(new MsgReceiveItemDelagate()).addItemViewDelegate(new MsgSendItemDelagate());
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_send:
                if (et_content.getText().toString().equals("")) {
                    toastShow("发送内容不能为空");
                    return;
                }
                ChatMessage msg = new ChatMessage(R.drawable.empty_default, "张三", et_content.getText().toString(),
                        null, false);
                adapter_multiItemType.getDatas().add(msg);
                adapter_multiItemType.notifyItemInserted(adapter_multiItemType.getDatas().size() - 1);
                rec_msg.scrollToPosition(adapter_multiItemType.getDatas().size() - 1);
                break;
        }
    }
}
