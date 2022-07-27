package com.example.meteor

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.meteor.databinding.ActivityMainBinding
import kotlinx.coroutines.*
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.channels.consumeEach
import java.text.DecimalFormat

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        var binding = ActivityMainBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.activityMain)
        val channel = Channel<Int>()
        val backgroundScope = CoroutineScope(Dispatchers.Default + Job())

        backgroundScope.launch {
            var s = 1200
            var flag = 0

            binding.startB.setOnClickListener {
                s = 1200
                flag = 0
                binding.d1text.text = ""
                binding.d2text.text = ""
                binding.d3text.text = ""
                binding.d4text.text = ""
                binding.p1text.text = ""
                binding.p2text.text = ""
                binding.p3text.text = ""
                binding.p4text.text = ""
            }

            binding.destroyB.setOnClickListener {
                val df = DecimalFormat("00")

                var a = df.format(s/60%60)
                var b = df.format(s%60)

                var c = df.format((s-100)/60%60)
                var d = df.format((s-100)%60)

                var e = df.format((s-70)/60%60)
                var f = df.format((s-70)%60)

                if(flag==0) {
                    binding.d1text.text = "$a$b"
                    binding.p1text.text = "$c$d"
                    flag++
                }
                else if(flag==1) {
                    binding.d2text.text = "$a$b"
                    binding.p2text.text = "$e$f"
                    flag++
                }
                else if(flag==2) {
                    binding.d3text.text = "$a$b"
                    binding.p3text.text = "$c$d"
                    flag++
                }
                else if(flag==3) {
                    binding.d4text.text = "$a$b"
                    binding.p4text.text = "$c$d"
                    flag++
                }
            }

            for(i in 1..1200) {
                delay(1000)
                s--

                channel.send(s)
            }
        }

        val mainScope = GlobalScope.launch(Dispatchers.Main) {
            channel.consumeEach {
                val df = DecimalFormat("00")

                var m = df.format(it/60%60)
                var s = df.format(it%60)

                binding.resultView.text = "00:$m:$s"
            }
        }
    }
}