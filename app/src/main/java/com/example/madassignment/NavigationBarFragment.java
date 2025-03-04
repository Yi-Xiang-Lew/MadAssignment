package com.example.madassignment;

import android.graphics.Color;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageButton;
import android.widget.ImageView;


public class NavigationBarFragment extends Fragment {

    private ImageButton settingsButton, backButton;
    private ImageView menuTitle;
    private GameData gameData;

    private  CreateUser userModel;
    private EditUser editUserModel;

    NavigationData navigationData;

    public NavigationBarFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        navigationData = new ViewModelProvider(getActivity()).get(NavigationData.class);
        editUserModel = new ViewModelProvider(getActivity()).get(EditUser.class);
        userModel = new ViewModelProvider(getActivity()).get(CreateUser.class);
        gameData = new ViewModelProvider(getActivity()).get(GameData.class);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_navigation_bar, container, false);
        NavigationData navigationData = new ViewModelProvider(getActivity()).get(NavigationData.class);

        backButton = view.findViewById(R.id.backButton);
        settingsButton = view.findViewById(R.id.settingsButton);

        /* The navigation integers describe the following fragments:
            navigationData == 0 -> Menu Fragment
            navigationData == 1 -> Board Fragment
            navigationData == 2 -> Settings Fragment
            navigationData == 3 -> Profile Fragment
            navigationData == 4 -> Leaderboard Fragment
            navigationData == 5 -> User Select Fragment
            navigationData == 99 -> Menu Animation Fragment

         */

        backButton = view.findViewById(R.id.backButton);
        settingsButton = view.findViewById(R.id.settingsButton);
        menuTitle = view.findViewById(R.id.menuTitle);

        settingsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Starts the undo button animation - updated by Ryan
                Animation settings = AnimationUtils.loadAnimation(getActivity(),R.anim.settings_rotation_anim);
                settingsButton.startAnimation(settings);

                navigationData.setHistoricalClickedValue(navigationData.getClickedValue());
                navigationData.setClickedValue(2);

            }
        });
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Animation back = AnimationUtils.loadAnimation(getActivity(),R.anim.back_anim);

                // Animation listener for pressing back button
                back.setAnimationListener(new Animation.AnimationListener() {
                    @Override
                    public void onAnimationStart(Animation animation) {
                        // Animation started - Changes colour of the button to show it has been pressed
                        int cyan = Color.CYAN;
                        backButton.setColorFilter(cyan);
                    }

                    @Override
                    public void onAnimationEnd(Animation animation) {
                        // Animation ended - removes colour filter at the end of the animation
                        backButton.setColorFilter(null);
                    }

                    @Override
                    public void onAnimationRepeat(Animation animation) {
                        // Can be left empty as no repeats are occurring
                    }
                });

                backButton.startAnimation(back);

                if(navigationData.getClickedValue() == 1) {
                    // If we are on the Board Fragment, take us back to the Menu Fragment
                    navigationData.setClickedValue(0);
                }
                if (navigationData.getClickedValue() == 2){
                    if (navigationData.getHistoricalClickedValue() != 3) {
                        gameData.setNeedSaveGameState(true);
                        System.out.println(Boolean.toString(gameData.getNeedSaveGameState()));
                        navigationData.setClickedValue(1);
                    }
                    else {
                        navigationData.setClickedValue(navigationData.getHistoricalClickedValue());
                    }
                }
                else if (navigationData.getClickedValue() == 3) {
                    //restore state
                    userModel.setUserIcon(0);
                    userModel.setUserName("");
                    editUserModel.setDeleteUserId(0L);
                    editUserModel.setDeleteUserPosition(0);
                    editUserModel.setUserIcon(0);
                    editUserModel.setUserName("");
                    editUserModel.setUserId(0L);
                    navigationData.setClickedValue(navigationData.getHistoricalClickedValue());
                    if (navigationData.getHistoricalClickedValue() == 1) {
                        gameData.setNeedSaveGameState(true);
                        System.out.println(Boolean.toString(gameData.getNeedSaveGameState()));
                    }
                }
                else if (navigationData.getClickedValue() == 4) {
                    navigationData.setClickedValue(navigationData.getHistoricalClickedValue());
                    // TODO: Determine if the user is already in a game and if so go to BOARD fragment otherwise MENU fragment

                }
                else if (navigationData.getClickedValue() == 5) {
                    // If we are on the User Select Fragment, take us back to the Menu Fragment
                    navigationData.setClickedValue(navigationData.getHistoricalClickedValue());
                    navigationData.setClickedValue(1);
                }

                else if (navigationData.getClickedValue() == 6) {
                    navigationData.setClickedValue(2);

                }
            }

        });

        navigationData.clickedValue.observe(getActivity(), new Observer<Integer>() {
            @Override
            public void onChanged(Integer integer) {
                switch(integer) {
                    case 0:
                        settingsButton.setVisibility(View.VISIBLE);
                        backButton.setVisibility(View.GONE);
                        menuTitle.setVisibility(View.VISIBLE);
                        break;
                    case 1:
                        settingsButton.setVisibility(View.VISIBLE);
                        backButton.setVisibility(View.VISIBLE);
                        menuTitle.setVisibility(View.VISIBLE);
                        break;
                    case 2:
                        settingsButton.setVisibility(View.GONE);
                        backButton.setVisibility(View.VISIBLE);
                        menuTitle.setVisibility(View.VISIBLE);
                        break;
                    case 3:
                        settingsButton.setVisibility(View.GONE);
                        backButton.setVisibility(View.VISIBLE);
                        menuTitle.setVisibility(View.VISIBLE);
                        break;
                    case 4:
                        settingsButton.setVisibility(View.GONE);
                        backButton.setVisibility(View.VISIBLE);
                        menuTitle.setVisibility(View.VISIBLE);
                        break;
                    case 5:
                        settingsButton.setVisibility(View.VISIBLE);
                        backButton.setVisibility(View.VISIBLE);
                        menuTitle.setVisibility(View.VISIBLE);
                        break;
                    case 99:
                        settingsButton.setVisibility(View.GONE);
                        backButton.setVisibility(View.GONE);
                        menuTitle.setVisibility(View.GONE);
                        break;
                }

            }
        });
        return view;
    }
}