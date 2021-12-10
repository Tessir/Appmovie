package com.gmail.eamosse.imdb.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.gmail.eamosse.imdb.R
import com.gmail.eamosse.imdb.databinding.FragmentHomeSecondBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

// import com.gmail.eamosse.imdb.databinding.FragmentHomeBinding
// import com.gmail.eamosse.imdb.databinding.FragmentHomeBindingImpl

class HomeSecondFragment : Fragment() {
    private val homeViewModel: HomeViewModel by viewModel()
    private val args: HomeSecondFragmentArgs by navArgs()
    private lateinit var binding: FragmentHomeSecondBinding
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
//        return inflater.inflate(R.layout.fragment_home_second, container, false)
        binding = FragmentHomeSecondBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        with(homeViewModel) {
            token.observe(
                viewLifecycleOwner,
                Observer {
                    // récupérer les catégories
                    getMoviebyCategories(args.myArg)
                }
            )
            moviesbycategory.observe(
                viewLifecycleOwner,
                Observer {
                    binding.movieList.adapter = MovieAdapter(it)
                }
            )
            error.observe(
                viewLifecycleOwner,
                Observer {
                    // afficher l'erreur
                }
            )
        }
        view.findViewById<TextView>(R.id.textview_home_second).text =
            getString(R.string.hello_home_second, args.myArg)

        view.findViewById<Button>(R.id.button_home_second).setOnClickListener {
            findNavController().navigate(R.id.action_HomeSecondFragment_to_HomeFragment)
        }
    }
}
