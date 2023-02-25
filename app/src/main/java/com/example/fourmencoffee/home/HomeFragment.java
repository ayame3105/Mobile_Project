package com.example.fourmencoffee.home;

import static android.content.ContentValues.TAG;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.Toast;

import com.denzcoskun.imageslider.ImageSlider;
import com.denzcoskun.imageslider.constants.ScaleTypes;
import com.denzcoskun.imageslider.models.SlideModel;
import com.example.fourmencoffee.R;
import com.example.fourmencoffee.adapters.HomeAdapter;
import com.example.fourmencoffee.adapters.PopularAdapters;
import com.example.fourmencoffee.adapters.RecommendedAdapter;
import com.example.fourmencoffee.adapters.ViewAllAdapter;
import com.example.fourmencoffee.model.HomeCategory;
import com.example.fourmencoffee.model.PoppularModel;
import com.example.fourmencoffee.model.RecommendedModel;
import com.example.fourmencoffee.model.ViewAllModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;


public class HomeFragment extends Fragment {
    public static final String TAG_HOME_FRAGMENT = HomeFragment.class.getName();
    ScrollView scrollView;
    ProgressBar progressBar;
    //Slider show
    private ImageSlider imageSlider;
    //Search View
    EditText search_box;
    private  List<ViewAllModel> viewAllModelList;
    private RecyclerView recyclerViewSearch;
    private ViewAllAdapter viewAllAdapter;




    RecyclerView popularRec, homeCatRec, recommendedRec;
    FirebaseFirestore db;
    //popular items
    List<PoppularModel> poppularModelList;
    PopularAdapters popularAdapters;

    //Home category
    List<HomeCategory> categoryList;
    HomeAdapter homeAdapter;

    //Recommended items
    List<RecommendedModel> recommendedModelList;
    RecommendedAdapter recommendedAdapter;

    @Override
    public void onResume() {
        super.onResume();
    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        View root = inflater.inflate(R.layout.fragment_home, container, false);
        db= FirebaseFirestore.getInstance();

        popularRec = root.findViewById(R.id.pop_xem_them_pho_bien);
        homeCatRec = root.findViewById(R.id.pop_xem_them_kham_pha);
        recommendedRec = root.findViewById(R.id.pop_xem_them_de_xuat);
        scrollView = root.findViewById(R.id.scroll_view_home_fragment);
        progressBar = root.findViewById(R.id.progressbar);

        progressBar.setVisibility(View.VISIBLE);
        scrollView.setVisibility(View.GONE);
        BottomNavigationView navigationView = getActivity().findViewById(R.id.bottomNavigationView);
        popularRec.setOnTouchListener(new TranslateAnimationUtil(getActivity(), navigationView));
        recommendedRec.setOnTouchListener(new TranslateAnimationUtil(getActivity(), navigationView));
        //Slider
        imageSlider = root.findViewById(R.id.image_slider);
        ArrayList<SlideModel> slideModels = new ArrayList<>();
        slideModels.add(new SlideModel("https://i.imgur.com/yiEqWjQ.jpeg", ScaleTypes.FIT));
        slideModels.add(new SlideModel("https://i.imgur.com/xq2yywM.jpeg", ScaleTypes.FIT));
        slideModels.add(new SlideModel("https://i.imgur.com/Z2FKWqz.jpeg", ScaleTypes.FIT));
        slideModels.add(new SlideModel("https://theme.hstatic.net/200000309869/1000702189/14/slideshow_2.jpg", ScaleTypes.FIT));
        slideModels.add(new SlideModel("https://file.hstatic.net/200000309869/article/ed175835325ff901a04e_1bca649603d04e64b2fa64509186fa07_1024x1024.jpg", ScaleTypes.FIT));
        slideModels.add(new SlideModel("https://file.hstatic.net/200000309869/article/8395feb1f363383d6172_c471461bfcbd438d842ef2859b4c1375_1024x1024.jpg", ScaleTypes.FIT));
        imageSlider.setImageList(slideModels, ScaleTypes.FIT);

        //Popular item
        popularRec.setLayoutManager(new LinearLayoutManager(getActivity(),RecyclerView.HORIZONTAL,false));
        poppularModelList = new ArrayList<>();
        popularAdapters = new PopularAdapters(getActivity(),poppularModelList);
        popularRec.setAdapter(popularAdapters);

        db.collection("PoppularPtoducts")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                PoppularModel poppularModel = document.toObject(PoppularModel.class);
                                poppularModelList.add(poppularModel);
                                popularAdapters.notifyDataSetChanged();

                                progressBar.setVisibility(View.GONE);
                                scrollView.setVisibility(View.VISIBLE);
                            }
                        } else {
                            Toast.makeText(getActivity(),"Lỗi",Toast.LENGTH_SHORT).show();
                        }
                    }
                });

        //Home Category
        homeCatRec.setLayoutManager(new LinearLayoutManager(getActivity(),RecyclerView.HORIZONTAL,false));
        categoryList = new ArrayList<>();
        homeAdapter = new HomeAdapter(getActivity(),categoryList);
        homeCatRec.setAdapter(homeAdapter);

        db.collection("HomeCategory")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                HomeCategory homeCategory = document.toObject(HomeCategory.class);
                                categoryList.add(homeCategory);
                                homeAdapter.notifyDataSetChanged();
                            }
                        } else {
                            Toast.makeText(getActivity(),"Lỗi",Toast.LENGTH_SHORT).show();
                        }
                    }
                });

        //Recommended
        StaggeredGridLayoutManager staggeredGridLayoutManager = new StaggeredGridLayoutManager(2,
                StaggeredGridLayoutManager.VERTICAL);
        recommendedRec.setLayoutManager(new StaggeredGridLayoutManager(2,
                StaggeredGridLayoutManager.VERTICAL));

        recommendedModelList = new ArrayList<>();
        recommendedAdapter = new RecommendedAdapter(getActivity(),recommendedModelList);
        recommendedRec.setAdapter(recommendedAdapter);

        db.collection("Recommended")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                RecommendedModel recommendedModel = document.toObject(RecommendedModel.class);
                                recommendedModelList.add(recommendedModel);
                                recommendedAdapter.notifyDataSetChanged();
                            }
                        } else {
                            Toast.makeText(getActivity(),"Lỗi",Toast.LENGTH_SHORT).show();
                        }
                    }
                });

        recyclerViewSearch = root.findViewById(R.id.search_rec);
        search_box = root.findViewById(R.id.search_box);

        viewAllModelList = new ArrayList<>();
        viewAllAdapter = new ViewAllAdapter(getContext(),viewAllModelList);
        recyclerViewSearch.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerViewSearch.setAdapter(viewAllAdapter);
        recyclerViewSearch.setHasFixedSize(true);

        search_box.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.toString().isEmpty())
                {
                    viewAllModelList.clear();
                    viewAllAdapter.notifyDataSetChanged();
                }
                else
                {
                    searchProduct(s.toString());

                }
            }
        });














        return root;
    }

    private void searchProduct(String type) {
        if (!type.isEmpty())
        {
            db.collection("AllProducts").whereEqualTo("type",type).get()
                    .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                            if (task.isSuccessful() && task.getResult() != null)
                            {
                                viewAllModelList.clear();
                                viewAllAdapter.notifyDataSetChanged();
                                for (DocumentSnapshot doc : task.getResult().getDocuments())
                                {
                                    ViewAllModel viewAllModel = doc.toObject(ViewAllModel.class);
                                    viewAllModelList.add(viewAllModel);
                                    viewAllAdapter.notifyDataSetChanged();
                                }
                            }
                        }
                    });
        }
    }
}