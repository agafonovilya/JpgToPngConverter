package ru.geekbrains.jpgtopngconverter.view

import android.net.Uri
import moxy.MvpView
import moxy.viewstate.strategy.AddToEndSingleStrategy
import moxy.viewstate.strategy.SkipStrategy
import moxy.viewstate.strategy.StateStrategyType

@StateStrategyType(AddToEndSingleStrategy::class)
interface MainView: MvpView {

    fun setImage(uri: Uri)

    @StateStrategyType(SkipStrategy::class)
    fun showSnackbar(text: String)

    fun setTextOnConvertButton(text: String)

}