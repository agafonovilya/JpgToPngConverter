package ru.geekbrains.jpgtopngconverter.model

import android.net.Uri
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.observables.ConnectableObservable
import io.reactivex.rxjava3.schedulers.Schedulers

class Model {

    var uri: Uri? = null

    fun setImage(uri: Uri) {
        this.uri = uri
    }

    fun getConvertedImage(): Uri? {
        return uri
    }

    fun convert(): ConnectableObservable<Int> =
        Observable.create<Int>{ emitter ->
            for (i in 10 downTo 1) {
                Thread.sleep(1000)
                emitter.onNext(i)
            }

            //Здесь должна была быть конвертация

            emitter.onComplete()
        }.subscribeOn(Schedulers.newThread())
            .observeOn(AndroidSchedulers.mainThread())
            .publish()

}
