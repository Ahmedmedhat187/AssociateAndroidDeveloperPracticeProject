package com.ahmed.androiddeveloperpracticeproject.ui.fragments;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;
import com.ahmed.androiddeveloperpracticeproject.R;
import com.ahmed.androiddeveloperpracticeproject.adapters.RecyclerViewAdapter;
import com.ahmed.androiddeveloperpracticeproject.api.RetrofitClient;
import com.ahmed.androiddeveloperpracticeproject.models.LearnerModel;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FragmentLearningLeaders extends Fragment implements SwipeRefreshLayout.OnRefreshListener {

    SwipeRefreshLayout refreshLayout;
    RecyclerView recyclerView;
    ProgressBar progressBar;
    TextView emptyView;

    RecyclerViewAdapter adapter;
    List<LearnerModel> learningLeaders = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_learning_leaders, container, false);

        refreshLayout = view.findViewById(R.id.refreshLayout);
        refreshLayout.setOnRefreshListener(this);

        progressBar = view.findViewById(R.id.progressBar);
        emptyView = view.findViewById(R.id.empty_view);
        recyclerView = view.findViewById(R.id.recyclerView);

        adapter = new RecyclerViewAdapter(getContext());
        recyclerView.setAdapter(adapter);

        return view;
    }


    @Override
    public void onResume() {
        super.onResume();
        getAllLearningLeaders();
    }

    @Override
    public void onRefresh() {
        getAllLearningLeaders();
        refreshLayout.setRefreshing(false);
    }

    public void getAllLearningLeaders(){
        recyclerView.setVisibility(View.GONE);
        progressBar.setVisibility(View.VISIBLE);

        Call<List<LearnerModel>> learningLeadersCall = RetrofitClient.getInterface().getLearningLeaders();
        learningLeadersCall.enqueue(new Callback<List<LearnerModel>>() {
            @Override
            public void onResponse(Call<List<LearnerModel>> call, Response<List<LearnerModel>> response) {

                progressBar.setVisibility(View.GONE);

                if(response.isSuccessful()){
                    learningLeaders = response.body();

                    if (learningLeaders.isEmpty()){
                        recyclerView.setVisibility(View.GONE);
                        emptyView.setVisibility(View.VISIBLE);
                        emptyView.setText(R.string.no_data_available);
                    } else {
                        emptyView.setVisibility(View.GONE);
                        recyclerView.setVisibility(View.VISIBLE);
                        adapter.updateLearnerList(learningLeaders);
                    }
                }
                else{
                    recyclerView.setVisibility(View.GONE);
                    emptyView.setVisibility(View.VISIBLE);
                    emptyView.setText(R.string.failed_to_get_learning_leaders);
                }
            }
            @Override
            public void onFailure(Call<List<LearnerModel>> call, Throwable t) {
                progressBar.setVisibility(View.GONE);

                if (t instanceof IOException){
                    recyclerView.setVisibility(View.GONE);
                    emptyView.setVisibility(View.VISIBLE);
                    emptyView.setText(R.string.connection_error);
                }
                else{
                    recyclerView.setVisibility(View.GONE);
                    emptyView.setVisibility(View.VISIBLE);
                    emptyView.setText(R.string.failed_to_get_learning_leaders);
                }
            }
        });
    }

}