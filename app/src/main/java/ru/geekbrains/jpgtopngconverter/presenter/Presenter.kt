package ru.geekbrains.jpgtopngconverter.presenter

import android.net.Uri
import android.util.Log
import io.reactivex.rxjava3.core.Observer
import io.reactivex.rxjava3.disposables.Disposable
import io.reactivex.rxjava3.observables.ConnectableObservable
import moxy.InjectViewState
import moxy.MvpPresenter
import ru.geekbrains.jpgtopngconverter.model.Model
import ru.geekbrains.jpgtopngconverter.view.MainView

val TAG = "Converter"

@InjectViewState
class Presenter: MvpPresenter<MainView>() {
    var disposable: Disposable? = null

    private val model = Model()

    fun imageRecieved(uri: Uri) {
        model.setImage(uri)
    }

    fun clickOnConvert(){

        val hotObservable: ConnectableObservable<Int> = model.convert()

        hotObservable.subscribe(object: Observer<Int> {

            override fun onSubscribe(d: Disposable?) {
                disposable = d
                Log.d(TAG, "onSubscribe: ")
            }

            override fun onNext(int: Int?) {
                viewState.setTextOnConvertButton(int.toString())
            }

            override fun onError(e: Throwable?) {
                Log.d(TAG, "onError: " + e?.message)
            }

            override fun onComplete() {
                model.getConvertedImage()?.let {
                    viewState.setImage(it)
                }
                viewState.setTextOnConvertButton("Convert")
            }
        }
        )

        hotObservable.connect()
    }


}