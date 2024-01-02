package com.example.fitlifein30days

import com.example.fitlifein30days.comment.CommentRepository
import com.example.fitlifein30days.comment.CommentViewModel
import android.content.Context
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.room.Room
import com.example.fitlifein30days.appdb.AppDatabase
import com.example.fitlifein30days.screen.AgeGenderScreen
import com.example.fitlifein30days.screen.DayDetailScreen
import com.example.fitlifein30days.screen.HeightScreen
import com.example.fitlifein30days.screen.MainScreen
import com.example.fitlifein30days.screen.NameSurnameScreen
import com.example.fitlifein30days.screen.WeightScreen
import com.example.fitlifein30days.screen.WorkoutFlowScreen
import com.example.fitlifein30days.screen.WorkoutsResultScreen
import com.example.fitlifein30days.ui.theme.FitLifeIn30DaysTheme
import com.example.fitlifein30days.user.UserViewModel
import com.example.fitlifein30days.workout.WorkoutViewModel

class MainActivity : ComponentActivity() {
    private val userViewModel = UserViewModel()
    private var isUserValid by mutableStateOf(false)
    private lateinit var workoutViewModel: WorkoutViewModel
    private lateinit var commentViewModel: CommentViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val db = Room.databaseBuilder(this, AppDatabase::class.java,
            "CommentDB").allowMainThreadQueries().build()
        val commentRepository = CommentRepository(db.commentDao())
        commentViewModel = CommentViewModel(commentRepository)
        workoutViewModel = WorkoutViewModel(this)
        setContent {
            FitLifeIn30DaysTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    isUserValid = userViewModel.loadData(this)
                    NavigateStartApp(userViewModel = userViewModel, commentViewModel= commentViewModel,isUserValid = isUserValid,this)
                }
            }
        }
    }


    @Composable
    fun NavigateStartApp(userViewModel: UserViewModel, commentViewModel: CommentViewModel, isUserValid: Boolean, context: Context) {
        val navController = rememberNavController()
        var startDestination ="registerScreen"

        if(isUserValid){
            startDestination = "mainScreen"
        }

        NavHost(navController = navController, startDestination = startDestination) {
            composable("registerScreen") { NameSurnameScreen(navController, userViewModel) }
            composable("ageGenderScreen") { AgeGenderScreen(navController, userViewModel) }
            composable("heightScreen") { HeightScreen(navController, userViewModel) }
            composable("weightScreen") { WeightScreen(navController, userViewModel,context) }
            composable("mainScreen") { MainScreen(userViewModel, commentViewModel,navController,context) }
            composable("dayDetail/{day}/{completed}") { backStackEntry ->
                DayDetailScreen(
                    day = backStackEntry.arguments?.getString("day")?.toInt() ?: 1,
                    onBack = { navController.navigate("mainScreen") },
                    navController = navController,
                    completed = backStackEntry.arguments?.getString("completed")?.toBoolean() ?: false,
                    commentViewModel= commentViewModel
                )
                }
            composable("workoutFlow/{day}") { backStackEntry ->
                WorkoutFlowScreen(
                    day = backStackEntry.arguments?.getString("day")?.toInt() ?: 1,
                    navController,
                    userViewModel,
                    workoutViewModel
                )
            }
            composable("workoutResult/{day}") { backStackEntry ->
                WorkoutsResultScreen(
                    day = backStackEntry.arguments?.getString("day")?.toInt() ?: 1,
                    navController,
                    userViewModel,
                    context
                )
            }
        }

    }

    @Preview
    @Composable
    fun Preview() {
        val userViewModel = UserViewModel()
        NavigateStartApp(userViewModel = userViewModel,commentViewModel, isUserValid = true,this)
    }
}