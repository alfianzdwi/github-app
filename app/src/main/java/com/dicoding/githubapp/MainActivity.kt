package com.dicoding.githubapp

import android.app.SearchManager
import android.content.Context
import android.content.Intent
import android.content.res.TypedArray
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.dicoding.githubapp.databinding.ActivityMainBinding
import java.util.*
import kotlin.collections.ArrayList

class MainActivity : AppCompatActivity() {

    private lateinit var dataUsername: Array<String>
    private lateinit var dataName: Array<String>
    private lateinit var dataLocation: Array<String>
    private lateinit var dataRepository: Array<String>
    private lateinit var dataCompany: Array<String>
    private lateinit var dataFollowers: Array<String>
    private lateinit var dataFollowing: Array<String>
    private lateinit var dataAvatar: TypedArray
    private lateinit var binding: ActivityMainBinding
    private var userList: ArrayList<User> = arrayListOf()
    private var newUserList: ArrayList<User> = arrayListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.rvUsers.setHasFixedSize(true)
        prepare()
        addItem()
    }

    private fun prepare() {
        dataAvatar = resources.obtainTypedArray(R.array.avatar)
        dataUsername = resources.getStringArray(R.array.username)
        dataName = resources.getStringArray(R.array.name)
        dataLocation = resources.getStringArray(R.array.location)
        dataRepository = resources.getStringArray(R.array.repository)
        dataCompany = resources.getStringArray(R.array.company)
        dataFollowers = resources.getStringArray(R.array.followers)
        dataFollowing = resources.getStringArray(R.array.following)
    }

    private fun addItem() {
        for (position in dataName.indices) {
            val user = User(
                dataAvatar.getResourceId(position, -1),
                dataUsername[position],
                dataName[position],
                dataLocation[position],
                dataRepository[position],
                dataCompany[position],
                dataFollowers[position],
                dataFollowing[position],
            )
            userList.add(user)
        }
        binding.rvUsers.layoutManager = LinearLayoutManager(this)
        val myAdapter = ListAdapter(userList)
        binding.rvUsers.adapter = myAdapter

        myAdapter.setOnItemClickCallback(object : ListAdapter.OnItemClickCallback{
            override fun onItemClicked(data: User) {
                showSelectedUser(data)
            }
        })
    }

    private fun showSelectedUser(user: User) {
        val intent = Intent(this, DetailUserActivity::class.java)
        intent.putExtra(DetailUserActivity.EXTRA_USER, user)
        startActivity(intent)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.option_menu, menu)

        val searchManager = getSystemService(Context.SEARCH_SERVICE) as SearchManager
        val searchView = menu.findItem(R.id.search).actionView as SearchView // Untuk mengambil komponen searchview yang sebelumnya sudah di inflate

        searchView.setSearchableInfo(searchManager.getSearchableInfo(componentName))
        searchView.queryHint = resources.getString(R.string.search_hint) // SetQueryHint() berguna untuk memberikan hint pada pengguna tentang query search apa yang telah dimasukkan
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener{

            //Method ini ketika search selesai atau OK,Metode onQueryTextSubmit() akan dipanggil saat Submit ditekan
            override fun onQueryTextSubmit(query: String?): Boolean {
                Toast.makeText(this@MainActivity, query, Toast.LENGTH_SHORT).show()
                return true
            }

            //Method ini untuk merespon tiap perubahan huruf pada searchView,onQueryTextChange() akan dipanggil setiap kali user memasukkan atau mengubah query yang ada pada inputan searchview.
            override fun onQueryTextChange(newText: String?): Boolean {
                userList.clear()
                val searchText = newText!!.toLowerCase(Locale.getDefault())

                if(searchText.isNotEmpty()){
                    newUserList.forEach {
                        if(it.username.toLowerCase(Locale.getDefault()).contains(searchText)){
                            userList.add(it)
                        }
                    }
                    binding.rvUsers.adapter!!.notifyDataSetChanged()
                }else{
                    userList.clear()
                    userList.addAll(newUserList)
                    binding.rvUsers.adapter!!.notifyDataSetChanged()
                }
                return false
            }
        })
        return true
    }

    private fun getUsers(username: String){

    }

}
