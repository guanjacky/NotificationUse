package com.gelinpizhi.notificationuse;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;

import android.os.Bundle;

import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.RemoteViews;
import android.widget.Toast;
/*
通知（nitification）

 */

public class MainActivity extends AppCompatActivity {
    private static final int NID_1=0x1;
    private static final int NID_2=0x2;
    private static final int NID_3=0x3;
    private static final int NID_4=0x4;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }

     //带点击跳转的通知
    public void sendNotification(View view) {
        //api11之前创建通知的方式
//        Notification notification = new Notification();
        //api11之后创建通知的方式
//        Notification.Builder builder = new Notification.Builder(this);
        //v4支持包
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this);
        //设置小图标
        builder.setSmallIcon(R.mipmap.ic_launcher);
        //设置通知的内容
        builder.setContentText("你好啊我曹,我叫尼古拉斯观宝宝");
        //设置标题
        builder.setContentTitle("你有一条新消息请查收");
        builder.setNumber(100);//设置数字
        builder.setTicker("新消息");//通知弹出来的时候提示消息


        //定义一个意图，当点击通知时要打开一个界面的方式
        Intent intent = new Intent(this,Main2Activity.class);
        //参数：请求编码（没用），意图，创建PendingIntent的方式。
        //创建pi的方式：
//        PendingIntent.FLAG_CANCEL_CURRENT;取消当前的Pi创建新的。
//        PendingIntent.FLAG_NO_CREATE;如果有就用没有不创建。
//        PendingIntent.FLAG_ONE_SHOT;只使用一次。
//        PendingIntent.FLAG_UPDATE_CURRENT;如果有，就更新Intent，没有就创建。
        PendingIntent pi  = PendingIntent.getActivity(this,0,intent,PendingIntent.FLAG_UPDATE_CURRENT);
        //设置通知的事件

        builder.setContentIntent(pi);
        builder.setOngoing(true);//设置为常驻通知，也就是说删不掉
        //设置通知出现时的状态（声音还是震动还是闪光），设置为ALL的时候就是全部都有
        builder.setDefaults(Notification.DEFAULT_SOUND);
        //根据已经设置好的属性创建一个通知对象
        Notification notification = builder.build();

        NotificationManager   nm = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        nm.notify(NID_1, notification);
    }

    //大通知，就是比较大，可以放的内容比小通知多
    public void sendBigNotification(View view) {
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this);
        //设置小图标
        builder.setSmallIcon(R.mipmap.ic_launcher);
        //设置通知的内容
        builder.setContentText("你好啊我曹,我叫大的尼古拉斯观宝宝");
        //设置标题
        builder.setContentTitle("你有一条大的新消息请查收");
        //设置大视图样式
        NotificationCompat.InboxStyle style =
                new NotificationCompat.InboxStyle();
        style.setBigContentTitle("别人笑我太疯癫，我笑他人看不穿");//设置大标题
        style.addLine("长亭外，古道边");//设置子内容
        style.addLine("炮火响连天");//设置子内容
        style.setSummaryText("观观");//设置小标题的内容
        builder.setNumber(26);//设置传过去的数字
        builder.setStyle(style);//设置style
        NotificationManager nm  = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
       nm.notify(NID_2,builder.build());


    }
    //带进度条的通知
    public void sendProgressNotification(View view) {
        final NotificationCompat.Builder builder3 = new NotificationCompat.Builder(this);
        //设置小图标
        builder3.setSmallIcon(R.mipmap.ic_launcher);
        //设置通知的内容
        builder3.setContentText("正在更新");
        //设置标题
        builder3.setContentTitle("更新模式的进度通知");
//        builder.setDefaults(Notification.DEFAULT_SOUND);
        builder3.setTicker("哈哈哈，惠州的朋友我来了");
//        builder.setProgress(100,5,false);//最后个参数意思是是否是确定的进度，false就是确定的意思。
        final NotificationManager nm = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
//        final Notification nnn = builder.build();
//        nm.notify(NID_3,nnn);

        //模拟更新的线程
        new Thread(new Runnable() {
            @Override
            public void run() {

                for (int progress = 0; progress <99 ; progress+=5) {
                    builder3.setProgress(100,progress,false);
                    nm.notify(NID_3,builder3.build());
                    try {
                        Thread.sleep(500);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                     if (progress>80){
                         builder3.setProgress(100,0,false);
                         builder3.setContentText("更新完成");
                         break;
                     }

                }



            }
        }).start();

    }
    //自定义的通知
    public void sendMyNotification(View view) {
        final NotificationCompat.Builder builder1 =
                new NotificationCompat.Builder(this);
        builder1.setSmallIcon(R.mipmap.ic_launcher);
        //创建一个远程的视图
        RemoteViews views = new RemoteViews(getPackageName(),
                R.layout.custom);
        views.setTextViewText(R.id.songname,"我是歌手");
//        views.setOnClickPendingIntent();点击控件时候的点击事件，通过服务来实现
//        views.setOnClickFillInIntent();
//        builder1.setContent(views);点击整个视图的监听事件。
        builder1.setTicker("尼古拉斯观宝宝");

        NotificationManager nb =
                (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
                 nb.notify(NID_4,builder1.build());




    }
}
