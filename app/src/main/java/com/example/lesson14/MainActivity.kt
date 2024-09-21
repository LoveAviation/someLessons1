package com.example.lesson14

import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.lesson14.adapter.FactAdapter
import com.example.lesson14.databinding.ActivityMainBinding
import io.reactivex.rxjava3.disposables.CompositeDisposable

class MainActivity : AppCompatActivity() {
    private val viewModel: MainVM by viewModels()
    private lateinit var binding: ActivityMainBinding

    private val disposable = CompositeDisposable()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.mainRV.layoutManager = LinearLayoutManager(this)

        viewModel.getFact()
            .subscribe({ facts ->
                binding.mainRV.adapter = FactAdapter(facts)
            }, { _ ->
                Log.e("MainActivity", "Error")
            }).let { disposable.add(it) }
    }

    override fun onDestroy() {
        super.onDestroy()
        disposable.clear()
    }
}