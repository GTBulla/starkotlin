package br.com.gtbulla.features.repository_git.presenter.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.paging.LoadState
import androidx.recyclerview.widget.RecyclerView
import br.com.gtbulla.features.repository_git.R
import br.com.gtbulla.features.repository_git.databinding.RepositoryItemLoadStateBinding

internal class RepositoryLoadStateViewHolder(
    private val binding: RepositoryItemLoadStateBinding,
    retry: () -> Unit
) : RecyclerView.ViewHolder(binding.root) {

    init {
        binding.buttonRetry.setOnClickListener { retry.invoke() }
    }

    fun bind(loadState: LoadState) {
        if (loadState is LoadState.Error) {
            binding.textErrorDescription.text = loadState.error.localizedMessage
        }
        binding.progressLoadMore.isVisible = loadState is LoadState.Loading
        binding.buttonRetry.isVisible = loadState is LoadState.Error
        binding.textErrorDescription.isVisible = loadState is LoadState.Error
    }

    companion object {
        fun create(parent: ViewGroup, retry: () -> Unit): RepositoryLoadStateViewHolder {
            val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.repository_item_load_state, parent, false)
            val binding = RepositoryItemLoadStateBinding.bind(view)
            return RepositoryLoadStateViewHolder(binding, retry)
        }
    }
}