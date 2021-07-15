package com.example.wegotnext.ui.activities

import android.content.DialogInterface
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AlertDialog
import com.example.wegotnext.R
import com.example.wegotnext.ui.adapter.GamesToShowAdapter
import com.example.wegotnext.viewmodels.MainActivityViewModel
import kotlinx.android.synthetic.main.activity_main.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : AppCompatActivity() {

    private val viewModel by viewModel<MainActivityViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setupGameTypeSelection()

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_activity_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id = item.itemId

        when(id) {
            R.id.item_logout -> {
                showDialog()
            }
        }

        return super.onOptionsItemSelected(item)
    }

    private fun showDialog(){
        // Late initialize an alert dialog object
        lateinit var dialog: AlertDialog

        // Initialize a new instance of alert dialog builder object
        val builder = AlertDialog.Builder(this)

        // Set a title for alert dialog
        builder.setTitle("Are you sure you want to log out?")

        // On click listener for dialog buttons
        val dialogClickListener = DialogInterface.OnClickListener{ _, which ->
            when(which){
                DialogInterface.BUTTON_POSITIVE -> {
                    viewModel.userLogout()
                    startActivity(Intent(this@MainActivity, LoginActivity::class.java))
                    finish()
                }
                DialogInterface.BUTTON_NEGATIVE -> {

                }

            }
        }
        // Set the alert dialog negative/no button
        builder.setNegativeButton("NO",dialogClickListener)
        // Set the alert dialog positive/yes button
        builder.setPositiveButton("YES",dialogClickListener)

        // Initialize the AlertDialog using builder object
        dialog = builder.create()

        // Finally, display the alert dialog
        dialog.show()
    }

    private fun setupGameTypeSelection() {
        viewPager.adapter =
            GamesToShowAdapter(
                supportFragmentManager
            )
        tabLayout.setupWithViewPager(viewPager)
    }
}