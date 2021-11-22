package com.gmail.eamosse.imdb.ui.home

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.NavHostFragment
import com.gmail.eamosse.imdb.databinding.FragmentHomeBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

class HomeFragment : Fragment() {

    private val homeViewModel: HomeViewModel by viewModel()
    private lateinit var binding: FragmentHomeBinding

    @SuppressLint("SetTextI18n")
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
//        val root = inflater.inflate(R.layout.fragment_home, container, false)
        binding = FragmentHomeBinding.inflate(inflater, container, false)

//        val textView: TextView = root.findViewById(R.id.text_home)
//        val textView = binding.textHome
//        homeViewModel.token.observe(
//            viewLifecycleOwner,
//            Observer {
//                textView.text = "${it.requestToken} - ${it.expiresAt}"
//            }
//
//        )
//        homeViewModel.error.observe(
//            viewLifecycleOwner,
//            Observer {
//                textView.text = "Erreur $it"
//            }
//        )
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        with(homeViewModel) {
            token.observe(
                viewLifecycleOwner,
                Observer {
                    // récupérer les catégories
                    getCategories()
                }
            )

            categories.observe(
                viewLifecycleOwner,
                Observer {
                    binding.categoryList.adapter = CategoryAdapter(it)
                }
            )
            error.observe(
                viewLifecycleOwner,
                Observer {
                    // afficher l'erreur
                }
            )
        }
    }
}
