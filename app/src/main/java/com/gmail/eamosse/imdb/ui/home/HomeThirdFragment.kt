package com.gmail.eamosse.imdb.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.gmail.eamosse.imdb.databinding.FragmentHomeThirdBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.koin.androidx.viewmodel.ext.android.viewModel

class HomeThirdFragment : Fragment() {

    // import com.gmail.eamosse.imdb.databinding.FragmentHomeBinding
// import com.gmail.eamosse.imdb.databinding.FragmentHomeBindingImpl
    private val homeViewModel: HomeViewModel by viewModel()
    private val args: HomeThirdFragmentArgs by navArgs()
    private lateinit var binding: FragmentHomeThirdBinding
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
//        return inflater.inflate(R.layout.fragment_home_second, container, false)
        binding = FragmentHomeThirdBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        with(homeViewModel) {
            // récupérer les catégories
            getDetailMovie(args.idmovie.toInt())
            movieDetail.observe(
                viewLifecycleOwner,
                { itmovie ->
                    binding.tvMovieTitle.text = itmovie.title
                    binding.tvDescription.text = itmovie.overview
                    context?.let { it1 ->
                        Glide.with(it1)
                            .load("https://image.tmdb.org/t/p/w500" + itmovie.poster_path)
                            .into(binding.ivMoviePoster)
                    }
                    var _isChecked = false
                    CoroutineScope(Dispatchers.IO).launch {
                        val count = checkMovie(itmovie.id.toString())
                        withContext(Dispatchers.Main) {
                            if (count > 0) {
                                binding.toggleFavorite.isChecked = true
                                _isChecked = true
                            } else {
                                binding.toggleFavorite.isChecked = false
                                _isChecked = false
                            }
                        }
                    }
                    binding.toggleFavorite.setOnClickListener {
                        _isChecked = !_isChecked
                        if (_isChecked) {
                            addToFavorite(itmovie)
                        } else {
                            removeFromFavorite(itmovie.id.toString())
                        }
                        binding.toggleFavorite.isChecked = _isChecked
                    }
                }
            )
            error.observe(
                viewLifecycleOwner,
                {
                    // afficher l'erreur
                }
            )
        }

//        view.findViewById<TextView>(R.id.textview_home_second).text =
//            getString(R.string.hello_home_second, args.mytitle)
//        view.findViewById<Button>(R.id.button_home_second).setOnClickListener {
//            findNavController().navigate(R.id.action_HomeSecondFragment_to_HomeFragment)
//        }
    }
}
