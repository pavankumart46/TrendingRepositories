package pavankreddy.blogspot.com;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.annotation.SuppressLint;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;
import javax.net.ssl.HttpsURLConnection;
import pavankreddy.blogspot.com.models.TrendingRepo;

public class MainActivity extends AppCompatActivity {

    private ProgressBar progressBar;
    private RecyclerView recyclerView;
    private static final String GITHUB_TRENDING_REPO_URL = "https://github-trending-api.now.sh/repositories";
    private String json_data = null;
    private LinearLayoutManager llm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        progressBar = findViewById(R.id.progressBar);
        recyclerView = findViewById(R.id.recyclerview);
        progressBar.setVisibility(View.VISIBLE);
        llm = new LinearLayoutManager(this);
        if(savedInstanceState!=null){
          if(savedInstanceState.containsKey(getResources().getString(R.string.KEY1))){
              json_data = savedInstanceState.getString(getResources().getString(R.string.KEY1));
              int pos = savedInstanceState.getInt(getResources().getString(R.string.KEY2));
              List<TrendingRepo> trendingRepos = parseJson(json_data);
              SetDataOnRecyclerView(trendingRepos);
              llm.scrollToPosition(pos);
          }
        }
        else {
            new LoadData().execute();
        }

    }

    private void SetDataOnRecyclerView(List<TrendingRepo> trendingRepos) {
        progressBar.setVisibility(View.INVISIBLE);
        RecyclerviewAdapter rva = new RecyclerviewAdapter(this,trendingRepos);
        recyclerView.setLayoutManager(llm);
        recyclerView.setAdapter(rva);
        recyclerView.addItemDecoration(new DividerItemDecoration(recyclerView.getContext(), DividerItemDecoration.VERTICAL));
    }

    public static List<TrendingRepo> parseJson(String s){
        List<TrendingRepo> trendingRepos;
        Type listType = new TypeToken<List<TrendingRepo>>(){}.getType();
        trendingRepos = new Gson().fromJson(s,listType);
        return trendingRepos;
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        if(json_data!=null){
            outState.putString(getResources().getString(R.string.KEY1),json_data);
            int recycler_position = llm.findFirstVisibleItemPosition();
            outState.putInt(getResources().getString(R.string.KEY2),recycler_position);
        }
    }

    @SuppressLint("StaticFieldLeak")
    public class LoadData extends AsyncTask<String, Void, String>{

        @Override
        protected String doInBackground(String... strings) {
            try {
                URL url = new URL(GITHUB_TRENDING_REPO_URL);
                HttpsURLConnection httpsURLConnection = (HttpsURLConnection) url.openConnection();
                InputStream inputStream = httpsURLConnection.getInputStream();
                InputStreamReader isr = new InputStreamReader(inputStream);
                BufferedReader br = new BufferedReader(isr);
                String line;
                StringBuilder stringBuilder = new StringBuilder();
                while((line = br.readLine())!=null){
                    stringBuilder.append(line);
                }
                return stringBuilder.toString();
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            json_data = s;
            List<TrendingRepo> trendingRepos = parseJson(s);
            SetDataOnRecyclerView(trendingRepos);
        }
    }
}
