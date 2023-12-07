package com.capstone.warungpintar.di

import com.capstone.warungpintar.data.remote.api.ApiConfig
import com.capstone.warungpintar.data.remote.api.ApiNotificationService
import com.capstone.warungpintar.data.remote.api.ApiUserService
import com.capstone.warungpintar.data.repository.NotificationRepository
import com.capstone.warungpintar.data.repository.UserRepository

object Injection {
    fun provideUserRepository(): UserRepository {
        val apiUserService = ApiConfig.getApiConfig().create(ApiUserService::class.java)
        return UserRepository.getInstance(apiUserService)
    }

    fun provideNotificationRepository(): NotificationRepository {
        val apiNotificationService =
            ApiConfig.getApiConfig().create(ApiNotificationService::class.java)
        return NotificationRepository.getInstance(apiNotificationService)
    }
}