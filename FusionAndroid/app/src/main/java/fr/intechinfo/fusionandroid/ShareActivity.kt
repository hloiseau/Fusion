package fr.intechinfo.fusionandroid

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Toast
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import java.io.File


class ShareActivity : Activity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val uri = intent.getParcelableExtra<Uri>(Intent.EXTRA_STREAM)
        val file = File(uri.path)
        val filePart = MultipartBody.Part.createFormData("file", file.name, RequestBody.create(MediaType.parse("image/*"), file))
        HttpExecute(HttpExecute.BuildAPI().uploadFile(filePart)).start()
        finish()
        Toast.makeText(this, "Fichier envoyé !", Toast.LENGTH_SHORT).show()

    }


}