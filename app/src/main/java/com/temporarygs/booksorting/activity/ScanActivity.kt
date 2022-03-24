package com.temporarygs.booksorting.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import com.google.zxing.integration.android.IntentIntegrator
import com.google.zxing.integration.android.IntentResult
import com.journeyapps.barcodescanner.CaptureManager
import com.journeyapps.barcodescanner.DecoratedBarcodeView
import com.temporarygs.booksorting.R
import com.temporarygs.booksorting.viewmodel.ScanViewModel
import com.temporarygs.booksorting.databinding.ActivityScanBinding

class ScanActivity : AppCompatActivity() {
    private lateinit var capture :CaptureManager
    private lateinit var scannerView: DecoratedBarcodeView
    private lateinit var scanViewModel : ScanViewModel
    private lateinit var stringMain : TextView
    private lateinit var stringSub : TextView
    private lateinit var binding : ActivityScanBinding
    private lateinit var toolbarTitle : TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_scan)//바인딩

        scanViewModel = ScanViewModel//뷰모델 객체 받아옴
        binding.viewModel = scanViewModel;//바인딩 변수에 뷰모델 등록
        stringMain = binding.ScanTextView2
        stringMain.setText(ScanViewModel.string) //뷰모델에 기록된 것으로 main 갱신
        stringSub = binding.ScanTextView1

        toolbarTitle = binding.toolbar.toolbarText//툴바 타이틀 변경
        toolbarTitle.setText(String.format("%03d",ScanViewModel.type))

       ScanViewModel.sub.observe(this, Observer {//이후 sub 변경은 livedata로 옵저빙
            binding.ScanTextView1.text = it//변경이 전달되면 main 변경
        })


        scannerView = findViewById(R.id.qr)
        capture = CaptureManager(this,scannerView)
        capture.initializeFromIntent(getIntent(),savedInstanceState);
        capture.decode()//스캐너 실행
    }

    override fun onResume() {
        super.onResume()
        stringSub.setText(String.format("%d번째 열 %d번째 책장 %d번째 줄",
            ScanViewModel.loc,
            ScanViewModel.col,
            ScanViewModel.row
        )) //1회 sub 갱신
        capture.onResume()
    }

    override fun onPause() {
        super.onPause()
        capture.onPause()
    }

    override fun onDestroy() {
        super.onDestroy()
        capture.onDestroy()
    }

    override fun onRequestPermissionsResult(//권한 얻기
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        capture.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }

}