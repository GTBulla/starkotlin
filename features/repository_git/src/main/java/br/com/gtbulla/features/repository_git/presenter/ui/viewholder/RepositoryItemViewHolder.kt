package br.com.gtbulla.features.repository_git.presenter.ui.viewholder

import androidx.recyclerview.widget.RecyclerView
import br.com.gtbulla.features.repository_git.R
import br.com.gtbulla.features.repository_git.databinding.RepositoryItemBinding
import br.com.gtbulla.libraries.common.model.presentation.RepositoryGitItemUI
import br.com.gtbulla.libraries.common.model.presentation.RepositoryGitOwnerUI
import com.squareup.picasso.NetworkPolicy
import com.squareup.picasso.Picasso

internal class RepositoryItemViewHolder internal constructor(
    private val binding: RepositoryItemBinding,
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(item: RepositoryGitItemUI) {
        bindFieldRepositoryName(item.name)
        bindFieldRepositoryForkCount(item.forkCount)
        bindFieldRepositoryStarCount(item.starCount)
        bindFieldsOwner(item.owner)
    }

    private fun bindFieldRepositoryName(repositoryName: String) {
        with(binding.textItemRepositoryName) {
            text = repositoryName
            contentDescription = repositoryName
            isSelected = true
        }
    }

    private fun bindFieldRepositoryForkCount(forkCount: Int) {
        with(binding.textItemRepositoryForkCount) {
            text = forkCount.toString()
            contentDescription = forkCount.toString()
        }
    }

    private fun bindFieldRepositoryStarCount(starCount: Int) {
        with(binding.textItemRepositoryStarCount) {
            text = starCount.toString()
            contentDescription = starCount.toString()
        }
    }

    private fun bindFieldsOwner(owner: RepositoryGitOwnerUI) {
        with(binding.textItemOwnerName) {
            text = owner.login
            contentDescription = owner.login
        }
        Picasso.get()
            .load(owner.avatarUrl)
            .error(R.drawable.ic_person)
            .networkPolicy(NetworkPolicy.OFFLINE)
            .into(binding.imageItemOwnerAvatar)
    }

}

