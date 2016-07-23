package com.xzit.weatherfirst;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.thinkland.sdk.android.DataCallBack;
import com.thinkland.sdk.android.JuheData;
import com.thinkland.sdk.android.Parameters;
import com.xzit.weatherfirst.bean.QuestionBean;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private ViewPager viewPager;
    private  List<QuestionBean>  questionBeanList =new ArrayList<>();
    private Context mContext;
    private TextView tv;
    private String radioAnswer;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        getJson();
        //initData();
    }

    private void initView() {
        viewPager=(ViewPager)findViewById(R.id.view_pager);
    }

    private void initData() {
        viewPager.setAdapter(new PagerAdapter() {
            @Override
            public int getCount() {
                Log.e("-->",""+questionBeanList.size());
                return questionBeanList.size();
            }

            @Override
            public boolean isViewFromObject(View view, Object object) {
                return view==object;
            }

            @Override
            public Object instantiateItem(ViewGroup container, int position) {
                View view=View.inflate(getApplicationContext(),R.layout.question,null);
                ImageView img=(ImageView)view.findViewById(R.id.imge);
                TextView question=(TextView)view.findViewById(R.id.question);
                RadioButton item1=(RadioButton)view.findViewById(R.id.item1);
                RadioButton item2=(RadioButton)view.findViewById(R.id.item2);
                RadioButton item3=(RadioButton)view.findViewById(R.id.item3);
                RadioButton item4=(RadioButton)view.findViewById(R.id.item4);
                TextView answer=(TextView)view.findViewById(R.id.answer);
                TextView explains=(TextView)view.findViewById(R.id.explains);
                img.setImageURI(Uri.parse(questionBeanList.get(position).getUrl()));
                question.setText(questionBeanList.get(position).getQuestion());
                item1.setText(questionBeanList.get(position).getItem1());
                item2.setText(questionBeanList.get(position).getItem2());
                item3.setText(questionBeanList.get(position).getItem3());
                item4.setText(questionBeanList.get(position).getItem4());
                answer.setText(questionBeanList.get(position).getAnswer());
                explains.setText(questionBeanList.get(position).getExplains());
                return view;
            }

            @Override
            public void destroyItem(ViewGroup container, int position, Object object) {
                container.removeView((View) object);
            }
        });
    }

    private void getJson() {
      //  tv=(TextView)findViewById(R.id.tv);
        mContext=this;
        //定义请求参数
        Parameters params=new Parameters();
        params.add("subject","1");
        params.add("model","c1");
        params.add("testType","rand");
        /**
         * 请求的方法 参数:
         * 第一个参数 当前请求的context
         * 第二个参数 接口id
         * 第三个参数 接口请求的url
         * 第四个参数 接口请求的方式
         * 第五个参数 接口请求的参数,键值对com.thinkland.sdk.android.Parameters类型;
         * 第六个参数 请求的回调方法,com.thinkland.sdk.android.DataCallBack;
         *
         */
        JuheData.executeWithAPI(mContext, 183, "http://api2.juheapi.com/jztk/query",
                JuheData.GET, params, new DataCallBack() {
                    @Override
                    public void onSuccess(int i, String s) {
                        try {
                            //创建json
                            JSONObject  json=new JSONObject(s);
                            //获取数组
                            JSONArray jsonArray=json.getJSONArray("result");
                            int length=jsonArray.length();
                           // tv.append("长度"+length);
                            //解析
                           for(int j=0;j<jsonArray.length();j++)   //i，j
                            {
                               JSONObject object=jsonArray.getJSONObject(j);  //看清i,j
//                              tv.append("question:"+object.getString("question")+" explains:"
//                                      +object.getString("explains")+"\n");
                                QuestionBean question=new QuestionBean(object.getString("url")
                                ,object.getString("question"),object.getString("item1"),object.getString("item2")
                                ,object.getString("item3"),object.getString("item4"),object.getString("answer")
                                ,object.getString("explains"));
                                questionBeanList.add(question);
                            }
                           // Log.e("-->",questionBeanList.size()+"长度");
                            //为ViewPager绑定适配器
                            viewPager.setAdapter(new PagerAdapter() {
                                @Override
                                public int getCount() {
                                    //Log.e("-->",""+questionBeanList.size());
                                    return questionBeanList.size();
                                }

                                @Override
                                public boolean isViewFromObject(View view, Object object) {
                                    return view==object;
                                }

                                @Override
                                public Object instantiateItem(ViewGroup container, final int position) {
                                    //获取新的布局
                                    View view=View.inflate(getApplicationContext(),R.layout.question,null);
                                    //取新的布局中的元素 要用view.findViewById否则会出现空指针异常
                                    //因为findViewById找的是setContentView里的主布局
                                    //主布局若想加载其他Layout布局，可在main.xml下定义<incloud layout="布局名称"/>
                                    final TextView exTV=(TextView)view.findViewById(R.id.ex_tv);
                                    final TextView TOrF=(TextView)view.findViewById(R.id.TOrF);
                                    final ImageView img=(ImageView)view.findViewById(R.id.imge);
                                    TextView question=(TextView)view.findViewById(R.id.question);
                                    RadioGroup radioGroup=(RadioGroup)view.findViewById(R.id.rg);
                                    final RadioButton item1=(RadioButton)view.findViewById(R.id.item1);
                                    final RadioButton item2=(RadioButton)view.findViewById(R.id.item2);
                                    final RadioButton item3=(RadioButton)view.findViewById(R.id.item3);
                                    RadioButton item4=(RadioButton)view.findViewById(R.id.item4);
                                    final TextView answer=(TextView)view.findViewById(R.id.answer);
                                    final TextView explains=(TextView)view.findViewById(R.id.explains);
                                    //Log.e("-->",questionBeanList.get(position).getUrl());
                                   // Log.e("-->",questionBeanList.get(position).getQuestion());
//                                    img.setImageURI(Uri.parse(questionBeanList.get(position).getUrl()));
                                    //图片读取
                                    if(questionBeanList.get(position).getUrl().equals("")) {
                                        //设置图片为不可见，且不占用空间
                                       img.setVisibility(View.GONE);
                                    }else {
                                        img.setVisibility(View.VISIBLE);
                                        //开启线程下载图片
                                        new Thread(new Runnable() {
                                            @Override
                                            public void run() {
                                                downloadBitmapFromServer(questionBeanList.get(position).getUrl(), img);
                                            }
                                        }).start();
                                    }
                                    //解析JSON并为布局绑定数据
                                    question.setText(questionBeanList.get(position).getQuestion());
                                  //  System.out.println("sss" + questionBeanList.get(position).getQuestion());
                                    item1.setText(questionBeanList.get(position).getItem1());
                                    item2.setText(questionBeanList.get(position).getItem2());
                                    item3.setText(questionBeanList.get(position).getItem3());
                                    item4.setText(questionBeanList.get(position).getItem4());
                                    answer.setText(questionBeanList.get(position).getAnswer());
                                    explains.setText(questionBeanList.get(position).getExplains());
                                    //设置监听 判断选项是否正确
                                    radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                                        @Override
                                        public void onCheckedChanged(RadioGroup group, int checkedId) {
                                            //checkedId判断的是地址
                                           if(checkedId==item1.getId()) {
                                               radioAnswer="1";
                                           }else if(checkedId==item2.getId()) {
                                               radioAnswer="2";
                                           }else  if(checkedId==item3.getId()) {
                                               radioAnswer="3";
                                           }else {
                                               radioAnswer="4";
                                           }
                                            if(radioAnswer.equals(questionBeanList.get(position).getAnswer()))
                                            {
                                                TOrF.setText("答案正确");
                                                TOrF.setTextColor(Color.DKGRAY);
                                                TOrF.setVisibility(View.VISIBLE);
                                            }
                                            else {
                                                TOrF.setText("答案错误");
                                                TOrF.setTextColor(Color.RED);
                                                TOrF.setVisibility(View.VISIBLE);
                                                exTV.setVisibility(View.VISIBLE);
                                                explains.setVisibility(View.VISIBLE);
                                            }
                                        }
                                    });
                                    //将view添加到布局中
                                    container.addView(view);
                                    return view;
                                }

                                @Override
                                public void destroyItem(ViewGroup container, int position, Object object) {
                                    container.removeView((View) object);
                                }
                            });
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                    @Override
                    public void onFinish() {
                        Toast.makeText(getApplicationContext(), "finish",Toast.LENGTH_SHORT).show();
                    }
                    @Override
                    public void onFailure(int i, String s, Throwable throwable) {
                        tv.append(throwable.getMessage() + "\n");
                    }
                });
    }

    //使用Handler来发送图片
    private Handler mHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
        }
    };

    //将url网络连接转换为图片
    public void downloadBitmapFromServer(String url, final ImageView imageView){
        imageView.setTag(url);
        InputStream is = null;
        try {
            //使用URL网络访问
            URL httpUrl = new URL(url);
            HttpURLConnection conn = (HttpURLConnection) httpUrl.openConnection();
            conn.setRequestMethod("GET");
            if (conn.getResponseCode() == 200){
                //使用流解析图片
                is =  conn.getInputStream();
                if (TextUtils.equals(url, imageView.getTag().toString())){
                    //使用Bitmap绘图
                    final Bitmap bitmap = BitmapFactory.decodeStream(is);
                    //Handler传递图片
                    mHandler.post(new Runnable() {
                        @Override
                        public void run() {
                            //为imagView绑定图片
                            imageView.setImageBitmap(bitmap);
                        }
                    });
                }
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            try {
                //关闭输入流
                if(is != null){
                    is.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
