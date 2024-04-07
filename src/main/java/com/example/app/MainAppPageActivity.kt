package com.example.app

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.activity.ComponentActivity
import android.content.Intent
import android.content.SharedPreferences
import android.graphics.Color
import android.widget.Button
import android.widget.CheckBox
import android.widget.LinearLayout
import android.widget.RelativeLayout
import android.widget.Toast

class MainAppPageActivity : ComponentActivity() {

    /**
     * Файл с натсройками приложения
     */
    private lateinit var settings: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_app_page)
        settings = getSharedPreferences(getString(R.string.name_sp_settings), Context.MODE_PRIVATE)
        setColorTheme()
    }


    /**
     * Функция получения цветовой темы приложения из SharedPreferences
     * @return lightThemeFlag
     */
    private fun getColorTheme() : Boolean {
        val lightThemeFlag: Boolean = settings.getBoolean("ColorTheme", true)
        Toast.makeText(applicationContext, "Light theme, $lightThemeFlag", Toast.LENGTH_LONG).show()
        return lightThemeFlag
    }

    /**
     * Функция установки цветовой темы, исходя из установленных настроек
     */
    private fun setColorTheme() {
        val lightAppTheme: Boolean = getColorTheme()
        val mainLayout = findViewById<RelativeLayout>(R.id.main_layout)
        if(lightAppTheme) {
            mainLayout.setBackgroundColor(Color.WHITE)
        } else {
            mainLayout.setBackgroundColor(Color.GRAY)
        }
    }

    /**
     * Обработка нажатия кнопки для перехода на страницу настроек
     */
    fun onSettingsButtonClicked(view: View) {
        val intent = Intent(this@MainAppPageActivity, SettingsActivity::class.java)
        startActivity(intent)
        finish()
    }

    /**
     * Обработка нажатия на кнокпу для просмотра отчёта
     */
    fun onReportButtonClicked(view: View) {
        val intent = Intent(this@MainAppPageActivity, ReportActivity::class.java)
        startActivity(intent)
    }

    /**
     * Обработка нажатия на кнокпу для просмотра отчёта
     */
    fun onPlanButtonClicked(view: View) {
        val intent = Intent(this@MainAppPageActivity, PlanActivity::class.java)
        startActivity(intent)
    }
}