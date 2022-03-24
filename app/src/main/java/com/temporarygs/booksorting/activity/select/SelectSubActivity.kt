package com.temporarygs.booksorting.activity.select

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import com.temporarygs.booksorting.R
import com.temporarygs.booksorting.activity.MainActivity
import com.temporarygs.booksorting.databinding.ActivitySelectSubBinding
import com.temporarygs.booksorting.viewmodel.SelectSubViewModel
import com.temporarygs.booksorting.viewmodel.SelectViewModel

class SelectSubActivity : AppCompatActivity() {
    private lateinit var binding : ActivitySelectSubBinding
    private lateinit var selectSubViewModel : SelectSubViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this,R.layout.activity_select_sub)
        selectSubViewModel = SelectSubViewModel
        binding.viewModel =  selectSubViewModel

        binding.selectInfoTextView.setText(selectSubViewModel.string.value)
        selectSubViewModel.string.observe(this, Observer {
            binding.selectInfoTextView.setText(it)
        })

        selectSubViewModel.move.observe(this, Observer {
            if(it) {
                val intent = Intent(this, MainActivity::class.java)
                intent.putExtra("type", selectSubViewModel.ty.toString())
                intent.putExtra("loc", selectSubViewModel.loc.toString())
                ContextCompat.startActivity(this, intent, null)
            }
        })

    }


    override fun onResume() {
        super.onResume()
        selectSubViewModel.move.value = false;
    }
}