package com.example.myapplication

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.net.Uri
import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.module_play.PlayTestActivity
import com.example.myapplication.base.ClassBean
import com.example.myapplication.database.DataBaseActivity
import com.example.myapplication.databinding.ActivityMainBinding
import com.example.myapplication.life.LifeActivity
import com.example.myapplication.service.ServiceActivity

class MainActivity : AppCompatActivity() {

    private val binding by lazy {
        ActivityMainBinding.inflate(LayoutInflater.from(this), null, false)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)


        val list = arrayListOf(ClassBean().apply {
            aClass = LifeActivity::class.java
            classInfo = "生命周期"
        }, ClassBean().apply {
            aClass = PlayTestActivity::class.java
            classInfo = "组件间跳转"
        }, ClassBean().apply {
            aClass = DataBaseActivity::class.java
            classInfo = "dataBase"
        }, ClassBean().apply {
            aClass = ServiceActivity::class.java
            classInfo = "Service"
        })

        binding.recyclerview.layoutManager = LinearLayoutManager(this)
        val adapter = Adapter(this)
        binding.recyclerview.adapter = adapter
        adapter.setOnItemClickListener(object : IClickListener {
            override fun onItemClick(adapter: Adapter, holder: ViewHolder, position: Int) {
                val classBean = adapter.list[position]
                // Scheme 协议 路由跳转测试
                if (classBean.aClass == LifeActivity::class.java) {
                    val intent = Intent()
                    intent.setData(Uri.parse("ldphahaha://ldplpptest/android_guide?param1=参数111&param2=参数222"))
                    intent.setAction(Intent.ACTION_VIEW)
                    startActivity(intent)
                    return
                }
                startActivity(Intent(this@MainActivity, classBean.aClass))
            }
        })
        adapter.setList(list)
    }

    interface IClickListener {
        fun onItemClick(adapter: Adapter, holder: ViewHolder, position: Int)
    }

    class Adapter(val context: Context) : RecyclerView.Adapter<ViewHolder>() {

        private var listener: IClickListener? = null
        val list: ArrayList<ClassBean> = arrayListOf()

        fun setOnItemClickListener(listener: IClickListener?) {
            this.listener = listener
        }

        fun setList(data: ArrayList<ClassBean>?) {
            list.clear()
            list.addAll(data ?: emptyList())
            notifyDataSetChanged()
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
            val textView = TextView(context)
            val layoutParams = RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 200)
            textView.gravity = Gravity.CENTER
            textView.setTextColor(Color.BLACK)
            textView.layoutParams = layoutParams
            return ViewHolder(textView).apply {
                itemView.setOnClickListener {
                    listener?.onItemClick(this@Adapter, this, adapterPosition)
                }
            }
        }

        override fun onBindViewHolder(holder: ViewHolder, position: Int) {
            (holder.itemView as TextView).apply {
                text = list[position].classInfo
            }
        }

        override fun getItemCount(): Int {
            return list.size
        }

    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {}

}

