package com.dim.rxjavaej;

import androidx.appcompat.app.AppCompatActivity;
import rx.Observer;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.dim.rxjavaej.controller.GitHubClient;
import com.dim.rxjavaej.controller.GitHubRepoAdapter;
import com.dim.rxjavaej.model.GitHubRepo;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getSimpleName();
    private GitHubRepoAdapter adapter = new GitHubRepoAdapter();
    private Subscription subscription;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final ListView listView = (ListView)findViewById(R.id.list_view_repos);
        listView.setAdapter(adapter);

        final EditText editTextUserName = (EditText)findViewById(R.id.edit_text_username);
        final Button buttonSearch = (Button)findViewById(R.id.button_search);
        buttonSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String userName = editTextUserName.getText().toString();
                if(!TextUtils.isEmpty(userName)){
                    getStarredRepos(userName);
                    editTextUserName.setText("");
                }
            }
        });
    }

    @Override
    protected  void onDestroy(){
        if(subscription != null && !subscription.isUnsubscribed()){
            subscription.unsubscribe();
        }
        super.onDestroy();
    }

    private void getStarredRepos(String username){
        subscription = GitHubClient.getInstance()
                .getStarredRepos(username)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<List<GitHubRepo>>() {
                    @Override
                    public void onCompleted() {
                        Log.d(TAG, "In onCompleted()");
                    }

                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();
                        Log.d(TAG, "In onError()");
                    }

                    @Override
                    public void onNext(List<GitHubRepo> gitHubRepos) {
                        Log.d(TAG, "In onNext()");
                        adapter.setGitHubRepos(gitHubRepos);
                    }
                });
    }
}