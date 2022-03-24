package com.temporarygs.booksorting.activity

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.VibrationEffect
import android.os.Vibrator
import android.widget.Toast
import androidx.room.Room
import com.google.zxing.integration.android.IntentIntegrator
import com.google.zxing.integration.android.IntentResult
import com.temporarygs.booksorting.R
import com.temporarygs.booksorting.activity.ScanActivity
import com.temporarygs.booksorting.model.data.Info
import com.temporarygs.booksorting.model.db.InfoDb
import com.temporarygs.booksorting.viewmodel.ScanViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class MainActivity : AppCompatActivity() {
    private lateinit var scanViewModel : ScanViewModel//뷰모델
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        scanViewModel = ScanViewModel//뷰모델 객체 받아옴
        ScanViewModel.string = getString(R.string.KR_init_scan)//기본 메시지 초기화
        var temp = getIntent().getStringExtra("col")
        if(temp!=null){
            scanViewModel.col  = temp.toInt()
        }else{
            scanViewModel.col = 1
        }
        temp = getIntent().getStringExtra("row")
        if(temp!=null){
            scanViewModel.row  = temp.toInt()
        }else{
            scanViewModel.row = 1
        }
        temp = getIntent().getStringExtra("loc")
        if(temp!=null){
            scanViewModel.loc  = temp.toInt()
        }else{
            scanViewModel.loc = 1
        }
        temp = getIntent().getStringExtra("type")//putExtra로 넘어온 현재 라인
        if (temp != null) {
            scanViewModel.type = temp.toInt() // 뷰모델에 저장
        }
    }
    override fun onResume() {
        super.onResume()
        val integrator = IntentIntegrator(this)
        integrator.captureActivity = ScanActivity::class.java
        integrator.initiateScan()//scan 액티비티 실행
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        val result : IntentResult = IntentIntegrator.parseActivityResult(requestCode, resultCode, data)
        if (result != null){
            if(result.contents == null) {//결과가 빈값임
                Toast.makeText(this, getString(R.string.KR_end_scan), Toast.LENGTH_SHORT).show()
                finish()
            }else {

                if (ScanViewModel.string == getString(R.string.KR_init_scan)) {//아직 기준이 없다면
                    ScanViewModel.string = result.contents    //기준 점으로 삼음
                } else {
                    if (sortingCheck(result.contents)) {//배열 체크
                        ScanViewModel.string = result.contents
                        Toast.makeText(this, result.contents, Toast.LENGTH_SHORT).show()
                        val infoDb = InfoDb.getInstance(this)//db 싱글톤 얻어옴
                        CoroutineScope(Dispatchers.IO).launch {//IO쓰레드 시작 -> Main에서 db처리 불가
                            var info = Info(//인포 객체 생성
                                result.contents,
                                scanViewModel.loc,
                                scanViewModel.type,
                                scanViewModel.row,
                                scanViewModel.col,
                                LocalDateTime.now().format(DateTimeFormatter.ISO_DATE)
                            )
                            infoDb?.InfoDao()?.insert(info)//인포 객체 삽입
                        }
                    } else {//배열 오류
                        var vibrator: Vibrator
                        vibrator = getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
                        vibrator.vibrate(VibrationEffect.createOneShot(1000, 100))
                        Toast.makeText(this, getString(R.string.KR_err_scan), Toast.LENGTH_LONG)
                            .show()
                        ScanViewModel.string = getString(R.string.KR_init_scan)//기본 메시지 초기화

                    }
                }

            }
        }else{
            super.onActivityResult(requestCode, resultCode, data)
        }
    }

    fun sortingCheck(string : String) : Boolean {// 정렬 체크
        var nowScan = string.split(" ")
        var befoScan = scanViewModel.string.split(" ");
        var nowIndex = 0
        var befoIndex = 0

        if(befoScan[befoIndex]=="R"){
            if(nowScan[nowIndex]!="R"){
                return false;
            }else{
                nowIndex++
                befoIndex++
                if(nowScan.size - nowIndex >= befoScan.size - befoIndex){
                    for (i in befoIndex until befoScan.size) {
                        if (nowScan[i].compareTo(befoScan[i]) < 0) {
                            return false
                        } else if (nowScan[i].compareTo(befoScan[i]) > 0) {
                            return true
                        }
                    }
                    return true;
                }
                else if(nowScan.size - nowIndex < befoScan.size - befoIndex){
                    for (i in nowIndex until nowScan.size) {
                        if (nowScan[i].compareTo(befoScan[i]) < 0) {
                            return false
                        } else if (nowScan[i].compareTo(befoScan[i]) > 0) {
                            return true
                        }
                    }
                    return false;
                }

            }
        }
        else if(befoScan[befoIndex]=="L"){
            if(nowScan[nowIndex]!="L"){
                return false;
            }else{
                nowIndex++
                befoIndex++
                if(nowScan.size - nowIndex >= befoScan.size - befoIndex){
                    for (i in befoIndex until befoScan.size) {
                        if (nowScan[i].compareTo(befoScan[i]) < 0) {
                            return false
                        } else if (nowScan[i].compareTo(befoScan[i]) > 0) {
                            return true
                        }
                    }
                    return true;
                }
                else if(nowScan.size - nowIndex < befoScan.size - befoIndex){
                    for (i in nowIndex until nowScan.size) {
                        if (nowScan[i].compareTo(befoScan[i]) < 0) {
                            return false
                        } else if (nowScan[i].compareTo(befoScan[i]) > 0) {
                            return true
                        }
                    }
                    return false;
                }

            }
        }else{
            if(befoScan[befoIndex]=="RT"){
                befoIndex++
            }

            if(nowScan[nowIndex]=="RT"){
                befoIndex++
            }
            if(nowScan.size - nowIndex >= befoScan.size - befoIndex){
                for (i in befoIndex until befoScan.size) {
                    if (nowScan[i].compareTo(befoScan[i]) < 0) {
                        return false
                    } else if (nowScan[i].compareTo(befoScan[i]) > 0) {
                        return true
                    }
                }
                return true;
            }
            else if(nowScan.size - nowIndex < befoScan.size - befoIndex){
                for (i in nowIndex until nowScan.size) {
                    if (nowScan[i].compareTo(befoScan[i]) < 0) {
                        return false
                    } else if (nowScan[i].compareTo(befoScan[i]) > 0) {
                        return true
                    }
                }
                return false;
            }


        }

        return false

    }
}