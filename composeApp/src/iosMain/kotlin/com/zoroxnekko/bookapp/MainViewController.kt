package com.zoroxnekko.bookapp

import androidx.compose.ui.window.ComposeUIViewController
import com.zoroxnekko.app.App
import com.zoroxnekko.bookapp.di.initKoin

fun MainViewController() = ComposeUIViewController(
    configure = {
        initKoin()
    }
) {
    App()
}