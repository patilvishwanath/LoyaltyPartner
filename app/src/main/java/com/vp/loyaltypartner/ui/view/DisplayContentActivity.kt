package com.vp.loyaltypartner.ui.view

import android.os.Build
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import coil.load
import com.vp.loyaltypartner.data.model.Hits
import com.vp.loyaltypartner.databinding.ActivityDisplayContentBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DisplayContentActivity : AppCompatActivity(){
  private lateinit var binding: ActivityDisplayContentBinding
  private var hit : Hits? = null

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    binding = ActivityDisplayContentBinding.inflate(layoutInflater)
    val view = binding.root
    setContentView(view)

    setUpView()
  }

  private fun setUpView() {

    if(intent.hasExtra(INTENT_HIT_OBJECT)){
     hit =  if (Build.VERSION.SDK_INT >= 33) {
        intent.getParcelableExtra(INTENT_HIT_OBJECT,Hits::class.java)
      } else {
        intent.getParcelableExtra<Hits>(INTENT_HIT_OBJECT)
      }
    }

    hit?.let {response->
      binding.ivLargeImage.load(response.largeImageURL)
      binding.tvUserName.text = response.user
      binding.tvUserTags.text = response.tags
      binding.tvLikes.text = response.likes.toString()
      binding.tvComments.text = response.comments.toString()
      binding.tvDownload.text = response.downloads.toString()
    }

  }

  companion object{
    const val INTENT_HIT_OBJECT = "intent_hit_object"
  }

}
