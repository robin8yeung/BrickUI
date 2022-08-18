package com.seewo.brick.live.params

import androidx.lifecycle.LiveData

class StaticData<T>(data: T?): LiveData<T>(data)