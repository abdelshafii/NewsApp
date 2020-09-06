package com.example.selfapp.ui.main;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.selfapp.ui.main.Utils;
import com.example.selfapp.data.ApiClient;
import com.example.selfapp.data.Interface;
import com.example.selfapp.pojo.Article;
import com.example.selfapp.pojo.NewsModel;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class NewsViewModel extends ViewModel {
    public MutableLiveData<List<Article>> mutableLiveData = new MutableLiveData<List<Article>>();
    public List<Article> articles = new ArrayList<>();

    public void getNewsSearch(final String keyWord){
        Interface anInterface = ApiClient.getApiClient().create(Interface.class);
        String country= Utils.getCountry();
        String language=Utils.getLanguage();
        Call<NewsModel> call;
        if (keyWord.length() > 0){
            call=anInterface.getNewsSearch(keyWord,language,"publishedAt",ApiClient.API_KEY);
            call.enqueue(new Callback<NewsModel>() {
                @Override
                public void onResponse(Call<NewsModel> call, Response<NewsModel> response) {
                    if(response.isSuccessful()&&response.body().getArticle()!=null){
                        if(!articles.isEmpty()){
                            articles.clear();
                        }
                        articles=response.body().getArticle();
                        mutableLiveData.setValue(Collections.unmodifiableList(articles));
                    }
                }

                @Override
                public void onFailure(Call<NewsModel> call, Throwable t) {

                }
            });
        }else{
            call=anInterface.getNews(country,ApiClient.API_KEY);
            call.enqueue(new Callback<NewsModel>() {
                @Override
                public void onResponse(Call<NewsModel> call, Response<NewsModel> response) {
                    if(response.isSuccessful()&&response.body().getArticle()!=null){
                        if(!articles.isEmpty()){
                            articles.clear();
                        }
                        articles=response.body().getArticle();
                        mutableLiveData.setValue(Collections.unmodifiableList(articles));
                    }
                }

                @Override
                public void onFailure(Call<NewsModel> call, Throwable t) {

                }
            });
        }

    }
}