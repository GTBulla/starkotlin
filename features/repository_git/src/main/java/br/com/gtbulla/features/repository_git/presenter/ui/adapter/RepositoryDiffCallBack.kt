package br.com.gtbulla.features.repository_git.presenter.ui.adapter

import androidx.recyclerview.widget.DiffUtil
import br.com.gtbulla.libraries.common.model.presentation.RepositoryGitItemUI

internal class RepositoryDiffCallBack : DiffUtil.ItemCallback<RepositoryGitItemUI>() {
    override fun areItemsTheSame(
        oldItem: RepositoryGitItemUI,
        newItem: RepositoryGitItemUI
    ): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(
        oldItem: RepositoryGitItemUI,
        newItem: RepositoryGitItemUI
    ): Boolean {
        return oldItem == newItem
    }
}