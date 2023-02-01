package com.vp.loyaltypartner.ui.main.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vp.loyaltypartner.data.model.APIResponse
import com.vp.loyaltypartner.data.repository.MainRepository
import com.vp.loyaltypartner.utils.NetworkHelper
import com.vp.loyaltypartner.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.cancelChildren
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Response
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val mainRepository: MainRepository,
    private val networkHelper: NetworkHelper
) : ViewModel() {

  private val _data = MutableLiveData<Resource<APIResponse>>()
  val data: LiveData<Resource<APIResponse>>
    get() = _data

  init {
    fetchData("fruits")
  }

   fun fetchData(queryString: String) {
    _data.postValue(Resource.loading(null))
    var response : Response<APIResponse>
   viewModelScope.launch {
      if(networkHelper.isNetworkConnected()) {
        withContext(Dispatchers.IO) {
          response = mainRepository.getApiResponse(queryString)
        }
        withContext(Dispatchers.Main) {
          if (response.isSuccessful) {
            _data.postValue(Resource.success(response.body()))
          } else _data.postValue(Resource.error(response.errorBody().toString(), null))
        }
      }else _data.postValue(Resource.error("No internet connection", null))
    }
  }

  fun resetSearch() {
    viewModelScope.coroutineContext.cancelChildren()
  }
}