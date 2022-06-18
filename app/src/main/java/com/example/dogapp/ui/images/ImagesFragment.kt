package com.example.dogapp.ui.images

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.dogapp.DogImage
import com.example.dogapp.databinding.FragmentImagesBinding
import com.example.dogapp.ui.favorites.FavoritesViewModel


class ImagesFragment : Fragment() {
    private var _binding: FragmentImagesBinding? = null

    private val binding get() = _binding!!
    private val dogImagesViewModel: DogImagesViewModel by activityViewModels()
    private val favoritesViewModel: FavoritesViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentImagesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val imagesFragmentArgs: ImagesFragmentArgs by navArgs()
        val item = imagesFragmentArgs.item
        if (item == null) {
            binding.textviewMessage.text = "No such item!"
            return
        }

        (requireActivity() as AppCompatActivity).supportActionBar?.title = "$item Images"

        dogImagesViewModel.itemsLiveData.observe(viewLifecycleOwner) { items ->
            binding.recyclerView.visibility = if (items == null) View.GONE else View.VISIBLE
            if (items != null) {
                val adapter = ImageAdapter(items) { position ->
                    val itemImg: String? = dogImagesViewModel[position]
                    val fav = DogImage(item, itemImg,true)
                    if (!favoritesViewModel.tempList.contains(fav)) {
                        favoritesViewModel.tempList.add(fav)
                        favoritesViewModel.favoritesData.value = favoritesViewModel.tempList
                    } else {
                        favoritesViewModel.tempList.remove(fav)
                        favoritesViewModel.favoritesData.value = favoritesViewModel.tempList
                    }
                }
                binding.recyclerView.layoutManager = LinearLayoutManager(activity)
                binding.recyclerView.adapter = adapter
            }
        }

        dogImagesViewModel.errorMessageLiveData.observe(viewLifecycleOwner) { errorMessage ->
            binding.textviewMessage.text = errorMessage
        }

        dogImagesViewModel.reload(item)

        binding.swiperefresh.setOnRefreshListener {
            dogImagesViewModel.reload(item)
            binding.swiperefresh.isRefreshing = false
        }

        binding.buttonBack.setOnClickListener {
            findNavController().popBackStack()
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}