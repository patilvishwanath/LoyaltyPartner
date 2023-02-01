package com.vp.loyaltypartner.ui.view

import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.vp.loyaltypartner.R
import com.vp.loyaltypartner.data.model.Hits
import com.vp.loyaltypartner.databinding.ActivityMainBinding
import com.vp.loyaltypartner.ui.viewmodel.MainViewModel
import com.vp.loyaltypartner.utils.DebouncingQueryTextListener
import com.vp.loyaltypartner.utils.Resource
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity(){

  private lateinit var binding: ActivityMainBinding
  private val mainViewModel : MainViewModel by viewModels()
  private lateinit var dataAdapter : DataAdapter

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    binding = ActivityMainBinding.inflate(layoutInflater)
    val view = binding.root
    setContentView(view)

    setUpView()
    setUpObserver()
    setUpSearchViewListener()

  }

  private fun setUpView() {
    binding.rvData.apply {
      layoutManager = LinearLayoutManager(this@MainActivity)
      dataAdapter = DataAdapter(arrayListOf(), DataAdapter.OnClickListener{clickedData->
        displayDialog(clickedData)
      })
      adapter = dataAdapter
    }
  }

  private fun displayDialog(clickedData: Hits) {
    val dialogClickListener =
      DialogInterface.OnClickListener { dialog, which ->
        when (which) {
          DialogInterface.BUTTON_POSITIVE -> {
            val intent = Intent(this, DisplayContentActivity::class.java)
            intent.putExtra(DisplayContentActivity.INTENT_HIT_OBJECT,clickedData)
            startActivity(intent)
          }

          DialogInterface.BUTTON_NEGATIVE -> {
            dialog.dismiss()
          }
        }
      }

    val builder: AlertDialog.Builder = AlertDialog.Builder(this)
    builder.setMessage(getString(R.string.dialog_message))
      .setPositiveButton(getString(R.string.yes), dialogClickListener)
      .setNegativeButton(getString(R.string.no), dialogClickListener)
      .show()
  }

  private fun setUpSearchViewListener() {
    binding.searchView.isIconified = false
    binding.searchView.setQuery("fruits",true)
    binding.searchView.clearFocus()
    binding.searchView.setOnQueryTextListener(DebouncingQueryTextListener(this@MainActivity) { newText ->
      newText?.let {
        if (it.isEmpty()) {
          mainViewModel.resetSearch()
        } else {
          mainViewModel.fetchData(it)
        }
      }
    })
  }

  private fun setUpObserver() {
    mainViewModel.data.observe(this) {apiResponse ->
      when(apiResponse.status){
        Resource.Status.SUCCESS -> {
          binding.progressBar.visibility = View.GONE
          binding.rvData.visibility = View.VISIBLE
          apiResponse.data?.let { hitData -> dataAdapter.updateList(hitData.hits) }
        }
        Resource.Status.ERROR -> {
          binding.progressBar.visibility = View.GONE
          Toast.makeText(this,apiResponse.message.toString(),Toast.LENGTH_LONG).show()

        }
        Resource.Status.LOADING -> {
          binding.progressBar.visibility = View.VISIBLE
          binding.rvData.visibility = View.GONE
        }
      }
    }
  }
}