package br.com.gtbulla.libraries.common.model.presentation

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class RepositoryGitItemUI(
    val id: Long,
    val name: String,
    val owner: RepositoryGitOwnerUI,
    val starCount: Int,
    val forkCount: Int,
) : Parcelable