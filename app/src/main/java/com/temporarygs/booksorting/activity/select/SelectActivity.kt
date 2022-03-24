package com.temporarygs.booksorting.activity.select

import android.content.Intent
import android.database.DatabaseUtils
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import com.temporarygs.booksorting.R
import com.temporarygs.booksorting.activity.MainActivity
import com.temporarygs.booksorting.databinding.ActivitySelectBinding
import com.temporarygs.booksorting.viewmodel.ScanViewModel
import com.temporarygs.booksorting.viewmodel.SelectViewModel

class SelectActivity : AppCompatActivity() {
    private lateinit var binding : ActivitySelectBinding
    private lateinit var selectViewModel : SelectViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this,R.layout.activity_select)
        selectViewModel = SelectViewModel
        binding.viewModel =  selectViewModel

        binding.selectInfoTextView.setText(selectViewModel.string.value)
        selectViewModel.string.observe(this, Observer {
            binding.selectInfoTextView.setText(it)
        })

        selectViewModel.move.observe(this, Observer {
            if(it) {
                val intent = Intent(this, MainActivity::class.java)
                intent.putExtra("type", selectViewModel.ty.toString())
                intent.putExtra("loc", selectViewModel.loc.toString())
                ContextCompat.startActivity(this, intent, null)
            }
        })

    }


    override fun onResume() {
        super.onResume()
        selectViewModel.move.value = false;
    }
}