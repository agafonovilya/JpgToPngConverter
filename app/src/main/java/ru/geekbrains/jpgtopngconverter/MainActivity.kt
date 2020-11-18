package ru.geekbrains.jpgtopngconverter

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.activity_main.*
import moxy.MvpAppCompatActivity
import moxy.ktx.moxyPresenter
import ru.geekbrains.jpgtopngconverter.presenter.Presenter
import ru.geekbrains.jpgtopngconverter.view.MainView


const val TAG = "Converter"

class MainActivity : MvpAppCompatActivity(), MainView {

    private val REQUEST_PERMISSION_CODE = 44
    private val READ_REQUEST_CODE = 42

    val presenter: Presenter by moxyPresenter {Presenter()}

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        convert.setOnClickListener { presenter.clickOnConvert() }
        load.setOnClickListener { performFileSearch() }
    }


    override fun setImage(uri: Uri) {
        image_view.setImageURI(uri)
    }

    override fun showSnackbar(text: String) {
        Snackbar.make(root_view, text, Snackbar.LENGTH_SHORT).show()
    }

    override fun setTextOnConvertButton(text: String) {
        convert.text = text
    }

    private fun checkPermission() =
        ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED

    private fun requestPermission() {
        ActivityCompat.requestPermissions(this,
            arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE),
            REQUEST_PERMISSION_CODE)
    }

    private fun performFileSearch(){
        val intent = Intent(Intent.ACTION_GET_CONTENT).apply {
            type = "image/*"
        }
        startActivityForResult(Intent.createChooser(intent, "Select a file"), READ_REQUEST_CODE)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, resultData: Intent?) {
        super.onActivityResult(requestCode, resultCode, resultData)

        if (requestCode == READ_REQUEST_CODE && resultCode == RESULT_OK) {
            resultData?.let {
                val uri: Uri? = it.data
                uri?.let { presenter.imageRecieved(uri)}
            }
        }
    }


}