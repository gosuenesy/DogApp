package com.example.dogapp.ui.favorites

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.dogapp.ui.home.DogViewModel
import com.example.dogapp.databinding.FragmentFavoritesBinding

class FavoritesFragment : Fragment() {

    private var _binding: FragmentFavoritesBinding? = null

    private val binding get() = _binding!!
    private val favoritesViewModel: FavoritesViewModel by activityViewModels()
    private val dogViewModel: DogViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFavoritesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        (requireActivity() as AppCompatActivity).supportActionBar?.title = "Favorites"

        val options = dogViewModel.itemsLiveData.value
        if (!options!!.contains("No filter")) {
            options.add(0, "No filter")
        }

        binding.spinner.adapter = ArrayAdapter<String>(requireContext(), android.R.layout.simple_list_item_1, options!!)

        binding.spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                //binding.spinnerResult.text = "Filter: ${options?.get(p2)}"
                favoritesViewModel.filterBreedName(options.get(p2))
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {
                //binding.spinnerResult.text = "Filter: Select option"
            }

        }

        favoritesViewModel.sortedData.observe(viewLifecycleOwner) { items ->
            binding.recyclerView.visibility = if (items == null) View.GONE else View.VISIBLE
            if (items != null) {
                val adapter = FavoriteAdapter(items) { position ->
                    //val itemImg: DogImage? = favoritesViewModel[position]
                }
                binding.recyclerView.layoutManager = LinearLayoutManager(activity)
                binding.recyclerView.adapter = adapter
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}