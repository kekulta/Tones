package com.kekulta.tones.features.main.presentation.ui

import android.os.Bundle
import androidx.activity.addCallback
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.createGraph
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.fragment
import com.kekulta.tones.R

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setupNavigation()
    }

    private fun setupNavigation() {
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.rootNavFragment) as NavHostFragment
        val navController = navHostFragment.navController


        navController.graph = navController.createGraph(
            startDestination = "menu"
        ) {
            fragment<MenuFragment>("menu") {
                label = "Menu"
            }

            fragment<QuizFragment>("quiz") {
                label = "Quiz"
            }

            fragment<SettingsFragment>("settings") {
                label = "Settings"
            }
        }

        onBackPressedDispatcher.addCallback { navController.popBackStack() }
    }
}

