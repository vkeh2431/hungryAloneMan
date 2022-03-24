package com.example.cameraexample;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import com.example.cameraexample.process.RecyclerItem_recipe;

public class ClickedHeartReceiver extends BroadcastReceiver {

    public interface clickedHeartReceiverInterface {
        void ReceiverInterface(RecyclerItem_recipe item_recipe);
    }

    public ClickedHeartReceiver() {

    }

    private clickedHeartReceiverInterface receiverInterface = null;

    public ClickedHeartReceiver(clickedHeartReceiverInterface receiverInterface) {
        this.receiverInterface = receiverInterface;
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        String action = intent.getAction();
        receiverInterface.ReceiverInterface((RecyclerItem_recipe) intent.getParcelableExtra("Parcelable"));
    }
}
