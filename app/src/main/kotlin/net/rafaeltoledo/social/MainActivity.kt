package net.rafaeltoledo.social

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.TextView
import org.koin.android.architecture.ext.viewModel

class MainActivity : AppCompatActivity() {

    private val mainViewModel: MainViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(TextView(this).apply {
            id = R.id.content
            text = mainViewModel.getString()
        })
    }
}