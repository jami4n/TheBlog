package com.jamian.theblog;

import android.animation.Animator;
import android.animation.AnimatorInflater;
import android.app.Dialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.AnimationUtils;
import android.view.animation.RotateAnimation;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.jamian.theblog.ZomatoData.Data;
import com.jamian.theblog.ZomatoData.Restaurant;
import com.jamian.theblog.ZomatoData.RestaurantObj;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    LinearLayout ll_content_board,ll_content_board_data;
    TextView tv_next,tv_previous;
    ImageView img_article_image;

    TextView txt_article_title,txt_article_desc;

    ArrayList<Pojo_Blog_Article> articlesList = new ArrayList<>();

    int currentArticleNumber = 0;
    int articleListSize;

    private float x1,x2;
    static final int MIN_DISTANCE = 300;

    TextView tv_app_title;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setBlogArticles();
        articleListSize = articlesList.size();

        ll_content_board = (LinearLayout) findViewById(R.id.ll_content_board);
        ll_content_board_data = (LinearLayout) findViewById(R.id.ll_content_board_data);

        img_article_image = (ImageView) findViewById(R.id.img_article_image);

        tv_next = (TextView) findViewById(R.id.tv_next);
        tv_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switchToNextBoard();
            }
        });

        tv_previous = (TextView) findViewById(R.id.tv_previous);
        tv_previous.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switchToPrevBoard();
            }
        });

        txt_article_title = (TextView) findViewById(R.id.txt_article_title);
        txt_article_desc = (TextView) findViewById(R.id.txt_article_desc);

        setFirstBoard();
        //GetDataFromZomato("persian");

        tv_app_title = (TextView) findViewById(R.id.tv_app_title);



    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {

        if(hasFocus){

            Animation anim = new RotateAnimation(0,-270,tv_app_title.getWidth(),0);
            anim.setDuration(1);
            anim.setFillAfter(true);



            tv_app_title.startAnimation(anim);
        }


    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch(event.getAction())
        {
            case MotionEvent.ACTION_DOWN:
                x1 = event.getX();
                break;
            case MotionEvent.ACTION_UP:
                x2 = event.getX();
                float deltaX = x2 - x1;
                if (Math.abs(deltaX) > MIN_DISTANCE)
                {
                    if (x2 > x1)
                    {
                        //Toast.makeText(this, "Left to Right swipe [Next]", Toast.LENGTH_SHORT).show ();
                        switchToNextBoard();
                    }

                    // Right to left swipe action
                    else
                    {
                        //Toast.makeText(this, "Right to Left swipe [Previous]", Toast.LENGTH_SHORT).show ();
                        switchToPrevBoard();
                    }

                }
                else
                {
                    //Toast.makeText(this, "Right to left", Toast.LENGTH_SHORT).show();
                    //getNextBoardContent();
                }
                break;
        }
        return super.onTouchEvent(event);
    }

    private void switchToPrevBoard() {

        Animation slide_out_left = AnimationUtils.loadAnimation(this,R.anim.slide_out_left);
        img_article_image.startAnimation(slide_out_left);


        Animator fade_out = AnimatorInflater.loadAnimator(getApplicationContext(),R.animator.board_content_fade_out);
        fade_out.setTarget(ll_content_board_data);

        final Animator flip_out_left = AnimatorInflater.loadAnimator(getApplicationContext(),R.animator.flip_out_left);
        flip_out_left.setTarget(ll_content_board);

        flip_out_left.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {
                getPrevBoardContent();
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });

        flip_out_left.start();
        fade_out.start();


    }

    private void getPrevBoardContent() {
        currentArticleNumber--;
        if(currentArticleNumber < 0){
            currentArticleNumber = articleListSize;
            currentArticleNumber--;
        }


        Glide.with(img_article_image.getContext()).load(articlesList.get(currentArticleNumber).getImageurl()).placeholder(R.drawable.placehole).into(img_article_image);


        txt_article_title.setText(articlesList.get(currentArticleNumber).getTitle());
        txt_article_desc.setText(articlesList.get(currentArticleNumber).getShortdesc());

        Animation slide_in = AnimationUtils.loadAnimation(this,R.anim.slide_in_right);
        img_article_image.startAnimation(slide_in);


    }

    private void GetDataFromZomato(String query) {
        Retrofit_Endpoint endpoint = Retro_Client.getClient().create(Retrofit_Endpoint.class);
        Call<Data> call = endpoint.getRestaurantsBySearch("3","city",query);

        String temp = call.request().header("user-key");
        Log.d("12345", "GetDataFromZomato: "+ temp );


        call.enqueue(new Callback<Data>() {
            @Override
            public void onResponse(Call<Data> call, Response<Data> response) {
                //Toast.makeText(MainActivity.this, "Success", Toast.LENGTH_SHORT).show();

                Log.d("12345",response.code() + "");
                Log.d("12345",response.message() + "");

                Log.d("12345", "onResponse: " +  response.body().getResults_shown());
                //Log.d("12345", "onResponse: " +  response.body().getRestaurants().get(0).getRestaurant().getName());
                int results =  Integer.parseInt(response.body().getResults_shown());

                if(results > 0){

                    Pojo_Blog_Article article;
                    articlesList.clear();

                    for(RestaurantObj x : response.body().getRestaurants()){

                        article = new Pojo_Blog_Article(x.getRestaurant().getThumb(),
                        x.getRestaurant().getName() + " - "  + x.getRestaurant().getLocation().getLocality(),
                        x.getRestaurant().getLocation().getAddress(),
                        x.getRestaurant().getLocation().getAddress());

                        articlesList.add(article);
                    }

                    articleListSize = articlesList.size();
                    setFirstBoard();

                }else{
                    Toast.makeText(MainActivity.this, "No results found", Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onFailure(Call<Data> call, Throwable t) {
                Toast.makeText(MainActivity.this, "Connection error", Toast.LENGTH_SHORT).show();

                Log.d("12345", "onFailure: " + t.toString());
            }
        });


    }


    private void setFirstBoard() {
        //Glide.with(this).load("https://static.independent.co.uk/s3fs-public/styles/story_large/public/thumbnails/image/2017/01/18/10/google.jpg").placeholder(R.drawable.pizza).into(img_article_image);

        //img_article_image = (ImageView) findViewById(R.id.img_article_image);

        Glide.with(img_article_image.getContext()).load(articlesList.get(0).getImageurl()).placeholder(R.drawable.placehole).into(img_article_image);


        txt_article_title.setText(articlesList.get(0).getTitle());
        txt_article_desc.setText(articlesList.get(0).getShortdesc());
    }



    private void switchToNextBoard() {
        Animation slide_out = AnimationUtils.loadAnimation(this,R.anim.slide_out_right);
        img_article_image.startAnimation(slide_out);


        Animator fade_out = AnimatorInflater.loadAnimator(getApplicationContext(),R.animator.board_content_fade_out);
        fade_out.setTarget(ll_content_board_data);

        final Animator flip_out_right = AnimatorInflater.loadAnimator(getApplicationContext(),R.animator.flip_out_right);
        flip_out_right.setTarget(ll_content_board);

        flip_out_right.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {
                getNextBoardContent();
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });

        flip_out_right.start();
        fade_out.start();




//        AnimatorSet animSet = new AnimatorSet();
//        Animator flip_out_righter = AnimatorInflater.loadAnimator(getApplicationContext(),R.animator.flip_out_right);
//        Animator flip_in_lefter = AnimatorInflater.loadAnimator(getApplicationContext(),R.animator.flip_out_left);
//        animSet.playSequentially(flip_out_righter);
//        animSet.setTarget(ll_content_board);
//        animSet.start();

    }

    private void getNextBoardContent() {
        currentArticleNumber++;
        if(currentArticleNumber >= articleListSize){
            currentArticleNumber = 0;
        }

        Glide.with(img_article_image.getContext()).load(articlesList.get(currentArticleNumber).getImageurl()).placeholder(R.drawable.placehole).into(img_article_image);

        txt_article_title.setText(articlesList.get(currentArticleNumber).getTitle());
        txt_article_desc.setText(articlesList.get(currentArticleNumber).getShortdesc());

        Animation slide_in = AnimationUtils.loadAnimation(this,R.anim.slide_in_left);
        img_article_image.startAnimation(slide_in);
    }

    private void setBlogArticles(){
        Pojo_Blog_Article article = new Pojo_Blog_Article("https://s-media-cache-ak0.pinimg.com/564x/61/db/a7/61dba7e7af77dc574655482310209bd3.jpg",
                "Traditional Italian Food: As Diverse and Tempting As Italy’s 20 Regions",
                "Italian cuisine is a general term to designate the whole set of each regional Italian cuisine practiced in each of the twenty, distinct Italian regions.",
                "Italian cuisine is a general term to designate the whole set of each regional Italian cuisine practiced in each of the twenty, distinct Italian regions.");

        articlesList.add(article);

        article = new Pojo_Blog_Article("https://s-media-cache-ak0.pinimg.com/236x/33/66/20/3366204fab51ea21beb67e69607a7ef4.jpg",
                "Definitive Proof That Doughnuts Are The New Cupcake.",
                "Remember when cupcakes were a big deal? Well, that’s all about to be over, because doughnuts have arrived.",
                "Remember when cupcakes were a big deal? Well, that’s all about to be over, because doughnuts have arrived.");

        articlesList.add(article);



        article = new Pojo_Blog_Article("https://s-media-cache-ak0.pinimg.com/564x/d0/02/2c/d0022c949234d77fea684df580859e3d.jpg",
                "How the French Macaron Became American.",
                "The meringue-y sandwich cookies—airy, dainty, gluten-free, and high-maintenance—are \"the new cupcake\" the nation has been waiting for.",
                "The meringue-y sandwich cookies—airy, dainty, gluten-free, and high-maintenance—are \"the new cupcake\" the nation has been waiting for.");

        articlesList.add(article);


        article = new Pojo_Blog_Article("https://s-media-cache-ak0.pinimg.com/236x/fe/84/1a/fe841a9e68fc3e89e65c7c8dadcbdcbd.jpg",
                "INSALATA PRIMAVERILE DI PATATE NOVELLE, PISELLI, ASPARAGI E ANETO",
                "Se ci frequentate da un po’ sicuramente lo avrete capito: ogni quattro o cinque post non resistiamo e ci sembra quasi obbligatorio postare un’insalata.",
                "Se ci frequentate da un po’ sicuramente lo avrete capito: ogni quattro o cinque post non resistiamo e ci sembra quasi obbligatorio postare un’insalata.");

        articlesList.add(article);


        article = new Pojo_Blog_Article("https://s-media-cache-ak0.pinimg.com/236x/6d/6e/6d/6d6e6d93230c4fd404e40f82de6e5929.jpg",
                "8 Reasons To Eat Blueberries and Why You Should Freeze Them!",
                "Graduate student Marin Plumb, found that frozen blueberries are equally nutritious as fresh blueberries, even after six months of freezing.",
                "Graduate student Marin Plumb, found that frozen blueberries are equally nutritious as fresh blueberries, even after six months of freezing.");

        articlesList.add(article);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id =  item.getItemId();
        if(id == R.id.menu_search){
            showSearchDialog();

        }

        return super.onOptionsItemSelected(item);
    }

    private void showSearchDialog() {
        final Dialog dialog = new Dialog(MainActivity.this);
        dialog.setContentView(R.layout.search_dialog);



        final EditText et_search = (EditText) dialog.findViewById(R.id.et_search_query);
        Button btn_search = (Button) dialog.findViewById(R.id.btn_search_res);

        dialog.show();

        btn_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String search = et_search.getText().toString();

                if(search.isEmpty()){
                    Toast.makeText(MainActivity.this, "Enter a search query", Toast.LENGTH_SHORT).show();
                }else{
                    GetDataFromZomato(search);
                    dialog.dismiss();
                }


            }
        });

    }


}
