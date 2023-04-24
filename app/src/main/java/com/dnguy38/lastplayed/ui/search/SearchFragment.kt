package com.dnguy38.lastplayed.ui.search

import android.annotation.SuppressLint
import android.content.SharedPreferences
import android.graphics.drawable.Drawable
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.AdapterView.OnItemSelectedListener
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.res.ResourcesCompat
import androidx.lifecycle.MutableLiveData
import androidx.preference.PreferenceManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.dnguy38.lastplayed.R
import com.dnguy38.lastplayed.data.last_fm.responses.AlbumMatch
import com.dnguy38.lastplayed.data.last_fm.responses.TrackMatch
import com.dnguy38.lastplayed.data.search.SearchResult
import com.dnguy38.lastplayed.data.search.SearchType
import com.dnguy38.lastplayed.databinding.FragmentSearchBinding
import java.io.InputStream
import java.net.MalformedURLException
import java.net.URL

class SearchFragment : Fragment() {

    private lateinit var viewModel: SearchViewModel
    private lateinit var binding: FragmentSearchBinding
    private val preferences: SharedPreferences by lazy {
        PreferenceManager.getDefaultSharedPreferences(requireActivity())
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel = ViewModelProvider(this).get(SearchViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSearchBinding.inflate(layoutInflater)

        val spinner = binding.searchTypeSpinner
        val search = binding.searchView
        val searchResults = binding.searchResults

        searchResults.layoutManager = LinearLayoutManager(context)

        viewModel.searchResults.observe(viewLifecycleOwner) {
            searchResults.adapter = SearchResultsAdapter(it.list)
        }

        spinner.onItemSelectedListener = object : OnItemSelectedListener {
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                search(search.query.toString())
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {
            }
        }

        search.setOnQueryTextListener(object : android.widget.SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(s: String): Boolean {
                return true
            }

            override fun onQueryTextChange(s: String): Boolean {
                search(s)

                return true
            }
        })

        return binding.root
    }

    fun search(query: String) {
        val spinner = binding.searchTypeSpinner
        val limit = preferences.getString("limit", "30")!!.toInt()

        viewModel.search(query, limit, when (spinner.selectedItemPosition) {
            0 -> SearchType.Album
            1 -> SearchType.Artist
            2 -> SearchType.Track
            else -> throw IllegalStateException("Unexpected item position")
        })
    }

    private inner class SearchResultsViewHolder(val view: View) : RecyclerView.ViewHolder(view) {
        private val searchResultImage: ImageView = view.findViewById(R.id.search_result_imageView)
        private val searchTitleView: TextView = view.findViewById(R.id.search_result_title_textView)

        @SuppressLint("UseCompatLoadingForDrawables")
        fun bind(searchResult: SearchResult) {
            val image = searchResult.image.filter {
                it.size == "medium"
            }[0]

            val imageDrawable: MutableLiveData<Drawable> =
                MutableLiveData(
                    ResourcesCompat.getDrawable(
                        resources, when (searchResult.type) {
                            SearchType.Album, SearchType.Track -> R.drawable.ic_music_note
                            SearchType.Artist -> R.drawable.ic_action_user
                        }, null
                    )
                )

            imageDrawable.observe(viewLifecycleOwner) {
                searchResultImage.setImageDrawable(it)
            }

            val networkThread = Thread {
                try {
                    val imageStream = URL(image.url).content as InputStream
                    imageDrawable.postValue(Drawable.createFromStream(imageStream, null)!!)
                } catch (exception: MalformedURLException) {
                    exception.printStackTrace()
                }
            }

            networkThread.start()

            searchTitleView.text = searchResult.name

            when (searchResult.type) {
                SearchType.Album -> {
                    val albumMatch = searchResult as AlbumMatch
                    val searchTrackArtistView: TextView =
                        view.findViewById(R.id.search_result_artist_textView)
                    searchTrackArtistView.text = albumMatch.artist
                }
                SearchType.Artist -> {}
                SearchType.Track -> {
                    val trackMatch = searchResult as TrackMatch
                    val searchTrackArtistView: TextView =
                        view.findViewById(R.id.search_result_artist_textView)
                    searchTrackArtistView.text = trackMatch.artist
                }
            }
        }
    }

    private inner class SearchResultsAdapter(private val list: List<SearchResult>) :
        RecyclerView.Adapter<SearchResultsViewHolder>() {
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchResultsViewHolder {
            val view = when (viewType) {
                0, 2 -> layoutInflater.inflate(R.layout.album_and_track_search_item, parent, false)
                1 -> layoutInflater.inflate(R.layout.artist_search_item, parent, false)
                else -> throw IllegalStateException("Illegal view type")
            }

            return SearchResultsViewHolder(view)
        }

        override fun onBindViewHolder(holder: SearchResultsViewHolder, position: Int) {
            holder.bind(list[position])
        }

        override fun getItemCount() = list.size

        override fun getItemViewType(position: Int): Int {
            return when (list[position].type) {
                SearchType.Album -> 0
                SearchType.Artist -> 1
                SearchType.Track -> 2
            }
        }
    }
}