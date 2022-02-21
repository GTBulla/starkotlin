package br.com.gtbulla.features.repository_git.presenter.ui.adapter

import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import br.com.gtbulla.features.repository_git.databinding.RepositoryItemBinding
import br.com.gtbulla.features.repository_git.presenter.ui.viewholder.RepositoryItemViewHolder
import br.com.gtbulla.libraries.common.model.presentation.RepositoryGitItemUI
import br.com.gtbulla.libraries.uicore.extensions.getInflater

internal class RepositoryAdapter() :
    PagingDataAdapter<RepositoryGitItemUI, RepositoryItemViewHolder>(RepositoryDiffCallBack()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RepositoryItemViewHolder {
        val binding = RepositoryItemBinding.inflate(parent.getInflater(), parent, false)
        return RepositoryItemViewHolder(binding)
//        val holder = MoviePosterViewHolder(
//            ItemMoviePosterBinding.inflate(
//                LayoutInflater.from(parent.context), parent, false
//            )
//        )
//        holder.binding.root.setOnClickListener { view ->
//            getItem(holder.adapterPosition)?.let { movieUi ->
//                view.findNavController().navigate(
//                    MovieListFragmentDirections.actionGoToSheetDetail(
//                        movie = movieUi
//                    )
//                )
//            }
//        }
//        return holder
    }

    override fun onBindViewHolder(holder: RepositoryItemViewHolder, position: Int) {
        holder.bind(getItem(position)!!)
    }
}


//: RecyclerView.Adapter<RepositoryItemViewHolder>() {
//
//    private var _dataSource = emptyList<RepositoryGitItem>()
//        set(value) {
//            val result = DiffUtil.calculateDiff(
//                RepositoryListDiffCallback(
//                    field,
//                    value
//                )
//            )
//            result.dispatchUpdatesTo(this)
//            field = value
//        }
//
//    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RepositoryItemViewHolder {
//        val binding = RepositoryItemBinding.inflate(parent.getInflater(), parent, false)
//        return RepositoryItemViewHolder(binding)
//    }
//
//    override fun onBindViewHolder(holder: RepositoryItemViewHolder, position: Int) {
//        holder.bind(_dataSource[position])
//    }
//
//    override fun getItemCount(): Int =
//        _dataSource.size
//
//    fun setDataSource(dataSource: List<RepositoryGitItem>) {
//        _dataSource = dataSource
//        notifyDataSetChanged()
//    }
//
//}