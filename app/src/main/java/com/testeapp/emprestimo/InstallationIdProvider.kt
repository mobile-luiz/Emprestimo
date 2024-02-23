package com.testeapp.emprestimo

import android.content.Context
import java.util.UUID


object InstallationIdProvider {
    private const val PREFS_NAME = "InstallationPrefs"
    private const val INSTALLATION_ID = "InstallationId"
    fun getInstallationId(context: Context): String? {
        val preferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        var installationId = preferences.getString(INSTALLATION_ID, null)
        if (installationId == null) {
            installationId = generateInstallationId()
            preferences.edit().putString(INSTALLATION_ID, installationId).apply()
        }
        val installationId1 = installationId
        return installationId
    }

    private fun generateInstallationId(): String {
        return UUID.randomUUID().toString()
    }
}

