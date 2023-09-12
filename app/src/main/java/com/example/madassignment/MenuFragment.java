package com.example.madassignment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.ImageButton;
import android.widget.ImageView;

public class MenuFragment extends Fragment {

    // Define UI Components
    private ImageButton aiButton;
    private ImageButton playerButton;
    private ImageView menuTitle;
    private ImageView lightSpot1;
    private ImageView lightSpot2;
    private ImageView lightSpot3;
    private ImageView lightSpot4;

    private ImageButton winConditionLeft;
    private ImageButton winConditionRight;

    private ImageButton boardSizeLeft;
    private ImageButton boardSizeRight;
    private ImageView winImageContainer;

    private ImageView boardImageContainer;

    // Define ViewModels
    private NavigationData navModel;

    private GameData gameData;

    public MenuFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        navModel = new ViewModelProvider(getActivity()).get(NavigationData.class);
        gameData = new ViewModelProvider(getActivity()).get(GameData.class);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_menu, container, false);

        // Define Buttons
        winConditionLeft = view.findViewById(R.id.left_win_button);
        winConditionRight= view.findViewById(R.id.right_win_button);
        boardSizeRight = view.findViewById(R.id.right_board_button);
        boardSizeLeft = view.findViewById(R.id.left_board_button);
        aiButton = view.findViewById(R.id.aiButton);
        playerButton = view.findViewById(R.id.playerButton);
        menuTitle = view.findViewById(R.id.menuTitle);
        lightSpot1 = view.findViewById(R.id.lightSpot1);
        lightSpot2 = view.findViewById(R.id.lightSpot2);
        lightSpot3 = view.findViewById(R.id.lightSpot3);
        lightSpot4 = view.findViewById(R.id.lightSpot4);

        //define images
        winImageContainer = view.findViewById(R.id.win_condition);
        boardImageContainer = view.findViewById(R.id.board_size);

        fadeAnimation(lightSpot1, 1);
        fadeAnimation(lightSpot2, 0);
        fadeAnimation(lightSpot3, 0);
        fadeAnimation(lightSpot4, 1);

        if(navModel.getAnimationClickedValue() == 0) {
            navModel.setClickedValue(99);
        }

        /* Both AI_Button and Player_Button currently only direct to the board fragment
            this is to be changed when the backend for the AI & player back-end is added.
         */
        aiButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                navModel.setClickedValue(1);
                gameData.setGameMode(1);
            }
        });

        playerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                navModel.setClickedValue(1);
                gameData.setGameMode(2);

            }
        });

        winConditionLeft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                handleWinConditionClick(-1);
            }
        });

        winConditionRight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                handleWinConditionClick(1);
            }
        });

        boardSizeLeft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                handleBoardSizeClick(-1);
            }
        });


        boardSizeRight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                handleBoardSizeClick(1);
            }
        });


        return view;
    }

    public void fadeAnimation(ImageView light_spot, int offset) {
        // Create a fade-in animation
        // Offset integer allows for variability in light spot fade behaviour
        AlphaAnimation fadeIn = new AlphaAnimation(0.0f, 1.0f);
        if (offset == 1) {
            fadeIn.setDuration(1500); // Fade in after 0.5 seconds
        }
        else {
            fadeIn.setDuration(1000); // Fade in after 1 second
        }
        fadeIn.setFillAfter(true);

        // Create a fade-out animation
        AlphaAnimation fadeOut = new AlphaAnimation(1.0f, 0.0f);
        if (offset == 1) {
            fadeOut.setStartOffset(1500);
            fadeOut.setDuration(1500); // Fade out after 0.5 seconds
        }
        else {
            fadeOut.setStartOffset(1000);
            fadeOut.setDuration(1000); // Fade out after 1 second
        }
        fadeOut.setFillAfter(true);

        fadeOut.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                // Restart the specific animation within the set
                light_spot.startAnimation(fadeIn);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });

        fadeIn.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                // Restart the specific animation within the set
                light_spot.startAnimation(fadeOut);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });

        light_spot.startAnimation(fadeIn);
    }

    public void handleWinConditionClick(int direction ){
        int winCondition = gameData.getWinCondition();
        if(direction == -1) {
            System.out.println("The left button is clicked");
        }
        else {
            System.out.println("The right button is clicked");
        }
        winCondition += direction;
        gameData.setWinCondition(winCondition);

        switch (winCondition) {
            case 3:
                winConditionLeft.setEnabled(false);
                winImageContainer.setImageResource(R.drawable.three_win_condition);
                break;
            case 4:
                winConditionLeft.setEnabled(true);
                winConditionRight.setEnabled(true);
                winImageContainer.setImageResource(R.drawable.four_win_condition);
                break;
            case 5:
                winConditionRight.setEnabled(false);
                winImageContainer.setImageResource(R.drawable.five_win_condition);
                break;
        }
    }

    public void handleBoardSizeClick(int direction){
        int boardSize = gameData.getBoardSize();
        if(direction == -1) {
            System.out.println("The left button is clicked");
        }
        else {
            System.out.println("The right button is clicked");
        }

        boardSize += direction;
        gameData.setBoardSize(boardSize);

        switch (boardSize) {
            case 3:
                boardSizeLeft.setEnabled(false);
                boardImageContainer.setImageResource(R.drawable.three_size_grid);
                break;
            case 4:
                boardSizeLeft.setEnabled(true);
                boardSizeRight.setEnabled(true);
                boardImageContainer.setImageResource(R.drawable.four_size_grid
                );
                break;
            case 5:
                boardSizeRight.setEnabled(false);
                boardImageContainer.setImageResource(R.drawable.five_size_grid);
                break;
        }
    }
}